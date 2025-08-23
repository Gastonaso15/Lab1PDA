package culturarte.presentacion;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.controlador.ICategoriaController;

public class AltaCategoriaInternalFrame extends JInternalFrame {

    private JTree treeCategorias;
    private JTextField tfNombre;
    private JComboBox<String> cbCategoriaPadre;
    private DefaultMutableTreeNode root;

    private ICategoriaController CategoriaContr;

    public AltaCategoriaInternalFrame(ICategoriaController icc) {
        super("Alta de Categoría", true, true, true, true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        CategoriaContr = icc;

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        root = new DefaultMutableTreeNode("Categoría");
        treeCategorias = new JTree(root);
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollTree = new JScrollPane(treeCategorias);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre:"));
        tfNombre = new JTextField();
        panel.add(tfNombre);

        panel.add(new JLabel("Categoría Padre:"));
        cbCategoriaPadre = new JComboBox<>();
        actualizarComboPadre();
        panel.add(cbCategoriaPadre);

        JButton btnCrear = new JButton("Crear Categoría");
        panel.add(btnCrear);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, panel);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnCerrar = new JButton("Cancelar");
        botones.add(btnCerrar);
        add(botones, BorderLayout.SOUTH);

        btnCerrar.addActionListener(e -> dispose());

        cargarCategorias();

        btnCrear.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String padre = (String) cbCategoriaPadre.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultMutableTreeNode parentNode = buscarNodo(root, padre);
            if (parentNode == null) {
                JOptionPane.showMessageDialog(this, "No se encontró la categoría padre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nombre);
            parentNode.add(nuevoNodo);
            ((DefaultTreeModel) treeCategorias.getModel()).reload();

            try {
                CategoriaContr.crearCategoria(nombre, padre);
                JOptionPane.showMessageDialog(this, "Categoría creada correctamente");
                tfNombre.setText("");
                actualizarComboPadre();
                cargarCategorias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void cargarCategorias() {
        root.removeAllChildren();
        cbCategoriaPadre.removeAllItems();

        root.setUserObject("Categoría");
        cbCategoriaPadre.addItem("Categoría");

        try {
            List<DTCategoria> categorias = CategoriaContr.listarDTCategorias();
            for (DTCategoria cat : categorias) {
                DefaultMutableTreeNode parentNode;

                if (cat.getCategoriaPadre() == null || cat.getCategoriaPadre().getNombre() == null || cat.getCategoriaPadre().getNombre().isEmpty()) {
                    parentNode = root;
                } else {
                    parentNode = buscarNodo(root, cat.getCategoriaPadre().getNombre());
                    if (parentNode == null) parentNode = root;
                }

                DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(cat.getNombre());
                parentNode.add(nodo);

                cbCategoriaPadre.addItem(cat.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar categorías: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        ((DefaultTreeModel) treeCategorias.getModel()).reload();
    }

    private DefaultMutableTreeNode buscarNodo(DefaultMutableTreeNode nodo, String nombre) {
        if (nodo.toString().equals(nombre)) return nodo;
        for (int i = 0; i < nodo.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) nodo.getChildAt(i);
            DefaultMutableTreeNode res = buscarNodo(child, nombre);
            if (res != null) return res;
        }
        return null;
    }

    private void actualizarComboPadre() {
        cbCategoriaPadre.removeAllItems();
        List<String> categorias = CategoriaContr.listarNombreCategorias();
        for (String c : categorias) {
            cbCategoriaPadre.addItem(c);
        }
        if (!categorias.contains("Categoría")) {
            cbCategoriaPadre.addItem("Categoría");
        }
}
}
