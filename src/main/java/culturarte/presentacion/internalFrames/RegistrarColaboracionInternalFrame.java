package culturarte.presentacion.internalFrames;

import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.DTs.DTTipoRetorno;
import culturarte.servicios.interfaces.IPropuestaController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class RegistrarColaboracionInternalFrame extends JInternalFrame {

    private final JList<DTPropuesta> jListPropuestas;
    private final JTextField txtColaborador;
    private final JTextField txtMonto;
    private final JComboBox<DTTipoRetorno> comboRetorno;
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

    public RegistrarColaboracionInternalFrame(IPropuestaController icp) {
        super("Registrar Colaboración a Propuesta", true, true, true, true);
        setSize(1100, 650);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        PropuestaContr = icp;

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBackground(new Color(250, 250, 250));
        List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
        jListPropuestas = new JList<>(propuestas.toArray(new DTPropuesta[0]));
        jListPropuestas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jListPropuestas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPropuestas = new JScrollPane(jListPropuestas);
        scrollPropuestas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Propuestas Disponibles",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelIzquierdo.add(scrollPropuestas, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBackground(new Color(250, 250, 250));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Información de la Propuesta",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelInfo.setBackground(Color.WHITE);

        JPanel gridInfo = new JPanel(new GridBagLayout());
        gridInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;

        lblTitulo = createDetailLabel("Título: ");
        gbc.gridx = 0; gbc.gridy = 0;
        gridInfo.add(createInfoLabel("Título:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblTitulo, gbc);

        lblDescripcion = createDetailLabel("Descripción: ");
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridInfo.add(createInfoLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblDescripcion, gbc);
        gbc.fill = GridBagConstraints.NONE;

        lblLugar = createDetailLabel("Lugar: ");
        gbc.gridx = 0; gbc.gridy = 2;
        gridInfo.add(createInfoLabel("Lugar:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblLugar, gbc);

        lblFechaPrevista = createDetailLabel("Fecha Prevista: ");
        gbc.gridx = 0; gbc.gridy = 3;
        gridInfo.add(createInfoLabel("Fecha Prevista:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblFechaPrevista, gbc);

        lblCategoria = createDetailLabel("Categoría: ");
        gbc.gridx = 0; gbc.gridy = 4;
        gridInfo.add(createInfoLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblCategoria, gbc);

        lblPrecioEntrada = createDetailLabel("Precio Entrada: ");
        gbc.gridx = 0; gbc.gridy = 5;
        gridInfo.add(createInfoLabel("Precio Entrada:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblPrecioEntrada, gbc);

        lblMontoNecesario = createDetailLabel("Monto Necesario: ");
        gbc.gridx = 0; gbc.gridy = 6;
        gridInfo.add(createInfoLabel("Monto Necesario:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblMontoNecesario, gbc);

        lblMontoTotal = createDetailLabel("Monto Recaudado: ");
        lblMontoTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMontoTotal.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0; gbc.gridy = 7;
        gridInfo.add(createInfoLabel("Monto Recaudado:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblMontoTotal, gbc);

        lblProponente = createDetailLabel("Proponente: ");
        gbc.gridx = 0; gbc.gridy = 8;
        gridInfo.add(createInfoLabel("Proponente:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblProponente, gbc);

        lblEstado = createDetailLabel("Estado: ");
        gbc.gridx = 0; gbc.gridy = 9;
        gridInfo.add(createInfoLabel("Estado:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblEstado, gbc);

        panelInfo.add(gridInfo);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Datos de la Colaboración",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelFormulario.setBackground(Color.WHITE);

        JPanel gridForm = new JPanel(new GridBagLayout());
        gridForm.setBackground(Color.WHITE);
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(10, 15, 10, 15);
        gbcForm.anchor = GridBagConstraints.WEST;

        gbcForm.gridx = 0; gbcForm.gridy = 0;
        gridForm.add(createInfoLabel("Colaborador (nickname):"), gbcForm);
        gbcForm.gridx = 1;
        txtColaborador = new JTextField(20);
        estilizarTextField(txtColaborador);
        gridForm.add(txtColaborador, gbcForm);

        gbcForm.gridx = 0; gbcForm.gridy = 1;
        gridForm.add(createInfoLabel("Monto:"), gbcForm);
        gbcForm.gridx = 1;
        txtMonto = new JTextField(20);
        estilizarTextField(txtMonto);
        gridForm.add(txtMonto, gbcForm);

        gbcForm.gridx = 0; gbcForm.gridy = 2;
        gridForm.add(createInfoLabel("Tipo Retorno:"), gbcForm);
        gbcForm.gridx = 1;
        comboRetorno = new JComboBox<>();
        comboRetorno.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboRetorno.setBackground(Color.WHITE);
        comboRetorno.setPreferredSize(new Dimension(200, 30));
        comboRetorno.setMaximumRowCount(10);
        gridForm.add(comboRetorno, gbcForm);

        panelFormulario.add(gridForm);

        panelDerecho.add(panelInfo, BorderLayout.CENTER);
        panelDerecho.add(panelFormulario, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(245, 248, 250));
        panelBotones.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton btnRegistrar = crearBoton("Registrar", new Color(40, 50, 70), Color.WHITE);
        JButton btnCancelar = crearBoton("Cancelar", new Color(60, 60, 60), Color.WHITE);
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

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

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta propuesta = jListPropuestas.getSelectedValue();
                if (propuesta != null) {
                    mostrarDetallesPropuesta(propuesta);
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            try {
                DTPropuesta propuestaSeleccionada = jListPropuestas.getSelectedValue();
                if (propuestaSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una propuesta.", "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nickname = txtColaborador.getText().trim();
                if (nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el nickname del colaborador.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (propuestaSeleccionada.getColaboraciones() != null) {
                    for (DTColaboracion c : propuestaSeleccionada.getColaboraciones()) {
                        if (c.getColaborador() != null && nickname.equalsIgnoreCase(c.getColaborador().getNickname())) {
                            JOptionPane.showMessageDialog(this,
                                    "El colaborador '" + nickname + "' ya ha colaborado con esta propuesta.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                String montoTexto = txtMonto.getText().trim();
                if (montoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el monto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double monto;
                try {
                    monto = Double.parseDouble(montoTexto);
                    if (monto <= 0) {
                        JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DTTipoRetorno tipoRetorno = (DTTipoRetorno) comboRetorno.getSelectedItem();
                if (tipoRetorno == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de retorno.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PropuestaContr.registrarColaboracion(
                        propuestaSeleccionada.getTitulo(),
                        nickname,
                        monto,
                        tipoRetorno.toString()
                );

                JOptionPane.showMessageDialog(this, "Colaboración registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                txtColaborador.setText("");
                txtMonto.setText("");
                comboRetorno.setSelectedIndex(0);
                jListPropuestas.clearSelection();
                limpiarDetalles();

                List<DTPropuesta> propuestasActualizadas = PropuestaContr.devolverTodasLasPropuestas();
                jListPropuestas.setListData(propuestasActualizadas.toArray(new DTPropuesta[0]));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setOpaque(true);
        label.setBackground(new Color(240, 248, 255));
        label.setForeground(new Color(30, 30, 30));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return label;
    }

    private void estilizarTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 8, 5, 8)
        ));
        field.setBackground(Color.WHITE);
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setOpaque(true);
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setContentAreaFilled(true);
        boton.setPreferredSize(new Dimension(120, 35));
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

    private void mostrarDetallesPropuesta(DTPropuesta propuesta) {
        lblTitulo.setText(propuesta.getTitulo() != null ? propuesta.getTitulo() : "");
        String desc = propuesta.getDescripcion() != null ? propuesta.getDescripcion() : "";
        if (desc.length() > 60) {
            desc = desc.substring(0, 60) + "...";
        }
        lblDescripcion.setText(desc);
        lblLugar.setText(propuesta.getLugar() != null ? propuesta.getLugar() : "");
        lblFechaPrevista.setText(propuesta.getFechaPrevista() != null ? propuesta.getFechaPrevista().toString() : "");
        lblPrecioEntrada.setText(propuesta.getPrecioEntrada() != null ? "$" + propuesta.getPrecioEntrada().toString() : "");
        lblMontoNecesario.setText(propuesta.getMontoNecesario() != null ? "$" + propuesta.getMontoNecesario().toString() : "");

        double montoTotal = 0;
        if (propuesta.getColaboraciones() != null) {
            for (DTColaboracion c : propuesta.getColaboraciones()) {
                if (c.getMonto() != null) {
                    montoTotal += c.getMonto();
                }
            }
        }
        lblMontoTotal.setText("$" + String.format("%.2f", montoTotal));

        lblCategoria.setText(propuesta.getCategoria() != null ? propuesta.getCategoria().getNombre() : "");

        String proponenteInfo = (propuesta.getDTProponente() != null) ?
                propuesta.getDTProponente().getNickname() : "N/A";
        lblProponente.setText(proponenteInfo);

        String estadoInfo = (propuesta.getEstadoActual() != null) ?
                propuesta.getEstadoActual().toString() : "N/A";
        lblEstado.setText(estadoInfo);

        comboRetorno.removeAllItems();
        if (propuesta.getTiposRetorno() != null) {
            for (DTTipoRetorno tipo : propuesta.getTiposRetorno()) {
                comboRetorno.addItem(tipo);
            }
        }
    }

    private void limpiarDetalles() {
        lblTitulo.setText("");
        lblDescripcion.setText("");
        lblLugar.setText("");
        lblFechaPrevista.setText("");
        lblPrecioEntrada.setText("");
        lblMontoNecesario.setText("");
        lblProponente.setText("");
        lblEstado.setText("");
        lblCategoria.setText("");
        lblMontoTotal.setText("");
        comboRetorno.removeAllItems();
    }
}
