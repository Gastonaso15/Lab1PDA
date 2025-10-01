package culturarte.presentacion.internalFrames;

import culturarte.logica.controladores.IUsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeguirUsuarioInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;

    private final JComboBox<String> cbSeguidor;
    private final JComboBox<String> cbSeguido;

    public SeguirUsuarioInternalFrame(IUsuarioController icu) {
        super("Seguir Usuario", true, true, true, true);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Usuario que sigue:"));
        cbSeguidor = new JComboBox<>();
        cargarUsuarios(cbSeguidor);
        panel.add(cbSeguidor);

        panel.add(new JLabel("Usuario a seguir:"));
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
                UsuarioContr.seguirUsuario(nicknameSeguidor, nicknameSeguido);
                JOptionPane.showMessageDialog(this,
                        nicknameSeguidor + " ahora sigue a " + nicknameSeguido,
                        "Seguir Usuario",
                        JOptionPane.INFORMATION_MESSAGE);
                cbSeguidor.setSelectedIndex(0);
                cbSeguido.setSelectedIndex(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al seguir usuario: " + ex.getMessage(),
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
