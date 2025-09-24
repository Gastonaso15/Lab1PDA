package culturarte.presentacion.helpers;

import culturarte.logica.DTs.DTCategoria;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.List;

public class CategoriaUIHelper {

    public static void cargarCategorias(JTree tree, List<DTCategoria> categorias) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categoría");

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
