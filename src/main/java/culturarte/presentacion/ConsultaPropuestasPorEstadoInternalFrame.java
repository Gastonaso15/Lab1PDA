package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTTipoRetorno;
import culturarte.logica.controlador.IPropuestaController;

public class ConsultaPropuestasPorEstadoInternalFrame extends JInternalFrame {

    private final IPropuestaController propuestaController;

    private final JComboBox<DTEstadoPropuesta> comboEstados;
    private final JButton btnConsultar;
    private final JList<DTPropuesta> jListPropuestas;
    private ImagenUIHelper.ImagenPanel lblImagen;

    private JTextArea txtDescripcion;
    private JTextArea txtColaboradores;
    private JTextArea txtHistorial;

    private JLabel lblTitulo;
    private JLabel lblLugar;
    private JLabel lblFechaPrevista;
    private JLabel lblEstado;
    private JLabel lblMontoTotal;
    private JLabel lblProponente;
    private JLabel lblPrecioEntrada;
    private JLabel lblCategoria;
    private JLabel lblMontoNecesario;
    private JLabel lblFechaPublicacion;
    private JLabel lblTiposRetorno;

    public ConsultaPropuestasPorEstadoInternalFrame(IPropuestaController icp) {
        super("Consulta de Propuestas por Estado", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        propuestaController = icp;

        // Panel superior: selección de estado y botón consultar
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Estado:"));

        comboEstados = new JComboBox<>();
        cargarEstados();
        panelSuperior.add(comboEstados);

        btnConsultar = new JButton("Consultar");
        panelSuperior.add(btnConsultar);
        add(panelSuperior, BorderLayout.NORTH);

        // Lista de propuestas
        jListPropuestas = new JList<>();
        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        scrollList.setPreferredSize(new Dimension(200, 0));
        add(scrollList, BorderLayout.WEST);

        // Panel de detalles
        JPanel panelDetalles = new JPanel(new GridLayout(5, 3, 5, 5));
        inicializarPanelDetalles(panelDetalles);
        add(panelDetalles, BorderLayout.CENTER);

        // Listeners
        btnConsultar.addActionListener(e -> consultarPropuestasPorEstado());
        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta p = jListPropuestas.getSelectedValue();
                if (p != null) mostrarDetalles(p);
            }
        });
    }

    private void inicializarPanelDetalles(JPanel panel) {
        // Etiquetas y componentes
        lblTitulo = crearLabelPanel(panel, "Título:");
        txtDescripcion = crearTextAreaPanel(panel, "Descripción:");
        lblProponente = crearLabelPanel(panel, "Proponente:");
        lblLugar = crearLabelPanel(panel, "Lugar:");
        lblFechaPrevista = crearLabelPanel(panel, "Fecha Prevista:");
        lblPrecioEntrada = crearLabelPanel(panel, "Precio Entrada:");
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        panel.add(new JLabel("Imagen:"));
        panel.add(lblImagen);
        estilizarLabelContenido(lblImagen);
        lblCategoria = crearLabelPanel(panel, "Categoría:");
        lblEstado = crearLabelPanel(panel, "Estado:");
        txtColaboradores = crearTextAreaPanel(panel, "Colaboradores:");
        lblMontoTotal = crearLabelPanel(panel, "Monto total recaudado:");
        lblMontoNecesario = crearLabelPanel(panel, "Monto Necesario:");
        lblFechaPublicacion = crearLabelPanel(panel, "Fecha Publicación:");
        txtHistorial = crearTextAreaPanel(panel, "Historial:");
        lblTiposRetorno = crearLabelPanel(panel, "Tipos de Retorno:");
    }

    private JLabel crearLabelPanel(JPanel panel, String texto) {
        panel.add(new JLabel(texto));
        JLabel label = new JLabel();
        panel.add(label);
        estilizarLabelContenido(label);
        return label;
    }

    private JTextArea crearTextAreaPanel(JPanel panel, String texto) {
        panel.add(new JLabel(texto));
        JTextArea area = crearTextArea();
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(scroll);
        return area;
    }

    private JTextArea crearTextArea() {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFont(area.getFont().deriveFont(Font.BOLD));
        area.setRows(5);
        return area;
    }

    private void cargarEstados() {
        for (DTEstadoPropuesta estado : DTEstadoPropuesta.values()) comboEstados.addItem(estado);
    }

    private void consultarPropuestasPorEstado() {
        DTEstadoPropuesta estado = (DTEstadoPropuesta) comboEstados.getSelectedItem();
        if (estado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estado", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            List<DTPropuesta> propuestas = Optional.ofNullable(propuestaController.devolverPropuestasPorEstado(estado))
                    .orElse(Collections.emptyList());

            DefaultListModel<DTPropuesta> modelo = new DefaultListModel<>();
            propuestas.forEach(modelo::addElement);
            jListPropuestas.setModel(modelo);

            limpiarDetalles();

            if (propuestas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay propuestas en estado " + estado.name(),
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar propuestas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalles(DTPropuesta p) {
        lblTitulo.setText(Optional.ofNullable(p.getTitulo()).orElse(""));
        txtDescripcion.setText(Optional.ofNullable(p.getDescripcion()).orElse(""));
        lblLugar.setText(Optional.ofNullable(p.getLugar()).orElse(""));
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Sin estado");
        lblImagen.setImagen(p.getImagen());

        String colaboradores = Optional.ofNullable(p.getColaboraciones()).orElse(Collections.emptyList())
                .stream().map(c -> c.getColaborador().getNickname())
                .collect(Collectors.joining(", "));
        txtColaboradores.setText(colaboradores);

        double montoTotal = Optional.ofNullable(p.getColaboraciones()).orElse(Collections.emptyList())
                .stream().mapToDouble(c -> c.getMonto() != null ? c.getMonto() : 0).sum();
        lblMontoTotal.setText(String.format("%.2f", montoTotal));
        lblMontoNecesario.setText(p.getMontoNecesario() != null ? String.format("%.2f", p.getMontoNecesario()) : "0.00");
        lblPrecioEntrada.setText(p.getPrecioEntrada() != null ? String.format("%.2f", p.getPrecioEntrada()) : "0.00");

        lblFechaPublicacion.setText(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "");
        lblCategoria.setText(p.getCategoria() != null ? p.getCategoria().getNombre() : "");
        lblProponente.setText(p.getDTProponente() != null ? p.getDTProponente().getNombre() : "N/A");

        txtHistorial.setText(Optional.ofNullable(p.getHistorial()).orElse(Collections.emptyList())
                .stream().map(h -> h.getEstado().toString() + " (" + h.getFechaCambio() + ")")
                .collect(Collectors.joining(", ")));

        lblTiposRetorno.setText(Optional.ofNullable(p.getTiposRetorno()).orElse(Collections.emptyList())
                .stream().map(DTTipoRetorno::toString).collect(Collectors.joining(", ")));
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
