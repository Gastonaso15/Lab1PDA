package culturarte.presentacion.internalFrames;

import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTEstadoPropuesta;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.presentacion.helpers.ImagenUIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ConsultaPerfilProponenteInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;
    private final JList<String> listProponentes;
    private final JLabel lblNickname;
    private final JLabel lblNombre;
    private final JLabel lblApellido;
    private final JLabel lblCorreo;
    private final JLabel lblFechaNacimiento;
    private final JLabel lblDireccion;
    private final JTextArea txtBiografia;
    private final JLabel lblWeb;
    private final ImagenUIHelper.ImagenPanel lblImagen;
    private final JPanel panelPropuestas;

    public ConsultaPerfilProponenteInternalFrame(IUsuarioController icu) {
        super("Consultar Perfil de Proponente", true, true, true, true);
        setSize(1200, 700);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        UsuarioContr = icu;

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBackground(new Color(250, 250, 250));
        List<String> nicknames = icu.devolverNicknamesProponentes();
        listProponentes = new JList<>(nicknames.toArray(new String[0]));
        listProponentes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        listProponentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollProponentes = new JScrollPane(listProponentes);
        scrollProponentes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Proponentes",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelIzquierdo.add(scrollProponentes, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBackground(new Color(250, 250, 250));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Información del Proponente",
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

        lblDireccion = createDetailLabel("Dirección: ");
        gbc.gridx = 0; gbc.gridy = 5;
        gridInfo.add(createInfoLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblDireccion, gbc);

        lblWeb = createDetailLabel("Sitio Web: ");
        gbc.gridx = 0; gbc.gridy = 6;
        gridInfo.add(createInfoLabel("Sitio Web:"), gbc);
        gbc.gridx = 1;
        gridInfo.add(lblWeb, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridInfo.add(createInfoLabel("Biografía:"), gbc);
        gbc.gridx = 1;
        txtBiografia = new JTextArea(3, 30);
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        txtBiografia.setEditable(false);
        txtBiografia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBiografia.setBackground(new Color(240, 248, 255));
        txtBiografia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollBio = new JScrollPane(txtBiografia);
        scrollBio.setBorder(null);
        gridInfo.add(scrollBio, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLoweredBevelBorder());
        gridInfo.add(lblImagen, gbc);

        panelInfo.add(gridInfo);

        // Hacer que el panel de información también tenga scroll
        JScrollPane scrollInfo = new JScrollPane(panelInfo);
        scrollInfo.setBorder(null);
        scrollInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollInfo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        panelPropuestas = new JPanel();
        panelPropuestas.setLayout(new BoxLayout(panelPropuestas, BoxLayout.Y_AXIS));
        panelPropuestas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Propuestas del Proponente",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelPropuestas.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPropuestas = new JScrollPane(panelPropuestas);
        scrollPropuestas.setBorder(null);
        scrollPropuestas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPropuestas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Usar JSplitPane vertical para permitir ajustar el tamaño
        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollInfo, scrollPropuestas);
        splitVertical.setDividerLocation(400);
        splitVertical.setDividerSize(8);
        splitVertical.setResizeWeight(0.4); // 40% para info, 60% para propuestas
        splitVertical.setBorder(null);
        splitVertical.setOneTouchExpandable(true);
        
        panelDerecho.add(splitVertical, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        listProponentes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nickname = listProponentes.getSelectedValue();
                if (nickname != null) {
                    mostrarProponente(nickname);
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

    private void mostrarProponente(String nickname) {
        try {
            DTProponente proponente = UsuarioContr.devolverProponentePorNickname(nickname);
            lblNickname.setText(proponente.getNickname() != null ? proponente.getNickname() : "");
            lblNombre.setText(proponente.getNombre() != null ? proponente.getNombre() : "");
            lblApellido.setText(proponente.getApellido() != null ? proponente.getApellido() : "");
            lblCorreo.setText(proponente.getCorreo() != null ? proponente.getCorreo() : "");
            lblFechaNacimiento.setText(proponente.getFechaNacimiento() != null ? proponente.getFechaNacimiento().toString() : "");
            lblDireccion.setText(proponente.getDireccion() != null ? proponente.getDireccion() : "");
            txtBiografia.setText(proponente.getBio() != null ? proponente.getBio() : "");
            lblWeb.setText(proponente.getSitioWeb() != null ? proponente.getSitioWeb() : "");

            lblImagen.setImagen(proponente.getImagen());

            panelPropuestas.removeAll();
            if (proponente.getPropuestas() != null) {
                for (DTPropuesta p : proponente.getPropuestas()) {
                    if (p.getEstadoActual() == DTEstadoPropuesta.INGRESADA) {
                        continue;
                    }

                    JPanel pPanel = new JPanel();
                    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                    pPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        p.getTitulo() + " - " + p.getEstadoActual(),
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 12),
                        new Color(70, 70, 70)
                    ));

                    switch(p.getEstadoActual()) {
                        case PUBLICADA -> pPanel.setBackground(new Color(200, 255, 200));
                        case EN_FINANCIACION -> pPanel.setBackground(new Color(255, 255, 200));
                        case CANCELADA -> pPanel.setBackground(new Color(255, 200, 200));
                        case FINANCIADA -> pPanel.setBackground(new Color(200, 220, 255));
                        case NO_FINANCIADA -> pPanel.setBackground(new Color(230, 230, 230));
                        default -> pPanel.setBackground(Color.WHITE);
                    }

                    double dineroRecaudado = 0;
                    StringBuilder colaboradoresStr = new StringBuilder();
                    if (p.getColaboraciones() != null) {
                        for (DTColaboracion c : p.getColaboraciones()) {
                            if (c.getMonto() != null) dineroRecaudado += c.getMonto();
                            if (!colaboradoresStr.isEmpty()) colaboradoresStr.append(", ");
                            if (c.getColaborador() != null) {
                                colaboradoresStr.append(c.getColaborador().getNickname());
                            }
                        }
                    }

                    JLabel lblTitulo = new JLabel("Título: " + (p.getTitulo() != null ? p.getTitulo() : "Sin título"));
                    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    lblTitulo.setBorder(new EmptyBorder(5, 5, 5, 5));
                    pPanel.add(lblTitulo);
                    
                    if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
                        JTextArea txtDescripcion = new JTextArea("Descripción: " + p.getDescripcion());
                        txtDescripcion.setLineWrap(true);
                        txtDescripcion.setWrapStyleWord(true);
                        txtDescripcion.setEditable(false);
                        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                        txtDescripcion.setBackground(pPanel.getBackground());
                        txtDescripcion.setBorder(new EmptyBorder(2, 5, 2, 5));
                        pPanel.add(txtDescripcion);
                    }
                    
                    if (p.getCategoria() != null) {
                        JLabel lblCategoria = new JLabel("Categoría: " + p.getCategoria().getNombre());
                        lblCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        lblCategoria.setBorder(new EmptyBorder(2, 5, 2, 5));
                        pPanel.add(lblCategoria);
                    }
                    
                    JLabel lblFecha = new JLabel("Fecha Prevista: " + (p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "No especificada"));
                    lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    lblFecha.setBorder(new EmptyBorder(2, 5, 2, 5));
                    pPanel.add(lblFecha);
                    
                    JLabel lblLugar = new JLabel("Lugar: " + (p.getLugar() != null && !p.getLugar().isEmpty() ? p.getLugar() : "No especificado"));
                    lblLugar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    lblLugar.setBorder(new EmptyBorder(2, 5, 2, 5));
                    pPanel.add(lblLugar);
                    
                    JLabel lblMonto = new JLabel("Monto Necesario: $" + (p.getMontoNecesario() != null ? String.format("%.2f", p.getMontoNecesario()) : "0.00"));
                    lblMonto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    lblMonto.setBorder(new EmptyBorder(2, 5, 2, 5));
                    pPanel.add(lblMonto);
                    
                    JLabel lblRecaudado = new JLabel("Dinero Recaudado: $" + String.format("%.2f", dineroRecaudado));
                    lblRecaudado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    lblRecaudado.setBorder(new EmptyBorder(2, 5, 2, 5));
                    pPanel.add(lblRecaudado);
                    
                    if (p.getPrecioEntrada() != null && p.getPrecioEntrada() > 0) {
                        JLabel lblPrecioEntrada = new JLabel("Precio de Entrada: $" + String.format("%.2f", p.getPrecioEntrada()));
                        lblPrecioEntrada.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        lblPrecioEntrada.setBorder(new EmptyBorder(2, 5, 2, 5));
                        pPanel.add(lblPrecioEntrada);
                    }
                    
                    JLabel lblEstado = new JLabel("Estado: " + (p.getEstadoActual() != null ? p.getEstadoActual() : "Sin estado"));
                    lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    lblEstado.setBorder(new EmptyBorder(2, 5, 2, 5));
                    pPanel.add(lblEstado);
                    
                    JLabel lblColab = new JLabel("Colaboradores: " + (colaboradoresStr.length() > 0 ? colaboradoresStr.toString() : "Ninguno"));
                    lblColab.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    lblColab.setBorder(new EmptyBorder(2, 5, 5, 5));
                    pPanel.add(lblColab);
                    
                    pPanel.setMinimumSize(new Dimension(300, 200));
                    pPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

                    panelPropuestas.add(pPanel);
                    panelPropuestas.add(Box.createRigidArea(new Dimension(0, 10)));
                }
        } else {
            JLabel noPropuestas = new JLabel("Este proponente no tiene propuestas.");
            noPropuestas.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noPropuestas.setForeground(new Color(120, 120, 120));
            panelPropuestas.add(noPropuestas);
        }

        // Forzar actualización del panel
        panelPropuestas.revalidate();
        panelPropuestas.repaint();
        
        // Asegurar que el panel sea visible
        panelPropuestas.setVisible(true);
        
        // Actualizar el contenedor padre
        panelPropuestas.getParent().revalidate();
        panelPropuestas.getParent().repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar proponente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
