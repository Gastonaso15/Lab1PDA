package culturarte.logica.controladores;

import culturarte.logica.DTs.DTUsuario;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;

public class SesionController implements ISesionController{
    private Usuario usuarioActual;

    @Override
    public DTUsuario login(String usu, String password){
        Usuario u = UsuarioManejador.getInstance().obtenerUsuarioPorNickname(usu);
        if(u == null){
            u = UsuarioManejador.getInstance().obtenerUsuarioPorCorreo(usu);
        }
        if(u != null && u.getPassword().equals(password)){
            usuarioActual = u;
            return new DTUsuario(
                    u.getNickname(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getCorreo(),
                    u.getImagen()
            );
        } else {
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

    @Override
    public boolean isLoggedIn(){
        return usuarioActual != null;
    }
}