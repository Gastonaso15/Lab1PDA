package culturarte.servicios;

import culturarte.logica.controladores.*;
import culturarte.servicios.interfaces.IPropuestaController;
//import culturarte.servicios.interfaces.ISesionController;
import culturarte.servicios.interfaces.IUsuarioController;

public class Fabrica {

    private static Fabrica instancia;

    private Fabrica() {
    }

    public static Fabrica getInstance() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    public IUsuarioController getIUsuarioController() { return new UsuarioController();}
    public IPropuestaController getIPropuestaController() {return new PropuestaController();}
    //public ISesionController getISesionController() {return new SesionController();}

}
