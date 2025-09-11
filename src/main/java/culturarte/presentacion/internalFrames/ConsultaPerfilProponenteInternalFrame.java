package culturarte.presentacion.internalFrames;

import culturarte.logica.DTs.DTColaboracion;
import culturarte.logica.DTs.DTEstadoPropuesta;
import culturarte.logica.DTs.DTProponente;
import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.controladores.IUsuarioController;
import culturarte.presentacion.helpers.ImagenUIHelper;

import javax.swing.*;
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
        setSize(1000, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        List<String> nicknames = icu.devolverNicknamesProponentes();
        listProponentes = new JList<>(nicknames.toArray(new String[0]));
        JScrollPane scrollProponentes = new JScrollPane(listProponentes);
        panelIzquierdo.add(new JLabel("Proponentes:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollProponentes, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelInfo = new JPanel(new GridLayout(1,3,10,0));

        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        lblNickname = new JLabel("Nickname: ");
        lblNombre = new JLabel("Nombre: ");
        lblApellido = new JLabel("Apellido: ");
        lblFechaNacimiento = new JLabel("Fecha de nacimiento: ");
        col1.add(lblNickname);
        col1.add(lblNombre);
        col1.add(lblApellido);
        col1.add(lblFechaNacimiento);

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
        scrollPropuestas.setPreferredSize(new Dimension(600, 300));

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(scrollPropuestas, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(200);
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

    private void mostrarProponente(String nickname) {
        try {
            DTProponente proponente = UsuarioContr.devolverProponentePorNickname(nickname);
            lblNickname.setText("Nickname: " + proponente.getNickname());
            lblNombre.setText("Nombre: " + proponente.getNombre());
            lblApellido.setText("Apellido: " + proponente.getApellido());
            lblCorreo.setText("Correo: " + proponente.getCorreo());
            lblFechaNacimiento.setText("Fecha de Nacimiento: " + proponente.getFechaNacimiento());
            lblDireccion.setText("Direccion: " + proponente.getDireccion());
            txtBiografia.setText("Biografía: " + (proponente.getBio() != null ? proponente.getBio() : ""));
            lblWeb.setText("Sitio Web: " + (proponente.getSitioWeb() != null ? proponente.getSitioWeb() : ""));

            lblImagen.setImagen(proponente.getImagen());

            panelPropuestas.removeAll();
            for (DTPropuesta p : proponente.getPropuestas()) {

                if (p.getEstadoActual() == DTEstadoPropuesta.INGRESADA) {
                    continue;
                }

                JPanel pPanel = new JPanel();
                pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                pPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        p.getTitulo() + " - " + p.getEstadoActual(),
                        TitledBorder.LEFT,
                        TitledBorder.TOP
                ));

                switch(p.getEstadoActual()) {
                    //case INGRESADA -> pPanel.setBackground(new Color(238, 10, 238));
                    case PUBLICADA -> pPanel.setBackground(new Color(144, 238, 144));
                    case EN_FINANCIACION -> pPanel.setBackground(new Color(255, 255, 102));
                    case CANCELADA -> pPanel.setBackground(new Color(255, 102, 102));
                    case FINANCIADA -> pPanel.setBackground(new Color(102, 178, 255));
                    case NO_FINANCIADA -> pPanel.setBackground(new Color(211, 211, 211));
                }

                double dineroRecaudado = 0;
                StringBuilder colaboradoresStr = new StringBuilder();
                for (DTColaboracion c : p.getColaboraciones()) {
                    dineroRecaudado += c.getMonto();
                    if (!colaboradoresStr.isEmpty()) {
                        colaboradoresStr.append(", ");
                    }
                    colaboradoresStr.append(c.getColaborador().getNickname());
                }

                pPanel.add(new JLabel("Titulo: " + p.getTitulo()));
                pPanel.add(new JLabel("Fecha Prevista: " + p.getFechaPrevista()));
                pPanel.add(new JLabel("Monto Necesario: " + p.getMontoNecesario()));
                pPanel.add(new JLabel("Dinero recaudado: " + dineroRecaudado));
                pPanel.add(new JLabel("Colaboradores: " + colaboradoresStr));

                panelPropuestas.add(pPanel);
                panelPropuestas.add(Box.createRigidArea(new Dimension(0,5)));
            }

            panelPropuestas.revalidate();
            panelPropuestas.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar proponente: " + ex.getMessage());
        }
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}