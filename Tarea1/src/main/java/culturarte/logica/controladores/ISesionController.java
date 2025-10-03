package culturarte.logica.controladores;

import culturarte.logica.DTs.*;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;

public interface ISesionController
{
    public void login(String nick, String password);
    public void logout();
    public Usuario getUsuarioActual();
    public boolean isLoggedIn();
}