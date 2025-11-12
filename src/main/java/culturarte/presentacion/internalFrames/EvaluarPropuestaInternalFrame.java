package culturarte.presentacion.internalFrames;

import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.interfaces.IPropuestaController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class EvaluarPropuestaInternalFrame extends JInternalFrame {

    private final IPropuestaController propuestaController;
    private final JList<DTPropuesta> jListPropuestas;
    private final JLabel lblTitulo, lblLugar, lblFechaPrevista, lblCategoria;
    private final JLabel lblPrecioEntrada, lblMontoNecesario, lblProponente, lblEstado;
    private final JTextArea txtDescripcion;
    private final JRadioButton rbPublicar, rbCancelar;
    private final JButton btnConfirmar, btnCerrar;

    public EvaluarPropuestaInternalFrame(IPropuestaController controller) {
        super("Evaluar Propuesta", true, true, true, true);
        this.propuestaController = controller;

        setSize(1100, 650);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBackground(new Color(250, 250, 250));
        jListPropuestas = new JList<>();
        jListPropuestas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jListPropuestas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPropuestas = new JScrollPane(jListPropuestas);
        scrollPropuestas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Propuestas INGRESADAS",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelIzquierdo.add(scrollPropuestas, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBackground(new Color(250, 250, 250));

        JPanel panelDetalle = new JPanel();
        panelDetalle.setLayout(new BoxLayout(panelDetalle, BoxLayout.Y_AXIS));
        panelDetalle.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Detalles de la Propuesta",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 70, 70)
        ));
        panelDetalle.setBackground(Color.WHITE);

        JPanel gridDetalle = new JPanel(new GridBagLayout());
        gridDetalle.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        lblTitulo = createDetailLabel("Título: ");
        gbc.gridx = 0; gbc.gridy = 0;
        gridDetalle.add(createInfoLabel("Título:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblTitulo, gbc);

        lblProponente = createDetailLabel("Proponente: ");
        gbc.gridx = 0; gbc.gridy = 1;
        gridDetalle.add(createInfoLabel("Proponente:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblProponente, gbc);

        lblCategoria = createDetailLabel("Categoría: ");
        gbc.gridx = 0; gbc.gridy = 2;
        gridDetalle.add(createInfoLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblCategoria, gbc);

        lblEstado = createDetailLabel("Estado: ");
        gbc.gridx = 0; gbc.gridy = 3;
        gridDetalle.add(createInfoLabel("Estado:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridDetalle.add(createInfoLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextArea(2, 25);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescripcion.setBackground(new Color(240, 248, 255));
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 8, 5, 8)
        ));
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(null);
        gridDetalle.add(scrollDesc, gbc);
        gbc.fill = GridBagConstraints.NONE;

        lblLugar = createDetailLabel("Lugar: ");
        gbc.gridx = 0; gbc.gridy = 5;
        gridDetalle.add(createInfoLabel("Lugar:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblLugar, gbc);

        lblPrecioEntrada = createDetailLabel("Precio Entrada: ");
        gbc.gridx = 0; gbc.gridy = 6;
        gridDetalle.add(createInfoLabel("Precio Entrada:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblPrecioEntrada, gbc);

        lblMontoNecesario = createDetailLabel("Monto Necesario: ");
        gbc.gridx = 0; gbc.gridy = 7;
        gridDetalle.add(createInfoLabel("Monto Necesario:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblMontoNecesario, gbc);

        lblFechaPrevista = createDetailLabel("Fecha Prevista: ");
        gbc.gridx = 0; gbc.gridy = 8;
        gridDetalle.add(createInfoLabel("Fecha Prevista:"), gbc);
        gbc.gridx = 1;
        gridDetalle.add(lblFechaPrevista, gbc);

        panelDetalle.add(gridDetalle);

        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.Y_AXIS));
        panelAcciones.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Acción a Realizar",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelAcciones.setBackground(Color.WHITE);
        panelAcciones.setBorder(BorderFactory.createCompoundBorder(
            panelAcciones.getBorder(),
            new EmptyBorder(15, 15, 15, 15)
        ));

        rbPublicar = new JRadioButton("Publicar Propuesta");
        rbPublicar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rbPublicar.setBackground(Color.WHITE);
        rbCancelar = new JRadioButton("Cancelar Propuesta");
        rbCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rbCancelar.setBackground(Color.WHITE);
        ButtonGroup group = new ButtonGroup();
        group.add(rbPublicar);
        group.add(rbCancelar);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.add(rbPublicar);
        radioPanel.add(rbCancelar);

        panelAcciones.add(radioPanel);
        panelAcciones.add(Box.createVerticalStrut(15));

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        botonesPanel.setBackground(Color.WHITE);
        btnConfirmar = crearBoton("Confirmar", new Color(40, 50, 70), Color.WHITE);
        btnCerrar = crearBoton("Cerrar", new Color(60, 60, 60), Color.WHITE);
        botonesPanel.add(btnConfirmar);
        botonesPanel.add(btnCerrar);
        panelAcciones.add(botonesPanel);

        panelDerecho.add(panelDetalle, BorderLayout.CENTER);
        panelDerecho.add(panelAcciones, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta propuesta = jListPropuestas.getSelectedValue();
                if (propuesta != null) mostrarDetallesPropuesta(propuesta);
            }
        });

        btnConfirmar.addActionListener(e -> evaluarSeleccion());
        btnCerrar.addActionListener(e -> dispose());

        cargarPropuestas();
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

    private void mostrarDetallesPropuesta(DTPropuesta p) {
        lblTitulo.setText(p.getTitulo() != null ? p.getTitulo() : "");
        lblProponente.setText(p.getDTProponente() != null ? p.getDTProponente().getNombre() : "");
        lblCategoria.setText(p.getCategoria() != null ? p.getCategoria().getNombre() : "N/A");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "N/A");
        txtDescripcion.setText(p.getDescripcion() != null ? p.getDescripcion() : "");
        lblLugar.setText(p.getLugar() != null ? p.getLugar() : "");
        lblPrecioEntrada.setText("$" + (p.getPrecioEntrada() != null ? p.getPrecioEntrada().toString() : "0"));
        lblMontoNecesario.setText("$" + (p.getMontoNecesario() != null ? p.getMontoNecesario().toString() : "0"));
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        rbPublicar.setSelected(false);
        rbCancelar.setSelected(false);
    }

    private void limpiarDetalle() {
        lblTitulo.setText("");
        lblProponente.setText("");
        lblCategoria.setText("");
        lblEstado.setText("");
        txtDescripcion.setText("");
        lblLugar.setText("");
        lblPrecioEntrada.setText("");
        lblMontoNecesario.setText("");
        lblFechaPrevista.setText("");
        rbPublicar.setSelected(false);
        rbCancelar.setSelected(false);
        jListPropuestas.clearSelection();
    }

    private void cargarPropuestas() {
        List<DTPropuesta> propuestas = propuestaController.getPropuestasIngresadas();
        if (propuestas != null) {
            jListPropuestas.setListData(propuestas.toArray(new DTPropuesta[0]));
        } else {
            jListPropuestas.setListData(new DTPropuesta[0]);
        }
        limpiarDetalle();
    }

    private void evaluarSeleccion() {
        DTPropuesta propuesta = jListPropuestas.getSelectedValue();
        if (propuesta == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una propuesta", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!rbPublicar.isSelected() && !rbCancelar.isSelected()) {
            JOptionPane.showMessageDialog(this, "Seleccione Publicar o Cancelar", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean publicar = rbPublicar.isSelected();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea " + (publicar ? "PUBLICAR" : "CANCELAR") + " la propuesta \"" + propuesta.getTitulo() + "\"?",
                "Confirmar evaluación",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            propuestaController.evaluarPropuesta(propuesta.getTitulo(), publicar);
            JOptionPane.showMessageDialog(this, "Propuesta evaluada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPropuestas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
