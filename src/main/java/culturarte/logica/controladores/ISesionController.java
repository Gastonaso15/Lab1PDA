package culturarte.logica.controladores;

import culturarte.logica.DTs.DTUsuario;
import culturarte.logica.modelos.*;

public interface ISesionController
{
    DTUsuario login(String nick, String password);
    void logout();
    Usuario getUsuarioActual();
    boolean isLoggedIn();
}