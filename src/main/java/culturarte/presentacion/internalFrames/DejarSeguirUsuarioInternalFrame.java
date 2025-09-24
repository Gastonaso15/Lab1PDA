
package culturarte.presentacion.internalFrames;

import culturarte.logica.controladores.IUsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DejarSeguirUsuarioInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;

    private final JComboBox<String> cbSeguidor;
    private final JComboBox<String> cbSeguido;

    public DejarSeguirUsuarioInternalFrame(IUsuarioController icu) {
        super("Dejar de Seguir Usuario", true, true, true, true);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Usuario que sigue:"));
        cbSeguidor = new JComboBox<>();
        cargarUsuarios(cbSeguidor, icu.devolverNicknamesUsuarios());
        panel.add(cbSeguidor);

        panel.add(new JLabel("Usuario a dejar de seguir:"));
        cbSeguido = new JComboBox<>();
        cbSeguido.setEnabled(false);
        panel.add(cbSeguido);

        cbSeguidor.addActionListener(e -> {
            String nicknameSeguidor = (String) cbSeguidor.getSelectedItem();
            if (nicknameSeguidor != null) {
                List<String> usuariosSeguidos = icu.devolverUsuariosSeguidos(nicknameSeguidor);
                if (usuariosSeguidos == null || usuariosSeguidos.isEmpty()) {
                    cbSeguido.removeAllItems();
                    cbSeguido.addItem("El usuario no sigue a nadie");
                    cbSeguido.setEnabled(false);
                } else {
                    cargarUsuarios(cbSeguido, usuariosSeguidos);
                    cbSeguido.setEnabled(true);
                }
            } else {
                cbSeguido.removeAllItems();
            }
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

    private void cargarUsuarios(JComboBox<String> combo, List<String> usuarios) {
        combo.removeAllItems();
        if (usuarios != null) {
            for (String u : usuarios) {
                combo.addItem(u);
            }
        }
    }
}
