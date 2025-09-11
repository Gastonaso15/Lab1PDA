package culturarte.presentacion.internalFrames;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.DTs.DTTipoRetorno;
import culturarte.logica.controladores.IPropuestaController;
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
        setSize(1000, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

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

    private void cargarPropuestas() {
        List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
        DefaultListModel<DTPropuesta> modeloLista = new DefaultListModel<>();
        for (DTPropuesta p : propuestas) {
            modeloLista.addElement(p);
        }
        jListPropuestas.setModel(modeloLista);
    }

    private void mostrarDetalles(DTPropuesta p) {
        lblTitulo.setText(p.getTitulo());
        txtDescripcion.setText(p.getDescripcion());
        lblProponente.setText(p.getDTProponente().getNombre());
        lblLugar.setText(p.getLugar());
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Sin estado");
        lblPrecioEntrada.setText(p.getPrecioEntrada() != null ? p.getPrecioEntrada().toString() : "");
        lblMontoNecesario.setText(p.getMontoNecesario() != null ? p.getMontoNecesario().toString() : "");
        lblFechaPublicacion.setText(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "");
        lblCategoria.setText(p.getCategoria() != null ? p.getCategoria().getNombre() : "");
        txtHistorial.setText(p.getHistorial().stream()
                .map(h -> h.getEstado().toString() + " (" + h.getFechaCambio() + ")")
                .collect(Collectors.joining(", ")));
        lblTiposRetorno.setText(p.getTiposRetorno().stream()
                .map(DTTipoRetorno::toString)
                .collect(Collectors.joining(", ")));

        lblImagen.setImagen(p.getImagen());

        String colaboradores = p.getColaboraciones()
                .stream()
                .map(c -> c.getColaborador().getNickname())
                .collect(Collectors.joining(", "));
        txtColaboradores.setText(colaboradores);

        double montoTotal = p.getColaboraciones()
                .stream()
                .mapToDouble(c -> c.getMonto() != null ? c.getMonto() : 0)
                .sum();
        lblMontoTotal.setText(String.valueOf(montoTotal));
    }

    private void estilizarLabelContenido(JComponent comp) {
        comp.setOpaque(true);
        comp.setBackground(new Color(200, 230, 250));
        comp.setForeground(Color.BLACK);
        comp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }
}