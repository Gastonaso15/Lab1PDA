package culturarte.logica.controlador;

import culturarte.logica.manejador.UsuarioManejador;
import culturarte.persistencia.JPAUtil;
import culturarte.logica.modelo.Usuario;
import jakarta.persistence.EntityManager;

public class UsuarioController implements IUsuarioController {
    private EntityManager em;

    public UsuarioController() {
        EntityManager em = JPAUtil.getEntityManager();
    }

    @Override
    public  void crearUsuario(String nickname, String nombre, String apellido, String correo) throws Exception {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Usuario u = mu.obtenerUsuario(nickname);
        if (u != null)
            throw new Exception("El usuario " + nickname + " ya esta registrado");
        u = new Usuario(nickname, nombre,apellido,correo);
        mu.addUsuario(u);
    }


}