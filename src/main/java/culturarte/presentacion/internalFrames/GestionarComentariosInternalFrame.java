package culturarte.presentacion.internalFrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import culturarte.servicios.DTs.DTComentario;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.interfaces.IPropuestaController;

public class GestionarComentariosInternalFrame extends JInternalFrame {

    private JList<DTPropuesta> jListPropuestas;
    private JList<DTComentario> jListComentarios;
    private JTextArea txtNuevoComentario;
    private JTextField tfNicknameUsuario;
    private JLabel lblTituloPropuesta;
    private JLabel lblDescripcionPropuesta;
    private JLabel lblEstadoPropuesta;
    private JButton btnAgregarComentario;
    private JButton btnActualizarComentarios;

    private final IPropuestaController PropuestaContr;

    public GestionarComentariosInternalFrame(IPropuestaController icp) {
        super("Gestionar Comentarios", true, true, true, true);
        setSize(1200, 700);
        setLayout(new BorderLayout());

        PropuestaContr = icp;


        JPanel panelIzquierdo = crearPanelPropuestas();
        add(panelIzquierdo, BorderLayout.WEST);

        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelDerecho = crearPanelAgregarComentario();
        add(panelDerecho, BorderLayout.EAST);

        cargarPropuestas();
    }

    private JPanel crearPanelPropuestas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Propuestas"));
        panel.setPreferredSize(new Dimension(250, 0));

        jListPropuestas = new JList<>();
        jListPropuestas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDetallesPropuesta();
                cargarComentariosPropuesta();
            }
        });

        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        panel.add(scrollList, BorderLayout.CENTER);

        btnActualizarComentarios = new JButton("Actualizar Lista");
        btnActualizarComentarios.addActionListener(e -> cargarPropuestas());
        panel.add(btnActualizarComentarios, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detalles de Propuesta y Comentarios"));

        JPanel panelDetalles = new JPanel(new GridLayout(3, 2, 5, 5));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Información de la Propuesta"));

        panelDetalles.add(new JLabel("Título:"));
        lblTituloPropuesta = new JLabel("Seleccione una propuesta");
        panelDetalles.add(lblTituloPropuesta);

        panelDetalles.add(new JLabel("Descripción:"));
        lblDescripcionPropuesta = new JLabel("Seleccione una propuesta");
        lblDescripcionPropuesta.setVerticalAlignment(SwingConstants.TOP);
        panelDetalles.add(lblDescripcionPropuesta);

        panelDetalles.add(new JLabel("Estado:"));
        lblEstadoPropuesta = new JLabel("Seleccione una propuesta");
        panelDetalles.add(lblEstadoPropuesta);

        JPanel panelComentarios = new JPanel(new BorderLayout());
        panelComentarios.setBorder(BorderFactory.createTitledBorder("Comentarios"));

        jListComentarios = new JList<>();
        jListComentarios.setCellRenderer(new ComentarioListCellRenderer());
        JScrollPane scrollComentarios = new JScrollPane(jListComentarios);
        panelComentarios.add(scrollComentarios, BorderLayout.CENTER);

        panel.add(panelDetalles, BorderLayout.NORTH);
        panel.add(panelComentarios, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelAgregarComentario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Agregar Comentario"));
        panel.setPreferredSize(new Dimension(300, 0));

        JPanel panelFormulario = new JPanel(new GridLayout(4, 1, 5, 5));

        JPanel panelUsuario = new JPanel(new BorderLayout());
        panelUsuario.add(new JLabel("Nickname Usuario:"), BorderLayout.NORTH);
        tfNicknameUsuario = new JTextField();
        tfNicknameUsuario.setToolTipText("Ingrese el nickname del usuario que comenta");
        panelUsuario.add(tfNicknameUsuario, BorderLayout.CENTER);
        panelFormulario.add(panelUsuario);

        JPanel panelComentario = new JPanel(new BorderLayout());
        panelComentario.add(new JLabel("Contenido del Comentario:"), BorderLayout.NORTH);
        txtNuevoComentario = new JTextArea(6, 20);
        txtNuevoComentario.setLineWrap(true);
        txtNuevoComentario.setWrapStyleWord(true);
        txtNuevoComentario.setToolTipText("Ingrese el contenido del comentario");
        JScrollPane scrollComentario = new JScrollPane(txtNuevoComentario);
        panelComentario.add(scrollComentario, BorderLayout.CENTER);
        panelFormulario.add(panelComentario);

        btnAgregarComentario = new JButton("Agregar Comentario");
        btnAgregarComentario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarComentario();
            }
        });
        panelFormulario.add(btnAgregarComentario);

        JTextArea txtInstrucciones = new JTextArea(4, 20);
        txtInstrucciones.setEditable(false);
        txtInstrucciones.setBackground(getBackground());
        txtInstrucciones.setText("Instrucciones:\n" +
                "1. Seleccione una propuesta\n" +
                "2. Ingrese el nickname del usuario\n" +
                "3. Escriba el comentario\n" +
                "4. Haga clic en 'Agregar Comentario'");
        panelFormulario.add(txtInstrucciones);

        panel.add(panelFormulario, BorderLayout.CENTER);

        return panel;
    }

    private void cargarPropuestas() {
        try {
            List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
            jListPropuestas.setListData(propuestas.toArray(new DTPropuesta[0]));
            
            if (propuestas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay propuestas disponibles.", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar propuestas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDetallesPropuesta() {
        DTPropuesta propuestaSeleccionada = jListPropuestas.getSelectedValue();
        
        if (propuestaSeleccionada != null) {
            lblTituloPropuesta.setText(propuestaSeleccionada.getTitulo());
            lblDescripcionPropuesta.setText(propuestaSeleccionada.getDescripcion());
            lblEstadoPropuesta.setText(propuestaSeleccionada.getEstadoActual().toString());
        } else {
            lblTituloPropuesta.setText("Seleccione una propuesta");
            lblDescripcionPropuesta.setText("Seleccione una propuesta");
            lblEstadoPropuesta.setText("Seleccione una propuesta");
        }
    }

    private void cargarComentariosPropuesta() {
        DTPropuesta propuestaSeleccionada = jListPropuestas.getSelectedValue();
        
        if (propuestaSeleccionada != null) {
            try {
                List<DTComentario> comentarios = PropuestaContr.obtenerComentariosPropuesta(
                    propuestaSeleccionada.getTitulo());
                jListComentarios.setListData(comentarios.toArray(new DTComentario[0]));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar comentarios: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                jListComentarios.setListData(new DTComentario[0]);
            }
        } else {
            jListComentarios.setListData(new DTComentario[0]);
        }
    }

    private void agregarComentario() {
        DTPropuesta propuestaSeleccionada = jListPropuestas.getSelectedValue();
        
        if (propuestaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una propuesta.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nickname = tfNicknameUsuario.getText().trim();
        String contenido = txtNuevoComentario.getText().trim();

        if (nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nickname del usuario.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el contenido del comentario.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            PropuestaContr.agregarComentario(propuestaSeleccionada.getTitulo(), nickname, contenido);
            
            JOptionPane.showMessageDialog(this, "Comentario agregado exitosamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            tfNicknameUsuario.setText("");
            txtNuevoComentario.setText("");

            cargarComentariosPropuesta();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar comentario: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ComentarioListCellRenderer extends DefaultListCellRenderer {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof DTComentario comentario) {
                String texto = String.format("<html><b>%s</b> - %s<br/><i>%s</i><br/>%s", 
                    comentario.getUsuarioNickname(),
                    comentario.getFechaHora().format(formatter),
                    comentario.getContenido().length() > 100 ? 
                        comentario.getContenido().substring(0, 100) + "..." : 
                        comentario.getContenido(),
                    comentario.getUsuarioNombreCompleto());
                setText(texto);
            }
            
            return this;
        }
    }
}
