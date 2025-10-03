package culturarte.logica.controladores;

import culturarte.logica.DTs.*;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;

public class SesionController implements ISesionController{
    private Usuario usuarioActual;

    @Override
    public void login(String nick, String password){
        Usuario u = UsuarioManejador.getInstance().obtenerUsuarioPorNickname(nick);
        if(u!=null && u.getPassword().equals(password)){
            usuarioActual = u;
        }else{
            throw new RuntimeException("Datos incorrectos");
        }
    }
    @Override
    public void logout(){
        usuarioActual = null;
    }
    @Override
    public Usuario getUsuarioActual(){
        return usuarioActual;
    }
}