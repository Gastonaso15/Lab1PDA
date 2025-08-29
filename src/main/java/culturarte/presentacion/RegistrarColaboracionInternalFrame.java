package culturarte.presentacion;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTTipoRetorno;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistrarColaboracionInternalFrame extends JInternalFrame {

    private IPropuestaController PropuestaContr;
    private IUsuarioController UsuarioContr;

    private JList<DTPropuesta> jListPropuestas;
    private JTextField txtColaborador;
    private JTextField txtMonto;
    private JComboBox<DTTipoRetorno> comboRetorno;
    private JLabel lblTitulo;
    private JLabel lblDescripcion;
    private JLabel lblLugar;
    private JLabel lblFechaPrevista;
    private JLabel lblPrecioEntrada;
    private JLabel lblMontoNecesario;
    private JLabel lblProponente;
    private JLabel lblEstado;

    public RegistrarColaboracionInternalFrame(IPropuestaController icp, IUsuarioController icu) {
        super("Registrar Colaboración a Propuesta", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;
        UsuarioContr = icu;

        // Panel izquierdo - Lista de propuestas
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
        jListPropuestas = new JList<>(propuestas.toArray(new DTPropuesta[0]));
        JScrollPane scrollPropuestas = new JScrollPane(jListPropuestas);
        panelIzquierdo.add(new JLabel("Propuestas:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollPropuestas, BorderLayout.CENTER);

        // Panel derecho
        JPanel panelDerecho = new JPanel(new BorderLayout());

        // Panel de información de la propuesta
        JPanel panelInfo = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        lblTitulo = new JLabel("Título: ");
        lblDescripcion = new JLabel("Descripción: ");
        lblLugar = new JLabel("Lugar: ");
        lblFechaPrevista = new JLabel("Fecha Prevista: ");
        col1.add(lblTitulo);
        col1.add(lblDescripcion);
        col1.add(lblLugar);
        col1.add(lblFechaPrevista);

        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        lblPrecioEntrada = new JLabel("Precio Entrada: ");
        lblMontoNecesario = new JLabel("Monto Necesario: ");
        lblProponente = new JLabel("Proponente: ");
        lblEstado = new JLabel("Estado: ");
        col2.add(lblPrecioEntrada);
        col2.add(lblMontoNecesario);
        col2.add(lblProponente);
        col2.add(lblEstado);

        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 16);
        setFontToLabels(fontInfo, lblTitulo, lblDescripcion, lblLugar, lblFechaPrevista,
                lblPrecioEntrada, lblMontoNecesario, lblProponente, lblEstado);

        panelInfo.add(col1);
        panelInfo.add(col2);

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));

        panelFormulario.add(new JLabel("Colaborador (nickname):"));
        txtColaborador = new JTextField();
        panelFormulario.add(txtColaborador);

        panelFormulario.add(new JLabel("Monto:"));
        txtMonto = new JTextField();
        panelFormulario.add(txtMonto);

        panelFormulario.add(new JLabel("Tipo Retorno:"));
        comboRetorno = new JComboBox<>(DTTipoRetorno.values());
        panelFormulario.add(comboRetorno);

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // Configurar renderer para la lista
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

        // Listener para selección de propuesta
        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta propuesta = jListPropuestas.getSelectedValue();
                if (propuesta != null) {
                    mostrarDetallesPropuesta(propuesta);
                }
            }
        });

        // Listener para botón registrar
        btnRegistrar.addActionListener(e -> {
            try {
                DTPropuesta propuestaSeleccionada = jListPropuestas.getSelectedValue();
                if (propuestaSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una propuesta.");
                    return;
                }

                String nickname = txtColaborador.getText().trim();
                if (nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el nickname del colaborador.");
                    return;
                }

                String montoTexto = txtMonto.getText().trim();
                if (montoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el monto.");
                    return;
                }

                Double monto;
                try {
                    monto = Double.parseDouble(montoTexto);
                    if (monto <= 0) {
                        JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.");
                    return;
                }

                DTTipoRetorno tipoRetorno = (DTTipoRetorno) comboRetorno.getSelectedItem();

                PropuestaContr.registrarColaboracion(
                        propuestaSeleccionada.getTitulo(),
                        nickname,
                        monto,
                        tipoRetorno.toString()
                );

                JOptionPane.showMessageDialog(this, "Colaboración registrada con éxito");

                // Limpiar formulario
                txtColaborador.setText("");
                txtMonto.setText("");
                comboRetorno.setSelectedIndex(0);
                jListPropuestas.clearSelection();
                limpiarDetalles();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Listener para botón cancelar
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

        String proponenteInfo = (propuesta.getDTProponente() != null) ?
                propuesta.getDTProponente().getNickname() : "N/A";
        lblProponente.setText("Proponente: " + proponenteInfo);

        String estadoInfo = (propuesta.getEstadoActual() != null) ?
                propuesta.getEstadoActual().toString() : "N/A";
        lblEstado.setText("Estado: " + estadoInfo);
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
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}
