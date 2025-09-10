package culturarte.logica.controlador;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.DT.DTColaboracion;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.manejador.PropuestaManejador;
import culturarte.logica.manejador.UsuarioManejador;
import culturarte.logica.modelo.*;
import culturarte.persistencia.JPAUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PropuestaController implements IPropuestaController {

    public PropuestaController() {
        JPAUtil.getEntityManager();
    }

    @Override
    public  void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, String imagen, String proponente, String categoria, List<String> listaTipos) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);
        if (p != null)
            throw new Exception("La propuesta con el titulo " + titulo + " ya esta registrada");
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Proponente prop = (Proponente) mu.obtenerUsuarioNick(proponente);

        Categoria Cat = mp.obtenerPorNombre(categoria);
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

        p = new Propuesta(titulo,descripcion,lugar,fechaPrevista,precioEntrada,montoNecesario,imagen,prop,Cat,tipos);
        mp.addPropuesta(p);
    }

    @Override
    public List<DTPropuesta> devolverTodasLasPropuestas(){
        PropuestaManejador mp = PropuestaManejador.getinstance();
        return mp.obtenerTodasLasPropuestas();
    }

    @Override
    public List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado) {
        EstadoPropuesta estadoModelo = EstadoPropuesta.valueOf(estado.toString());
        PropuestaManejador mp = PropuestaManejador.getinstance();
        return mp.obtenerPropuestasPorEstado(estadoModelo);
    }
    @Override
    public void modificarPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                                   Double precioEntrada, Double montoNecesario,
                                   String imagen, List<String> listaTipos, String categoria) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);

        if (p == null) {
            throw new Exception("⚠️ La propuesta con el título " + titulo + " no existe.");
        }

        Categoria Cat = mp.obtenerPorNombre(categoria);
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
        PropuestaManejador mc = PropuestaManejador.getinstance();
        Categoria catPadre = mc.obtenerPorNombre(padre);
        if (mc.obtenerPorNombre(nombre) != null) {
            throw new Exception("La categoría ya existe.");
        }
        if (catPadre == null) {
            catPadre = mc.obtenerPorNombre("Categoría");
        }
        Categoria nueva = new Categoria(nombre,catPadre);
        mc.addCategoria(nueva);
    }

    @Override
    public List<DTCategoria> listarDTCategorias(){
        PropuestaManejador mc = PropuestaManejador.getinstance();
        return mc.listarDTCategorias();
    }

    @Override
    public void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto, String tipoRetorno) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getinstance();
        UsuarioManejador um = UsuarioManejador.getinstance();

        Propuesta propuesta = pm.obtenerPropuesta(tituloPropuesta);
        if (propuesta == null) {
            throw new Exception("La propuesta con título " + tituloPropuesta + " no existe.");
        }

        Usuario usu = um.obtenerUsuarioNick(nicknameColaborador);
        if (!(usu instanceof Colaborador colaborador)) {
            throw new Exception("El usuario " + nicknameColaborador + " no es un colaborador válido.");
        }

        TipoRetorno retorno;
        try {
            retorno = TipoRetorno.valueOf(tipoRetorno);
        } catch (IllegalArgumentException e) {
            throw new Exception("El tipo de retorno ingresado no es válido.");
        }

        Colaboracion colaboracion = new Colaboracion(
                propuesta,
                colaborador,
                monto,
                retorno,
                java.time.LocalDateTime.now()
        );

        pm.agregarColaboracion(colaboracion);
    }

    public List<DTColaboracion> obtenerTodasLasColaboraciones() {
        PropuestaManejador pm = PropuestaManejador.getinstance();
        return pm.getColaboraciones();
    }

    public void cancelarColaboracion(Long idColaboracion) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getinstance();
        pm.cancelarColaboracion(idColaboracion);
    }
}
