package culturarte.logica.controlador;

import culturarte.logica.DT.DTColaborador;
import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTUsuario;
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
    public void crearUsuario(DTUsuario dtu) throws Exception {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Usuario u = mu.obtenerUsuarioNick(dtu.getNickname());
        if (u != null)
            throw new Exception("El usuario con el nickname " + dtu.getNickname() + " ya esta registrado");
        u = mu.obtenerUsuarioCorreo(dtu.getCorreo());
        if (u != null)
            throw new Exception("El usuario con el correo " + dtu.getCorreo() + " ya esta registrado");
        if (dtu instanceof DTProponente) {
            DTProponente dtp = (DTProponente) dtu;
            u = new Proponente(dtp.getNickname(), dtp.getNombre(), dtp.getApellido(), dtp.getCorreo(), dtp.getImagen(), dtp.getFechaNacimiento(), dtp.getDireccion(), dtp.getBio(), dtp.getSitioWeb());
        } else if (dtu instanceof DTColaborador) {
            DTColaborador dtc = (DTColaborador) dtu;
            u = new Colaborador(dtc.getNickname(), dtc.getNombre(), dtc.getApellido(), dtc.getCorreo(), dtc.getImagen(), dtc.getFechaNacimiento());
        } else {
            throw new Exception("Tipo de usuario no reconocido");
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