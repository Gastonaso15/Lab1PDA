package culturarte.logica;

import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;
import culturarte.logica.controlador.PropuestaController;
import culturarte.logica.controlador.UsuarioController;

public class Fabrica {

    private static Fabrica instancia;

    private Fabrica() {
    };

    public static Fabrica getInstance() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    public IUsuarioController getIUsuarioController() {
        return new UsuarioController();
    }
    public IPropuestaController getIPropuestaController() {return new PropuestaController();}

}
