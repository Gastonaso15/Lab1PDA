package culturarte.logica.controlador;

public interface IUsuarioController {
    public abstract void crearUsuario(String nickname,String nombre,String apellido,String correo) throws Exception;
}
