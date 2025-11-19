package culturarte.presentacion.internalFrames;

import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTColaborador;
import culturarte.servicios.DTs.DTEstadoPropuesta;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.presentacion.helpers.ImagenUIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ConsultaPerfilColaboradorInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;
    private final JList<String> listColaboradores;
    private final JLabel lblNickname;
    private final JLabel lblNombre;
    private final JLabel lblApellido;
    private final JLabel lblCorreo;
    private final JLabel lblFechaNacimiento;
    private final ImagenUIHelper.ImagenPanel lblImagen;
    private final JPanel panelPropuestas;

    public ConsultaPerfilColaboradorInternalFrame(IUsuarioController icu) {
        super("Consultar Perfil de Colaborador", true, true, true, true);
        setSize(1200, 700);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        UsuarioContr = icu;

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBackground(new Color(250, 250, 250));
        List<String> nicknames = icu.devolverNicknamesColaboradores();
        listColaboradores = new JList<>(nicknames.toArray(new String[0]));
        listColaboradores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        listColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollColaboradores = new JScrollPane(listColaboradores);
        scrollColaboradores.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Colaboradores",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelIzquierdo.add(scrollColaboradores, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBackground(new Color(250, 250, 250));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Información del Colaborador",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 70, 70)
        ));
        panelInfo.setBackground(Color.WHITE);

        JPanel gridInfo = new JPanel(new GridBagLayout());
        gridInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;

        lblNickname = createDetailLabel("Nickname: ");
        gbc.gridx = 0; gbc.gridy = 0;
        gridInfo.add(createInfoLabel("Nickname:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblNickname, gbc);

        lblNombre = createDetailLabel("Nombre: ");
        gbc.gridx = 0; gbc.gridy = 1;
        gridInfo.add(createInfoLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblNombre, gbc);

        lblApellido = createDetailLabel("Apellido: ");
        gbc.gridx = 0; gbc.gridy = 2;
        gridInfo.add(createInfoLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblApellido, gbc);

        lblCorreo = createDetailLabel("Correo: ");
        gbc.gridx = 0; gbc.gridy = 3;
        gridInfo.add(createInfoLabel("Correo:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblCorreo, gbc);

        lblFechaNacimiento = createDetailLabel("Fecha de Nacimiento: ");
        gbc.gridx = 0; gbc.gridy = 4;
        gridInfo.add(createInfoLabel("Fecha de Nacimiento:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblFechaNacimiento, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLoweredBevelBorder());
        gridInfo.add(lblImagen, gbc);

        panelInfo.add(gridInfo);

        panelPropuestas = new JPanel();
        panelPropuestas.setLayout(new BoxLayout(panelPropuestas, BoxLayout.Y_AXIS));
        panelPropuestas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Propuestas en las que Colabora",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelPropuestas.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPropuestas = new JScrollPane(panelPropuestas);
        scrollPropuestas.setBorder(null);

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(scrollPropuestas, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        listColaboradores.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nickname = listColaboradores.getSelectedValue();
                if (nickname != null) {
                    mostrarColaborador(nickname);
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

    private void mostrarColaborador(String nickname) {
        try {
            DTColaborador colaborador = UsuarioContr.devolverColaboradorPorNickname(nickname);

            lblNickname.setText(colaborador.getNickname() != null ? colaborador.getNickname() : "");
            lblNombre.setText(colaborador.getNombre() != null ? colaborador.getNombre() : "");
            lblApellido.setText(colaborador.getApellido() != null ? colaborador.getApellido() : "");
            lblCorreo.setText(colaborador.getCorreo() != null ? colaborador.getCorreo() : "");
            lblFechaNacimiento.setText(colaborador.getFechaNacimiento() != null ? colaborador.getFechaNacimiento().toString() : "");

            lblImagen.setImagen(colaborador.getImagen());

            panelPropuestas.removeAll();

            if (colaborador.getColaboraciones() != null && !colaborador.getColaboraciones().isEmpty()) {
                for (DTColaboracion colaboracion : colaborador.getColaboraciones()) {
                    DTPropuesta propuesta = colaboracion.getPropuesta();

                    if (propuesta.getEstadoActual() == DTEstadoPropuesta.INGRESADA) {
                        continue;
                    }

                    JPanel pPanel = new JPanel();
                    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                    boolean proponenteEliminado = propuesta.getDTProponente() != null && 
                        propuesta.getDTProponente().getFechaEliminacion() != null;
                    
                    pPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        propuesta.getTitulo() + " - " + propuesta.getEstadoActual(),
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 12),
                        new Color(70, 70, 70)
                    ));

                    Color backgroundColor;
                    switch(propuesta.getEstadoActual()) {
                        case PUBLICADA -> backgroundColor = new Color(200, 255, 200);
                        case EN_FINANCIACION -> backgroundColor = new Color(255, 255, 200);
                        case CANCELADA -> backgroundColor = new Color(255, 200, 200);
                        case FINANCIADA -> backgroundColor = new Color(200, 220, 255);
                        case NO_FINANCIADA -> backgroundColor = new Color(230, 230, 230);
                        default -> backgroundColor = Color.WHITE;
                    }
                    
                    // Si el proponente está eliminado, hacer el fondo más opaco/deshabilitado
                    if (proponenteEliminado) {
                        backgroundColor = new Color(
                            Math.max(0, backgroundColor.getRed() - 30),
                            Math.max(0, backgroundColor.getGreen() - 30),
                            Math.max(0, backgroundColor.getBlue() - 30)
                        );
                        pPanel.setToolTipText("Proponente eliminado - Esta propuesta no está disponible para consulta");
                    }
                    pPanel.setBackground(backgroundColor);

                    double dineroRecaudado = 0;
                    if (propuesta.getColaboraciones() != null) {
                        for (DTColaboracion c : propuesta.getColaboraciones()) {
                            if (c.getMonto() != null) {
                                dineroRecaudado += c.getMonto();
                            }
                        }
                    }

                    JLabel lblTitulo = new JLabel("Título: " + propuesta.getTitulo());
                    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    pPanel.add(lblTitulo);
                    String proponenteInfo = "Proponente: " + (propuesta.getDTProponente() != null ?
                            propuesta.getDTProponente().getNickname() : "N/A");
                    if (propuesta.getDTProponente() != null && 
                        propuesta.getDTProponente().getFechaEliminacion() != null) {
                        proponenteInfo += " (Eliminado)";
                    }
                    JLabel lblProponente = new JLabel(proponenteInfo);
                    if (propuesta.getDTProponente() != null && 
                        propuesta.getDTProponente().getFechaEliminacion() != null) {
                        lblProponente.setForeground(new Color(150, 0, 0));
                        lblProponente.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                    }
                    pPanel.add(lblProponente);
                    if (propuesta.getFechaPrevista() != null) {
                        pPanel.add(new JLabel("Fecha Prevista: " + propuesta.getFechaPrevista()));
                    }
                    if (propuesta.getMontoNecesario() != null) {
                        pPanel.add(new JLabel("Monto Necesario: $" + propuesta.getMontoNecesario()));
                    }
                    pPanel.add(new JLabel("Dinero Recaudado: $" + String.format("%.2f", dineroRecaudado)));
                    pPanel.add(new JLabel("Mi Colaboración: $" + (colaboracion.getMonto() != null ? String.format("%.2f", colaboracion.getMonto()) : "0")));
                    // Mostrar fecha de colaboración
                    String fechaColaboracionTexto;
                    if (colaboracion.getFechaHora() != null) {
                        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        fechaColaboracionTexto = "Fecha de Colaboración: " + colaboracion.getFechaHora().format(formatter);
                    } else {
                        fechaColaboracionTexto = "Fecha de Colaboración: No disponible";
                    }
                    JLabel lblFechaColaboracion = new JLabel(fechaColaboracionTexto);
                    lblFechaColaboracion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    pPanel.add(lblFechaColaboracion);
                    pPanel.add(new JLabel("Estado Actual: " + propuesta.getEstadoActual()));

                    panelPropuestas.add(pPanel);
                    panelPropuestas.add(Box.createRigidArea(new Dimension(0, 8)));
                }
            } else {
                JLabel lblSinColaboraciones = new JLabel("Este colaborador no ha participado en ninguna propuesta.");
                lblSinColaboraciones.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                lblSinColaboraciones.setForeground(new Color(120, 120, 120));
                panelPropuestas.add(lblSinColaboraciones);
            }

            panelPropuestas.revalidate();
            panelPropuestas.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar colaborador: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
