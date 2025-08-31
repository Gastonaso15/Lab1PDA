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
        this.desktop = new JDesktopPane();
        setupFrame(); // --- Configuración base de la ventana (Swing) ---
        initControllers(); // --- Obtener controladores desde la Fábrica ---
        setJMenuBar(buildMenuBar());// --- Barra de menú (Usuarios / Propuestas) ---
        add(desktop, BorderLayout.CENTER);// Agregamos el escritorio al centro del frame
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        /*
        Eso cambia cómo se arrastran los JInternalFrame dentro del JDesktopPane:

        OUTLINE_DRAG_MODE: mientras arrastrás, se dibuja solo el contorno de la ventana interna. Es súper fluido y no repinta to do el contenido (mejor rendimiento).

        LIVE_DRAG_MODE (valor por defecto): arrastra la ventana completa repintando su contenido en cada pixel que movés. Se ve “más real”, pero consume más.
         */
    }

    // --------------------------------------------------------------------
    // 4) Métodos privados de construcción
    // --------------------------------------------------------------------
    private void setupFrame() { //(Esto es para configurar Swing (título, tamaño, cierre))
        setTitle("Culturarte - Estación de Trabajo");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrar en pantalla
        desktop.setOpaque(true); // por si el L&F lo deja transparente
        desktop.setBackground(new java.awt.Color(100, 140, 212)); // celestito
        /*
        Faltan detalles que no me dio el tiempo y por eso no lo dejo puesto todavia
        JLabel fondo = new JLabel(new ImageIcon("ruta/imagen.png"));
        fondo.setBounds(0, 0, fondo.getIcon().getIconWidth(), fondo.getIcon().getIconHeight());
        desktop.add(fondo, JLayeredPane.FRAME_CONTENT_LAYER); // capa de fondo
        */

    }

    private void initControllers() {// Esto inicializa las referencias a los controladores de la capa lógica
        Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIUsuarioController();
        ICP = fabrica.getIPropuestaController();
    }

    private JMenuBar buildMenuBar() {      // Construye la barra de menú y registra las acciones (listeners) -> retorna un JMenuBar
        JMenuBar menu = new JMenuBar(); //Creo la barra de "menu"


        // ---------------- USUARIOS ----------------

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
        consultaProponente.addActionListener(e -> abrirConsultaProponente());
        consultaColaborador.addActionListener(e -> abrirConsultaColaborador());
        seguirUsuario.addActionListener(e -> abrirSeguirUsuario());
        dejarSeguirUsuario.addActionListener(e -> abrirDejarSeguirUsuario());



        // ---------------- PROPUESTAS --------------

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

        // Acciones (listeners)
        altaPropuesta.addActionListener(e -> abrirAltaPropuesta());
        consultarPropuesta.addActionListener(e -> abrirConsultarPropuesta());
        modificarPropuesta.addActionListener(e -> abrirModificarPropuesta());

        // Agregar menús a la barra "menu"
        menu.add(usuarios);
        menu.add(propuestas);
        setJMenuBar(menu);

        //Retorno el JMenu ya configurado
        return menu;
    }


    //¿NO HAY PROBLEMA CON DEFINIR ESTO DESPUES DEL private JMenuBar buildMenuBar() { que los llama?


    // Metodo común para agregar y mostrar un InternalFrame en el desktop
    private void abrir(JInternalFrame frame) {
        desktop.add(frame);
        frame.setVisible(true);
        //Prevencion para que no explote
        try {
            frame.setSelected(true); // foco al abrir
        } catch (java.beans.PropertyVetoException ignored) {}
    }
    // 5) Helpers — abrir cada InternalFrame
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


    private void abrirModificarPropuesta() {
        ModificarDatosPropuestaInternalFrame frame = new ModificarDatosPropuestaInternalFrame(ICP);
        abrir(frame);
    }



}



