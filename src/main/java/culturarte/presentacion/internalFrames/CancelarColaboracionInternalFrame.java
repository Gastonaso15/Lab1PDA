package culturarte.presentacion.internalFrames;

import culturarte.logica.DTs.DTColaboracion;
import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.controladores.IPropuestaController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CancelarColaboracionInternalFrame extends JInternalFrame {

    private final JList<DTPropuesta> jListPropuestas;
    private final JList<DTColaboracion> jListColaboraciones;
    private final JLabel lblTitulo;
    private final JLabel lblDescripcion;
    private final JLabel lblLugar;
    private final JLabel lblFechaPrevista;
    private final JLabel lblPrecioEntrada;
    private final JLabel lblMontoNecesario;
    private final JLabel lblProponente;
    private final JLabel lblEstado;
    private final JLabel lblMontoTotal;
    private final JLabel lblCategoria;

    private final IPropuestaController PropuestaContr;

    public CancelarColaboracionInternalFrame(IPropuestaController icp) {
        super("Cancelar Colaboración a Propuesta", true, true, true, true);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
        jListPropuestas = new JList<>(propuestas.toArray(new DTPropuesta[0]));
        JScrollPane scrollPropuestas = new JScrollPane(jListPropuestas);
        panelIzquierdo.add(new JLabel("Propuestas:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollPropuestas, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelInfo = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        lblTitulo = new JLabel("Título: ");
        lblDescripcion = new JLabel("Descripción: ");
        lblLugar = new JLabel("Lugar: ");
        lblFechaPrevista = new JLabel("Fecha Prevista: ");
        lblCategoria = new JLabel("Categoria: ");
        col1.add(lblTitulo);
        col1.add(lblDescripcion);
        col1.add(lblLugar);
        col1.add(lblFechaPrevista);
        col1.add(lblCategoria);

        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        lblPrecioEntrada = new JLabel("Precio Entrada: ");
        lblMontoNecesario = new JLabel("Monto Necesario: ");
        lblMontoTotal = new JLabel("Monto Recaudado: ");
        lblProponente = new JLabel("Proponente: ");
        lblEstado = new JLabel("Estado: ");
        col2.add(lblPrecioEntrada);
        col2.add(lblMontoNecesario);
        col2.add(lblMontoTotal);
        col2.add(lblProponente);
        col2.add(lblEstado);

        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 16);
        setFontToLabels(fontInfo, lblTitulo, lblDescripcion, lblLugar, lblFechaPrevista,
                lblPrecioEntrada, lblMontoNecesario, lblProponente, lblEstado, lblCategoria, lblMontoTotal);

        panelInfo.add(col1);
        panelInfo.add(col2);

        JPanel panelColaboraciones = new JPanel(new BorderLayout());
        panelColaboraciones.add(new JLabel("Colaboraciones:"), BorderLayout.NORTH);
        jListColaboraciones = new JList<>();
        JScrollPane scrollColaboraciones = new JScrollPane(jListColaboraciones);
        panelColaboraciones.add(scrollColaboraciones, BorderLayout.CENTER);

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(panelColaboraciones, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnCancelarColaboracion = new JButton("Cancelar Colaboración");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnCancelarColaboracion);
        panelBotones.add(btnCancelar);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        jListPropuestas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DTPropuesta p) {
                    String proponenteNick = (p.getDTProponente() != null) ?
                            p.getDTProponente().getNickname() : "N/A";
                    setText(p.getTitulo() + " (" + proponenteNick + ")");
                }
                return this;
            }
        });

        jListColaboraciones.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DTColaboracion c) {
                    String colaboradorNick = (c.getColaborador() != null) ?
                            c.getColaborador().getNickname() : "N/A";
                    setText(colaboradorNick + " - $" + c.getMonto());
                }
                return this;
            }
        });

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta propuesta = jListPropuestas.getSelectedValue();
                if (propuesta != null) {
                    mostrarDetallesPropuesta(propuesta);
                    cargarColaboraciones(propuesta);
                }
            }
        });

        btnCancelarColaboracion.addActionListener(e -> {
            try {
                DTPropuesta propuestaSeleccionada = jListPropuestas.getSelectedValue();
                DTColaboracion colaboracionSeleccionada = jListColaboraciones.getSelectedValue();

                if (propuestaSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una propuesta.");
                    return;
                }

                if (colaboracionSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una colaboración.");
                    return;
                }

                String nickname = colaboracionSeleccionada.getColaborador().getNickname();

                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro que desea cancelar la colaboración de " + nickname +
                                " por $" + colaboracionSeleccionada.getMonto() + "?",
                        "Confirmar cancelación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    PropuestaContr.cancelarColaboracion(
                            colaboracionSeleccionada.getId()
                    );

                    JOptionPane.showMessageDialog(this, "Colaboración cancelada con éxito");

                    jListPropuestas.clearSelection();
                    jListColaboraciones.setListData(new DTColaboracion[0]);
                    limpiarDetalles();

                    List<DTPropuesta> propuestasActualizadas = PropuestaContr.devolverTodasLasPropuestas();
                    jListPropuestas.setListData(propuestasActualizadas.toArray(new DTPropuesta[0]));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void mostrarDetallesPropuesta(DTPropuesta propuesta) {
        lblTitulo.setText("Título: " + propuesta.getTitulo());
        lblDescripcion.setText("Descripción: " +
                (propuesta.getDescripcion().length() > 50 ?
                        propuesta.getDescripcion().substring(0, 50) + "..." :
                        propuesta.getDescripcion()));
        lblLugar.setText("Lugar: " + propuesta.getLugar());
        lblFechaPrevista.setText("Fecha Prevista: " + propuesta.getFechaPrevista());
        lblPrecioEntrada.setText("Precio Entrada: $" + propuesta.getPrecioEntrada());
        lblMontoNecesario.setText("Monto Necesario: $" + propuesta.getMontoNecesario());

        double montoTotal = 0;
        if (propuesta.getColaboraciones() != null) {
            for (DTColaboracion c : propuesta.getColaboraciones()) {
                if (c.getMonto() != null) {
                    montoTotal += c.getMonto();
                }
            }
        }
        lblMontoTotal.setText("Monto Recaudado: $" + montoTotal);

        lblCategoria.setText("Categoria: " + propuesta.getCategoria().getNombre());

        String proponenteInfo = (propuesta.getDTProponente() != null) ?
                propuesta.getDTProponente().getNickname() : "N/A";
        lblProponente.setText("Proponente: " + proponenteInfo);

        String estadoInfo = (propuesta.getEstadoActual() != null) ?
                propuesta.getEstadoActual().toString() : "N/A";
        lblEstado.setText("Estado: " + estadoInfo);
    }

    private void cargarColaboraciones(DTPropuesta propuesta) {
        if (propuesta.getColaboraciones() != null && !propuesta.getColaboraciones().isEmpty()) {
            jListColaboraciones.setListData(propuesta.getColaboraciones().toArray(new DTColaboracion[0]));
        } else {
            jListColaboraciones.setListData(new DTColaboracion[0]);
        }
    }

    private void limpiarDetalles() {
        lblTitulo.setText("Título: ");
        lblDescripcion.setText("Descripción: ");
        lblLugar.setText("Lugar: ");
        lblFechaPrevista.setText("Fecha Prevista: ");
        lblPrecioEntrada.setText("Precio Entrada: ");
        lblMontoNecesario.setText("Monto Necesario: ");
        lblProponente.setText("Proponente: ");
        lblEstado.setText("Estado: ");
        lblCategoria.setText("Categoria: ");
        lblMontoTotal.setText("Monto Recaudado: ");
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}
