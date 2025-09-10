package culturarte.logica.controlador;

import culturarte.logica.DT.DTColaborador;
import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTUsuario;

import java.util.List;

public interface IUsuarioController {
    void crearUsuario(DTUsuario dtu) throws Exception;
    List<String> devolverNicknamesProponentes();
    List<String> devolverNicknamesColaboradores();
    DTColaborador obtenerColaboradorCompleto(String nickname) throws Exception;
    DTProponente obtenerProponenteCompleto(String nickname) throws Exception;
    void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception;
    List<String> devolverNicknamesUsuarios();
    void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido);
}
