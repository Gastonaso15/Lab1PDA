package culturarte.logica.controlador;

import culturarte.logica.DT.*;
import culturarte.logica.manejador.UsuarioManejador;
import culturarte.logica.modelo.*;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController implements IUsuarioController {

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

    @Override
    public DTProponente obtenerProponenteCompleto(String nickname) throws Exception {
        UsuarioManejador um = UsuarioManejador.getinstance();
        Proponente prop = um.obtenerProponenteNick(nickname);

        if (prop == null) {
            throw new Exception("El proponente " + nickname + " no existe");
        }

        String nombre = prop.getNombre();
        String apellido = prop.getApellido();
        String correo = prop.getCorreo();
        LocalDate fechaNacimiento = prop.getFechaNacimiento();
        String direccion = prop.getDireccion();
        String biografia = prop.getBio();
        String sitioWeb = prop.getSitioWeb();
        byte[] imagen = prop.getImagen();

        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        for (Propuesta p : prop.getPropuestas()) {

            DTCategoria dtCategoria = null;
            if (p.getCategoria() != null) {
                DTCategoria dtCategoriaPadre = null;
                if (p.getCategoria().getCategoriaPadre() != null) {
                    dtCategoriaPadre = new DTCategoria(
                            p.getCategoria().getCategoriaPadre().getNombre(),
                            new ArrayList<>(),
                            null
                    );
                }
                dtCategoria = new DTCategoria(
                        p.getCategoria().getNombre(),
                        new ArrayList<>(),
                        dtCategoriaPadre
                );
            }

            List<DTTipoRetorno> dtTiposRetorno = new ArrayList<>();
            if (p.getTiposRetorno() != null) {
                for (TipoRetorno tr : p.getTiposRetorno()) {
                    dtTiposRetorno.add(DTTipoRetorno.valueOf(tr.name()));
                }
            }

            DTEstadoPropuesta dtEstadoActual = null;
            if (p.getEstadoActual() != null) {
                dtEstadoActual = DTEstadoPropuesta.valueOf(p.getEstadoActual().name());
            }

            List<DTColaboracion> dtColaboraciones = new ArrayList<>();
            for (Colaboracion c : p.getColaboraciones()) {
                DTColaborador dtColaborador = new DTColaborador(
                        c.getColaborador().getNickname()
                );
                DTColaboracion dtc = new DTColaboracion(
                        dtColaborador,
                        c.getMonto()
                );
                dtColaboraciones.add(dtc);
            }

            DTPropuesta dtp = new DTPropuesta(
                    p.getTitulo(),
                    p.getDescripcion(),
                    p.getLugar(),
                    p.getFechaPrevista(),
                    p.getPrecioEntrada(),
                    p.getMontoNecesario(),
                    p.getImagen(),
                    dtCategoria,
                    dtTiposRetorno,
                    dtEstadoActual,
                    dtColaboraciones
            );
            dtPropuestas.add(dtp);
        }
        DTProponente dtProp = new DTProponente(nickname,nombre, apellido,correo,fechaNacimiento, imagen, direccion, biografia, sitioWeb, dtPropuestas);

        return dtProp;
    }


}