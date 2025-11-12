package culturarte.presentacion.internalFrames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

import culturarte.servicios.DTs.*;
import culturarte.servicios.interfaces.IPropuestaController;
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
        setSize(1200, 700);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        PropuestaContr = icp;

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(new Color(250, 250, 250));
        panelSuperior.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Filtro de Búsqueda",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));

        JLabel lblEstadoFiltro = createInfoLabel("Estado:");
        panelSuperior.add(lblEstadoFiltro);

        comboEstados = new JComboBox<>();
        comboEstados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboEstados.setBackground(Color.WHITE);
        cargarEstados();
        panelSuperior.add(comboEstados);

        JButton btnConsultar = crearBoton("Consultar", new Color(40, 50, 70), Color.WHITE);
        panelSuperior.add(btnConsultar);

        add(panelSuperior, BorderLayout.NORTH);

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

        JPanel gridDetalles = new JPanel(new GridBagLayout());
        gridDetalles.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        gridDetalles.add(createInfoLabel("Título:"), gbc);
        gbc.gridx = 1;
        lblTitulo = new JLabel();
        estilizarLabelContenido(lblTitulo);
        gridDetalles.add(lblTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridDetalles.add(createInfoLabel("Descripción:"), gbc);
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
        gridDetalles.add(scrollDesc, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 2;
        gridDetalles.add(createInfoLabel("Proponente:"), gbc);
        gbc.gridx = 1;
        lblProponente = new JLabel();
        estilizarLabelContenido(lblProponente);
        gridDetalles.add(lblProponente, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gridDetalles.add(createInfoLabel("Lugar:"), gbc);
        gbc.gridx = 1;
        lblLugar = new JLabel();
        estilizarLabelContenido(lblLugar);
        gridDetalles.add(lblLugar, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gridDetalles.add(createInfoLabel("Fecha Prevista:"), gbc);
        gbc.gridx = 1;
        lblFechaPrevista = new JLabel();
        estilizarLabelContenido(lblFechaPrevista);
        gridDetalles.add(lblFechaPrevista, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gridDetalles.add(createInfoLabel("Precio Entrada:"), gbc);
        gbc.gridx = 1;
        lblPrecioEntrada = new JLabel();
        estilizarLabelContenido(lblPrecioEntrada);
        gridDetalles.add(lblPrecioEntrada, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gridDetalles.add(createInfoLabel("Monto Necesario:"), gbc);
        gbc.gridx = 1;
        lblMontoNecesario = new JLabel();
        estilizarLabelContenido(lblMontoNecesario);
        gridDetalles.add(lblMontoNecesario, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gridDetalles.add(createInfoLabel("Fecha Publicación:"), gbc);
        gbc.gridx = 1;
        lblFechaPublicacion = new JLabel();
        estilizarLabelContenido(lblFechaPublicacion);
        gridDetalles.add(lblFechaPublicacion, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        gridDetalles.add(createInfoLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        lblCategoria = new JLabel();
        estilizarLabelContenido(lblCategoria);
        gridDetalles.add(lblCategoria, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        gridDetalles.add(createInfoLabel("Estado:"), gbc);
        gbc.gridx = 1;
        lblEstado = new JLabel();
        estilizarLabelContenido(lblEstado);
        gridDetalles.add(lblEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        gridDetalles.add(createInfoLabel("Tipos de Retorno:"), gbc);
        gbc.gridx = 1;
        lblTiposRetorno = new JLabel();
        estilizarLabelContenido(lblTiposRetorno);
        gridDetalles.add(lblTiposRetorno, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridDetalles.add(createInfoLabel("Colaboradores:"), gbc);
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
        gridDetalles.add(scrollColab, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 12;
        gridDetalles.add(createInfoLabel("Monto total recaudado:"), gbc);
        gbc.gridx = 1;
        lblMontoTotal = new JLabel();
        estilizarLabelContenido(lblMontoTotal);
        lblMontoTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMontoTotal.setForeground(new Color(0, 100, 0));
        gridDetalles.add(lblMontoTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 13;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridDetalles.add(createInfoLabel("Historial:"), gbc);
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
        gridDetalles.add(scrollHist, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLoweredBevelBorder());
        gridDetalles.add(lblImagen, gbc);

        panelDetalles.add(gridDetalles);
        panelDetalles.add(Box.createVerticalGlue());

        add(scrollDetalles, BorderLayout.CENTER);

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

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(8, 20, 8, 20)
        ));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        return boton;
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
        lblTitulo.setText(p.getTitulo() != null ? p.getTitulo() : "");
        txtDescripcion.setText(p.getDescripcion() != null ? p.getDescripcion() : "");
        lblLugar.setText(p.getLugar() != null ? p.getLugar() : "");
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Sin estado");
        lblPrecioEntrada.setText(p.getPrecioEntrada() != null ? "$" + p.getPrecioEntrada().toString() : "");
        lblMontoNecesario.setText(p.getMontoNecesario() != null ? "$" + p.getMontoNecesario().toString() : "");
        lblFechaPublicacion.setText(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "");
        lblCategoria.setText(p.getCategoria() != null ? p.getCategoria().getNombre() : "");
        lblProponente.setText(p.getDTProponente() != null ? p.getDTProponente().getNombre() : "");

        lblImagen.setImagen(p.getImagen());

        StringBuilder colaboradoresStr = new StringBuilder();
        if (p.getColaboraciones() != null) {
            for (DTColaboracion c : p.getColaboraciones()) {
                if (!colaboradoresStr.isEmpty()) colaboradoresStr.append(", ");
                if (c.getColaborador() != null) {
                    colaboradoresStr.append(c.getColaborador().getNickname());
                }
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
        comp.setBackground(new Color(240, 248, 255));
        comp.setForeground(new Color(30, 30, 30));
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comp.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }
}
