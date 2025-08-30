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

    // --------------------------------------------------------------------
    // 1) ENTRANCE POINT (main)
    // --------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));  // Esto asegura que la UI de Swing se construya en el hilo de eventos
    }

    // --------------------------------------------------------------------
    // 2) PRIVATE ATRIBUTES (UI + controladores)
    // --------------------------------------------------------------------
    private final JDesktopPane desktop;         // Esto es el “escritorio” MDI donde se agregan los JInternalFrame
    private IUsuarioController ICU;             // Interfaces de la capa de lógica que usa esta pantalla
    private IPropuestaController ICP;

    // --------------------------------------------------------------------
    // 3) CONSTRUCTOR (arma la ventana y delega responsabilidades)
    // --------------------------------------------------------------------
    public EstacionDeTrabajo() {
        setupFrame(); // --- Configuración base de la ventana (Swing) ---
        initControllers(); // --- Obtener controladores desde la Fábrica ---
        setJMenuBar(buildMenuBar());// --- Barra de menú (Usuarios / Propuestas) ---
        add(desktop, BorderLayout.CENTER);// Agregamos el escritorio al centro del frame
    }

    // --------------------------------------------------------------------
    // 4) Métodos privados de construcción
    // --------------------------------------------------------------------
    private void setupFrame() { //(Esto es para configurar Swing (título, tamaño, cierre))
        setTitle("Culturarte - Estación de Trabajo");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrar en pantalla
    }

    private void initControllers() {// Esto inicializa las referencias a los controladores de la capa lógica
        Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIUsuarioController();
        ICP = fabrica.getIPropuestaController();
    }

    private JMenuBar buildMenuBar() {      // Construye la barra de menú y registra las acciones (listeners) -> retorna un JMenuBar
        JMenuBar menu = new JMenuBar(); //Creo la barra de "menu"
        //-------------------------------------------
        // ---------------- USUARIOS ----------------
        //-------------------------------------------
        //Creo menu "Usuarios"
        JMenu usuarios = new JMenu("Usuarios");

        //Creo items para posteriormente agregar al menu "Usuarios"

        JMenuItem altaUsuario = new JMenuItem("Alta de Usuario");
        JMenuItem consultaProponente = new JMenuItem("Consultar Perfil de Proponente");
        JMenuItem consultaColaborador = new JMenuItem("Consultar Perfil de Colaborador");
        JMenuItem seguirUsuario = new JMenuItem("Seguir Usuario");
        JMenuItem dejarSeguirUsuario = new JMenuItem("Dejar de Seguir Usuario");

        //Agrego los items al menu Creoado previamente
        usuarios.add(altaUsuario);
        usuarios.add(consultaProponente);
        usuarios.add(consultaColaborador);
        usuarios.add(seguirUsuario);
        usuarios.add(dejarSeguirUsuario);

        // Acciones (listeners)
        altaUsuario.addActionListener(e -> abrirAltaUsuario());
        //-------------------------------------------
        // ---------------- PROPUESTAS --------------
        //-------------------------------------------
        //Creo el segundo menu "Propuestas"
        JMenu propuestas = new JMenu("Propuestas");

        //Creo items para posteriormente agregar al menu "Propuestas"
        JMenuItem altaPropuesta = new JMenuItem("Alta de Propuesta");
        JMenuItem consultarPropuesta = new JMenuItem("Consultar Propuesta");
        JMenuItem consultaPropEstado = new JMenuItem("Consulta de Propuestas por Estado");
        JMenuItem modificarPropuesta = new JMenuItem("Modificar Propuesta");
        JMenuItem altaCategoria = new JMenuItem("Alta de Categoria");
        JMenuItem registrarColaboracion = new JMenuItem("Registrar Colaboracion a Propuesta");
        JMenuItem consultarColaboracion = new JMenuItem("Consultar Colaboracion a Propuesta");
        JMenuItem cancelarColaboracion = new JMenuItem("Cancelar Colaboracion a Propuesta");

        //Agrego los items al menu Creoado previamente
        propuestas.add(altaPropuesta);
        propuestas.add(consultarPropuesta);
        propuestas.add(consultaPropEstado);
        propuestas.add(modificarPropuesta);
        propuestas.add(altaCategoria);
        propuestas.add(registrarColaboracion);
        propuestas.add(consultarColaboracion);
        propuestas.add(cancelarColaboracion);

        // Acciones (listeners) de Propuestas

        // Agregar menús a la barra "menu"
        menu.add(usuarios);
        menu.add(propuestas);
        setJMenuBar(menu);
        return menu;
    }

    desktop = new JDesktopPane();

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
