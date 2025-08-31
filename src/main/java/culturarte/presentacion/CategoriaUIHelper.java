package culturarte.presentacion;

import culturarte.logica.DT.DTCategoria;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.List;

public class CategoriaUIHelper {

    public static void cargarCategorias(JTree tree, JComboBox<String> combo, List<DTCategoria> categorias) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categoría");

        if (combo != null) {
            combo.removeAllItems();
            combo.addItem("Categoría");
        }

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

            if (combo != null) {
                combo.addItem(cat.getNombre());
            }
        }

        ((DefaultTreeModel) tree.getModel()).setRoot(root);
        ((DefaultTreeModel) tree.getModel()).reload();
    }

    public static DefaultMutableTreeNode buscarNodo(DefaultMutableTreeNode root, String nombre) {
        Enumeration<TreeNode> enumeration = root.depthFirstEnumeration();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
            if (nombre.equals(node.getUserObject().toString())) {
                return node;
            }
        }
        return null;
    }
}
