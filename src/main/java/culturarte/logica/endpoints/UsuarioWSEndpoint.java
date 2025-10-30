package culturarte.logica.endpoints;

import culturarte.logica.endpoints.envoltorios.ListaStrings;
import culturarte.servicios.Fabrica;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.servicios.interfaces.web.IUsuarioControllerWS;
import culturarte.servicios.DTs.*;

import jakarta.jws.WebService;

@WebService(endpointInterface = "culturarte.servicios.interfaces.web.IUsuarioControllerWS")
public class UsuarioWSEndpoint implements IUsuarioControllerWS {

    private IUsuarioController controlador;

    public UsuarioWSEndpoint() {
        this.controlador = Fabrica.getInstance().getIUsuarioController();
    }

    @Override
    public void crearUsuario(DTUsuario dtu) throws Exception {
        this.controlador.crearUsuario(dtu);
    }

    @Override
    public ListaStrings devolverNicknamesUsuarios() {
        return new ListaStrings(this.controlador.devolverNicknamesUsuarios());
    }

    @Override
    public ListaStrings devolverNicknamesProponentes() {
        return new ListaStrings(this.controlador.devolverNicknamesProponentes());
    }

    @Override
    public ListaStrings devolverNicknamesColaboradores() {
        return new ListaStrings(this.controlador.devolverNicknamesColaboradores());
    }

    @Override
    public DTProponente devolverProponentePorNickname(String nickname) throws Exception {
        return this.controlador.devolverProponentePorNickname(nickname);
    }

    @Override
    public DTColaborador devolverColaboradorPorNickname(String nickname) throws Exception {
        return this.controlador.devolverColaboradorPorNickname(nickname);
    }

    @Override
    public void seguirUsuario(String nickSeguidor, String nickSeguido) throws Exception {
        this.controlador.seguirUsuario(nickSeguidor, nickSeguido);
    }

    @Override
    public ListaStrings devolverUsuariosSeguidos(String nicknameSeguidor) {
        return new ListaStrings(this.controlador.devolverUsuariosSeguidos(nicknameSeguidor));
    }

    @Override
    public void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) {
        this.controlador.dejarDeSeguirUsuario(nickSeguidor, nickSeguido);
    }

    @Override
    public boolean UsuarioUnoYaSigueUsuarioDos(String nickSeguidor, String nickSeguido) {
        return this.controlador.UsuarioUnoYaSigueUsuarioDos(nickSeguidor, nickSeguido);
    }

    @Override
    public void marcarPropuestaFavorita(String nickname, String titulo) {
        this.controlador.marcarPropuestaFavorita(nickname, titulo);
    }

    @Override
    public void quitarPropuestaFavorita(String nickname, String titulo) {
        this.controlador.quitarPropuestaFavorita(nickname, titulo);
    }

    @Override
    public boolean UsuarioYaTienePropuestaFavorita(String nickSeguidor, String nickSeguido) {
        return this.controlador.UsuarioYaTienePropuestaFavorita(nickSeguidor, nickSeguido);
    }

    @Override
    public DTUsuario login(String nick, String password) {
        return controlador.login(nick, password);
    }
}
