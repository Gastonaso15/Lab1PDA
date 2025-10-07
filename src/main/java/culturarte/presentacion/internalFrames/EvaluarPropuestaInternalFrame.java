package culturarte.presentacion.internalFrames;

import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.DTs.DTEstadoPropuesta;
import culturarte.logica.controladores.IPropuestaController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EvaluarPropuestaInternalFrame extends JInternalFrame {

    private final IPropuestaController propuestaController;
    private final JList<DTPropuesta> jListPropuestas;
    private final JLabel lblTitulo, lblDescripcion, lblLugar, lblFechaPrevista, lblCategoria;
    private final JLabel lblPrecioEntrada, lblMontoNecesario, lblProponente, lblEstado;
    private final JRadioButton rbPublicar, rbCancelar;
    private final JButton btnConfirmar, btnCerrar;

    public EvaluarPropuestaInternalFrame(IPropuestaController controller) {
        super("Evaluar Propuesta", true, true, true, true);
        this.propuestaController = controller;

        setSize(1000, 500);
        setLayout(new BorderLayout());

        // Panel izquierdo: Lista de propuestas INGRESADAS
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        jListPropuestas = new JList<>();
        JScrollPane scrollPropuestas = new JScrollPane(jListPropuestas);
        panelIzquierdo.add(new JLabel("Propuestas INGRESADAS:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollPropuestas, BorderLayout.CENTER);

        // Panel derecho: Detalles
        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelDetalle = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDetalle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTitulo = new JLabel("Título: ");
        lblDescripcion = new JLabel("Descripción: ");
        lblLugar = new JLabel("Lugar: ");
        lblFechaPrevista = new JLabel("Fecha Prevista: ");
        lblCategoria = new JLabel("Categoría: ");
        lblPrecioEntrada = new JLabel("Precio Entrada: ");
        lblMontoNecesario = new JLabel("Monto Necesario: ");
        lblProponente = new JLabel("Proponente: ");
        lblEstado = new JLabel("Estado: ");

        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 16);
        setFontToLabels(fontInfo, lblTitulo, lblDescripcion, lblLugar, lblFechaPrevista,
                lblPrecioEntrada, lblMontoNecesario, lblProponente, lblEstado, lblCategoria);

        panelDerecho.add(panelDetalle, BorderLayout.NORTH);

        // Organizar etiquetas en el panel detalle
        panelDetalle.add(lblTitulo);
        panelDetalle.add(lblProponente);
        panelDetalle.add(lblCategoria);
        panelDetalle.add(lblEstado);
        panelDetalle.add(lblDescripcion);
        panelDetalle.add(lblLugar);
        panelDetalle.add(lblPrecioEntrada);
        panelDetalle.add(lblMontoNecesario);

        // Panel inferior: Acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rbPublicar = new JRadioButton("Publicar");
        rbCancelar = new JRadioButton("Cancelar");
        ButtonGroup group = new ButtonGroup();
        group.add(rbPublicar);
        group.add(rbCancelar);

        btnConfirmar = new JButton("Confirmar");
        btnCerrar = new JButton("Cerrar");

        panelAcciones.add(rbPublicar);
        panelAcciones.add(rbCancelar);
        panelAcciones.add(btnConfirmar);
        panelAcciones.add(btnCerrar);
        panelDerecho.add(panelAcciones, BorderLayout.SOUTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // Eventos
        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta propuesta = jListPropuestas.getSelectedValue();
                if (propuesta != null) mostrarDetallesPropuesta(propuesta);
            }
        });

        btnConfirmar.addActionListener(e -> evaluarSeleccion());
        btnCerrar.addActionListener(e -> dispose());

        // Cargar lista inicial
        cargarPropuestas();
    }

    private void mostrarDetallesPropuesta(DTPropuesta p) {
        lblTitulo.setText("Título: " + p.getTitulo());
        lblProponente.setText("Proponente: " + p.getProponente());
        lblCategoria.setText("Categoría: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "N/A"));
        lblEstado.setText("Estado: " + (p.getEstadoActual() != null ? p.getEstadoActual() : "N/A"));
        lblDescripcion.setText("Descripción: " + (p.getDescripcion() != null ? p.getDescripcion() : ""));
        lblLugar.setText("Lugar: " + (p.getLugar() != null ? p.getLugar() : ""));
        lblPrecioEntrada.setText("Precio Entrada: $" + (p.getPrecioEntrada() != null ? p.getPrecioEntrada() : 0));
        lblMontoNecesario.setText("Monto Necesario: $" + (p.getMontoNecesario() != null ? p.getMontoNecesario() : 0));
        rbPublicar.setSelected(false);
        rbCancelar.setSelected(false);
    }

    private void limpiarDetalle() {
        lblTitulo.setText("Título: ");
        lblProponente.setText("Proponente: ");
        lblCategoria.setText("Categoría: ");
        lblEstado.setText("Estado: ");
        lblDescripcion.setText("Descripción: ");
        lblLugar.setText("Lugar: ");
        lblPrecioEntrada.setText("Precio Entrada: ");
        lblMontoNecesario.setText("Monto Necesario: ");
        rbPublicar.setSelected(false);
        rbCancelar.setSelected(false);
        jListPropuestas.clearSelection();
    }

    private void cargarPropuestas() {
        List<DTPropuesta> propuestas = propuestaController.getPropuestasIngresadas();
        if (propuestas != null) {
            jListPropuestas.setListData(propuestas.toArray(new DTPropuesta[0]));
        } else {
            jListPropuestas.setListData(new DTPropuesta[0]);
        }
        limpiarDetalle();
    }

    private void evaluarSeleccion() {
        DTPropuesta propuesta = jListPropuestas.getSelectedValue();
        if (propuesta == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una propuesta", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!rbPublicar.isSelected() && !rbCancelar.isSelected()) {
            JOptionPane.showMessageDialog(this, "Seleccione Publicar o Cancelar", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean publicar = rbPublicar.isSelected();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea " + (publicar ? "PUBLICAR" : "CANCELAR") + " la propuesta \"" + propuesta.getTitulo() + "\"?",
                "Confirmar evaluación",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            propuestaController.evaluarPropuesta(propuesta.getTitulo(), publicar);
            JOptionPane.showMessageDialog(this, "Propuesta evaluada con éxito");
            cargarPropuestas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}
