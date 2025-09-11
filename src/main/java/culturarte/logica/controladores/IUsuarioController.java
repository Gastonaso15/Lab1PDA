package culturarte.logica.controladores;

import culturarte.logica.DTs.DTColaborador;
import culturarte.logica.DTs.DTProponente;
import culturarte.logica.DTs.DTUsuario;

import java.util.List;

public interface IUsuarioController {
    void crearUsuario(DTUsuario dtu) throws Exception;
    List<String> devolverNicknamesProponentes();
    List<String> devolverNicknamesColaboradores();
    DTColaborador devolverColaboradorPorNickname(String nickname) throws Exception;
    DTProponente devolverProponentePorNickname(String nickname) throws Exception;
    void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception;
    List<String> devolverNicknamesUsuarios();
    void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido);
}
