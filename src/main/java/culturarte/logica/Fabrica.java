package culturarte.logica;

import culturarte.logica.controladores.*;

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


}
