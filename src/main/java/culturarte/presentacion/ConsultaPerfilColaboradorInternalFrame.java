package culturarte.presentacion;

import culturarte.logica.DT.DTColaboracion;
import culturarte.logica.DT.DTColaborador;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ConsultaPerfilColaboradorInternalFrame extends JInternalFrame {

    private final IUsuarioController usuarioController;
    private final JList<String> listColaboradores;
    private final JLabel lblNickname;
    private final JLabel lblNombre;
    private final JLabel lblApellido;
    private final JLabel lblCorreo;
    private final JLabel lblFechaNacimiento;
    private final ImagenUIHelper.ImagenPanel lblImagen;
    private final JPanel panelPropuestas;

    public ConsultaPerfilColaboradorInternalFrame(IUsuarioController usuarioController) {
        super("Consultar Perfil de Colaborador", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        this.usuarioController = usuarioController;

        // Panel izquierdo - lista de colaboradores
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        List<String> nicknames = usuarioController.devolverNicknamesColaboradores();
        listColaboradores = new JList<>(nicknames.toArray(new String[0]));
        JScrollPane scrollColaboradores = new JScrollPane(listColaboradores);
        panelIzquierdo.add(new JLabel("Colaboradores:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollColaboradores, BorderLayout.CENTER);

        // Panel derecho - información del colaborador
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
        col2.add(lblNickname);
        col2.add(lblNombre);
        col2.add(lblApellido);
        col2.add(lblFechaNacimiento);

        // Columna 3: Correo
        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        lblCorreo = new JLabel("Correo: ");
        col3.add(lblCorreo);

        panelInfo.add(col1);
        panelInfo.add(col2);
        panelInfo.add(col3);

        // Fuente consistente
        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 20);
        setFontToLabels(fontInfo, lblNickname, lblNombre, lblApellido, lblFechaNacimiento, lblCorreo);

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

        // Listener de selección de colaborador
        listColaboradores.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nickname = listColaboradores.getSelectedValue();
                if (nickname != null) {
                    mostrarColaborador(nickname);
                }
            }
        });
    }

    private void mostrarColaborador(String nickname) {
        try {
            DTColaborador colaborador = usuarioController.obtenerColaboradorCompleto(nickname);

            lblNickname.setText("Nickname: " + colaborador.getNickname());
            lblNombre.setText("Nombre: " + colaborador.getNombre());
            lblApellido.setText("Apellido: " + colaborador.getApellido());
            lblCorreo.setText("Correo: " + colaborador.getCorreo());
            lblFechaNacimiento.setText("Fecha de Nacimiento: " + colaborador.getFechaNacimiento());
            lblImagen.setImagen(colaborador.getImagen());

            panelPropuestas.removeAll();

            if (colaborador.getColaboraciones() != null && !colaborador.getColaboraciones().isEmpty()) {
                for (DTColaboracion colaboracion : colaborador.getColaboraciones()) {
                    DTPropuesta propuesta = colaboracion.getPropuesta();

                    if (propuesta.getEstadoActual() == DTEstadoPropuesta.INGRESADA) continue;

                    JPanel pPanel = new JPanel();
                    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                    pPanel.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Color.BLACK),
                            propuesta.getTitulo() + " - " + propuesta.getEstadoActual(),
                            TitledBorder.LEFT,
                            TitledBorder.TOP
                    ));

                    // Colorear según estado
                    switch (propuesta.getEstadoActual()) {
                        case PUBLICADA -> pPanel.setBackground(new Color(144, 238, 144));
                        case EN_FINANCIACION -> pPanel.setBackground(new Color(255, 255, 102));
                        case CANCELADA -> pPanel.setBackground(new Color(255, 102, 102));
                        case FINANCIADA -> pPanel.setBackground(new Color(102, 178, 255));
                        case NO_FINANCIADA -> pPanel.setBackground(new Color(211, 211, 211));
                    }

                    double dineroRecaudado = propuesta.getColaboraciones() != null ?
                            propuesta.getColaboraciones().stream().mapToDouble(DTColaboracion::getMonto).sum() : 0;

                    pPanel.add(new JLabel("Título: " + propuesta.getTitulo()));
                    pPanel.add(new JLabel("Proponente: " +
                            (propuesta.getDTProponente() != null ? propuesta.getDTProponente().getNickname() : "N/A")));
                    pPanel.add(new JLabel("Fecha Prevista: " + propuesta.getFechaPrevista()));
                    pPanel.add(new JLabel("Monto Necesario: $" + propuesta.getMontoNecesario()));
                    pPanel.add(new JLabel("Dinero Recaudado: $" + dineroRecaudado));
                    pPanel.add(new JLabel("Mi Colaboración: $" + colaboracion.getMonto()));
                    pPanel.add(new JLabel("Estado Actual: " + propuesta.getEstadoActual()));

                    panelPropuestas.add(pPanel);
                    panelPropuestas.add(Box.createRigidArea(new Dimension(0, 5)));
                }
            } else {
                JLabel lblSinColaboraciones = new JLabel("Este colaborador no ha participado en ninguna propuesta.");
                lblSinColaboraciones.setFont(new Font("Times New Roman", Font.ITALIC, 16));
                panelPropuestas.add(lblSinColaboraciones);
            }

            panelPropuestas.revalidate();
            panelPropuestas.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar colaborador: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) lbl.setFont(font);
    }
}
