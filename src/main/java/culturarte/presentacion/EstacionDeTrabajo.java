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
        setSize(800, 600);
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

        JMenu propuestas = new JMenu("Propuestas");
        JMenuItem altaPropuesta = new JMenuItem("Alta de Propuesta");
        propuestas.add(altaPropuesta);
        JMenuItem consultarPropuesta = new JMenuItem("Consultar Propuesta");
        propuestas.add(consultarPropuesta);

        menu.add(usuarios);
        menu.add(propuestas);
        setJMenuBar(menu);

        altaUsuario.addActionListener(e -> {
            AltaUsuarioInternalFrame frame = new AltaUsuarioInternalFrame(ICU);
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));
    }
}
