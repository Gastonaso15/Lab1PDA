package culturarte.presentacion;

import culturarte.logica.DT.DTColaboracion;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ConsultaPerfilProponenteInternalFrame extends JInternalFrame {

    private final IUsuarioController usuarioController;
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

    public ConsultaPerfilProponenteInternalFrame(IUsuarioController usuarioController) {
        super("Consultar Perfil de Proponente", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        this.usuarioController = usuarioController;

        // Panel izquierdo: lista de proponentes
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        List<String> nicknames = usuarioController.devolverNicknamesProponentes();
        listProponentes = new JList<>(nicknames.toArray(new String[0]));
        JScrollPane scrollProponentes = new JScrollPane(listProponentes);
        panelIzquierdo.add(new JLabel("Proponentes:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollProponentes, BorderLayout.CENTER);

        // Panel derecho: información y propuestas
        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelInfo = new JPanel(new GridLayout(1, 3, 10, 0));

        // Columna 1: Imagen
        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        lblImagen = new ImagenUIHelper.ImagenPanel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        col1.add(lblImagen);

        // Columna 2: Datos personales
        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        lblNickname = new JLabel("Nickname: ");
        lblNombre = new JLabel("Nombre: ");
        lblApellido = new JLabel("Apellido: ");
        lblFechaNacimiento = new JLabel("Fecha de nacimiento: ");
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

        col2.add(lblNickname);
        col2.add(lblNombre);
        col2.add(lblApellido);
        col2.add(lblFechaNacimiento);
        col2.add(lblCorreo);
        col2.add(lblDireccion);
        col2.add(lblWeb);
        col2.add(scrollBio);

        panelInfo.add(col1);
        panelInfo.add(col2);

        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 20);
        setFontToLabels(fontInfo, lblNickname, lblNombre, lblApellido, lblFechaNacimiento,
                lblCorreo, lblDireccion, lblWeb);

        // Panel de propuestas
        panelPropuestas = new JPanel();
        panelPropuestas.setLayout(new BoxLayout(panelPropuestas, BoxLayout.Y_AXIS));
        JScrollPane scrollPropuestas = new JScrollPane(panelPropuestas);
        scrollPropuestas.setPreferredSize(new Dimension(600, 300));

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(scrollPropuestas, BorderLayout.CENTER);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        // Listener de selección
        listProponentes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nickname = listProponentes.getSelectedValue();
                if (nickname != null) mostrarProponente(nickname);
            }
        });
    }

    private void mostrarProponente(String nickname) {
        try {
            DTProponente proponente = usuarioController.obtenerProponenteCompleto(nickname);

            lblNickname.setText("Nickname: " + proponente.getNickname());
            lblNombre.setText("Nombre: " + proponente.getNombre());
            lblApellido.setText("Apellido: " + proponente.getApellido());
            lblCorreo.setText("Correo: " + proponente.getCorreo());
            lblFechaNacimiento.setText("Fecha de Nacimiento: " + proponente.getFechaNacimiento());
            lblDireccion.setText("Dirección: " + proponente.getDireccion());
            lblWeb.setText("Sitio Web: " + (proponente.getSitioWeb() != null ? proponente.getSitioWeb() : ""));
            txtBiografia.setText("Biografía: " + (proponente.getBio() != null ? proponente.getBio() : ""));
            lblImagen.setImagen(proponente.getImagen());

            panelPropuestas.removeAll();

            if (proponente.getPropuestas() != null && !proponente.getPropuestas().isEmpty()) {
                for (DTPropuesta propuesta : proponente.getPropuestas()) {
                    if (propuesta.getEstadoActual() == DTEstadoPropuesta.INGRESADA) continue;

                    JPanel pPanel = new JPanel();
                    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                    pPanel.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Color.BLACK),
                            propuesta.getTitulo() + " - " + propuesta.getEstadoActual(),
                            TitledBorder.LEFT,
                            TitledBorder.TOP
                    ));

                    switch (propuesta.getEstadoActual()) {
                        case PUBLICADA -> pPanel.setBackground(new Color(144, 238, 144));
                        case EN_FINANCIACION -> pPanel.setBackground(new Color(255, 255, 102));
                        case CANCELADA -> pPanel.setBackground(new Color(255, 102, 102));
                        case FINANCIADA -> pPanel.setBackground(new Color(102, 178, 255));
                        case NO_FINANCIADA -> pPanel.setBackground(new Color(211, 211, 211));
                    }

                    double dineroRecaudado = 0;
                    StringBuilder colaboradoresStr = new StringBuilder();
                    for (DTColaboracion c : propuesta.getColaboraciones()) {
                        dineroRecaudado += c.getMonto();
                        if (!colaboradoresStr.isEmpty()) {
                            colaboradoresStr.append(", ");
                        }
                        colaboradoresStr.append(c.getColaborador().getNickname());
                    }


                    pPanel.add(new JLabel("Título: " + propuesta.getTitulo()));
                    pPanel.add(new JLabel("Fecha Prevista: " + propuesta.getFechaPrevista()));
                    pPanel.add(new JLabel("Monto Necesario: " + propuesta.getMontoNecesario()));
                    pPanel.add(new JLabel("Dinero Recaudado: " + dineroRecaudado));
                    pPanel.add(new JLabel("Colaboradores: " + colaboradoresStr));

                    panelPropuestas.add(pPanel);
                    panelPropuestas.add(Box.createRigidArea(new Dimension(0, 5)));
                }
            }

            panelPropuestas.revalidate();
            panelPropuestas.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar proponente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) lbl.setFont(font);
    }
}
