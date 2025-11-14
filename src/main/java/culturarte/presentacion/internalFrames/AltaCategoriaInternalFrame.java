package culturarte.presentacion.internalFrames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;

import culturarte.servicios.DTs.DTCategoria;
import culturarte.servicios.interfaces.IPropuestaController;
import culturarte.presentacion.helpers.CategoriaUIHelper;

public class AltaCategoriaInternalFrame extends JInternalFrame {

    private final JTree treeCategorias;
    private final JTextField tfNombre;

    private final IPropuestaController PropuestaContr;

    public AltaCategoriaInternalFrame(IPropuestaController icp) {
        super("Alta de Categoría", true, true, true, true);
        setSize(800, 600);
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        PropuestaContr = icp;

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Nueva Categoría",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelFormulario.setBackground(Color.WHITE);

        JPanel gridForm = new JPanel(new GridBagLayout());
        gridForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNombre = createLabel("Nombre de Nueva Categoría:");
        gridForm.add(lblNombre, gbc);
        gbc.gridx = 1;
        tfNombre = new JTextField(25);
        estilizarTextField(tfNombre);
        gridForm.add(tfNombre, gbc);

        panelFormulario.add(Box.createVerticalStrut(20));
        panelFormulario.add(gridForm);
        panelFormulario.add(Box.createVerticalGlue());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categoría");
        treeCategorias = new JTree(root);
        treeCategorias.setShowsRootHandles(true);
        treeCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollTree = new JScrollPane(treeCategorias);
        scrollTree.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Seleccionar Categoría Padre",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFormulario, scrollTree);
        splitPane.setDividerLocation(200);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        botones.setBackground(new Color(245, 248, 250));
        botones.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton btnCrear = crearBoton("Crear Categoría", new Color(40, 50, 70), Color.WHITE);
        JButton btnCerrar = crearBoton("Cancelar", new Color(60, 60, 60), Color.WHITE);
        botones.add(btnCrear);
        botones.add(btnCerrar);
        add(botones, BorderLayout.SOUTH);

        btnCerrar.addActionListener(e -> dispose());

        recargarCategorias();

        btnCrear.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeCategorias.getLastSelectedPathComponent();
            String padre = (selectedNode != null) ? selectedNode.toString() : null;

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                PropuestaContr.crearCategoria(nombre, padre);
                JOptionPane.showMessageDialog(this, "Categoría creada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                tfNombre.setText("");
                recargarCategorias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private void estilizarTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 8, 5, 8)
        ));
        field.setBackground(Color.WHITE);
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

    private void recargarCategorias() {
        try {
            List<DTCategoria> categorias = PropuestaContr.devolverTodasLasCategorias();
            CategoriaUIHelper.cargarCategorias(treeCategorias, categorias);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar categorías: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
