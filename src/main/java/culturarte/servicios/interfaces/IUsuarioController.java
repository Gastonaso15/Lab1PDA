package culturarte.servicios.interfaces;

import culturarte.servicios.DTs.DTColaborador;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTUsuario;

import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IUsuarioController {
    @WebMethod
    void crearUsuario(DTUsuario dtu) throws Exception;
    @WebMethod
    List<String> devolverNicknamesUsuarios();
    @WebMethod
    List<String> devolverNicknamesProponentes();
    @WebMethod
    List<String> devolverNicknamesColaboradores();
    @WebMethod
    DTProponente devolverProponentePorNickname(String nickname) throws Exception;
    @WebMethod
    DTColaborador devolverColaboradorPorNickname(String nickname) throws Exception;
    @WebMethod
    void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception;
    @WebMethod
    List<String> devolverUsuariosSeguidos(String nicknameSeguidor);
    @WebMethod
    void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido);
    @WebMethod
    boolean UsuarioUnoYaSigueUsuarioDos(String nickSeguidor, String nickSeguido);
    @WebMethod
    void marcarPropuestaFavorita(String nickname,String titulo);
    @WebMethod
    void quitarPropuestaFavorita(String nickname,String titulo);
    @WebMethod
    boolean UsuarioYaTienePropuestaFavorita(String nickSeguidor, String nickSeguido);
}
