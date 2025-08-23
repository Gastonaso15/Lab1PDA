package culturarte.presentacion;

import culturarte.logica.controlador.ICategoriaController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AltaCategoriaInternalFrame extends JInternalFrame {

    private JList<String> listaCategorias;
    private JTextField tfNombre;
    private JComboBox<String> cbPadre;

    private ICategoriaController CategoriaContr;

    public AltaCategoriaInternalFrame(ICategoriaController icc) {
        super("Alta de Categoria", true, true, true, true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        CategoriaContr = icc;

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        listaCategorias = new JList<>();
        actualizarLista();
        JScrollPane scrollLista = new JScrollPane(listaCategorias);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Categorías existentes"));
        add(scrollLista, BorderLayout.WEST);
        scrollLista.setPreferredSize(new Dimension(200, 0));

        panel.add(new JLabel("Nombre:"));
        tfNombre = new JTextField();
        panel.add(tfNombre);

        panel.add(new JLabel("Categoría Padre:"));
        cbPadre = new JComboBox<>();
        actualizarComboPadre();
        panel.add(cbPadre);

        JButton btnCrear = new JButton("Crear Categoría");
        panel.add(btnCrear);

        add(panel, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String padre = (String) cbPadre.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                CategoriaContr.crearCategoria(nombre, padre);
                JOptionPane.showMessageDialog(this, "Categoría creada correctamente");

                tfNombre.setText("");
                actualizarLista();
                actualizarComboPadre();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);


    }

    private void actualizarLista() {
        List<String> categorias = CategoriaContr.listarNombreCategorias();
        listaCategorias.setListData(categorias.toArray(new String[0]));
    }

    private void actualizarComboPadre() {
        cbPadre.removeAllItems();
        List<String> categorias = CategoriaContr.listarNombreCategorias();
        for (String c : categorias) {
            cbPadre.addItem(c);
        }
        if (!categorias.contains("Categoría")) {
            cbPadre.addItem("Categoría");
        }

}
}
