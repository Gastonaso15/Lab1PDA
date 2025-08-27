package culturarte.presentacion;

import culturarte.logica.Fabrica;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaPropuestasPorEstadoInternalFrame extends JInternalFrame {

    private JComboBox<DTEstadoPropuesta> comboEstados;
    private JButton btnConsultar;
    private JButton btnVerDetalle;
    private JButton btnCerrar;
    private JTable tablaPropuestas;
    private DefaultTableModel modeloTabla;
    private IPropuestaController propuestaController;

    public ConsultaPropuestasPorEstadoInternalFrame(IPropuestaController icp) {
        super("Consulta de Propuestas por Estado", true, true, true, true);
        this.propuestaController = icp;
        initComponents();
        setupLayout();
        setupEvents();
        cargarEstados();
        setSize(900, 600);
        setLocation(50, 50);
    }

    private void initComponents() {
        comboEstados = new JComboBox<>();
        comboEstados.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        btnConsultar = new JButton("Consultar");
        btnConsultar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnConsultar.setBackground(new Color(70, 130, 180));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFocusPainted(false);
        
        btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVerDetalle.setBackground(new Color(60, 179, 113));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFocusPainted(false);
        btnVerDetalle.setEnabled(false);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnCerrar.setBackground(new Color(220, 20, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);

        String[] columnas = {"ID", "Título", "Proponente", "Estado", "Fecha Prevista"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override 
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };
        tablaPropuestas = new JTable(modeloTabla);
        tablaPropuestas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPropuestas.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaPropuestas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaPropuestas.setRowHeight(25);
        tablaPropuestas.setGridColor(new Color(230, 230, 230));
    }

    private void setupLayout() {
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        norte.setBackground(new Color(248, 248, 248));
        
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 14));
        norte.add(lblEstado);
        norte.add(comboEstados);
        norte.add(btnConsultar);

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        sur.setBackground(new Color(248, 248, 248));
        sur.add(btnVerDetalle);
        sur.add(btnCerrar);

        setLayout(new BorderLayout());
        add(norte, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(tablaPropuestas);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Propuestas Encontradas",
            0, 0, new Font("SansSerif", Font.BOLD, 12), new Color(80, 80, 80)
        ));
        add(scrollPane, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnConsultar.addActionListener(e -> consultar());
        btnCerrar.addActionListener(e -> dispose());
        btnVerDetalle.addActionListener(e -> verDetalle());

        tablaPropuestas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnVerDetalle.setEnabled(tablaPropuestas.getSelectedRow() != -1);
            }
        });
    }

    private void cargarEstados() {
        for (DTEstadoPropuesta estado : DTEstadoPropuesta.values()) {
            comboEstados.addItem(estado);
        }
    }

    private void consultar() {
        DTEstadoPropuesta estado = (DTEstadoPropuesta) comboEstados.getSelectedItem();
        if (estado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un estado", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            List<DTPropuesta> dts = propuestaController.devolverPropuestasPorEstado(estado);
            modeloTabla.setRowCount(0);
            
            for (DTPropuesta dt : dts) {
                Object[] fila = {
                    dt.getId(),
                    dt.getTitulo(),
                    dt.getDTProponente() != null ? dt.getDTProponente().getNickname() : "N/A",
                    dt.getEstadoActual() != null ? dt.getEstadoActual().name() : "Sin estado",
                    dt.getFechaPrevista() != null ? dt.getFechaPrevista().toString() : "No definida"
                };
                modeloTabla.addRow(fila);
            }
            
            if (dts.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay propuestas en estado " + estado.name(), 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al consultar propuestas: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void verDetalle() {
        int row = tablaPropuestas.getSelectedRow();
        if (row == -1) return;
        
        Object idObj = modeloTabla.getValueAt(row, 0);
        if (idObj == null) {
            JOptionPane.showMessageDialog(this, 
                "No se pudo determinar el ID de la propuesta", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Long id = (idObj instanceof Long) ? (Long) idObj : Long.valueOf(idObj.toString());
            DTPropuesta dt = propuestaController.obtenerPropuestaPorId(id);
            
            if (dt == null) {
                JOptionPane.showMessageDialog(this, 
                    "Propuesta no encontrada", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════\n");
            sb.append("           DETALLE DE PROPUESTA\n");
            sb.append("═══════════════════════════════════════\n\n");
            sb.append("ID: ").append(dt.getId()).append("\n");
            sb.append("Título: ").append(dt.getTitulo()).append("\n");
            sb.append("Proponente: ").append(dt.getDTProponente() != null ? dt.getDTProponente().getNickname() : "N/A").append("\n");
            sb.append("Estado: ").append(dt.getEstadoActual() != null ? dt.getEstadoActual().name() : "Sin estado").append("\n\n");
            sb.append("Descripción:\n").append(dt.getDescripcion() != null ? dt.getDescripcion() : "Sin descripción").append("\n\n");
            sb.append("Lugar: ").append(dt.getLugar() != null ? dt.getLugar() : "No especificado").append("\n");
            sb.append("Fecha Prevista: ").append(dt.getFechaPrevista() != null ? dt.getFechaPrevista().toString() : "No definida").append("\n");
            sb.append("Fecha Publicación: ").append(dt.getFechaPublicacion() != null ? dt.getFechaPublicacion().toString() : "No publicada").append("\n");
            sb.append("Precio Entrada: $").append(dt.getPrecioEntrada() != null ? dt.getPrecioEntrada() : "0.00").append("\n");
            sb.append("Monto Necesario: $").append(dt.getMontoNecesario() != null ? dt.getMontoNecesario() : "0.00").append("\n");
            
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
            ta.setBackground(new Color(250, 250, 250));
            ta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JScrollPane scrollPane = new JScrollPane(ta);
            scrollPane.setPreferredSize(new Dimension(500, 400));
            
            JOptionPane.showMessageDialog(this, 
                scrollPane, 
                "Detalle de Propuesta - " + dt.getTitulo(), 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "ID de propuesta inválido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al obtener detalles: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
