package culturarte.presentacion.internalFrames;

import culturarte.servicios.interfaces.IUsuarioController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class DejarSeguirUsuarioInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;

    private final JComboBox<String> cbSeguidor;
    private final JComboBox<String> cbSeguido;

    public DejarSeguirUsuarioInternalFrame(IUsuarioController icu) {
        super("Dejar de Seguir Usuario", true, true, true, true);
        setSize(700, 400);
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

        UsuarioContr = icu;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Seleccionar Usuarios",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 70, 70)
        ));
        panel.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblSeguidor = createLabel("Usuario que sigue:");
        gridPanel.add(lblSeguidor, gbc);
        gbc.gridx = 1;
        cbSeguidor = new JComboBox<>();
        cbSeguidor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbSeguidor.setBackground(Color.WHITE);
        cbSeguidor.setPreferredSize(new Dimension(200, 30));
        cbSeguidor.setMaximumRowCount(10);
        estilizarCombo(cbSeguidor);
        cargarUsuarios(cbSeguidor, icu.devolverNicknamesUsuarios());
        gridPanel.add(cbSeguidor, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblSeguido = createLabel("Usuario a dejar de seguir:");
        gridPanel.add(lblSeguido, gbc);
        gbc.gridx = 1;
        cbSeguido = new JComboBox<>();
        cbSeguido.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbSeguido.setBackground(Color.WHITE);
        cbSeguido.setPreferredSize(new Dimension(200, 30));
        cbSeguido.setMaximumRowCount(10);
        estilizarCombo(cbSeguido);
        cbSeguido.setEnabled(false);
        gridPanel.add(cbSeguido, gbc);

        panel.add(Box.createVerticalStrut(20));
        panel.add(gridPanel);
        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

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

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        botones.setBackground(new Color(245, 248, 250));
        botones.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton aceptar = crearBoton("Aceptar", new Color(40, 50, 70), Color.WHITE);
        JButton cancelar = crearBoton("Cancelar", new Color(60, 60, 60), Color.WHITE);
        botones.add(aceptar);
        botones.add(cancelar);
        add(botones, BorderLayout.SOUTH);

        cancelar.addActionListener(e -> dispose());

        aceptar.addActionListener(e -> {
            String nicknameSeguidor = (String) cbSeguidor.getSelectedItem();
            String nicknameSeguido = (String) cbSeguido.getSelectedItem();

            if (nicknameSeguidor == null || nicknameSeguido == null || 
                "El usuario no sigue a nadie".equals(nicknameSeguido)) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar ambos usuarios válidos",
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
                if (cbSeguidor.getItemCount() > 0) cbSeguidor.setSelectedIndex(0);
                cbSeguido.removeAllItems();
                cbSeguido.setEnabled(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al dejar de seguir usuario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 8, 5, 8)
        ));
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setOpaque(true);
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setContentAreaFilled(true);
        boton.setPreferredSize(new Dimension(120, 35));
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

    private void cargarUsuarios(JComboBox<String> combo, List<String> usuarios) {
        combo.removeAllItems();
        if (usuarios != null) {
            for (String u : usuarios) {
                combo.addItem(u);
            }
        }
    }
}
