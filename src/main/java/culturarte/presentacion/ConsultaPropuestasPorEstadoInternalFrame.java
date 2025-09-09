package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTTipoRetorno;
import culturarte.logica.controlador.IPropuestaController;

public class ConsultaPropuestasPorEstadoInternalFrame extends JInternalFrame {

    private JComboBox<DTEstadoPropuesta> comboEstados;
    private JButton btnConsultar;
    private JList<DTPropuesta> jListPropuestas;
    private JLabel lblTitulo;
    private JTextArea txtDescripcion;
    private JLabel lblLugar;
    private JLabel lblFechaPrevista;
    private JLabel lblEstado;
    private JLabel lblImagen;
    private JTextArea txtColaboradores;
    private JLabel lblMontoTotal;

    private JLabel lblProponente;
    private JLabel lblPrecioEntrada;
    private JLabel lblCategoria;
    private JLabel lblMontoNecesario;
    private JLabel lblFechaPublicacion;
    private JTextArea txtHistorial;
    private JLabel lblTiposRetorno;


    private IPropuestaController PropuestaContr;

    public ConsultaPropuestasPorEstadoInternalFrame(IPropuestaController icp) {
        super("Consulta de Propuestas por Estado", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Estado:"));

        comboEstados = new JComboBox<>();
        cargarEstados();
        panelSuperior.add(comboEstados);

        btnConsultar = new JButton("Consultar");
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
        lblImagen = new JLabel();
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
            comboEstados.addItem(estado);
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

        if (p.getImagen() != null) {
            ImageIcon icon = new ImageIcon(p.getImagen());
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } else {
            lblImagen.setIcon(null);
        }

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

        txtHistorial.setText(p.getHistorial().stream()
                .map(h -> h.getEstado().toString() + " (" + h.getFechaCambio() + ")")
                .collect(Collectors.joining(", ")));
        lblTiposRetorno.setText(p.getTiposRetorno().stream()
                .map(DTTipoRetorno::toString)
                .collect(Collectors.joining(", ")));
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
        lblImagen.setIcon(null);
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