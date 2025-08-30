package culturarte.presentacion;

// === Imports de Swing y AWT ===
import javax.swing.*;
import java.awt.*;

// === Imports de la lógica / controladores ===
import culturarte.logica.Fabrica;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

/**
 * Estación de Trabajo (Frame principal) — versión refactorizada y comentada
 *
 * Guía visual del archivo:
 * 1) main: punto de entrada de la app
 * 2) atributos privados (UI + controladores)
 * 3) constructor: arma la ventana y delega en métodos privados
 * 4) métodos privados de construcción (frame, controladores, menús)
 * 5) helpers para abrir cada InternalFrame
 */
public class EstacionDeTrabajo extends JFrame {
    // 1) ENTRANCE POINT (main)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));  // Esto asegura que la UI de Swing se construya en el hilo de eventos
    }

    // 2) PRIVATE ATRIBUTES (UI + controladores)
    private final JDesktopPane desktop;         // Esto es el “escritorio” MDI donde se agregan los JInternalFrame
    private IUsuarioController ICU;             // Interfaces de la capa de lógica que usa esta pantalla
    private IPropuestaController ICP;

    // 3) CONSTRUCTOR (arma la ventana y delega responsabilidades)
    public EstacionDeTrabajo() {
        setupFrame(); // --- Configuración base de la ventana (Swing) ---
        initControllers(); // --- Obtener controladores desde la Fábrica ---
        setJMenuBar(buildMenuBar());// --- Barra de menú (Usuarios / Propuestas) ---
        add(desktop, BorderLayout.CENTER);// Agregamos el escritorio al centro del frame
    }

    // 4) Métodos privados de construcción
    // Esto es para configurar Swing (título, tamaño, cierre)
private void setupFrame() {
        setTitle("Culturarte - Estación de Trabajo");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        JMenuBar menu = new JMenuBar();

        Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIUsuarioController();
        ICP = fabrica.getIPropuestaController();

        JMenu usuarios = new JMenu("Usuarios");
        JMenuItem altaUsuario = new JMenuItem("Alta de Usuario");
        usuarios.add(altaUsuario);
        JMenuItem consultaProponente = new JMenuItem("Consultar Perfil de Proponente");
        usuarios.add(consultaProponente);
        JMenuItem consultaColaborador = new JMenuItem("Consultar Perfil de Colaborador");
        usuarios.add(consultaColaborador);
        JMenuItem seguirUsuario = new JMenuItem("Seguir Usuario");
        usuarios.add(seguirUsuario);
        JMenuItem dejarSeguirUsuario = new JMenuItem("Dejar de Seguir Usuario");
        usuarios.add(dejarSeguirUsuario);

        JMenu propuestas = new JMenu("Propuestas");
        JMenuItem altaPropuesta = new JMenuItem("Alta de Propuesta");
        propuestas.add(altaPropuesta);
        JMenuItem consultarPropuesta = new JMenuItem("Consultar Propuesta");
        propuestas.add(consultarPropuesta);
        JMenuItem consultaPropEstado = new JMenuItem("Consulta de Propuestas por Estado");
        propuestas.add(consultaPropEstado);
        JMenuItem modificarPropuesta = new JMenuItem("Modificar Propuesta");
        propuestas.add(modificarPropuesta);
        JMenuItem altaCategoria = new JMenuItem("Alta de Categoria");
        propuestas.add(altaCategoria);
        JMenuItem registrarColaboracion = new JMenuItem("Registrar Colaboracion a Propuesta");
        propuestas.add(registrarColaboracion);
        JMenuItem consultarColaboracion = new JMenuItem("Consultar Colaboracion a Propuesta");
        propuestas.add(consultarColaboracion);
        JMenuItem cancelarColaboracion = new JMenuItem("Cancelar Colaboracion a Propuesta");
        propuestas.add(cancelarColaboracion);


        menu.add(usuarios);
        menu.add(propuestas);
        setJMenuBar(menu);


        altaUsuario.addActionListener(e -> {
            AltaUsuarioInternalFrame frame = new AltaUsuarioInternalFrame(ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultaProponente.addActionListener(e -> {
            ConsultaPerfilProponenteInternalFrame frame = new ConsultaPerfilProponenteInternalFrame(ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultaColaborador.addActionListener(e -> {
            ConsultaPerfilColaboradorInternalFrame frame = new ConsultaPerfilColaboradorInternalFrame(ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        seguirUsuario.addActionListener(e -> {
            SeguirUsuarioInternalFrame frame = new SeguirUsuarioInternalFrame(ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        dejarSeguirUsuario.addActionListener(e -> {
            DejarSeguirUsuarioInternalFrame frame = new DejarSeguirUsuarioInternalFrame(ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        altaPropuesta.addActionListener(e -> {
            AltaPropuestaInternalFrame frame = new AltaPropuestaInternalFrame(ICP,ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultarPropuesta.addActionListener(e -> {
            ConsultarPropuestaInternalFrame frame = new ConsultarPropuestaInternalFrame(ICP);
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultaPropEstado.addActionListener(e -> {
            ConsultaPropuestasPorEstadoInternalFrame frame = new ConsultaPropuestasPorEstadoInternalFrame(ICP);
            desktop.add(frame);
            frame.setVisible(true);
        });

        modificarPropuesta.addActionListener(e -> {
            ModificarDatosPropuestaInternalFrame frame = new ModificarDatosPropuestaInternalFrame(ICP);
            desktop.add(frame);
            frame.setVisible(true);
        });

        altaCategoria.addActionListener(e -> {
            AltaCategoriaInternalFrame frame = new AltaCategoriaInternalFrame(ICP);
            desktop.add(frame);
            frame.setVisible(true);
        });

        registrarColaboracion.addActionListener(e -> {
            RegistrarColaboracionInternalFrame frame = new RegistrarColaboracionInternalFrame(ICP,ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultarColaboracion.addActionListener(e -> {
            ConsultaColaboracionInternalFrame frame = new ConsultaColaboracionInternalFrame(ICP,ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });

        cancelarColaboracion.addActionListener(e -> {
            CancelarColaboracionInternalFrame frame = new CancelarColaboracionInternalFrame(ICP,ICU);
            desktop.add(frame);
            frame.setVisible(true);
        });





}
