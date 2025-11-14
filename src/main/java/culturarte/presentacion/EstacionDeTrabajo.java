package culturarte.presentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import culturarte.servicios.Fabrica;
import culturarte.servicios.interfaces.IPropuestaController;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.presentacion.internalFrames.*;

public class EstacionDeTrabajo extends JFrame {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Button.background", new Color(40, 50, 70));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("ComboBox.background", Color.WHITE);
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("List.font", new Font("Segoe UI", Font.PLAIN, 13));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));
    }

    private final JDesktopPane desktop;
    private IUsuarioController ICU;
    private IPropuestaController ICP;
    private JLabel statusLabel;
    private JToolBar toolBar;

    public EstacionDeTrabajo() {
        this.desktop = new JDesktopPane();
        setupFrame();
        initControllers();
        setJMenuBar(buildMenuBar());
        add(buildToolBar(), BorderLayout.NORTH);
        add(desktop, BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    private void setupFrame() {
        setTitle("Culturarte - Estación de Trabajo");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktop.setOpaque(true);
        desktop.setBackground(new Color(245, 248, 250));

        try {
            java.net.URL url = EstacionDeTrabajo.class.getResource("/uploads/Swing/fondo.png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                JLabel fondo = new JLabel(icon);
                fondo.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
                desktop.add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
            }
        } catch (Exception e) {
        }
    }

    private void initControllers() {
        Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIUsuarioController();
        ICP = fabrica.getIPropuestaController();
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(new LineBorder(new Color(180, 180, 180), 1));
        menuBar.setBackground(new Color(250, 250, 250));

        JMenu usuarios = new JMenu("  Usuarios  ");
        usuarios.setMnemonic('U');
        usuarios.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JMenuItem altaUsuario = createMenuItem("Alta de Usuario", 'A', e -> abrirAltaUsuario());
        JMenuItem consultaProponente = createMenuItem("Consultar Perfil de Proponente", 'P', e -> abrirConsultaProponente());
        JMenuItem consultaColaborador = createMenuItem("Consultar Perfil de Colaborador", 'C', e -> abrirConsultaColaborador());
        usuarios.add(altaUsuario);
        usuarios.add(consultaProponente);
        usuarios.add(consultaColaborador);
        usuarios.addSeparator();

        JMenuItem seguirUsuario = createMenuItem("Seguir Usuario", 'S', e -> abrirSeguirUsuario());
        JMenuItem dejarSeguirUsuario = createMenuItem("Dejar de Seguir Usuario", 'D', e -> abrirDejarSeguirUsuario());
        usuarios.add(seguirUsuario);
        usuarios.add(dejarSeguirUsuario);
        usuarios.addSeparator();

        JMenuItem verProponentesEliminados = createMenuItem("Ver Proponentes Eliminados", 'E', e -> abrirVerProponentesEliminados());
        JMenuItem verRegistroAccesos = createMenuItem("Ver Registro de Acceso al Sitio", 'R', e -> abrirVerRegistroAccesos());
        usuarios.add(verProponentesEliminados);
        usuarios.add(verRegistroAccesos);

        JMenu propuestas = new JMenu("  Propuestas  ");
        propuestas.setMnemonic('P');
        propuestas.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JMenuItem altaPropuesta = createMenuItem("Alta de Propuesta", 'A', e -> abrirAltaPropuesta());
        JMenuItem consultarPropuesta = createMenuItem("Consultar Propuesta", 'C', e -> abrirConsultarPropuesta());
        JMenuItem consultaPropEstado = createMenuItem("Consulta de Propuestas por Estado", 'E', e -> abrirConsultaPropEstado());
        JMenuItem modificarPropuesta = createMenuItem("Modificar Propuesta", 'M', e -> abrirModificarPropuesta());
        propuestas.add(altaPropuesta);
        propuestas.add(consultarPropuesta);
        propuestas.add(consultaPropEstado);
        propuestas.add(modificarPropuesta);
        propuestas.addSeparator();

        JMenuItem altaCategoria = createMenuItem("Alta de Categoría", 'C', e -> abrirAltaCategoria());
        propuestas.add(altaCategoria);
        propuestas.addSeparator();

        JMenuItem registrarColaboracion = createMenuItem("Registrar Colaboración", 'R', e -> abrirRegistrarColaboracion());
        JMenuItem consultarColaboracion = createMenuItem("Consultar Colaboración", 'O', e -> abrirConsultarColaboracion());
        JMenuItem cancelarColaboracion = createMenuItem("Cancelar Colaboración", 'N', e -> abrirCancelarColaboracion());
        propuestas.add(registrarColaboracion);
        propuestas.add(consultarColaboracion);
        propuestas.add(cancelarColaboracion);
        propuestas.addSeparator();

        JMenuItem evaluarPropuesta = createMenuItem("Evaluar Propuesta", 'V', e -> abrirEvaluarPropuesta());
        JMenuItem gestionarComentarios = createMenuItem("Gestionar Comentarios", 'G', e -> abrirGestionarComentarios());
        propuestas.add(evaluarPropuesta);
        propuestas.add(gestionarComentarios);

        JMenu ventana = new JMenu("  Ventana  ");
        ventana.setMnemonic('V');
        ventana.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JMenuItem cascada = createMenuItem("Cascada", 'C', e -> organizarCascada());
        JMenuItem mosaico = createMenuItem("Mosaico", 'M', e -> organizarMosaico());
        JMenuItem cerrarTodas = createMenuItem("Cerrar Todas", 'T', e -> cerrarTodas());
        ventana.add(cascada);
        ventana.add(mosaico);
        ventana.addSeparator();
        ventana.add(cerrarTodas);

        menuBar.add(usuarios);
        menuBar.add(propuestas);
        menuBar.add(ventana);

        return menuBar;
    }

    private JMenuItem createMenuItem(String text, int mnemonic, ActionListener listener) {
        JMenuItem item = new JMenuItem(text, mnemonic);
        item.addActionListener(listener);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        item.setBorder(new EmptyBorder(4, 8, 4, 8));
        return item;
    }

    private JToolBar buildToolBar() {
        toolBar = new JToolBar("Herramientas");
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        toolBar.setBackground(new Color(250, 250, 250));

        return toolBar;
    }

    private void addToolBarButton(String text, String tooltip, ActionListener listener) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(new Color(50, 50, 50));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        button.setMargin(new Insets(8, 15, 8, 15));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(2, 2, 2, 2)
        ));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 235, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });
        toolBar.add(button);
    }

    private JPanel buildStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(3, 0, 3, 0)
        ));
        statusBar.setBackground(new Color(245, 245, 245));
        statusBar.setPreferredSize(new Dimension(getWidth(), 28));

        statusLabel = new JLabel("  Listo");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(60, 60, 60));
        statusLabel.setBorder(new EmptyBorder(0, 15, 0, 15));
        statusBar.add(statusLabel, BorderLayout.WEST);

        JLabel versionLabel = new JLabel("Culturarte v1.0  ");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(100, 100, 100));
        versionLabel.setBorder(new EmptyBorder(0, 15, 0, 15));
        statusBar.add(versionLabel, BorderLayout.EAST);

        return statusBar;
    }

    private void organizarCascada() {
        JInternalFrame[] frames = desktop.getAllFrames();
        if (frames.length == 0) return;

        int x = 0, y = 0;
        int offset = 30;
        for (JInternalFrame frame : frames) {
            try {
                frame.setIcon(false);
                frame.setLocation(x, y);
                frame.setSize(800, 600);
                x += offset;
                y += offset;
                if (x + 800 > desktop.getWidth() || y + 600 > desktop.getHeight()) {
                    x = 0;
                    y = 0;
                }
            } catch (java.beans.PropertyVetoException ignored) {}
        }
        actualizarEstado("Ventanas organizadas en cascada");
    }

    private void organizarMosaico() {
        JInternalFrame[] frames = desktop.getAllFrames();
        if (frames.length == 0) return;

        int count = frames.length;
        int cols = (int) Math.ceil(Math.sqrt(count));
        int rows = (int) Math.ceil((double) count / cols);

        int width = desktop.getWidth() / cols;
        int height = desktop.getHeight() / rows;

        int index = 0;
        for (int row = 0; row < rows && index < count; row++) {
            for (int col = 0; col < cols && index < count; col++) {
                try {
                    frames[index].setIcon(false);
                    frames[index].setLocation(col * width, row * height);
                    frames[index].setSize(width - 5, height - 5);
                    index++;
                } catch (java.beans.PropertyVetoException ignored) {}
            }
        }
        actualizarEstado("Ventanas organizadas en mosaico");
    }

    private void cerrarTodas() {
        JInternalFrame[] frames = desktop.getAllFrames();
        for (JInternalFrame frame : frames) {
            frame.dispose();
        }
        actualizarEstado("Todas las ventanas cerradas");
    }

    private void abrir(JInternalFrame frame) {
        desktop.add(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
            frame.moveToFront();
        } catch (java.beans.PropertyVetoException ignored) {}
        actualizarEstado("Ventana abierta: " + frame.getTitle());
    }

    private void actualizarEstado(String mensaje) {
        if (statusLabel != null) {
            statusLabel.setText(" " + mensaje);
        }
    }

    private void abrirAltaUsuario() {
        AltaUsuarioInternalFrame frame = new AltaUsuarioInternalFrame(ICU);
        abrir(frame);
    }


    private void abrirConsultaProponente() {
        ConsultaPerfilProponenteInternalFrame frame = new ConsultaPerfilProponenteInternalFrame(ICU);
        abrir(frame);
    }


    private void abrirConsultaColaborador() {
        ConsultaPerfilColaboradorInternalFrame frame = new ConsultaPerfilColaboradorInternalFrame(ICU);
        abrir(frame);
    }


    private void abrirSeguirUsuario() {
        SeguirUsuarioInternalFrame frame = new SeguirUsuarioInternalFrame(ICU);
        abrir(frame);
    }


    private void abrirDejarSeguirUsuario() {
        DejarSeguirUsuarioInternalFrame frame = new DejarSeguirUsuarioInternalFrame(ICU);
        abrir(frame);
    }


    private void abrirAltaPropuesta() {
        AltaPropuestaInternalFrame frame = new AltaPropuestaInternalFrame(ICP, ICU);
        abrir(frame);
    }


    private void abrirConsultarPropuesta() {
        ConsultarPropuestaInternalFrame frame = new ConsultarPropuestaInternalFrame(ICP);
        abrir(frame);
    }

    private void abrirConsultaPropEstado() {
        ConsultaPropuestasPorEstadoInternalFrame frame = new ConsultaPropuestasPorEstadoInternalFrame(ICP);
        abrir(frame);
    }


    private void abrirModificarPropuesta() {
        ModificarDatosPropuestaInternalFrame frame = new ModificarDatosPropuestaInternalFrame(ICP);
        abrir(frame);
    }

    private void abrirAltaCategoria() {
        AltaCategoriaInternalFrame frame = new AltaCategoriaInternalFrame(ICP);
        abrir(frame);
    }

    private void abrirRegistrarColaboracion() {
        RegistrarColaboracionInternalFrame frame = new RegistrarColaboracionInternalFrame(ICP);
        abrir(frame);
    }

    private void abrirConsultarColaboracion() {
        ConsultaColaboracionInternalFrame frame = new ConsultaColaboracionInternalFrame(ICU);
        abrir(frame);
    }

    private void abrirCancelarColaboracion() {
        CancelarColaboracionInternalFrame frame = new CancelarColaboracionInternalFrame(ICP);
        abrir(frame);
    }
    private void abrirEvaluarPropuesta() {
        EvaluarPropuestaInternalFrame frame = new EvaluarPropuestaInternalFrame(ICP);
        abrir(frame);
    }

    private void abrirGestionarComentarios() {
        GestionarComentariosInternalFrame frame = new GestionarComentariosInternalFrame(ICP);
        abrir(frame);
    }

    private void abrirVerProponentesEliminados() {
        VerProponentesEliminadosInternalFrame frame = new VerProponentesEliminadosInternalFrame(ICU);
        abrir(frame);
    }

    private void abrirVerRegistroAccesos() {
        VerRegistroAccesoInternalFrame frame = new VerRegistroAccesoInternalFrame(ICU);
        abrir(frame);
    }
}
