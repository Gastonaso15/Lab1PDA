package culturarte.presentacion;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.List;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.controlador.IPropuestaController;

public class AltaCategoriaInternalFrame extends JInternalFrame {

    private JTree treeCategorias;
    private JTextField tfNombre;
    private JComboBox<String> cbCategoriaPadre;

    private final IPropuestaController propuestaController;

    public AltaCategoriaInternalFrame(IPropuestaController propuestaController) {
        super("Alta de Categoría", true, true, true, true);
        this.propuestaController = propuestaController;

        setSize(1200, 500);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Panel principal con inputs
        JPanel panelInputs = new JPanel();
        panelInputs.setLayout(new BoxLayout(panelInputs, BoxLayout.Y_AXIS));
        panelInputs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInputs.add(new JLabel("Nombre de Nueva Categoría:"));
        tfNombre = new JTextField();
        panelInputs.add(tfNombre);

        panelInputs.add(Box.createVerticalStrut(10));
        panelInputs.add(new JLabel("Seleccionar Categoría Padre:"));

        cbCategoriaPadre = new JComboBox<>();
        panelInputs.add(cbCategoriaPadre);

        // Árbol de categorías
        treeCategorias = new JTree(new DefaultMutableTreeNode("Categoría"));
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollTree = new JScrollPane(treeCategorias);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelInputs, scrollTree);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnCrear = new JButton("Crear Categoría");
        JButton btnCerrar = new JButton("Cancelar");
        panelBotones.add(btnCrear);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnCerrar.addActionListener(e -> dispose());
        btnCrear.addActionListener(e -> crearCategoria());

        // Inicializar categorías
        recargarCategorias();
    }

    private void crearCategoria() {
        String nombre = tfNombre.getText().trim();
        String padre = (String) cbCategoriaPadre.getSelectedItem();

        if (!UIHelper.hayCamposVacios(nombre)) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            propuestaController.crearCategoria(nombre, padre);
            JOptionPane.showMessageDialog(this, "Categoría creada correctamente");
            UIHelper.limpiarCampos(tfNombre);
            recargarCategorias();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recargarCategorias() {
        try {
            List<DTCategoria> categorias = propuestaController.listarDTCategorias();
            CategoriaUIHelper.cargarCategorias(treeCategorias, cbCategoriaPadre, categorias);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar categorías: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
