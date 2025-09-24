package culturarte.logica.controladores;

import culturarte.logica.DTs.DTColaborador;
import culturarte.logica.DTs.DTProponente;
import culturarte.logica.DTs.DTUsuario;

import java.util.List;

public interface IUsuarioController {
    void crearUsuario(DTUsuario dtu) throws Exception;
    List<String> devolverNicknamesUsuarios();
    List<String> devolverNicknamesProponentes();
    List<String> devolverNicknamesColaboradores();
    DTProponente devolverProponentePorNickname(String nickname) throws Exception;
    DTColaborador devolverColaboradorPorNickname(String nickname) throws Exception;
    void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception;
    List<String> devolverUsuariosSeguidos(String nicknameSeguidor);
    void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido);
}
