package culturarte.presentacion;

import culturarte.logica.DT.DTColaboracion;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IPropuestaController;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CancelarColaboracionInternalFrame extends JInternalFrame {

    private final IPropuestaController propuestaController;

    private JList<String> listColaboraciones;
    private DefaultListModel<String> modelColaboraciones;

    private JLabel lblPropuestaTitulo;
    private JLabel lblProponenteNick;
    private JLabel lblColaboradorNick;
    private JLabel lblMonto;
    private JLabel lblTipoRetorno;
    private JLabel lblFechaHora;
    private JLabel lblEstadoPropuesta;
    private JLabel lblMontoNecesario;
    private JLabel lblTotalRecaudado;

    private DTColaboracion colaboracionSeleccionada;
    private List<DTColaboracion> listaColaboracionesGlobal;

    public CancelarColaboracionInternalFrame(IPropuestaController propuestaController) {
        super("Cancelar Colaboración a Propuesta", true, true, true, true);
        this.propuestaController = propuestaController;

        setSize(1200, 500);
        setLayout(new BorderLayout());

        initComponents();
        cargarColaboracionesDelSistema();
        setVisible(true);
    }

    private void initComponents() {
        modelColaboraciones = new DefaultListModel<>();
        listColaboraciones = new JList<>(modelColaboraciones);
        listColaboraciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Inicializar labels
        lblPropuestaTitulo = new JLabel();
        lblProponenteNick = new JLabel();
        lblColaboradorNick = new JLabel();
        lblMonto = new JLabel();
        lblTipoRetorno = new JLabel();
        lblFechaHora = new JLabel();
        lblEstadoPropuesta = new JLabel();
        lblMontoNecesario = new JLabel();
        lblTotalRecaudado = new JLabel();

        UIHelper.setFontToLabels(new Font("Times New Roman", Font.PLAIN, 14),
                lblPropuestaTitulo, lblProponenteNick, lblColaboradorNick, lblMonto,
                lblTipoRetorno, lblFechaHora, lblEstadoPropuesta, lblMontoNecesario, lblTotalRecaudado);

        // Paneles
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelIzquierdo(), crearPanelDerecho());
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        // Listeners
        listColaboraciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listColaboraciones.getSelectedIndex();
                if (index >= 0) mostrarDetallesColaboracion(index);
            }
        });
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Colaboraciones Registradas"));
        panel.setPreferredSize(new Dimension(500, 400));
        panel.add(new JScrollPane(listColaboraciones), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detalles de la Colaboración"));

        JPanel panelDetalles = new JPanel(new GridLayout(10, 1, 5, 5));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        UIHelper.agregarLabels(panelDetalles,
                lblPropuestaTitulo, lblProponenteNick, lblColaboradorNick,
                lblMonto, lblTipoRetorno, lblFechaHora, lblEstadoPropuesta,
                lblMontoNecesario, lblTotalRecaudado);

        JButton btnCancelar = new JButton("Cancelar Colaboración");
        btnCancelar.addActionListener(e -> cancelarColaboracionSeleccionada());

        panel.add(panelDetalles, BorderLayout.CENTER);
        panel.add(btnCancelar, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarColaboracionesDelSistema() {
        try {
            modelColaboraciones.clear();
            limpiarDetalles();

            listaColaboracionesGlobal = propuestaController.obtenerTodasLasColaboraciones();

            if (listaColaboracionesGlobal != null && !listaColaboracionesGlobal.isEmpty()) {
                int index = 1;
                for (DTColaboracion c : listaColaboracionesGlobal) {
                    DTPropuesta p = c.getPropuesta();
                    modelColaboraciones.addElement(String.format("%d. %s | Colaborador: %s | $%.2f",
                            index++, p.getTitulo(), c.getColaborador().getNickname(), c.getMonto()));
                }
            } else {
                modelColaboraciones.addElement("No existen colaboraciones registradas en el sistema");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar colaboraciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetallesColaboracion(int index) {
        if (listaColaboracionesGlobal == null || index >= listaColaboracionesGlobal.size()) return;

        colaboracionSeleccionada = listaColaboracionesGlobal.get(index);
        DTPropuesta propuesta = colaboracionSeleccionada.getPropuesta();

        lblPropuestaTitulo.setText("Título: " + propuesta.getTitulo());
        lblProponenteNick.setText("Proponente: " +
                (propuesta.getDTProponente() != null ? propuesta.getDTProponente().getNickname() : "N/A"));
        lblColaboradorNick.setText("Colaborador: " + colaboracionSeleccionada.getColaborador().getNickname());
        lblMonto.setText(String.format("Monto: $%.2f", colaboracionSeleccionada.getMonto()));
        lblTipoRetorno.setText("Tipo de Retorno: " +
                (colaboracionSeleccionada.getTipoRetorno() != null ?
                        colaboracionSeleccionada.getTipoRetorno().toString() : "N/A"));
        lblFechaHora.setText(colaboracionSeleccionada.getFechaHora() != null ?
                "Fecha y Hora: " + colaboracionSeleccionada.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) :
                "Fecha y Hora: N/A");
        lblEstadoPropuesta.setText("Estado Propuesta: " + propuesta.getEstadoActual());
        lblMontoNecesario.setText(String.format("Monto Necesario: $%.2f", propuesta.getMontoNecesario()));

        double totalRecaudado = propuesta.getColaboraciones() != null ?
                propuesta.getColaboraciones().stream().mapToDouble(DTColaboracion::getMonto).sum() : 0;
        lblTotalRecaudado.setText(String.format("Total Recaudado: $%.2f", totalRecaudado));
    }

    private void cancelarColaboracionSeleccionada() {
        if (colaboracionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una colaboración para cancelar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cancelar esta colaboración?",
                "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                propuestaController.cancelarColaboracion(colaboracionSeleccionada.getId());
                JOptionPane.showMessageDialog(this, "Colaboración cancelada con éxito.");
                cargarColaboracionesDelSistema();
                limpiarDetalles();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cancelar colaboración: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarDetalles() {
        UIHelper.limpiarLabels(lblPropuestaTitulo, lblProponenteNick, lblColaboradorNick,
                lblMonto, lblTipoRetorno, lblFechaHora, lblEstadoPropuesta,
                lblMontoNecesario, lblTotalRecaudado);
        colaboracionSeleccionada = null;
    }
}
