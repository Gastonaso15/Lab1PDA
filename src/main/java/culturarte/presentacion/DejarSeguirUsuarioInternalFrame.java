package culturarte.presentacion;

import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DejarSeguirUsuarioInternalFrame extends JInternalFrame {

    private IUsuarioController UsuarioContr;

    private JComboBox<String> cbSeguidor;
    private JComboBox<String> cbSeguido;

    public DejarSeguirUsuarioInternalFrame(IUsuarioController icu) {
        super("Seguir Usuario", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Usuario que sigue:"));
        cbSeguidor = new JComboBox<>();
        cargarUsuarios(cbSeguidor);
        panel.add(cbSeguidor);

        panel.add(new JLabel("Usuario a dejar de seguir:"));
        cbSeguido = new JComboBox<>();
        cargarUsuarios(cbSeguido);
        panel.add(cbSeguido);

        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");
        botones.add(aceptar);
        botones.add(cancelar);
        add(botones, BorderLayout.SOUTH);

        cancelar.addActionListener(e -> dispose());

        aceptar.addActionListener(e -> {
            String nicknameSeguidor = (String) cbSeguidor.getSelectedItem();
            String nicknameSeguido = (String) cbSeguido.getSelectedItem();

            if (nicknameSeguidor == null || nicknameSeguido == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar ambos usuarios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                UsuarioContr.dejarDeSeguirUsuario(nicknameSeguidor, nicknameSeguido);
                JOptionPane.showMessageDialog(this,
                        nicknameSeguidor + " dejó de seguir a " + nicknameSeguido,
                        "Dejar de Seguir Usuario",
                        JOptionPane.INFORMATION_MESSAGE);
                cbSeguidor.setSelectedIndex(0);
                cbSeguido.setSelectedIndex(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al dejar de seguir usuario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void cargarUsuarios(JComboBox<String> combo) {
        combo.removeAllItems();
        List<String> usuarios = UsuarioContr.devolverNicknamesUsuarios();
        if (usuarios != null) {
            for (String u : usuarios) {
                combo.addItem(u);
            }
        }
    }
}