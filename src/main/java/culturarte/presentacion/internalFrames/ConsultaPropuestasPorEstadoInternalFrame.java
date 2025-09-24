
package culturarte.presentacion.internalFrames;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import culturarte.logica.DTs.*;
import culturarte.logica.controladores.IPropuestaController;
import culturarte.presentacion.helpers.ImagenUIHelper;

public class ConsultaPropuestasPorEstadoInternalFrame extends JInternalFrame {

    private final JComboBox<DTEstadoPropuesta> comboEstados;
    private final JList<DTPropuesta> jListPropuestas;
    private final JLabel lblTitulo;
    private final JTextArea txtDescripcion;
    private final JLabel lblLugar;
    private final JLabel lblFechaPrevista;
    private final JLabel lblEstado;
    private final ImagenUIHelper.ImagenPanel lblImagen;
    private final JTextArea txtColaboradores;
    private final JLabel lblMontoTotal;
    private final JLabel lblProponente;
    private final JLabel lblPrecioEntrada;
    private final JLabel lblCategoria;
    private final JLabel lblMontoNecesario;
    private final JLabel lblFechaPublicacion;
    private final JTextArea txtHistorial;
    private final JLabel lblTiposRetorno;

    private final IPropuestaController PropuestaContr;

    public ConsultaPropuestasPorEstadoInternalFrame(IPropuestaController icp) {
        super("Consulta de Propuestas por Estado", true, true, true, true);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Estado:"));

        comboEstados = new JComboBox<>();
        cargarEstados();
        panelSuperior.add(comboEstados);

        JButton btnConsultar = new JButton("Consultar");
        panelSuperior.add(btnConsultar);

        add(panelSuperior, BorderLayout.NORTH);

        jListPropuestas = new JList<>();
        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        scrollList.setPreferredSize(new Dimension(200, 0));
        add(scrollList, BorderLayout.WEST);

        JPanel panelDetalles = new JPanel(new GridLayout(5, 3, 5, 5));

        panelDetalles.add(new JLabel("Título:"));
        lblTitulo = new JLabel();
        panelDetalles.add(lblTitulo);
        estilizarLabelContenido(lblTitulo);

        panelDetalles.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setRows(5);
        txtDescripcion.setFont(txtDescripcion.getFont().deriveFont(Font.BOLD));
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(BorderFactory.createEmptyBorder());
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDetalles.add(scrollDesc);
        estilizarLabelContenido(txtDescripcion);

        panelDetalles.add(new JLabel("Proponente:"));
        lblProponente = new JLabel();
        panelDetalles.add(lblProponente);
        estilizarLabelContenido(lblProponente);

        panelDetalles.add(new JLabel("Lugar:"));
        lblLugar = new JLabel();
        panelDetalles.add(lblLugar);
        estilizarLabelContenido(lblLugar);

        panelDetalles.add(new JLabel("Fecha Prevista:"));
        lblFechaPrevista = new JLabel();
        panelDetalles.add(lblFechaPrevista);
        estilizarLabelContenido(lblFechaPrevista);

        panelDetalles.add(new JLabel("Precio Entrada:"));
        lblPrecioEntrada = new JLabel();
        panelDetalles.add(lblPrecioEntrada);
        estilizarLabelContenido(lblPrecioEntrada);

        panelDetalles.add(new JLabel("Imagen:"));
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        panelDetalles.add(lblImagen);
        estilizarLabelContenido(lblImagen);

        panelDetalles.add(new JLabel("Categoría:"));
        lblCategoria = new JLabel();
        panelDetalles.add(lblCategoria);
        estilizarLabelContenido(lblCategoria);

        panelDetalles.add(new JLabel("Estado:"));
        lblEstado = new JLabel();
        panelDetalles.add(lblEstado);
        estilizarLabelContenido(lblEstado);

        panelDetalles.add(new JLabel("Colaboradores:"));
        txtColaboradores = new JTextArea();
        txtColaboradores.setLineWrap(true);
        txtColaboradores.setWrapStyleWord(true);
        txtColaboradores.setEditable(false);
        txtColaboradores.setRows(5);
        txtColaboradores.setFont(txtColaboradores.getFont().deriveFont(Font.BOLD));
        JScrollPane scrollColab = new JScrollPane(txtColaboradores);
        scrollColab.setBorder(BorderFactory.createEmptyBorder());
        scrollColab.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDetalles.add(scrollColab);
        estilizarLabelContenido(txtColaboradores);

        panelDetalles.add(new JLabel("Monto total recaudado:"));
        lblMontoTotal = new JLabel();
        panelDetalles.add(lblMontoTotal);
        estilizarLabelContenido(lblMontoTotal);

        panelDetalles.add(new JLabel("Monto Necesario:"));
        lblMontoNecesario = new JLabel();
        panelDetalles.add(lblMontoNecesario);
        estilizarLabelContenido(lblMontoNecesario);

        panelDetalles.add(new JLabel("Fecha Publicación:"));
        lblFechaPublicacion = new JLabel();
        panelDetalles.add(lblFechaPublicacion);
        estilizarLabelContenido(lblFechaPublicacion);

        panelDetalles.add(new JLabel("Historial:"));
        txtHistorial = new JTextArea();
        txtHistorial.setLineWrap(true);
        txtHistorial.setWrapStyleWord(true);
        txtHistorial.setEditable(false);
        txtHistorial.setRows(5);
        txtHistorial.setFont(txtHistorial.getFont().deriveFont(Font.BOLD));
        txtHistorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane scrollHist = new JScrollPane(txtHistorial);
        scrollHist.setBorder(BorderFactory.createEmptyBorder());
        scrollHist.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDetalles.add(scrollHist);
        estilizarLabelContenido(txtHistorial);

        panelDetalles.add(new JLabel("Tipos de Retorno:"));
        lblTiposRetorno = new JLabel();
        panelDetalles.add(lblTiposRetorno);
        estilizarLabelContenido(lblTiposRetorno);

        add(panelDetalles, BorderLayout.CENTER);

        btnConsultar.addActionListener(e -> consultarPropuestasPorEstado());

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta p = jListPropuestas.getSelectedValue();
                if (p != null) {
                    mostrarDetalles(p);
                }
            }
        });
    }

    private void cargarEstados() {
        for (DTEstadoPropuesta estado : DTEstadoPropuesta.values()) {
            if (estado != DTEstadoPropuesta.INGRESADA) {
                comboEstados.addItem(estado);
            }
        }
    }

    private void consultarPropuestasPorEstado() {
        DTEstadoPropuesta estadoSeleccionado = (DTEstadoPropuesta) comboEstados.getSelectedItem();

        if (estadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estado", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<DTPropuesta> propuestas = PropuestaContr.devolverPropuestasPorEstado(estadoSeleccionado);

            DefaultListModel<DTPropuesta> modeloLista = new DefaultListModel<>();
            for (DTPropuesta p : propuestas) {
                modeloLista.addElement(p);
            }
            jListPropuestas.setModel(modeloLista);

            limpiarDetalles();

            if (propuestas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay propuestas en estado " + estadoSeleccionado.name(),
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al consultar propuestas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalles(DTPropuesta p) {
        lblTitulo.setText(p.getTitulo());
        txtDescripcion.setText(p.getDescripcion());
        lblLugar.setText(p.getLugar());
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Sin estado");

        lblImagen.setImagen(p.getImagen());

        StringBuilder colaboradoresStr = new StringBuilder();
        for (DTColaboracion c : p.getColaboraciones()) {
            if (!colaboradoresStr.isEmpty()) colaboradoresStr.append(", ");
            colaboradoresStr.append(c.getColaborador().getNickname());
        }
        txtColaboradores.setText(colaboradoresStr.toString());

        double montoTotal = 0;
        for (DTColaboracion c : p.getColaboraciones()) {
            if (c.getMonto() != null) montoTotal += c.getMonto();
        }
        lblMontoTotal.setText(String.valueOf(montoTotal));

        StringBuilder historialStr = new StringBuilder();
        for (DTPropuestaEstado h : p.getHistorial()) {
            if (!historialStr.isEmpty()) historialStr.append(", ");
            historialStr.append(h.getEstado().toString()).append(" (").append(h.getFechaCambio()).append(")");
        }
        txtHistorial.setText(historialStr.toString());

        StringBuilder tiposRetornoStr = new StringBuilder();
        for (DTTipoRetorno t : p.getTiposRetorno()) {
            if (!tiposRetornoStr.isEmpty()) tiposRetornoStr.append(", ");
            tiposRetornoStr.append(t.toString());
        }
        lblTiposRetorno.setText(tiposRetornoStr.toString());
        lblPrecioEntrada.setText(p.getPrecioEntrada() != null ? p.getPrecioEntrada().toString() : "");
        lblMontoNecesario.setText(p.getMontoNecesario() != null ? p.getMontoNecesario().toString() : "");
        lblFechaPublicacion.setText(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "");
        lblCategoria.setText(p.getCategoria() != null ? p.getCategoria().getNombre() : "");
        lblProponente.setText(p.getDTProponente().getNombre());
    }

    private void limpiarDetalles() {
        lblTitulo.setText("");
        txtDescripcion.setText("");
        lblLugar.setText("");
        lblFechaPrevista.setText("");
        lblEstado.setText("");
        lblImagen.setImagen(null);
        txtColaboradores.setText("");
        lblMontoTotal.setText("");

        lblCategoria.setText("");
        lblPrecioEntrada.setText("");
        lblProponente.setText("");
        lblMontoNecesario.setText("");
        lblFechaPublicacion.setText("");
        txtHistorial.setText("");
        lblTiposRetorno.setText("");
    }

    private void estilizarLabelContenido(JComponent comp) {
        comp.setOpaque(true);
        comp.setBackground(new Color(200, 230, 250));
        comp.setForeground(Color.BLACK);
        comp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }
}
