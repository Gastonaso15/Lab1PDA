//import culturarte.logica.Fabrica;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public ConsultaPropuestasPorEstadoInternalFrame() {
//    super("Consulta de Propuestas por Estado", true, true, true, true);
//    this.propuestaController = Fabrica.getInstance().getIPropuestaController();
//    initComponents();
//    setupLayout();
//    setupEvents();
//    cargarEstados();
//    setSize(800, 500);
//    setLocation(50, 50);
//}
//
//private void initComponents() {
//    comboEstados = new JComboBox<>();
//    btnConsultar = new JButton("Consultar");
//    btnVerDetalle = new JButton("Ver detalle");
//    btnCerrar = new JButton("Cerrar");
//
//    String[] columnas = {"ID", "Nombre", "Proponente", "Estado", "Fecha creación"};
//    modeloTabla = new DefaultTableModel(columnas, 0) {
//        @Override public boolean isCellEditable(int r, int c) { return false; }
//    };
//    tablaPropuestas = new JTable(modeloTabla);
//    tablaPropuestas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//    btnVerDetalle.setEnabled(false);
//}
//
//private void setupLayout() {
//    JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT));
//    norte.add(new JLabel("Estado:"));
//    norte.add(comboEstados);
//    norte.add(btnConsultar);
//
//    JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//    sur.add(btnVerDetalle);
//    sur.add(btnCerrar);
//
//    setLayout(new BorderLayout());
//    add(norte, BorderLayout.NORTH);
//    add(new JScrollPane(tablaPropuestas), BorderLayout.CENTER);
//    add(sur, BorderLayout.SOUTH);
//}
//
//private void setupEvents() {
//    btnConsultar.addActionListener(e -> consultar());
//    btnCerrar.addActionListener(e -> dispose());
//    btnVerDetalle.addActionListener(e -> verDetalle());
//
//    tablaPropuestas.getSelectionModel().addListSelectionListener(e -> {
//        if (!e.getValueIsAdjusting()) {
//            btnVerDetalle.setEnabled(tablaPropuestas.getSelectedRow() != -1);
//        }
//    });
//}
//
//private void cargarEstados() {
//    // Si ya tienes un mé todo para listar estados, úsalo. De lo contrario, usa el enum del modelo.
//    for (EstadoPropuesta ep : EstadoPropuesta.values()) {
//        comboEstados.addItem(ep);
//    }
//}
//
//private void consultar() {
//    EstadoPropuesta estado = (EstadoPropuesta) comboEstados.getSelectedItem();
//    if (estado == null) {
//        JOptionPane.showMessageDialog(this, "Seleccione un estado", "Aviso", JOptionPane.WARNING_MESSAGE);
//        return;
//    }
//    List<DTPropuesta> dts = propuestaController.listarPropuestasPorEstado(estado);
//    modeloTabla.setRowCount(0);
//    for (DTPropuesta dt : dts) {
//        Object[] fila = {
//                dt.getId(),                   // asumiendo que DTPropuesta tiene id; si no, usa nombre
//                dt.getNombre(),
//                dt.getProponenteNickname(),   // ajusta al nombre real del getter en tu DTO
//                dt.getEstado() != null ? dt.getEstado().name() : "",
//                dt.getFechaCreacion()
//        };
//        modeloTabla.addRow(fila);
//    }
//    if (dts.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "No hay propuestas en estado " + estado, "Información", JOptionPane.INFORMATION_MESSAGE);
//    }
//}
//
//private void verDetalle() {
//    int row = tablaPropuestas.getSelectedRow();
//    if (row == -1) return;
//    Object idObj = modeloTabla.getValueAt(row, 0);
//    if (idObj == null) {
//        JOptionPane.showMessageDialog(this, "No se pudo determinar el ID de la propuesta", "Error", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//    Long id = (idObj instanceof Long) ? (Long) idObj : Long.valueOf(idObj.toString());
//
//    // Reutiliza la UI de "Consulta de Propuesta" si existe
//    // O muestra un dialog sencillo con los datos del DTO
//    DTPropuesta dt = propuestaController.obtenerPropuestaPorId(id);
//    if (dt == null) {
//        JOptionPane.showMessageDialog(this, "Propuesta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//    StringBuilder sb = new StringBuilder();
//    sb.append("ID: ").append(dt.getId()).append("\n");
//    sb.append("Nombre: ").append(dt.getNombre()).append("\n");
//    sb.append("Proponente: ").append(dt.getProponenteNickname()).append("\n");
//    sb.append("Estado: ").append(dt.getEstado() != null ? dt.getEstado().name() : "").append("\n");
//    sb.append("Descripción: ").append(dt.getDescripcion()).append("\n");
//    sb.append("Fecha creación: ").append(dt.getFechaCreacion()).append("\n");
//    JTextArea ta = new JTextArea(sb.toString());
//    ta.setEditable(false);
//    JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Detalle de Propuesta", JOptionPane.INFORMATION_MESSAGE);
//}
//
//private final Object propuestaController;