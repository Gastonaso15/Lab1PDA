package culturarte.logica.endpoints;

import culturarte.servicios.Fabrica;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.servicios.DTs.*;
import java.util.List;

import jakarta.jws.WebService;

@WebService(endpointInterface = "culturarte.servicios.interfaces.IUsuarioController")
public class UsuarioWSEndpoint implements IUsuarioController {

    private IUsuarioController controlador;

    public UsuarioWSEndpoint() {
        this.controlador = Fabrica.getInstance().getIUsuarioController();
    }

    @Override
    public void crearUsuario(DTUsuario dtu) throws Exception {
        this.controlador.crearUsuario(dtu);
    }

    @Override
    public List<String> devolverNicknamesUsuarios() {
        return this.controlador.devolverNicknamesUsuarios();
    }

    @Override
    public List<String> devolverNicknamesProponentes(){
        return this.controlador.devolverNicknamesProponentes();
    }

    @Override
    public List<String> devolverNicknamesColaboradores() {
        return this.controlador.devolverNicknamesColaboradores();
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
    public List<String> devolverUsuariosSeguidos(String nicknameSeguidor) {
        return this.controlador.devolverUsuariosSeguidos(nicknameSeguidor);
    }

    @Override
    public void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) {
        this.controlador.dejarDeSeguirUsuario(nickSeguidor, nickSeguido);
    }

    @Override
    public boolean UsuarioUnoYaSigueUsuarioDos(String nickSeguidor, String nickSeguido){
        return this.controlador.UsuarioUnoYaSigueUsuarioDos(nickSeguidor, nickSeguido);
    }

    @Override
    public void marcarPropuestaFavorita(String nickname,String titulo) {
        this.controlador.marcarPropuestaFavorita(nickname, titulo);
    }

    @Override
    public void quitarPropuestaFavorita(String nickname,String titulo) {
        this.controlador.quitarPropuestaFavorita(nickname, titulo);
    }

    @Override
    public boolean UsuarioYaTienePropuestaFavorita(String nickSeguidor, String nickSeguido){
        return this.controlador.UsuarioYaTienePropuestaFavorita(nickSeguidor, nickSeguido);
    }
}