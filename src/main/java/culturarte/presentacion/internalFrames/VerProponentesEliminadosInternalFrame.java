package culturarte.presentacion.internalFrames;

import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTEstadoPropuesta;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.presentacion.helpers.ImagenUIHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VerProponentesEliminadosInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;
    private final JList<DTProponente> listProponentes;
    private final JLabel lblNickname;
    private final JLabel lblNombre;
    private final JLabel lblApellido;
    private final JLabel lblCorreo;
    private final JLabel lblFechaNacimiento;
    private final JLabel lblDireccion;
    private final JTextArea txtBiografia;
    private final JLabel lblWeb;
    private final JLabel lblFechaEliminacion;
    private final ImagenUIHelper.ImagenPanel lblImagen;
    private final JPanel panelPropuestas;

    public VerProponentesEliminadosInternalFrame(IUsuarioController icu) {
        super("Ver Proponentes Eliminados", true, true, true, true);
        setSize(1200, 600);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        
        DefaultListModel<DTProponente> model = new DefaultListModel<>();
        try {
            List<DTProponente> proponentesEliminados = icu.devolverProponentesEliminados();
            for (DTProponente prop : proponentesEliminados) {
                model.addElement(prop);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar proponentes eliminados: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        listProponentes = new JList<>(model);
        listProponentes.setCellRenderer(new ProponenteListCellRenderer());
        JScrollPane scrollProponentes = new JScrollPane(listProponentes);
        panelIzquierdo.add(new JLabel("Proponentes Eliminados:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollProponentes, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelInfo = new JPanel(new GridLayout(1, 3, 10, 0));

        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        lblNickname = new JLabel("Nickname: ");
        lblNombre = new JLabel("Nombre: ");
        lblApellido = new JLabel("Apellido: ");
        lblFechaNacimiento = new JLabel("Fecha de nacimiento: ");
        lblFechaEliminacion = new JLabel("Fecha de eliminación: ");
        lblFechaEliminacion.setForeground(Color.RED);
        lblFechaEliminacion.setFont(lblFechaEliminacion.getFont().deriveFont(Font.BOLD));
        col1.add(lblNickname);
        col1.add(lblNombre);
        col1.add(lblApellido);
        col1.add(lblFechaNacimiento);
        col1.add(lblFechaEliminacion);

        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        lblCorreo = new JLabel("Correo: ");
        lblDireccion = new JLabel("Dirección: ");
        lblWeb = new JLabel("Sitio Web: ");
        txtBiografia = new JTextArea();
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        txtBiografia.setEditable(false);
        txtBiografia.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        txtBiografia.setBackground(col2.getBackground());
        txtBiografia.setRows(5);
        JScrollPane scrollBio = new JScrollPane(txtBiografia);
        scrollBio.setBorder(BorderFactory.createEmptyBorder());
        scrollBio.setAlignmentX(Component.LEFT_ALIGNMENT);
        col2.add(lblCorreo);
        col2.add(lblDireccion);
        col2.add(lblWeb);
        col2.add(scrollBio);

        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        col3.add(lblImagen);

        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 20);
        setFontToLabels(fontInfo, lblNickname, lblNombre, lblApellido, lblFechaNacimiento,
                lblCorreo, lblDireccion, lblWeb);

        panelInfo.add(col3);
        panelInfo.add(col1);
        panelInfo.add(col2);

        panelPropuestas = new JPanel();
        panelPropuestas.setLayout(new BoxLayout(panelPropuestas, BoxLayout.Y_AXIS));
        JScrollPane scrollPropuestas = new JScrollPane(panelPropuestas);
        scrollPropuestas.setPreferredSize(new Dimension(700, 300));

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(scrollPropuestas, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        listProponentes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTProponente proponente = listProponentes.getSelectedValue();
                if (proponente != null) {
                    mostrarProponente(proponente);
                }
            }
        });
    }

    private void mostrarProponente(DTProponente proponente) {
        lblNickname.setText("Nickname: " + proponente.getNickname());
        lblNombre.setText("Nombre: " + proponente.getNombre());
        lblApellido.setText("Apellido: " + proponente.getApellido());
        lblCorreo.setText("Correo: " + proponente.getCorreo());
        lblFechaNacimiento.setText("Fecha de Nacimiento: " + 
            (proponente.getFechaNacimiento() != null ? proponente.getFechaNacimiento().toString() : ""));
        lblDireccion.setText("Dirección: " + 
            (proponente.getDireccion() != null ? proponente.getDireccion() : ""));
        txtBiografia.setText("Biografía: " + 
            (proponente.getBio() != null ? proponente.getBio() : ""));
        lblWeb.setText("Sitio Web: " + 
            (proponente.getSitioWeb() != null ? proponente.getSitioWeb() : ""));
        
        if (proponente.getFechaEliminacion() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lblFechaEliminacion.setText("Fecha de Eliminación: " + 
                proponente.getFechaEliminacion().format(formatter));
        } else {
            lblFechaEliminacion.setText("Fecha de Eliminación: No disponible");
        }

        lblImagen.setImagen(proponente.getImagen());

        panelPropuestas.removeAll();
        
        if (proponente.getPropuestas() != null && !proponente.getPropuestas().isEmpty()) {
            for (DTPropuesta p : proponente.getPropuestas()) {
                JPanel pPanel = new JPanel();
                pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                pPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        p.getTitulo() + " - " + 
                        (p.getEstadoActual() != null ? p.getEstadoActual() : "Sin estado"),
                        TitledBorder.LEFT,
                        TitledBorder.TOP
                ));

                if (p.getEstadoActual() != null) {
                    switch(p.getEstadoActual()) {
                        case INGRESADA -> pPanel.setBackground(new Color(238, 10, 238));
                        case PUBLICADA -> pPanel.setBackground(new Color(144, 238, 144));
                        case EN_FINANCIACION -> pPanel.setBackground(new Color(255, 255, 102));
                        case CANCELADA -> pPanel.setBackground(new Color(255, 102, 102));
                        case FINANCIADA -> pPanel.setBackground(new Color(102, 178, 255));
                        case NO_FINANCIADA -> pPanel.setBackground(new Color(211, 211, 211));
                    }
                }

                double dineroRecaudado = 0;
                StringBuilder colaboradoresStr = new StringBuilder();
                if (p.getColaboraciones() != null) {
                    for (DTColaboracion c : p.getColaboraciones()) {
                        if (c.getMonto() != null) {
                            dineroRecaudado += c.getMonto();
                        }
                        if (!colaboradoresStr.isEmpty()) {
                            colaboradoresStr.append(", ");
                        }
                        if (c.getColaborador() != null) {
                            colaboradoresStr.append(c.getColaborador().getNickname());
                        }
                    }
                }

                pPanel.add(new JLabel("Título: " + p.getTitulo()));
                if (p.getFechaPrevista() != null) {
                    pPanel.add(new JLabel("Fecha Prevista: " + p.getFechaPrevista()));
                }
                if (p.getMontoNecesario() != null) {
                    pPanel.add(new JLabel("Monto Necesario: " + p.getMontoNecesario()));
                }
                pPanel.add(new JLabel("Dinero recaudado: " + dineroRecaudado));
                if (!colaboradoresStr.isEmpty()) {
                    pPanel.add(new JLabel("Colaboradores: " + colaboradoresStr));
                } else {
                    pPanel.add(new JLabel("Colaboradores: Ninguno"));
                }
                if (p.getLugar() != null) {
                    pPanel.add(new JLabel("Lugar: " + p.getLugar()));
                }

                panelPropuestas.add(pPanel);
                panelPropuestas.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        } else {
            JLabel noPropuestas = new JLabel("Este proponente no tiene propuestas.");
            noPropuestas.setFont(new Font("Arial", Font.ITALIC, 14));
            panelPropuestas.add(noPropuestas);
        }

        panelPropuestas.revalidate();
        panelPropuestas.repaint();
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }

    private static class ProponenteListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof DTProponente prop) {
                setText(prop.getNickname() + " - " + prop.getNombre() + " " + prop.getApellido());
            }
            return this;
        }
    }
}
