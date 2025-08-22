package culturarte.logica.controlador;

import java.util.List;

public interface IUsuarioController {
    public abstract void crearUsuario(String nickname,String nombre,String apellido,String correo,String tipo) throws Exception;
    public abstract List<String> devolverNicknamesProponentes();
}
