package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

import culturarte.logica.controlador.IUsuarioController;
import culturarte.logica.DT.DTColaborador;
import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTUsuario;

public class AltaUsuarioInternalFrame extends JInternalFrame {

    private JTextField tfNickname, tfNombre, tfApellido, tfCorreo, tfFechaNacimiento, tfImagen;
    private JTextField tfDireccion, tfBiografia, tfSitioWeb;
    private JLabel lblDireccion, lblBiografia, lblSitioWeb;
    private JComboBox<String> cbTipoUsuario;

    private final IUsuarioController usuarioController;

    public AltaUsuarioInternalFrame(IUsuarioController usuarioController) {
        super("Alta de Usuario", true, true, true, true);
        this.usuarioController = usuarioController;

        setSize(800, 500);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos básicos
        tfNickname = new JTextField();
        tfNombre = new JTextField();
        tfApellido = new JTextField();
        tfCorreo = new JTextField();
        tfFechaNacimiento = new JTextField();
        tfImagen = new JTextField();
        tfDireccion = new JTextField();
        tfBiografia = new JTextField();
        tfSitioWeb = new JTextField();

        lblDireccion = new JLabel("Dirección:");
        lblBiografia = new JLabel("Biografía:");
        lblSitioWeb = new JLabel("Sitio Web:");

        // Campos opcionales ocultos por defecto
        lblDireccion.setVisible(false);
        tfDireccion.setVisible(false);
        lblBiografia.setVisible(false);
        tfBiografia.setVisible(false);
        lblSitioWeb.setVisible(false);
        tfSitioWeb.setVisible(false);

        // Combo de tipo de usuario
        cbTipoUsuario = new JComboBox<>(new String[]{"Colaborador", "Proponente"});
        cbTipoUsuario.addActionListener(e -> actualizarCamposPorTipo());

        // Agregar campos al panel usando UIHelper
        Object[][] campos = {
                {"Nickname:", tfNickname},
                {"Nombre:", tfNombre},
                {"Apellido:", tfApellido},
                {"Correo:", tfCorreo},
                {"Fecha Nacimiento (yyyy-MM-dd):", tfFechaNacimiento},
                {"Tipo de Usuario:", cbTipoUsuario}
        };
        UIHelper.agregarCamposAlPanel(panel, campos);

        // Imagen
        panel.add(crearCampoImagen());

        // Campos opcionales
        panel.add(lblDireccion);
        panel.add(tfDireccion);
        panel.add(lblBiografia);
        panel.add(tfBiografia);
        panel.add(lblSitioWeb);
        panel.add(tfSitioWeb);

        add(panel, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dispose());
        btnAceptar.addActionListener(e -> procesarAltaUsuario());
    }

    private JPanel crearCampoImagen() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Imagen (Opcional):"), BorderLayout.WEST);
        tfImagen.setEditable(false);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());
        panel.add(tfImagen, BorderLayout.CENTER);
        panel.add(btnSeleccionarImagen, BorderLayout.EAST);
        return panel;
    }

    private void actualizarCamposPorTipo() {
        boolean esProponente = "Proponente".equals(cbTipoUsuario.getSelectedItem());
        lblDireccion.setVisible(esProponente);
        tfDireccion.setVisible(esProponente);
        lblBiografia.setVisible(esProponente);
        tfBiografia.setVisible(esProponente);
        lblSitioWeb.setVisible(esProponente);
        tfSitioWeb.setVisible(esProponente);
        revalidate();
        repaint();
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfImagen.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void procesarAltaUsuario() {
        try {
            String nickname = tfNickname.getText().trim();
            String nombre = tfNombre.getText().trim();
            String apellido = tfApellido.getText().trim();
            String correo = tfCorreo.getText().trim();
            String fechaTexto = tfFechaNacimiento.getText().trim();
            String tipo = (String) cbTipoUsuario.getSelectedItem();

            boolean esProponente = "Proponente".equals(tipo);
            String direccion = tfDireccion.getText().trim();

            if (!UIHelper.hayCamposVacios(nickname, nombre, apellido, correo, fechaTexto)
                    || (esProponente && direccion.isEmpty())) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos obligatorios deben completarse",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaNacimiento;
            try {
                fechaNacimiento = UIHelper.parseFecha(fechaTexto);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Formato de fecha incorrecto, use yyyy-MM-dd",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rutaFinal = procesarImagen(tfImagen.getText().trim());

            DTUsuario usuario = esProponente
                    ? new DTProponente(nickname, nombre, apellido, correo,
                    fechaNacimiento, rutaFinal, direccion,
                    tfBiografia.getText().trim(), tfSitioWeb.getText().trim())
                    : new DTColaborador(nickname, nombre, apellido, correo, fechaNacimiento, rutaFinal);

            usuarioController.crearUsuario(usuario);
            JOptionPane.showMessageDialog(this,
                    "Usuario creado correctamente",
                    "Alta de Usuario",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos
            UIHelper.limpiarCampos(tfNickname, tfNombre, tfApellido, tfCorreo, tfFechaNacimiento,
                    tfImagen, tfDireccion, tfBiografia, tfSitioWeb);
            cbTipoUsuario.setSelectedIndex(0);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear usuario: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String procesarImagen(String rutaSeleccionada) throws IOException {
        if (rutaSeleccionada.isEmpty()) return null;

        File carpeta = new File("uploads/usuarios/");
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta de uploads.");
        }

        String nombreArchivo = UUID.randomUUID() + "_" + new File(rutaSeleccionada).getName();
        File destino = new File(carpeta, nombreArchivo);

        Files.copy(new File(rutaSeleccionada).toPath(),
                destino.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        return destino.getAbsolutePath();
    }
}
