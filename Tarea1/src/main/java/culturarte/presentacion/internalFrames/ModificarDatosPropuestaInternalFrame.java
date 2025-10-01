package culturarte.presentacion.internalFrames;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import culturarte.logica.DTs.DTCategoria;
import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.DTs.DTTipoRetorno;
import culturarte.logica.controladores.IPropuestaController;
import culturarte.presentacion.helpers.CategoriaUIHelper;

public class ModificarDatosPropuestaInternalFrame extends JInternalFrame {

    private final IPropuestaController ICP;

    private final JComboBox<String> cbPropuestas;
    private final JTextField tfDescripcion;
    private final JTextField tfLugar;
    private final JTextField tfFechaPrevista;
    private final JTextField tfPrecioEntrada;
    private final JTextField tfMontoNecesario;
    private final JTextField tfImagen;
    private final List<JCheckBox> checkBoxesTiposRetorno;
    private final JTree treeCategorias;

    private List<DTPropuesta> listaPropuestas;

    public ModificarDatosPropuestaInternalFrame(IPropuestaController ICP) {
        super("Modificar Propuesta", true, true, true, true);
        this.ICP = ICP;

        setSize(1000, 500);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        DefaultMutableTreeNode rootCategorias = new DefaultMutableTreeNode("Categorías");
        treeCategorias = new JTree(rootCategorias);
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollTree = new JScrollPane(treeCategorias);

        List<DTCategoria> categorias = ICP.devolverTodasLasCategorias();
        CategoriaUIHelper.cargarCategorias(treeCategorias, categorias);

        panel.add(new JLabel("Seleccionar Propuesta:"));
        cbPropuestas = new JComboBox<>();
        panel.add(cbPropuestas);

        panel.add(new JLabel("Descripcion:"));
        tfDescripcion = new JTextField();
        panel.add(tfDescripcion);

        panel.add(new JLabel("Lugar:"));
        tfLugar = new JTextField();
        panel.add(tfLugar);

        panel.add(new JLabel("Fecha Prevista (yyyy-mm-dd):"));
        tfFechaPrevista = new JTextField();
        panel.add(tfFechaPrevista);

        panel.add(new JLabel("Precio Entrada:"));
        tfPrecioEntrada = new JTextField();
        panel.add(tfPrecioEntrada);

        panel.add(new JLabel("Monto Necesario:"));
        tfMontoNecesario = new JTextField();
        panel.add(tfMontoNecesario);

        panel.add(new JLabel("Imagen:"));
        JPanel imagenPanel = new JPanel(new BorderLayout(5, 5));
        tfImagen = new JTextField();
        tfImagen.setEditable(false);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        imagenPanel.add(tfImagen, BorderLayout.CENTER);
        imagenPanel.add(btnSeleccionarImagen, BorderLayout.EAST);
        panel.add(imagenPanel);
        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                tfImagen.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        panel.add(new JLabel("Tipo(s) de Retorno:"));
        JPanel panelCheckBoxes = new JPanel();
        panelCheckBoxes.setLayout(new BoxLayout(panelCheckBoxes, BoxLayout.Y_AXIS));
        checkBoxesTiposRetorno = new ArrayList<>();
        List<String> tiposRetorno = listarTiposRetorno();
        for (String tipo : tiposRetorno) {
            JCheckBox checkBox = new JCheckBox(tipo);
            checkBoxesTiposRetorno.add(checkBox);
            panelCheckBoxes.add(checkBox);
        }
        JScrollPane scrollTiposRetorno = new JScrollPane(panelCheckBoxes);
        panel.add(scrollTiposRetorno);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel, scrollTree);
        splitPane.setDividerLocation(750);
        add(splitPane, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnGuardar = new JButton("Guardar Cambios");
        botones.add(btnGuardar);
        add(botones, BorderLayout.SOUTH);

        cargarPropuestas();

        cbPropuestas.addActionListener(e -> cargarDatosPropuesta());

        btnGuardar.addActionListener(e -> guardarCambios());
    }

    private void cargarPropuestas() {
        listaPropuestas = ICP.devolverTodasLasPropuestas();
        cbPropuestas.removeAllItems();
        for (DTPropuesta p : listaPropuestas) {
            cbPropuestas.addItem(p.getTitulo());
        }
        if (!listaPropuestas.isEmpty()) {
            cbPropuestas.setSelectedIndex(0);
            cargarDatosPropuesta();
        }
    }

    private void cargarDatosPropuesta() {
        int index = cbPropuestas.getSelectedIndex();
        if (index >= 0) {
            DTPropuesta p = listaPropuestas.get(index);
            tfDescripcion.setText(p.getDescripcion());
            tfLugar.setText(p.getLugar());
            tfFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
            tfPrecioEntrada.setText(p.getPrecioEntrada() != null ? p.getPrecioEntrada().toString() : "");
            tfMontoNecesario.setText(p.getMontoNecesario() != null ? p.getMontoNecesario().toString() : "");

            if (p.getImagen() != null && !p.getImagen().isEmpty()) {
                tfImagen.setText(p.getImagen());
            } else {
                tfImagen.setText("");
            }


            for (JCheckBox cb : checkBoxesTiposRetorno) {
                cb.setSelected(false);
                for (DTTipoRetorno t : p.getTiposRetorno()) {
                    if (cb.getText().equals(t.name())) {
                        cb.setSelected(true);
                    }
                }
            }

            if (p.getCategoria() != null) {
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeCategorias.getModel().getRoot();
                DefaultMutableTreeNode nodoCategoria = CategoriaUIHelper.buscarNodo(root, p.getCategoria().getNombre());
                if (nodoCategoria != null) {
                    treeCategorias.setSelectionPath(new javax.swing.tree.TreePath(nodoCategoria.getPath()));
                }
            }
        }
    }

    private void guardarCambios() {
        try {
            int index = cbPropuestas.getSelectedIndex();
            if (index < 0) return;

            DTPropuesta p = listaPropuestas.get(index);

            String descripcion = tfDescripcion.getText();
            String lugar = tfLugar.getText();
            LocalDate fechaPrevista = LocalDate.parse(tfFechaPrevista.getText());
            Double precioEntrada = Double.parseDouble(tfPrecioEntrada.getText());
            Double montoNecesario = Double.parseDouble(tfMontoNecesario.getText());

            String imagenBytes = p.getImagen();
            String nuevaRuta = tfImagen.getText();
            if (!nuevaRuta.isEmpty() && !nuevaRuta.equals("Imagen cargada")) {
                imagenBytes = nuevaRuta;
            }

            List<String> tiposSeleccionados = new ArrayList<>();
            for (JCheckBox cb : checkBoxesTiposRetorno) {
                if (cb.isSelected()) {
                    tiposSeleccionados.add(cb.getText());
                }
            }

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeCategorias.getLastSelectedPathComponent();
            String categoria = (selectedNode != null) ? selectedNode.toString() : null;

            ICP.modificarPropuesta(
                    p.getTitulo(),
                    descripcion,
                    lugar,
                    fechaPrevista,
                    precioEntrada,
                    montoNecesario,
                    imagenBytes,
                    tiposSeleccionados,
                    categoria
            );

            JOptionPane.showMessageDialog(this, "Propuesta modificada correctamente.");
            cargarPropuestas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar propuesta: " + ex.getMessage());
        }
    }

    public List<String> listarTiposRetorno() {
        List<String> lista = new ArrayList<>();
        for (DTTipoRetorno t : DTTipoRetorno.values()) {
            lista.add(t.name());
        }
        return lista;
    }
}