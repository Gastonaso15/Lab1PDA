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

    @Override
    public void seguirUsuario(String nickSeguidor, String nickSeguido) {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Usuario seguidor = mu.obtenerUsuarioNick(nickSeguidor);
        Usuario seguido = mu.obtenerUsuarioNick(nickSeguido);

        if (seguidor == null || seguido == null) {
            throw new IllegalArgumentException("Los usuarios no pueden ser nulos.");
        }
        if (seguidor.getNickname().equals(seguido.getNickname())) {
            throw new IllegalArgumentException("Un usuario no puede seguirse a sí mismo.");
        }
        if (mu.yaSigue(nickSeguidor, nickSeguido)) {
            throw new IllegalStateException("El usuario ya sigue a este usuario.");
        }

        mu.agregarSeguimiento(nickSeguidor, nickSeguido);

    }

    @Override
    public void dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Usuario seguidor = mu.obtenerUsuarioNick(nickSeguidor);
        Usuario seguido = mu.obtenerUsuarioNick(nickSeguido);

        if (seguidor == null || seguido == null) {
            throw new IllegalArgumentException("Los usuarios no pueden ser nulos.");
        }

        if (seguidor.getId().equals(seguido.getId())) {
            throw new IllegalArgumentException("Un usuario no puede dejar de seguirse a sí mismo.");
        }

        mu.eliminarSeguimiento(nickSeguidor, nickSeguido);
    }

    @Override
    public List<String> devolverNicknamesUsuarios() {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        List<String> nicknames = mu.devolverNicksUsuarios();
        return nicknames;
    }
    @Override
    public List<String> devolverNicknamesColaboradores() {
        UsuarioManejador mu = UsuarioManejador.getinstance();
        List<String> colaboradores = mu.obtenerNicknameColaboradores();
        return colaboradores;
    }

    @Override
    public DTColaborador obtenerColaboradorCompleto(String nickname) throws Exception {
        UsuarioManejador um = UsuarioManejador.getinstance();
        Colaborador colab = um.obtenerColaboradorNick(nickname);

        if (colab == null) {
            throw new Exception("El colaborador " + nickname + " no existe");
        }

        String nombre = colab.getNombre();
        String apellido = colab.getApellido();
        String correo = colab.getCorreo();
        LocalDate fechaNacimiento = colab.getFechaNacimiento();
        byte[] imagen = colab.getImagen();

        List<DTColaboracion> dtColaboraciones = new ArrayList<>();
        for (Colaboracion c : colab.getColaboraciones()) {

            // Crear DTPropuesta con información básica
            Propuesta prop = c.getPropuesta();
            DTCategoria dtCategoria = null;
            if (prop.getCategoria() != null) {
                DTCategoria dtCategoriaPadre = null;
                if (prop.getCategoria().getCategoriaPadre() != null) {
                    dtCategoriaPadre = new DTCategoria(
                            prop.getCategoria().getCategoriaPadre().getNombre(),
                            new ArrayList<>(),
                            null
                    );
                }
                dtCategoria = new DTCategoria(
                        prop.getCategoria().getNombre(),
                        new ArrayList<>(),
                        dtCategoriaPadre
                );
            }

            List<DTTipoRetorno> dtTiposRetorno = new ArrayList<>();
            if (prop.getTiposRetorno() != null) {
                for (TipoRetorno tr : prop.getTiposRetorno()) {
                    dtTiposRetorno.add(DTTipoRetorno.valueOf(tr.name()));
                }
            }

            DTEstadoPropuesta dtEstadoActual = null;
            if (prop.getEstadoActual() != null) {
                dtEstadoActual = DTEstadoPropuesta.valueOf(prop.getEstadoActual().name());
            }

            // Crear DTProponente básico
            DTProponente dtProponente = new DTProponente(
                    prop.getProponente().getNickname(),
                    prop.getProponente().getNombre(),
                    prop.getProponente().getApellido(),
                    prop.getProponente().getCorreo(),
                    prop.getProponente().getFechaNacimiento(),
                    prop.getProponente().getImagen(),
                    prop.getProponente().getDireccion(),
                    prop.getProponente().getBio(),
                    prop.getProponente().getSitioWeb(),
                    new ArrayList<>() // Lista vacía de propuestas para evitar recursión
            );

            DTPropuesta dtPropuesta = new DTPropuesta(
                    prop.getTitulo(),
                    prop.getDescripcion(),
                    prop.getLugar(),
                    prop.getFechaPrevista(),
                    prop.getPrecioEntrada(),
                    prop.getMontoNecesario(),
                    prop.getImagen(),
                    dtCategoria,
                    dtTiposRetorno,
                    dtEstadoActual,
                    new ArrayList<>() // Lista vacía para evitar recursión
            );
            dtPropuesta.setDTProponente(dtProponente);

            DTTipoRetorno dtTipoRetorno = null;
            if (c.getTipoRetorno() != null) {
                dtTipoRetorno = DTTipoRetorno.valueOf(c.getTipoRetorno().name());
            }

            DTColaboracion dtColaboracion = new DTColaboracion(
                    dtPropuesta,
                    new DTColaborador(nickname), // Solo nickname para evitar recursión
                    c.getMonto(),
                    dtTipoRetorno,
                    c.getFechaHora()
            );
            dtColaboraciones.add(dtColaboracion);
        }

        DTColaborador dtColab = new DTColaborador(nickname, nombre, apellido, correo, fechaNacimiento, imagen, dtColaboraciones);
        return dtColab;
    }

}
