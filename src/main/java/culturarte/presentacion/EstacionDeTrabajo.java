package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;

import culturarte.logica.Fabrica;
import culturarte.logica.controlador.ICategoriaController;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

public class EstacionDeTrabajo extends JFrame {

    private final JDesktopPane desktop;
    private IUsuarioController ICU;
    private IPropuestaController ICP;
    private ICategoriaController ICC;

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
        ICC = fabrica.getICategoriaController();

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
        JMenuItem altaCategoria = new JMenuItem("Alta de Categoria");
        propuestas.add(altaCategoria);

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
            AltaPropuestaInternalFrame frame = new AltaPropuestaInternalFrame(ICP,ICU,ICC);
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultarPropuesta.addActionListener(e -> {
            ConsultarPropuestaInternalFrame frame = new ConsultarPropuestaInternalFrame(ICP);
            desktop.add(frame);
            frame.setVisible(true);
        });

        altaCategoria.addActionListener(e -> {
            AltaCategoriaInternalFrame frame = new AltaCategoriaInternalFrame(ICC);
            desktop.add(frame);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));
    }
}
