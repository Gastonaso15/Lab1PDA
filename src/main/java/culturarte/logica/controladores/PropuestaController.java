package culturarte.logica.controladores;

import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;
import culturarte.servicios.DTs.*;
import culturarte.servicios.interfaces.IPropuestaController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PropuestaController implements IPropuestaController {

    public PropuestaController() {
    }

    @Override
    public void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, String imagen, String proponente, String categoria, List<String> listaTipos) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getInstance();
        Propuesta p = mp.obtenerPropuestaPorTitulo(titulo);
        if (p != null)
            throw new Exception("La propuesta con el titulo " + titulo + " ya esta registrada");
        UsuarioManejador mu = UsuarioManejador.getInstance();
        Proponente prop = (Proponente) mu.obtenerUsuarioPorNickname(proponente);

        Categoria Cat = mp.obtenerCategoriaPorNombre(categoria);
        if (Cat == null) {
            throw new Exception("La categoría " + categoria + " no existe");
        }

        List<TipoRetorno> tipos = new ArrayList<>();
        for (String t : listaTipos) {
            try {
                tipos.add(TipoRetorno.valueOf(t));
            } catch (IllegalArgumentException e) {
                throw new Exception("Tipo de retorno inválido: " + t);
            }
        }

        p = new Propuesta(titulo, descripcion, lugar, fechaPrevista, precioEntrada, montoNecesario, imagen, prop, Cat, tipos);
        mp.persistirPropuesta(p);
    }

    @Override
    public List<DTPropuesta> devolverTodasLasPropuestas() {
        PropuestaManejador mp = PropuestaManejador.getInstance();
        return mp.obtenerTodasLasPropuestas();
    }

    @Override
    public List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado) {
        EstadoPropuesta estadoModelo = EstadoPropuesta.valueOf(estado.toString());
        PropuestaManejador mp = PropuestaManejador.getInstance();
        return mp.obtenerPropuestasPorEstado(estadoModelo);
    }

    @Override
    public void modificarPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                                   Double precioEntrada, Double montoNecesario,
                                   String imagen, List<String> listaTipos, String categoria) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getInstance();
        Propuesta p = mp.obtenerPropuestaPorTitulo(titulo);

        if (p == null) {
            throw new Exception("La propuesta con el título " + titulo + " no existe.");
        }

        Categoria Cat = mp.obtenerCategoriaPorNombre(categoria);
        if (Cat == null) {
            throw new Exception("La categoría " + categoria + " no existe");
        }

        List<TipoRetorno> tipos = new ArrayList<>();
        for (String t : listaTipos) {
            try {
                tipos.add(TipoRetorno.valueOf(t));
            } catch (IllegalArgumentException e) {
                throw new Exception("Tipo de retorno inválido: " + t);
            }
        }

        p.setDescripcion(descripcion);
        p.setLugar(lugar);
        p.setFechaPrevista(fechaPrevista);
        p.setPrecioEntrada(precioEntrada);
        p.setMontoNecesario(montoNecesario);
        p.setCategoria(Cat);
        p.setImagen(imagen);
        p.setTiposRetorno(tipos);

        mp.actualizarPropuesta(p);
    }

    @Override
    public void crearCategoria(String nombre, String padre) throws Exception {
        PropuestaManejador mc = PropuestaManejador.getInstance();
        Categoria catPadre = mc.obtenerCategoriaPorNombre(padre);
        if (mc.obtenerCategoriaPorNombre(nombre) != null) {
            throw new Exception("La categoría ya existe.");
        }
        if (catPadre == null) {
            catPadre = mc.obtenerCategoriaPorNombre("Categoría");
        }
        Categoria nueva = new Categoria(nombre, catPadre);
        mc.persistirCategoria(nueva);
    }

    @Override
    public List<DTCategoria> devolverTodasLasCategorias() {
        PropuestaManejador mc = PropuestaManejador.getInstance();
        return mc.obtenerTodasLasCategorias();
    }

    @Override
    public void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto, String tipoRetorno) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        UsuarioManejador um = UsuarioManejador.getInstance();

        Propuesta propuesta = pm.obtenerPropuestaPorTitulo(tituloPropuesta);
        if (propuesta == null) {
            throw new Exception("La propuesta con título " + tituloPropuesta + " no existe.");
        }

        Usuario usu = um.obtenerUsuarioPorNickname(nicknameColaborador);
        if (!(usu instanceof Colaborador colaborador)) {
            throw new Exception("El usuario " + nicknameColaborador + " no es un colaborador válido.");
        }

        TipoRetorno retorno;
        try {
            retorno = TipoRetorno.valueOf(tipoRetorno);
        } catch (IllegalArgumentException e) {
            throw new Exception("El tipo de retorno ingresado no es válido.");
        }

        Colaboracion colaboracion = new Colaboracion(propuesta, colaborador, monto, retorno, java.time.LocalDateTime.now());

        pm.persistirColaboracion(colaboracion);
    }

    @Override
    public List<DTColaboracion> obtenerTodasLasColaboraciones() {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        return pm.obtenerTodasLasColaboraciones();
    }

    @Override
    public void cancelarColaboracion(Long idColaboracion) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        pm.cancelarColaboracion(idColaboracion);
    }

    @Override
    public void publicarPropuesta(String titulo) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta propuesta = pm.obtenerPropuestaPorTitulo(titulo);

        if (propuesta == null) {
            throw new Exception("La propuesta con titulo " + titulo + " no existe.");
        }
        propuesta.setEstadoActual(EstadoPropuesta.PUBLICADA);
        propuesta.getHistorial().add(new PropuestaEstado(propuesta, EstadoPropuesta.PUBLICADA, LocalDate.now()));

        pm.actualizarPropuesta(propuesta);
    }

    public void cancelarPropuesta(String titulo) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta propuesta = pm.obtenerPropuestaPorTitulo(titulo);

        if (propuesta == null) {
            throw new Exception("La propuesta con titulo " + titulo + " no existe.");
        }

        propuesta.setEstadoActual(EstadoPropuesta.CANCELADA);
        propuesta.getHistorial().add(new PropuestaEstado(propuesta, EstadoPropuesta.CANCELADA, LocalDate.now()));

        pm.actualizarPropuesta(propuesta);
    }

    public DTPropuesta getDTPropuesta(String titulo) {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta p = pm.obtenerPropuestaPorTitulo(titulo);
        return (p != null) ? p.getDataType() : null;
    }

    @Override
    public List<DTPropuesta> getPropuestasIngresadas() {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        List<DTPropuesta> propuestas = pm.obtenerPropuestasPorEstado(EstadoPropuesta.INGRESADA);
        return propuestas;
    }

    public void evaluarPropuesta(String titulo, boolean publicar) throws Exception{

        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta p = pm.obtenerPropuestaPorTitulo(titulo);

        if (p == null) {
            throw new Exception("La propuesta no existe");
        }

        EstadoPropuesta nuevoEstado = publicar ? EstadoPropuesta.PUBLICADA : EstadoPropuesta.CANCELADA;

        p.setEstadoActual(nuevoEstado);

        PropuestaEstado nuevo = new PropuestaEstado(p, nuevoEstado, LocalDate.now());
        p.agregarPropuestaEstado(nuevo);
        pm.actualizarPropuesta(p);
    }

    @Override
    public void agregarComentario(String tituloPropuesta, String nicknameUsuario, String contenido) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        UsuarioManejador um = UsuarioManejador.getInstance();

        Propuesta propuesta = pm.obtenerPropuestaPorTitulo(tituloPropuesta);
        if (propuesta == null) {
            throw new Exception("La propuesta con título " + tituloPropuesta + " no existe.");
        }

        Usuario usuario = um.obtenerUsuarioPorNickname(nicknameUsuario);
        if (usuario == null) {
            throw new Exception("El usuario " + nicknameUsuario + " no existe.");
        }

        if (contenido == null || contenido.trim().isEmpty()) {
            throw new Exception("El contenido del comentario no puede estar vacío.");
        }

        Comentario comentario = new Comentario(contenido.trim(), propuesta, usuario);
        propuesta.agregarComentario(comentario);
        pm.persistirComentario(comentario);
        pm.actualizarPropuesta(propuesta);
    }

    @Override
    public List<DTComentario> obtenerComentariosPropuesta(String tituloPropuesta) {
        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta propuesta = pm.obtenerPropuestaPorTitulo(tituloPropuesta);

        if (propuesta == null || propuesta.getComentarios() == null) {
            return new ArrayList<>();
        }

        List<DTComentario> comentariosDT = new ArrayList<>();
        for (Comentario comentario : propuesta.getComentarios()) {
            comentariosDT.add(comentario.getDataType());
        }

        return comentariosDT;
    }

    @Override
    public void extenderFinanciacion(DTUsuario usuario, String tituloPropuesta){
        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta propuesta = pm.obtenerPropuestaPorTitulo(tituloPropuesta);
        propuesta.setFechaPublicacion(propuesta.getFechaPublicacion().plusMonths(1));//Le agrego 1 mes a la fecha actual
        pm.actualizarPropuesta(propuesta); //debo persistirla para que se efectúe el cambio en la base de datos
    }

    @Override
    public DTPropuesta obtenerPropuestaPorTitulo(String titulo){
        PropuestaManejador pm = PropuestaManejador.getInstance();
        Propuesta propAux = pm.obtenerPropuestaPorTitulo(titulo);
        DTPropuesta prop = propAux.getDataType();
        return prop;
    }
}