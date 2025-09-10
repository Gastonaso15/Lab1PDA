package culturarte.presentacion;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.DT.DTTipoRetorno;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

public class AltaPropuestaInternalFrame extends JInternalFrame {

    private final IUsuarioController usuarioController;
    private final IPropuestaController propuestaController;

    private JTextField tfTitulo, tfDescripcion, tfLugar, tfFechaPrevista, tfPrecioEntrada, tfMontoNecesario, tfImagenPath;
    private JComboBox<String> cbProponente;
    private List<JCheckBox> checkBoxesTiposRetorno;

    public AltaPropuestaInternalFrame(IPropuestaController propuestaController, IUsuarioController usuarioController) {
        super("Alta de Propuesta", true, true, true, true);
        this.propuestaController = propuestaController;
        this.usuarioController = usuarioController;

        setSize(1200, 500);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Árbol de categorías
        DefaultMutableTreeNode rootCategorias = new DefaultMutableTreeNode("Categorías");
        JTree treeCategorias = new JTree(rootCategorias);
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollTree = new JScrollPane(treeCategorias);

        try {
            List<DTCategoria> categorias = propuestaController.listarDTCategorias();
            CategoriaUIHelper.cargarCategorias(treeCategorias, null, categorias);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar categorías: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Campos del formulario usando UIHelper
        panel.add(UIHelper.crearCampoConLabel("Título:", tfTitulo = new JTextField()));
        panel.add(UIHelper.crearCampoConLabel("Descripción:", tfDescripcion = new JTextField()));
        panel.add(UIHelper.crearCampoConLabel("Lugar:", tfLugar = new JTextField()));
        panel.add(UIHelper.crearCampoConLabel("Fecha Prevista (yyyy-MM-dd):", tfFechaPrevista = new JTextField()));
        panel.add(UIHelper.crearCampoConLabel("Precio Entrada:", tfPrecioEntrada = new JTextField()));
        panel.add(UIHelper.crearCampoConLabel("Monto Necesario:", tfMontoNecesario = new JTextField()));

        // Imagen
        panel.add(UIHelper.crearCampoImagen(this, tfImagenPath = new JTextField()));

        // Proponente
        panel.add(new JLabel("Proponente:"));
        cbProponente = new JComboBox<>();
        usuarioController.devolverNicknamesProponentes().forEach(cbProponente::addItem);
        panel.add(cbProponente);

        // Tipos de retorno
        panel.add(new JLabel("Tipo(s) de Retorno:"));
        panel.add(crearPanelTiposRetorno());

        // SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, panel);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dispose());
        btnAceptar.addActionListener(e -> procesarAltaPropuesta(treeCategorias));
    }

    private JPanel crearPanelTiposRetorno() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        checkBoxesTiposRetorno = new ArrayList<>();
        for (DTTipoRetorno t : DTTipoRetorno.values()) {
            JCheckBox cb = new JCheckBox(t.name());
            checkBoxesTiposRetorno.add(cb);
            panel.add(cb);
        }
        return panel;
    }

    private void procesarAltaPropuesta(JTree treeCategorias) {
        try {
            String titulo = tfTitulo.getText().trim();
            String descripcion = tfDescripcion.getText().trim();
            String lugar = tfLugar.getText().trim();
            LocalDate fechaPrevista = UIHelper.parseFecha(tfFechaPrevista.getText().trim());
            Double precioEntrada = Double.parseDouble(tfPrecioEntrada.getText().trim());
            Double montoNecesario = Double.parseDouble(tfMontoNecesario.getText().trim());
            String proponente = (String) cbProponente.getSelectedItem();

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeCategorias.getLastSelectedPathComponent();
            String categoria = (selectedNode != null) ? selectedNode.toString() : null;

            List<String> tiposSeleccionados = new ArrayList<>();
            checkBoxesTiposRetorno.stream().filter(JCheckBox::isSelected).forEach(cb -> tiposSeleccionados.add(cb.getText()));

            if (!UIHelper.hayCamposVacios(
                    titulo, descripcion, lugar, tfFechaPrevista.getText(),
                    tfPrecioEntrada.getText(), tfMontoNecesario.getText(), proponente, categoria
            ) || tiposSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Completar todos los campos obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rutaFinal = UIHelper.procesarImagen(tfImagenPath.getText().trim(), "uploads/propuestas/");

            propuestaController.crearPropuesta(
                    titulo, descripcion, lugar, fechaPrevista, precioEntrada,
                    montoNecesario, rutaFinal, proponente, categoria, tiposSeleccionados
            );

            JOptionPane.showMessageDialog(this,
                    "Propuesta creada correctamente",
                    "Alta de Propuesta",
                    JOptionPane.INFORMATION_MESSAGE);

            UIHelper.limpiarCampos(tfTitulo, tfDescripcion, tfLugar, tfFechaPrevista, tfPrecioEntrada, tfMontoNecesario, tfImagenPath);
            cbProponente.setSelectedIndex(0);
            checkBoxesTiposRetorno.forEach(cb -> cb.setSelected(false));

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto, use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al procesar imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear propuesta: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
