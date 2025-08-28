package culturarte.presentacion;

import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import java.awt.*;

public class ConsultaColaboracionInternalFrame extends JInternalFrame {

    private IPropuestaController PropuestaContr;
    private IUsuarioController UsuarioContr;

    public ConsultaColaboracionInternalFrame(IPropuestaController icp, IUsuarioController icu) {
        super("Consultar Colaboracion a Propuesta", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;
        UsuarioContr = icu;

    }
}
