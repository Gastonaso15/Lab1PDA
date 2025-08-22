package culturarte.logica.controlador;

import culturarte.logica.manejador.UsuarioManejador;
import culturarte.logica.modelo.Colaborador;
import culturarte.logica.modelo.Proponente;
import culturarte.persistencia.JPAUtil;
import culturarte.logica.modelo.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UsuarioController implements IUsuarioController {
    private EntityManager em;

    public UsuarioController() {
        EntityManager em = JPAUtil.getEntityManager();
    }

    @Override
    public void crearUsuario(String nickname, String nombre, String apellido, String correo,String tipo) throws Exception {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Usuario u = mu.obtenerUsuarioNick(nickname);
        if (u != null)
            throw new Exception("El usuario con el nickname " + nickname + " ya esta registrado");
        u = mu.obtenerUsuarioCorreo(nickname);
        if (u != null)
            throw new Exception("El usuario con el correo " + correo + " ya esta registrado");
        if ("Proponente".equals(tipo)) {
            u = new Proponente(nickname, nombre, apellido, correo);
        } else {
            u = new Colaborador(nickname, nombre, apellido, correo);
        }
        mu.addUsuario(u);
    }

    @Override
    public List<String> devolverNicknamesProponentes(){
        UsuarioManejador mu = UsuarioManejador.getinstance();
        List<String> props = mu.obtenerNicknameProponentes();
        return props;
    }




}