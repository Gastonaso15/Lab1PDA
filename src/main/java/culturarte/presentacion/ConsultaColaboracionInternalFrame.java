package culturarte.presentacion;

import culturarte.logica.DT.DTColaboracion;
import culturarte.logica.DT.DTColaborador;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaColaboracionInternalFrame extends JInternalFrame {

    private IPropuestaController PropuestaContr;
    private IUsuarioController UsuarioContr;

    // Componentes UI
    private JComboBox<String> comboColaboradores;
    private JList<String> listColaboraciones;
    private DefaultListModel<String> modelColaboraciones;

    // Panel de detalles de la colaboración seleccionada
    private JLabel lblPropuestaTitulo;
    private JLabel lblProponenteNick;
    private JLabel lblColaboradorNick;
    private JLabel lblMonto;
    private JLabel lblTipoRetorno;
    private JLabel lblFechaHora;
    private JLabel lblEstadoPropuesta;
    private JLabel lblMontoNecesario;
    private JLabel lblTotalRecaudado;

    // Datos actuales
    private DTColaborador colaboradorActual;

    public ConsultaColaboracionInternalFrame(IPropuestaController icp, IUsuarioController icu) {
        super("Consultar Colaboración a Propuesta", true, true, true, true);
        setSize(1200, 600);
        setLayout(new BorderLayout());

        PropuestaContr = icp;
        UsuarioContr = icu;

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        cargarColaboradores();
    }

    private void inicializarComponentes() {
        // ComboBox para seleccionar colaborador
        comboColaboradores = new JComboBox<>();
        comboColaboradores.setPreferredSize(new Dimension(200, 30));

        // Lista para mostrar colaboraciones
        modelColaboraciones = new DefaultListModel<>();
        listColaboraciones = new JList<>(modelColaboraciones);
        listColaboraciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Labels para mostrar detalles de la colaboración
        lblPropuestaTitulo = new JLabel("Título: ");
        lblProponenteNick = new JLabel("Proponente: ");
        lblColaboradorNick = new JLabel("Colaborador: ");
        lblMonto = new JLabel("Monto: ");
        lblTipoRetorno = new JLabel("Tipo de Retorno: ");
        lblFechaHora = new JLabel("Fecha y Hora: ");
        lblEstadoPropuesta = new JLabel("Estado Propuesta: ");
        lblMontoNecesario = new JLabel("Monto Necesario: ");
        lblTotalRecaudado = new JLabel("Total Recaudado: ");

        // Configurar fuente para los labels
        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 14);
        setFontToLabels(fontInfo, lblPropuestaTitulo, lblProponenteNick, lblColaboradorNick,
                lblMonto, lblTipoRetorno, lblFechaHora, lblEstadoPropuesta,
                lblMontoNecesario, lblTotalRecaudado);
    }

    private void configurarLayout() {
        // Panel superior - Selección de colaborador
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Seleccionar Colaborador"));
        panelSuperior.add(new JLabel("Colaborador: "));
        panelSuperior.add(comboColaboradores);

        // Panel izquierdo - Lista de colaboraciones
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Colaboraciones Realizadas"));
        panelIzquierdo.setPreferredSize(new Dimension(400, 400));

        JScrollPane scrollColaboraciones = new JScrollPane(listColaboraciones);
        panelIzquierdo.add(scrollColaboraciones, BorderLayout.CENTER);

        // Panel derecho - Detalles de la colaboración
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Detalles de la Colaboración"));

        JPanel panelDetalles = new JPanel(new GridLayout(9, 1, 5, 5));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelDetalles.add(lblPropuestaTitulo);
        panelDetalles.add(lblProponenteNick);
        panelDetalles.add(lblColaboradorNick);
        panelDetalles.add(lblMonto);
        panelDetalles.add(lblTipoRetorno);
        panelDetalles.add(lblFechaHora);
        panelDetalles.add(lblEstadoPropuesta);
        panelDetalles.add(lblMontoNecesario);
        panelDetalles.add(lblTotalRecaudado);

        panelDerecho.add(panelDetalles, BorderLayout.NORTH);

        // Panel central - Dividir izquierdo y derecho
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.4);

        // Agregar al frame principal
        add(panelSuperior, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        // Evento para cambio de colaborador
        comboColaboradores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) comboColaboradores.getSelectedItem();
                if (nickname != null && !nickname.isEmpty()) {
                    cargarColaboracionesDelColaborador(nickname);
                }
            }
        });

        // Evento para selección de colaboración en la lista
        listColaboraciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listColaboraciones.getSelectedIndex();
                if (index >= 0 && colaboradorActual != null) {
                    mostrarDetallesColaboracion(index);
                }
            }
        });
    }

    private void cargarColaboradores() {
        try {
            comboColaboradores.removeAllItems();
            comboColaboradores.addItem(""); // Opción vacía

            List<String> nicknames = UsuarioContr.devolverNicknamesColaboradores();
            for (String nickname : nicknames) {
                comboColaboradores.addItem(nickname);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar colaboradores: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarColaboracionesDelColaborador(String nickname) {
        try {
            // Limpiar lista anterior
            modelColaboraciones.clear();
            limpiarDetalles();

            // Obtener información completa del colaborador
            colaboradorActual = UsuarioContr.obtenerColaboradorCompleto(nickname);

            if (colaboradorActual.getColaboraciones() != null && !colaboradorActual.getColaboraciones().isEmpty()) {
                int index = 1;
                for (DTColaboracion colaboracion : colaboradorActual.getColaboraciones()) {
                    DTPropuesta propuesta = colaboracion.getPropuesta();
                    String itemText = index + ". " + propuesta.getTitulo() +
                            " ($" + colaboracion.getMonto() + ") - " + propuesta.getEstadoActual();
                    modelColaboraciones.addElement(itemText);
                    index++;
                }

                // Mostrar información del colaborador en el panel de detalles
                lblColaboradorNick.setText("Colaborador: " + colaboradorActual.getNickname() +
                        " (" + colaboradorActual.getNombre() + " " + colaboradorActual.getApellido() + ")");

            } else {
                modelColaboraciones.addElement("Este colaborador no tiene colaboraciones registradas");
                lblColaboradorNick.setText("Colaborador: " + colaboradorActual.getNickname() +
                        " (" + colaboradorActual.getNombre() + " " + colaboradorActual.getApellido() + ")");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar colaboraciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetallesColaboracion(int index) {
        try {
            if (colaboradorActual == null ||
                    colaboradorActual.getColaboraciones() == null ||
                    index >= colaboradorActual.getColaboraciones().size()) {
                return;
            }

            DTColaboracion colaboracion = colaboradorActual.getColaboraciones().get(index);
            DTPropuesta propuesta = colaboracion.getPropuesta();

            // Mostrar detalles de la colaboración
            lblPropuestaTitulo.setText("Título: " + propuesta.getTitulo());
            lblProponenteNick.setText("Proponente: " +
                    (propuesta.getDTProponente() != null ? propuesta.getDTProponente().getNickname() : "N/A"));

            lblMonto.setText("Monto: $" + String.format("%.2f", colaboracion.getMonto()));
            lblTipoRetorno.setText("Tipo de Retorno: " +
                    (colaboracion.getTipoRetorno() != null ? colaboracion.getTipoRetorno().toString() : "N/A"));

            // Formatear fecha y hora
            if (colaboracion.getFechaHora() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                lblFechaHora.setText("Fecha y Hora: " + colaboracion.getFechaHora().format(formatter));
            } else {
                lblFechaHora.setText("Fecha y Hora: N/A");
            }

            lblEstadoPropuesta.setText("Estado Propuesta: " + propuesta.getEstadoActual());
            lblMontoNecesario.setText("Monto Necesario: $" + String.format("%.2f", propuesta.getMontoNecesario()));

            // Calcular total recaudado para esta propuesta
            double totalRecaudado = 0;
            if (propuesta.getColaboraciones() != null) {
                for (DTColaboracion c : propuesta.getColaboraciones()) {
                    totalRecaudado += c.getMonto();
                }
            }
            lblTotalRecaudado.setText("Total Recaudado: $" + String.format("%.2f", totalRecaudado));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar detalles de la colaboración: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarDetalles() {
        lblPropuestaTitulo.setText("Título: ");
        lblProponenteNick.setText("Proponente: ");
        lblColaboradorNick.setText("Colaborador: ");
        lblMonto.setText("Monto: ");
        lblTipoRetorno.setText("Tipo de Retorno: ");
        lblFechaHora.setText("Fecha y Hora: ");
        lblEstadoPropuesta.setText("Estado Propuesta: ");
        lblMontoNecesario.setText("Monto Necesario: ");
        lblTotalRecaudado.setText("Total Recaudado: ");
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}