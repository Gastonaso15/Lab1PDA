package culturarte.logica.controladores;

import culturarte.logica.modelos.Usuario;

public interface ISesionController
{
    public void login(String nick, String password);
    public void logout();
    public Usuario getUsuarioActual();
    public boolean isLoggedIn();
}