package culturarte.servicios.interfaces;

import culturarte.servicios.DTs.DTColaborador;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTUsuario;

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
    boolean usuarioUnoYaSigueUsuarioDos(String nickSeguidor, String nickSeguido);
    void marcarPropuestaFavorita(String nickname,String titulo);
    void quitarPropuestaFavorita(String nickname,String titulo);
    boolean usuarioYaTienePropuestaFavorita(String nickSeguidor, String nickSeguido);
    DTUsuario login(String nick, String password);
    List<String> devolverUsuariosSeguidores(String nicknameSeguido);
    DTUsuario getDTUsuario(String nickname);
}
