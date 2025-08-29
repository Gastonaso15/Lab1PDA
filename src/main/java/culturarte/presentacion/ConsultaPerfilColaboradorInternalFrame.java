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

    private IUsuarioController UsuarioContr;
    private JList<String> listColaboradores;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JLabel lblImagen;
    private JPanel panelPropuestas;


    public ConsultaPerfilColaboradorInternalFrame(IUsuarioController icu) {
        super("Consultar Perfil de Proponente", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        // Panel izquierdo - Lista de colaboradores
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        List<String> nicknames = icu.devolverNicknamesColaboradores();
        listColaboradores = new JList<>(nicknames.toArray(new String[0]));
        JScrollPane scrollColaboradores = new JScrollPane(listColaboradores);
        panelIzquierdo.add(new JLabel("Colaboradores:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollColaboradores, BorderLayout.CENTER);

        // Panel derecho - Información del colaborador
        JPanel panelDerecho = new JPanel(new BorderLayout());

        // Panel de información personal
        JPanel panelInfo = new JPanel(new GridLayout(1, 3, 10, 0));

        // Columna 1 - Información básica
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

        // Columna 2 - Información adicional
        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        lblCorreo = new JLabel("Correo: ");
        col2.add(lblCorreo);

        // Columna 3 - Imagen
        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        col3.add(lblImagen);

        // Configurar fuente para los labels
        Font fontInfo = new Font("Times New Roman", Font.PLAIN, 20);
        setFontToLabels(fontInfo, lblNickname, lblNombre, lblApellido, lblFechaNacimiento, lblCorreo);

        panelInfo.add(col3);
        panelInfo.add(col1);
        panelInfo.add(col2);

        // Panel de propuestas colaboradas
        panelPropuestas = new JPanel();
        panelPropuestas.setLayout(new BoxLayout(panelPropuestas, BoxLayout.Y_AXIS));
        JScrollPane scrollPropuestas = new JScrollPane(panelPropuestas);
        scrollPropuestas.setPreferredSize(new Dimension(600, 300));

        panelDerecho.add(panelInfo, BorderLayout.NORTH);
        panelDerecho.add(scrollPropuestas, BorderLayout.CENTER);

        // Configurar split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        // Listener para selección de colaborador
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
            DTColaborador colaborador = UsuarioContr.obtenerColaboradorCompleto(nickname);

            // Actualizar información personal
            lblNickname.setText("Nickname: " + colaborador.getNickname());
            lblNombre.setText("Nombre: " + colaborador.getNombre());
            lblApellido.setText("Apellido: " + colaborador.getApellido());
            lblCorreo.setText("Correo: " + colaborador.getCorreo());
            lblFechaNacimiento.setText("Fecha de Nacimiento: " + colaborador.getFechaNacimiento());

            // Mostrar imagen si existe
            if (colaborador.getImagen() != null) {
                ImageIcon icon = new ImageIcon(colaborador.getImagen());
                Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
            } else {
                lblImagen.setIcon(null);
            }

            // Limpiar y actualizar panel de propuestas
            panelPropuestas.removeAll();

            if (colaborador.getColaboraciones() != null && !colaborador.getColaboraciones().isEmpty()) {
                for (DTColaboracion colaboracion : colaborador.getColaboraciones()) {
                    DTPropuesta propuesta = colaboracion.getPropuesta();

                    if (propuesta.getEstadoActual() == DTEstadoPropuesta.INGRESADA) {
                        continue; // No mostrar propuestas ingresadas
                    }

                    JPanel pPanel = new JPanel();
                    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
                    pPanel.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Color.BLACK),
                            propuesta.getTitulo() + " - " + propuesta.getEstadoActual(),
                            TitledBorder.LEFT,
                            TitledBorder.TOP
                    ));

                    // Configurar color de fondo según el estado
                    switch(propuesta.getEstadoActual()) {
                        case PUBLICADA -> pPanel.setBackground(new Color(144, 238, 144));
                        case EN_FINANCIACION -> pPanel.setBackground(new Color(255, 255, 102));
                        case CANCELADA -> pPanel.setBackground(new Color(255, 102, 102));
                        case FINANCIADA -> pPanel.setBackground(new Color(102, 178, 255));
                        case NO_FINANCIADA -> pPanel.setBackground(new Color(211, 211, 211));
                    }

                    // Calcular dinero total recaudado para esta propuesta
                    double dineroRecaudado = 0;
                    if (propuesta.getColaboraciones() != null) {
                        for (DTColaboracion c : propuesta.getColaboraciones()) {
                            dineroRecaudado += c.getMonto();
                        }
                    }

                    // Información de la propuesta
                    pPanel.add(new JLabel("Título: " + propuesta.getTitulo()));
                    pPanel.add(new JLabel("Proponente: " + (propuesta.getDTProponente() != null ?
                            propuesta.getDTProponente().getNickname() : "N/A")));
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
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }
}