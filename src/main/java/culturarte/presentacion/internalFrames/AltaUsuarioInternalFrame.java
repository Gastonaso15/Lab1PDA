package culturarte.presentacion.internalFrames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

import culturarte.servicios.DTs.DTColaborador;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTUsuario;
import culturarte.servicios.interfaces.IUsuarioController;

public class AltaUsuarioInternalFrame extends JInternalFrame {

    private final JTextField tfNickname;
    private final JTextField tfNombre;
    private final JTextField tfApellido;
    private final JPasswordField tfContrasenia;
    private final JPasswordField tfConfirmacionContrasenia;
    private final JTextField tfCorreo;
    private final JTextField tfFechaNacimiento;
    private final JTextField tfImagen;
    private final JTextField tfDireccion;
    private final JTextArea tfBiografia;
    private final JTextField tfSitioWeb;
    private final JComboBox<String> cbTipoUsuario;
    private final IUsuarioController UsuarioContr;
    private JPanel panelDatosBasicos;
    private JPanel panelProponente;

    public AltaUsuarioInternalFrame(IUsuarioController icu) {
        super("Alta de Usuario", true, true, true, true);
        setSize(950, 750);
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        UsuarioContr = icu;

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));

        panelDatosBasicos = new JPanel();
        panelDatosBasicos.setLayout(new BoxLayout(panelDatosBasicos, BoxLayout.Y_AXIS));
        panelDatosBasicos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Datos Básicos",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelDatosBasicos.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(createLabel("Nickname:"), gbc);
        gbc.gridx = 1;
        tfNickname = new JTextField(20);
        estilizarTextField(tfNickname);
        gridPanel.add(tfNickname, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gridPanel.add(createLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        tfNombre = new JTextField(20);
        estilizarTextField(tfNombre);
        gridPanel.add(tfNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gridPanel.add(createLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        tfApellido = new JTextField(20);
        estilizarTextField(tfApellido);
        gridPanel.add(tfApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gridPanel.add(createLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        tfContrasenia = new JPasswordField(20);
        estilizarTextField(tfContrasenia);
        gridPanel.add(tfContrasenia, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gridPanel.add(createLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1;
        tfConfirmacionContrasenia = new JPasswordField(20);
        estilizarTextField(tfConfirmacionContrasenia);
        gridPanel.add(tfConfirmacionContrasenia, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gridPanel.add(createLabel("Correo:"), gbc);
        gbc.gridx = 1;
        tfCorreo = new JTextField(20);
        estilizarTextField(tfCorreo);
        gridPanel.add(tfCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gridPanel.add(createLabel("Fecha de Nacimiento:"), gbc);
        gbc.gridx = 1;
        JPanel fechaPanel = new JPanel(new BorderLayout(5, 0));
        fechaPanel.setBackground(Color.WHITE);
        tfFechaNacimiento = new JTextField(15);
        estilizarTextField(tfFechaNacimiento);
        fechaPanel.add(tfFechaNacimiento, BorderLayout.CENTER);
        JLabel hintLabel = new JLabel("(yyyy-mm-dd)");
        hintLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        hintLabel.setForeground(new Color(120, 120, 120));
        fechaPanel.add(hintLabel, BorderLayout.EAST);
        gridPanel.add(fechaPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gridPanel.add(createLabel("Imagen (Opcional):"), gbc);
        gbc.gridx = 1;
        JPanel imagenPanel = new JPanel(new BorderLayout(5, 5));
        imagenPanel.setBackground(Color.WHITE);
        tfImagen = new JTextField();
        tfImagen.setEditable(false);
        estilizarTextField(tfImagen);
        JButton btnSeleccionarImagen = crearBoton("Seleccionar", new Color(40, 50, 70), Color.WHITE);
        imagenPanel.add(tfImagen, BorderLayout.CENTER);
        imagenPanel.add(btnSeleccionarImagen, BorderLayout.EAST);
        gridPanel.add(imagenPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        gridPanel.add(createLabel("Tipo Usuario:"), gbc);
        gbc.gridx = 1;
        cbTipoUsuario = new JComboBox<>(new String[]{"Colaborador","Proponente"});
        cbTipoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbTipoUsuario.setBackground(Color.WHITE);
        gridPanel.add(cbTipoUsuario, gbc);

        panelDatosBasicos.add(gridPanel);

        panelProponente = new JPanel();
        panelProponente.setLayout(new BoxLayout(panelProponente, BoxLayout.Y_AXIS));
        panelProponente.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Datos de Proponente",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelProponente.setBackground(Color.WHITE);
        panelProponente.setVisible(false);

        JPanel gridProponente = new JPanel(new GridBagLayout());
        gridProponente.setBackground(Color.WHITE);
        GridBagConstraints gbcProp = new GridBagConstraints();
        gbcProp.insets = new Insets(8, 10, 8, 10);
        gbcProp.anchor = GridBagConstraints.WEST;

        gbcProp.gridx = 0; gbcProp.gridy = 0;
        gridProponente.add(createLabel("Dirección:"), gbcProp);
        gbcProp.gridx = 1;
        tfDireccion = new JTextField(20);
        estilizarTextField(tfDireccion);
        gridProponente.add(tfDireccion, gbcProp);

        gbcProp.gridx = 0; gbcProp.gridy = 1;
        gridProponente.add(createLabel("Biografía (Opcional):"), gbcProp);
        gbcProp.gridx = 1;
        tfBiografia = new JTextArea(3, 20);
        tfBiografia.setLineWrap(true);
        tfBiografia.setWrapStyleWord(true);
        estilizarTextField(tfBiografia);
        JScrollPane scrollBio = new JScrollPane(tfBiografia);
        scrollBio.setBorder(BorderFactory.createLoweredBevelBorder());
        gridProponente.add(scrollBio, gbcProp);

        gbcProp.gridx = 0; gbcProp.gridy = 2;
        gridProponente.add(createLabel("Sitio Web (Opcional):"), gbcProp);
        gbcProp.gridx = 1;
        tfSitioWeb = new JTextField(20);
        estilizarTextField(tfSitioWeb);
        gridProponente.add(tfSitioWeb, gbcProp);

        panelProponente.add(gridProponente);

        JPanel contenedorPaneles = new JPanel();
        contenedorPaneles.setLayout(new BoxLayout(contenedorPaneles, BoxLayout.Y_AXIS));
        contenedorPaneles.setBackground(new Color(245, 248, 250));
        contenedorPaneles.add(panelDatosBasicos);
        contenedorPaneles.add(Box.createVerticalStrut(15));
        contenedorPaneles.add(panelProponente);

        JScrollPane scrollPrincipal = new JScrollPane(contenedorPaneles);
        scrollPrincipal.setBorder(null);
        scrollPrincipal.getViewport().setBackground(new Color(245, 248, 250));

        panelPrincipal.add(scrollPrincipal, BorderLayout.CENTER);

        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                tfImagen.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        cbTipoUsuario.addActionListener(e -> {
            boolean esProponente = "Proponente".equals(cbTipoUsuario.getSelectedItem());
            panelProponente.setVisible(esProponente);
            revalidate();
            repaint();
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        botones.setBackground(new Color(245, 248, 250));
        botones.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton aceptar = crearBoton("Aceptar", new Color(40, 50, 70), Color.WHITE);
        JButton cancelar = crearBoton("Cancelar", new Color(60, 60, 60), Color.WHITE);
        botones.add(aceptar);
        botones.add(cancelar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        cancelar.addActionListener(e -> dispose());

        aceptar.addActionListener(e -> {
            String nickname = tfNickname.getText().trim();
            String nombre = tfNombre.getText().trim();
            String apellido = tfApellido.getText().trim();
            String contrasenia = new String(tfContrasenia.getPassword()).trim();
            String confirmContrasenia = new String(tfConfirmacionContrasenia.getPassword()).trim();
            String correo = tfCorreo.getText().trim();
            String fechaTexto = tfFechaNacimiento.getText().trim();
            String tipo = (String) cbTipoUsuario.getSelectedItem();
            String rutaImagen = tfImagen.getText().trim();
            String rutaFinal = null;
            if (!rutaImagen.isEmpty()) {
                try {
                    File carpeta = new File("uploads/usuarios/");
                    if (!carpeta.exists()) {
                        carpeta.mkdirs();
                    }
                    String nombreArchivo = UUID.randomUUID() + "_" + new File(rutaImagen).getName();
                    File destino = new File(carpeta, nombreArchivo);

                    Files.copy(new File(rutaImagen).toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    rutaFinal = "uploads/usuarios/" + nombreArchivo;
                } catch (IOException ioEx) {
                    JOptionPane.showMessageDialog(this,
                            "Error al guardar la imagen: " + ioEx.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            boolean esProp = "Proponente".equals(cbTipoUsuario.getSelectedItem());
            String direccion = tfDireccion.getText().trim();
            if (nickname.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || contrasenia.isEmpty() || confirmContrasenia.isEmpty() || correo.isEmpty() || fechaTexto.isEmpty() || (esProp && direccion.isEmpty())) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos obligatorios deben completarse",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contrasenia.equals(confirmContrasenia)){
                JOptionPane.showMessageDialog(this,
                        "Las contraseñas deben ser iguales",
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
                usuario = new DTProponente(nickname, nombre, apellido, contrasenia, correo,fechaNacimiento,rutaFinal,direccion,bio,sitioWeb);
            } else {
                usuario = new DTColaborador(nickname, nombre, apellido, contrasenia, correo,fechaNacimiento,rutaFinal);
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
                tfContrasenia.setText("");
                tfConfirmacionContrasenia.setText("");
                tfCorreo.setText("");
                tfFechaNacimiento.setText("");
                tfImagen.setText("");
                tfDireccion.setText("");
                tfBiografia.setText("");
                tfSitioWeb.setText("");
                cbTipoUsuario.setSelectedIndex(0);
                panelProponente.setVisible(false);
                revalidate();
                repaint();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear usuario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private void estilizarTextField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 8, 5, 8)
        ));
        if (field instanceof JTextField || field instanceof JPasswordField) {
            ((JTextComponent) field).setBackground(Color.WHITE);
        } else if (field instanceof JTextArea) {
            ((JTextArea) field).setBackground(Color.WHITE);
        }
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(8, 20, 8, 20)
        ));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        return boton;
    }
}
