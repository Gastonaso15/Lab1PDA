package culturarte.logica.controlador;

import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTUsuario;

import java.util.List;

public interface IUsuarioController {
    public abstract void crearUsuario(DTUsuario dtu) throws Exception;
    public abstract List<String> devolverNicknamesProponentes();
    public abstract DTProponente obtenerProponenteCompleto(String nickname) throws Exception;
    public abstract void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception;
    public abstract List<String> devolverNicknamesUsuarios();
}
