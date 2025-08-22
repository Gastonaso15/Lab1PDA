package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;

import culturarte.logica.controlador.IUsuarioController;

public class AltaUsuarioInternalFrame extends JInternalFrame {

    private JTextField tfNickname;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfCorreo;
    private JComboBox<String> cbTipoUsuario;

    //private UsuarioService usuarioService;
    private IUsuarioController UsuarioContr;

    public AltaUsuarioInternalFrame(IUsuarioController icu) {
        super("Alta de Usuario", true, true, true, true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        //usuarioService = new UsuarioService();
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

        panel.add(new JLabel("Tipo Usuario:"));
        cbTipoUsuario = new JComboBox<>(new String[]{"Proponente", "Colaborador"});
        panel.add(cbTipoUsuario);

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
            String tipo = (String) cbTipoUsuario.getSelectedItem();

            if (nickname.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos obligatorios deben completarse",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            /*
            Usuario usuario;
            if ("Proponente".equals(tipo)) {
                usuario = new Proponente(nickname, nombre, apellido, correo);
            } else {
                usuario = new Colaborador(nickname, nombre, apellido, correo);
            }*/

            try {
                UsuarioContr.crearUsuario(nickname,nombre,apellido,correo,tipo);
                JOptionPane.showMessageDialog(this,
                        "Usuario creado correctamente",
                        "Alta de Usuario",
                        JOptionPane.INFORMATION_MESSAGE);

                tfNickname.setText("");
                tfNombre.setText("");
                tfApellido.setText("");
                tfCorreo.setText("");
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
