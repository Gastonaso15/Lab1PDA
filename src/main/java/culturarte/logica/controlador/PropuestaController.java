package culturarte.logica.controlador;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTTipoRetorno;
import culturarte.logica.manejador.CategoriaManejador;
import culturarte.logica.manejador.PropuestaManejador;
import culturarte.logica.manejador.UsuarioManejador;
import culturarte.logica.modelo.Categoria;
import culturarte.logica.modelo.EstadoPropuesta;
import culturarte.logica.modelo.Proponente;
import culturarte.logica.modelo.Propuesta;
import culturarte.logica.modelo.TipoRetorno;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PropuestaController implements IPropuestaController {

    public PropuestaController() {
        EntityManager em = JPAUtil.getEntityManager();
    }

    @Override
    public  void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, byte[] imagen, String proponente, String categoria, List<String> listaTipos) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);
        if (p != null)
            throw new Exception("La propuesta con el titulo " + titulo + " ya esta registrada");
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Proponente prop = (Proponente) mu.obtenerUsuarioNick(proponente);

        CategoriaManejador cm = CategoriaManejador.getinstance();
        Categoria Cat = cm.obtenerPorNombre(categoria);
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
        List<DTPropuesta> props = mp.obtenerTodasLasPropuestas();
        return props;
    }

    @Override
    public List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado) {
        EstadoPropuesta estadoModelo = EstadoPropuesta.valueOf(estado.toString());
        PropuestaManejador mp = PropuestaManejador.getinstance();
        return mp.obtenerPropuestasPorEstado(estadoModelo);
    }
    @Override
    public void modificarPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                                   Double precioEntrada, Double montoNecesario, LocalDate fechaPublicacion) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);

        if (p == null) {
            throw new Exception("⚠️ La propuesta con el título " + titulo + " no existe.");
        }

        // actualizar campos (excepto titulo)
        p.setDescripcion(descripcion);
        p.setLugar(lugar);
        p.setFechaPrevista(fechaPrevista);
        p.setPrecioEntrada(precioEntrada);
        p.setMontoNecesario(montoNecesario);
        p.setFechaPublicacion(fechaPublicacion);

        // persistir cambios
        mp.actualizarPropuesta(p);
    }



}
