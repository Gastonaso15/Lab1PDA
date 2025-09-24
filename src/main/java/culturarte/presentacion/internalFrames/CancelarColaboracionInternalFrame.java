package culturarte.presentacion.internalFrames;

import culturarte.logica.DTs.DTColaboracion;
import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.controladores.IPropuestaController;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CancelarColaboracionInternalFrame extends JInternalFrame {

    private final IPropuestaController PropuestaContr;

    private final JList<String> listColaboraciones;
    private final DefaultListModel<String> modelColaboraciones;

    private final JLabel lblPropuestaTitulo;
    private final JLabel lblProponenteNick;
    private final JLabel lblColaboradorNick;
    private final JLabel lblMonto;
    private final JLabel lblTipoRetorno;
    private final JLabel lblFechaHora;
    private final JLabel lblEstadoPropuesta;
    private final JLabel lblMontoNecesario;
    private final JLabel lblTotalRecaudado;

    private DTColaboracion colaboracionSeleccionada;
    private List<DTColaboracion> listaColaboracionesGlobal;

    public CancelarColaboracionInternalFrame(IPropuestaController icp) {
        super("Cancelar Colaboración a Propuesta", true, true, true, true);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        modelColaboraciones = new DefaultListModel<>();
        listColaboraciones = new JList<>(modelColaboraciones);
        listColaboraciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lblPropuestaTitulo = new JLabel("Título: ");
        lblProponenteNick = new JLabel("Proponente: ");
        lblColaboradorNick = new JLabel("Colaborador: ");
        lblMonto = new JLabel("Monto: ");
        lblTipoRetorno = new JLabel("Tipo de Retorno: ");
        lblFechaHora = new JLabel("Fecha y Hora: ");
        lblEstadoPropuesta = new JLabel("Estado Propuesta: ");
        lblMontoNecesario = new JLabel("Monto Necesario: ");
        lblTotalRecaudado = new JLabel("Total Recaudado: ");

        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 14);
        setFontToLabels(fontInfo, lblPropuestaTitulo, lblProponenteNick, lblColaboradorNick,
                lblMonto, lblTipoRetorno, lblFechaHora, lblEstadoPropuesta,
                lblMontoNecesario, lblTotalRecaudado);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Colaboraciones Registradas"));
        panelIzquierdo.setPreferredSize(new Dimension(500, 400));

        JScrollPane scrollColaboraciones = new JScrollPane(listColaboraciones);
        panelIzquierdo.add(scrollColaboraciones, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Detalles de la Colaboración"));

        JPanel panelDetalles = new JPanel(new GridLayout(10, 1, 5, 5));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelDetalles.add(lblPropuestaTitulo);
        panelDetalles.add(lblProponenteNick);
        panelDetalles.add(lblColaboradorNick);
        panelDetalles.add(lblMonto);
        panelDetalles.add(lblTipoRetorno);
        panelDetalles.add(lblFechaHora);
        panelDetalles.add(lblEstadoPropuesta);
        panelDetalles.add(lblMontoNecesario);
        panelDetalles.add(lblTotalRecaudado);

        JButton btnCancelar = new JButton("Cancelar Colaboración");
        panelDerecho.add(panelDetalles, BorderLayout.CENTER);
        panelDerecho.add(btnCancelar, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        btnCancelar.addActionListener(e -> {
            if (colaboracionSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una colaboración para cancelar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea cancelar esta colaboración?",
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    PropuestaContr.cancelarColaboracion(colaboracionSeleccionada.getId());
                    JOptionPane.showMessageDialog(this, "Colaboración cancelada con éxito.");
                    cargarColaboracionesDelSistema();
                    limpiarDetalles();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al cancelar colaboración: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        listColaboraciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listColaboraciones.getSelectedIndex();
                if (index >= 0) {
                    mostrarDetallesColaboracion(index);
                }
            }
        });

        cargarColaboracionesDelSistema();

    }


    private void cargarColaboracionesDelSistema() {
        try {
            modelColaboraciones.clear();
            limpiarDetalles();

            listaColaboracionesGlobal = PropuestaContr.obtenerTodasLasColaboraciones();

            if (listaColaboracionesGlobal != null && !listaColaboracionesGlobal.isEmpty()) {
                int index = 1;
                for (DTColaboracion colaboracion : listaColaboracionesGlobal) {
                    DTPropuesta propuesta = colaboracion.getPropuesta();
                    String itemText = index + ". " + propuesta.getTitulo()
                            + " | Colaborador: " + colaboracion.getColaborador().getNickname()
                            + " | $" + colaboracion.getMonto();
                    modelColaboraciones.addElement(itemText);
                    index++;
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
        try {
            if (listaColaboracionesGlobal == null || index >= listaColaboracionesGlobal.size()) return;

            colaboracionSeleccionada = listaColaboracionesGlobal.get(index);
            DTPropuesta propuesta = colaboracionSeleccionada.getPropuesta();

            lblPropuestaTitulo.setText("Título: " + propuesta.getTitulo());
            lblProponenteNick.setText("Proponente: " +
                    (propuesta.getDTProponente() != null ? propuesta.getDTProponente().getNickname() : "N/A"));
            lblColaboradorNick.setText("Colaborador: " + colaboracionSeleccionada.getColaborador().getNickname());
            lblMonto.setText("Monto: $" + String.format("%.2f", colaboracionSeleccionada.getMonto()));
            lblTipoRetorno.setText("Tipo de Retorno: " +
                    (colaboracionSeleccionada.getTipoRetorno() != null ? colaboracionSeleccionada.getTipoRetorno().toString() : "N/A"));

            if (colaboracionSeleccionada.getFechaHora() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                lblFechaHora.setText("Fecha y Hora: " + colaboracionSeleccionada.getFechaHora().format(formatter));
            } else {
                lblFechaHora.setText("Fecha y Hora: N/A");
            }

            lblEstadoPropuesta.setText("Estado Propuesta: " + propuesta.getEstadoActual());
            lblMontoNecesario.setText("Monto Necesario: $" + String.format("%.2f", propuesta.getMontoNecesario()));

            double totalRecaudado = 0;
            if (propuesta.getColaboraciones() != null) {
                for (DTColaboracion c : propuesta.getColaboraciones()) {
                    totalRecaudado += c.getMonto();
                }
            }
            lblTotalRecaudado.setText("Total Recaudado: $" + String.format("%.2f", totalRecaudado));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar detalles de la colaboración: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarDetalles() {
        lblPropuestaTitulo.setText("Título: ");
        lblProponenteNick.setText("Proponente: ");
        lblColaboradorNick.setText("Colaborador: ");
        lblMonto.setText("Monto: ");
        lblTipoRetorno.setText("Tipo de Retorno: ");
        lblFechaHora.setText("Fecha y Hora: ");
        lblEstadoPropuesta.setText("Estado Propuesta: ");
        lblMontoNecesario.setText("Monto Necesario: ");
        lblTotalRecaudado.setText("Total Recaudado: ");
        colaboracionSeleccionada = null;
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}