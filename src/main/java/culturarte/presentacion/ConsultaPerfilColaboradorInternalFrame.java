package culturarte.presentacion;

import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import java.awt.*;

public class ConsultaPerfilColaboradorInternalFrame extends JInternalFrame {

    private IUsuarioController UsuarioContr;

    public ConsultaPerfilColaboradorInternalFrame(IUsuarioController icu) {
        super("Consultar Perfil de Colaborador", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

    }

}
