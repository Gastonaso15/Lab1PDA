package culturarte.presentacion;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.controlador.IPropuestaController;

public class AltaCategoriaInternalFrame extends JInternalFrame {

    private JTree treeCategorias;
    private JTextField tfNombre;
    private JComboBox<String> cbCategoriaPadre;
    private DefaultMutableTreeNode root;

    private IPropuestaController PropuestaContr;

    public AltaCategoriaInternalFrame(IPropuestaController icp) {
        super("Alta de Categoría", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        root = new DefaultMutableTreeNode("Categoría");
        treeCategorias = new JTree(root);
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollTree = new JScrollPane(treeCategorias);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre de Nueva Categoria:"));
        tfNombre = new JTextField();
        panel.add(tfNombre);

        panel.add(new JLabel("Seleccionar Categoría Padre:"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel,scrollTree);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnCrear = new JButton("Crear Categoría");
        botones.add(btnCrear);
        JButton btnCerrar = new JButton("Cancelar");
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
                JOptionPane.showMessageDialog(this, "Categoría creada correctamente");
                tfNombre.setText("");
                recargarCategorias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void recargarCategorias() {
        try {
            List<DTCategoria> categorias = PropuestaContr.listarDTCategorias();
            CategoriaUIHelper.cargarCategorias(treeCategorias, cbCategoriaPadre, categorias);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar categorías: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
