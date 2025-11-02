package culturarte.servicios.interfaces.web;

import culturarte.logica.endpoints.envoltorios.ListaStrings;
import culturarte.servicios.DTs.DTColaborador;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTUsuario;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IUsuarioControllerWS {
    @WebMethod
    void crearUsuario(DTUsuario dtu) throws Exception;
    @WebMethod
    ListaStrings devolverNicknamesUsuarios();
    @WebMethod
    ListaStrings devolverNicknamesProponentes();
    @WebMethod
    ListaStrings devolverNicknamesColaboradores();
    @WebMethod
    DTProponente devolverProponentePorNickname(String nickname) throws Exception;
    @WebMethod
    DTColaborador devolverColaboradorPorNickname(String nickname) throws Exception;
    @WebMethod
    void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception;
    @WebMethod
    ListaStrings devolverUsuariosSeguidos(String nicknameSeguidor);
    @WebMethod
    void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido);
    @WebMethod
    boolean usuarioUnoYaSigueUsuarioDos(String nickSeguidor, String nickSeguido);
    @WebMethod
    void marcarPropuestaFavorita(String nickname,String titulo);
    @WebMethod
    void quitarPropuestaFavorita(String nickname,String titulo);
    @WebMethod
    boolean usuarioYaTienePropuestaFavorita(String nickSeguidor, String nickSeguido);
    @WebMethod
    DTUsuario login(String nick, String password);
    @WebMethod
    DTUsuario obtenerUsuarioPorNickname(String nickname) throws Exception;
    @WebMethod
    ListaStrings obtenerFollowers(String nicknameSeguido);
}
