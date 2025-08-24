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

        recargarCategorias();

        btnCrear.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String padre = (String) cbCategoriaPadre.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                CategoriaContr.crearCategoria(nombre, padre);
                JOptionPane.showMessageDialog(this, "Categoría creada correctamente");
                tfNombre.setText("");
                actualizarComboPadre();
                recargarCategorias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
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

    private void recargarCategorias() {
        try {
            List<DTCategoria> categorias = CategoriaContr.listarDTCategorias();
            CategoriaUIHelper.cargarCategorias(treeCategorias, cbCategoriaPadre, categorias);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar categorías: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
