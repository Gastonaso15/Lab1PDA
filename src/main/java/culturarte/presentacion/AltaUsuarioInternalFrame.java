package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import culturarte.logica.DT.DTColaborador;
import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTUsuario;
import culturarte.logica.controlador.IUsuarioController;

public class AltaUsuarioInternalFrame extends JInternalFrame {

    private JTextField tfNickname;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfCorreo;
    private JTextField tfFechaNacimiento;
    private JTextField tfImagen;
    private JTextField tfDireccion;
    private JTextField tfBiografia;
    private JTextField tfSitioWeb;

    private JComboBox<String> cbTipoUsuario;

    private IUsuarioController UsuarioContr;

    public AltaUsuarioInternalFrame(IUsuarioController icu) {
        super("Alta de Usuario", true, true, true, true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Nickname:"));
        tfNickname = new JTextField();
        panel.add(tfNickname);

        panel.add(new JLabel("Nombre:"));
        tfNombre = new JTextField();
        panel.add(tfNombre);

        panel.add(new JLabel("Apellido:"));
        tfApellido = new JTextField();
        panel.add(tfApellido);

        panel.add(new JLabel("Correo:"));
        tfCorreo = new JTextField();
        panel.add(tfCorreo);

        panel.add(new JLabel("Fecha de Nacimiento (yyyy-MM-dd):"));
        tfFechaNacimiento = new JTextField();
        panel.add(tfFechaNacimiento);

        panel.add(new JLabel("Imagen:"));
        JPanel imagenPanel = new JPanel(new BorderLayout(5, 5));
        tfImagen = new JTextField();
        tfImagen.setEditable(false);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        imagenPanel.add(tfImagen, BorderLayout.CENTER);
        imagenPanel.add(btnSeleccionarImagen, BorderLayout.EAST);
        panel.add(imagenPanel);

        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                tfImagen.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        panel.add(new JLabel("Tipo Usuario:"));
        cbTipoUsuario = new JComboBox<>(new String[]{"Colaborador","Proponente"});
        panel.add(cbTipoUsuario);

        JLabel lblDireccion = new JLabel("Dirección:");
        tfDireccion = new JTextField();
        lblDireccion.setVisible(false);
        tfDireccion.setVisible(false);
        panel.add(lblDireccion);
        panel.add(tfDireccion);

        JLabel lblBiografia = new JLabel("Biografía:");
        tfBiografia = new JTextField();
        lblBiografia.setVisible(false);
        tfBiografia.setVisible(false);
        panel.add(lblBiografia);
        panel.add(tfBiografia);

        JLabel lblSitioWeb = new JLabel("Sitio Web:");
        tfSitioWeb = new JTextField();
        lblSitioWeb.setVisible(false);
        tfSitioWeb.setVisible(false);
        panel.add(lblSitioWeb);
        panel.add(tfSitioWeb);

        cbTipoUsuario.addActionListener(e -> {
            boolean esProponente = "Proponente".equals(cbTipoUsuario.getSelectedItem());

            lblDireccion.setVisible(esProponente);
            tfDireccion.setVisible(esProponente);
            lblBiografia.setVisible(esProponente);
            tfBiografia.setVisible(esProponente);
            lblSitioWeb.setVisible(esProponente);
            tfSitioWeb.setVisible(esProponente);

            panel.revalidate();
            panel.repaint();
        });

        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");
        botones.add(aceptar);
        botones.add(cancelar);
        add(botones, BorderLayout.SOUTH);

        cancelar.addActionListener(e -> dispose());

        aceptar.addActionListener(e -> {
            String nickname = tfNickname.getText().trim();
            String nombre = tfNombre.getText().trim();
            String apellido = tfApellido.getText().trim();
            String correo = tfCorreo.getText().trim();
            String fechaTexto = tfFechaNacimiento.getText().trim();
            String tipo = (String) cbTipoUsuario.getSelectedItem();
            byte[] imagenBytes = null;
            String rutaImagen = tfImagen.getText().trim();
            if (!rutaImagen.isEmpty()) {
                File file = new File(rutaImagen);
                try (FileInputStream fis = new FileInputStream(file)) {
                    imagenBytes = fis.readAllBytes();
                } catch (IOException ioEx) {
                    JOptionPane.showMessageDialog(this,
                            "Error al leer la imagen: " + ioEx.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            boolean esProp = "Proponente".equals(cbTipoUsuario.getSelectedItem());
            String direccion = tfDireccion.getText().trim();
            if (nickname.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || fechaTexto.isEmpty() || (esProp && direccion.isEmpty())) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos obligatorios deben completarse",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaNacimiento;
            try {
                fechaNacimiento = LocalDate.parse(fechaTexto);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Formato de fecha incorrecto, use yyyy-MM-dd",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            DTUsuario usuario;
            if ("Proponente".equals(tipo)) {
                String bio = tfBiografia.getText().trim();
                String sitioWeb = tfSitioWeb.getText().trim();
                usuario = new DTProponente(nickname, nombre, apellido, correo,fechaNacimiento,imagenBytes,direccion,bio,sitioWeb);
            } else {
                usuario = new DTColaborador(nickname, nombre, apellido, correo,fechaNacimiento,imagenBytes);
            }

            try {
                UsuarioContr.crearUsuario(usuario);
                JOptionPane.showMessageDialog(this,
                        "Usuario creado correctamente",
                        "Alta de Usuario",
                        JOptionPane.INFORMATION_MESSAGE);

                tfNickname.setText("");
                tfNombre.setText("");
                tfApellido.setText("");
                tfCorreo.setText("");
                tfFechaNacimiento.setText("");
                tfImagen.setText("");
                tfDireccion.setText("");
                tfBiografia.setText("");
                tfSitioWeb.setText("");
                cbTipoUsuario.setSelectedIndex(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear usuario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
