package culturarte.ui;

import javax.swing.*;
import java.awt.*;

public class EstacionDeTrabajo extends JFrame {

    private final JDesktopPane desktop;

    public EstacionDeTrabajo() {
        setTitle("Culturarte - Estación de Trabajo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        JMenuBar menu = new JMenuBar();

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
            AltaUsuarioInternalFrame frame = new AltaUsuarioInternalFrame();
            desktop.add(frame);
            frame.setVisible(true);
        });

        altaPropuesta.addActionListener(e -> {
            AltaPropuestaInternalFrame frame = new AltaPropuestaInternalFrame();
            desktop.add(frame);
            frame.setVisible(true);
        });

        consultarPropuesta.addActionListener(e -> {
            ConsultarPropuestaInternalFrame frame = new ConsultarPropuestaInternalFrame();
            desktop.add(frame);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionDeTrabajo().setVisible(true));
    }
}
