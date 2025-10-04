package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;

import culturarte.logica.Fabrica;
import culturarte.logica.controladores.IPropuestaController;
import culturarte.logica.controladores.IUsuarioController;
import culturarte.presentacion.internalFrames.*;

public class EstacionDeTrabajo extends JFrame {

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));
    }

    private final JDesktopPane desktop;
    private IUsuarioController ICU;
    private IPropuestaController ICP;

    public EstacionDeTrabajo() {
        this.desktop = new JDesktopPane();
        setupFrame();
        initControllers();
        setJMenuBar(buildMenuBar());
        add(desktop, BorderLayout.CENTER);
    }
    private void setupFrame() {
        setTitle("Culturarte - Estación de Trabajo");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktop.setOpaque(true);
        desktop.setBackground(new java.awt.Color(100, 140, 212));

        // --- fondo desde resources: src/main/resources/SwingImages/fondo.png ---
        java.net.URL url = EstacionDeTrabajo.class.getResource("/SwingImages/fondo.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            JLabel fondo = new JLabel(icon);
            fondo.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            desktop.add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        } else {
            System.out.println("No encontré /img/fondo.png");
        }
    }

    private void initControllers() {
        Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIUsuarioController();
        ICP = fabrica.getIPropuestaController();
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menu = new JMenuBar();

        JMenu usuarios = new JMenu("Usuarios");

        JMenuItem altaUsuario = new JMenuItem("Alta de Usuario");
        JMenuItem consultaProponente = new JMenuItem("Consultar Perfil de Proponente");
        JMenuItem consultaColaborador = new JMenuItem("Consultar Perfil de Colaborador");
        JMenuItem seguirUsuario = new JMenuItem("Seguir Usuario");
        JMenuItem dejarSeguirUsuario = new JMenuItem("Dejar de Seguir Usuario");

        usuarios.add(altaUsuario);
        usuarios.add(consultaProponente);
        usuarios.add(consultaColaborador);
        usuarios.add(seguirUsuario);
        usuarios.add(dejarSeguirUsuario);

        altaUsuario.addActionListener(e -> abrirAltaUsuario());
        consultaProponente.addActionListener(e -> abrirConsultaProponente());
        consultaColaborador.addActionListener(e -> abrirConsultaColaborador());
        seguirUsuario.addActionListener(e -> abrirSeguirUsuario());
        dejarSeguirUsuario.addActionListener(e -> abrirDejarSeguirUsuario());

        JMenu propuestas = new JMenu("Propuestas");

        JMenuItem altaPropuesta = new JMenuItem("Alta de Propuesta");
        JMenuItem consultarPropuesta = new JMenuItem("Consultar Propuesta");
        JMenuItem consultaPropEstado = new JMenuItem("Consulta de Propuestas por Estado");
        JMenuItem modificarPropuesta = new JMenuItem("Modificar Propuesta");
        JMenuItem altaCategoria = new JMenuItem("Alta de Categoria");
        JMenuItem registrarColaboracion = new JMenuItem("Registrar Colaboracion a Propuesta");
        JMenuItem consultarColaboracion = new JMenuItem("Consultar Colaboracion a Propuesta");
        JMenuItem cancelarColaboracion = new JMenuItem("Cancelar Colaboracion a Propuesta");

        propuestas.add(altaPropuesta);
        propuestas.add(consultarPropuesta);
        propuestas.add(consultaPropEstado);
        propuestas.add(modificarPropuesta);
        propuestas.add(altaCategoria);
        propuestas.add(registrarColaboracion);
        propuestas.add(consultarColaboracion);
        propuestas.add(cancelarColaboracion);

        altaPropuesta.addActionListener(e -> abrirAltaPropuesta());
        consultarPropuesta.addActionListener(e -> abrirConsultarPropuesta());
        consultaPropEstado.addActionListener(e ->abrirConsultaPropEstado());
        modificarPropuesta.addActionListener(e -> abrirModificarPropuesta());
        altaCategoria.addActionListener(e ->abrirAltaCategoria());
        registrarColaboracion.addActionListener(e ->abrirRegistrarColaboracion());
        consultarColaboracion.addActionListener(e ->abrirConsultarColaboracion());
        cancelarColaboracion.addActionListener(e ->abrirCancelarColaboracion());

        menu.add(usuarios);
        menu.add(propuestas);
        setJMenuBar(menu);

        return menu;
    }

    private void abrir(JInternalFrame frame) {
        desktop.add(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException ignored) {}
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

}
