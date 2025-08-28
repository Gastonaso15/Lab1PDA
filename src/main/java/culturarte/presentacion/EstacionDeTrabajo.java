package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;

import culturarte.logica.Fabrica;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

public class EstacionDeTrabajo extends JFrame {

    private final JDesktopPane desktop;
    private IUsuarioController ICU;
    private IPropuestaController ICP;

    public EstacionDeTrabajo() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));
    }

}
