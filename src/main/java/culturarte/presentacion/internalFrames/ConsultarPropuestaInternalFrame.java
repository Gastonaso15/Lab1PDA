package culturarte.presentacion.internalFrames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.DTs.DTPropuestaEstado;
import culturarte.servicios.DTs.DTTipoRetorno;
import culturarte.servicios.interfaces.IPropuestaController;
import culturarte.presentacion.helpers.ImagenUIHelper;

public class ConsultarPropuestaInternalFrame extends JInternalFrame {

    private final JList<DTPropuesta> jListPropuestas;
    private final JLabel lblTitulo;
    private final JTextArea txtDescripcion;
    private final JLabel lblProponente;
    private final JLabel lblLugar;
    private final JLabel lblFechaPrevista;
    private final JLabel lblEstado;
    private final ImagenUIHelper.ImagenPanel lblImagen;
    private final JTextArea txtColaboradores;
    private final JLabel lblMontoTotal;
    private final JLabel lblPrecioEntrada;
    private final JLabel lblMontoNecesario;
    private final JLabel lblFechaPublicacion;
    private final JLabel lblCategoria;
    private final JTextArea txtHistorial;
    private final JLabel lblTiposRetorno;

    private final IPropuestaController PropuestaContr;

    public ConsultarPropuestaInternalFrame(IPropuestaController icp) {
        super("Consultar Propuesta", true, true, true, true);
        setSize(1200, 700);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        PropuestaContr = icp;

        jListPropuestas = new JList<>();
        jListPropuestas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jListPropuestas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        scrollList.setPreferredSize(new Dimension(250, 0));
        scrollList.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Propuestas",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        add(scrollList, BorderLayout.WEST);

        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
        panelDetalles.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Detalles de la Propuesta",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 70, 70)
        ));
        panelDetalles.setBackground(new Color(250, 250, 250));

        JScrollPane scrollDetalles = new JScrollPane(panelDetalles);
        scrollDetalles.setBorder(null);

        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panelInfo.add(createInfoLabel("Título:"), gbc);
        gbc.gridx = 1;
        lblTitulo = new JLabel();
        estilizarLabelContenido(lblTitulo);
        panelInfo.add(lblTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelInfo.add(createInfoLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextArea(3, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescripcion.setBackground(new Color(240, 248, 255));
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(null);
        panelInfo.add(scrollDesc, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 2;
        panelInfo.add(createInfoLabel("Proponente:"), gbc);
        gbc.gridx = 1;
        lblProponente = new JLabel();
        estilizarLabelContenido(lblProponente);
        panelInfo.add(lblProponente, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelInfo.add(createInfoLabel("Lugar:"), gbc);
        gbc.gridx = 1;
        lblLugar = new JLabel();
        estilizarLabelContenido(lblLugar);
        panelInfo.add(lblLugar, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelInfo.add(createInfoLabel("Fecha Prevista:"), gbc);
        gbc.gridx = 1;
        lblFechaPrevista = new JLabel();
        estilizarLabelContenido(lblFechaPrevista);
        panelInfo.add(lblFechaPrevista, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelInfo.add(createInfoLabel("Precio Entrada:"), gbc);
        gbc.gridx = 1;
        lblPrecioEntrada = new JLabel();
        estilizarLabelContenido(lblPrecioEntrada);
        panelInfo.add(lblPrecioEntrada, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelInfo.add(createInfoLabel("Monto Necesario:"), gbc);
        gbc.gridx = 1;
        lblMontoNecesario = new JLabel();
        estilizarLabelContenido(lblMontoNecesario);
        panelInfo.add(lblMontoNecesario, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        panelInfo.add(createInfoLabel("Fecha Publicación:"), gbc);
        gbc.gridx = 1;
        lblFechaPublicacion = new JLabel();
        estilizarLabelContenido(lblFechaPublicacion);
        panelInfo.add(lblFechaPublicacion, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        panelInfo.add(createInfoLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        lblCategoria = new JLabel();
        estilizarLabelContenido(lblCategoria);
        panelInfo.add(lblCategoria, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        panelInfo.add(createInfoLabel("Estado:"), gbc);
        gbc.gridx = 1;
        lblEstado = new JLabel();
        estilizarLabelContenido(lblEstado);
        panelInfo.add(lblEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        panelInfo.add(createInfoLabel("Tipos de Retorno:"), gbc);
        gbc.gridx = 1;
        lblTiposRetorno = new JLabel();
        estilizarLabelContenido(lblTiposRetorno);
        panelInfo.add(lblTiposRetorno, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelInfo.add(createInfoLabel("Colaboradores:"), gbc);
        gbc.gridx = 1;
        txtColaboradores = new JTextArea(3, 30);
        txtColaboradores.setLineWrap(true);
        txtColaboradores.setWrapStyleWord(true);
        txtColaboradores.setEditable(false);
        txtColaboradores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtColaboradores.setBackground(new Color(240, 248, 255));
        txtColaboradores.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollColab = new JScrollPane(txtColaboradores);
        scrollColab.setBorder(null);
        panelInfo.add(scrollColab, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 12;
        panelInfo.add(createInfoLabel("Monto total recaudado:"), gbc);
        gbc.gridx = 1;
        lblMontoTotal = new JLabel();
        estilizarLabelContenido(lblMontoTotal);
        lblMontoTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMontoTotal.setForeground(new Color(0, 100, 0));
        panelInfo.add(lblMontoTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 13;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelInfo.add(createInfoLabel("Historial:"), gbc);
        gbc.gridx = 1;
        txtHistorial = new JTextArea(3, 30);
        txtHistorial.setLineWrap(true);
        txtHistorial.setWrapStyleWord(true);
        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtHistorial.setBackground(new Color(240, 248, 255));
        txtHistorial.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollHist = new JScrollPane(txtHistorial);
        scrollHist.setBorder(null);
        panelInfo.add(scrollHist, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLoweredBevelBorder());
        panelInfo.add(lblImagen, gbc);

        panelDetalles.add(panelInfo);
        panelDetalles.add(Box.createVerticalGlue());

        add(scrollDetalles, BorderLayout.CENTER);

        cargarPropuestas();

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta p = jListPropuestas.getSelectedValue();
                if (p != null) {
                    mostrarDetalles(p);
                }
            }
        });
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private void cargarPropuestas() {
        List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
        DefaultListModel<DTPropuesta> modeloLista = new DefaultListModel<>();
        for (DTPropuesta p : propuestas) {
            if (p.getEstadoActual() != null && !p.getEstadoActual().toString().equals("INGRESADA")) {
                modeloLista.addElement(p);
            }
        }
        jListPropuestas.setModel(modeloLista);
    }

    private void mostrarDetalles(DTPropuesta p) {
        lblTitulo.setText(p.getTitulo() != null ? p.getTitulo() : "");
        txtDescripcion.setText(p.getDescripcion() != null ? p.getDescripcion() : "");
        lblProponente.setText(p.getDTProponente() != null ? p.getDTProponente().getNombre() : "");
        lblLugar.setText(p.getLugar() != null ? p.getLugar() : "");
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Sin estado");
        lblPrecioEntrada.setText(p.getPrecioEntrada() != null ? "$" + p.getPrecioEntrada().toString() : "");
        lblMontoNecesario.setText(p.getMontoNecesario() != null ? "$" + p.getMontoNecesario().toString() : "");
        lblFechaPublicacion.setText(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "");
        lblCategoria.setText(p.getCategoria() != null ? p.getCategoria().getNombre() : "");

        StringBuilder historialStr = new StringBuilder();
        if (p.getHistorial() != null) {
            for (DTPropuestaEstado h : p.getHistorial()) {
                if (!historialStr.isEmpty()) historialStr.append("\n");
                historialStr.append(h.getEstado().toString()).append(" - ").append(h.getFechaCambio());
            }
        }
        txtHistorial.setText(historialStr.toString());

        StringBuilder tiposRetornoStr = new StringBuilder();
        if (p.getTiposRetorno() != null) {
            for (DTTipoRetorno t : p.getTiposRetorno()) {
                if (!tiposRetornoStr.isEmpty()) tiposRetornoStr.append(", ");
                tiposRetornoStr.append(t.toString());
            }
        }
        lblTiposRetorno.setText(tiposRetornoStr.toString());

        lblImagen.setImagen(p.getImagen());

        StringBuilder colaboradoresStr = new StringBuilder();
        if (p.getColaboraciones() != null) {
            for (DTColaboracion c : p.getColaboraciones()) {
                if (!colaboradoresStr.isEmpty()) colaboradoresStr.append(", ");
                colaboradoresStr.append(c.getColaborador().getNickname());
            }
        }
        txtColaboradores.setText(colaboradoresStr.toString());

        double montoTotal = 0;
        if (p.getColaboraciones() != null) {
            for (DTColaboracion c : p.getColaboraciones()) {
                if (c.getMonto() != null) montoTotal += c.getMonto();
            }
        }
        lblMontoTotal.setText("$" + String.format("%.2f", montoTotal));
    }

    private void estilizarLabelContenido(JComponent comp) {
        comp.setOpaque(true);
        comp.setBackground(new Color(240, 248, 255));
        comp.setForeground(new Color(30, 30, 30));
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comp.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }
}
