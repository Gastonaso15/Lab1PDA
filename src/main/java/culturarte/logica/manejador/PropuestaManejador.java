package culturarte.logica.manejador;

import culturarte.logica.DT.*;
import culturarte.logica.modelo.*;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


public class PropuestaManejador {
    private static PropuestaManejador instancia = null;
    private EntityManager em;

    public static PropuestaManejador getinstance() {
        if (instancia == null)
            instancia = new PropuestaManejador();
        return instancia;
    }

    public void addPropuesta(Propuesta pro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(pro);
            t.commit();
        }
        catch(Exception e){
            t.rollback();
        }
        em.close();
    }

    public List<DTPropuesta> obtenerTodasLasPropuestas() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Propuesta> propuestas = em.createQuery("SELECT p FROM Propuesta p", Propuesta.class)
                .getResultList();
        for (Propuesta p : propuestas) {
            p.getHistorial().size();
            p.getColaboraciones().size();
            p.getTiposRetorno().size();
        }
        em.close();
        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        for (Propuesta p : propuestas) {

            DTCategoria dtCategoria = null;
            if (p.getCategoria() != null) {
                dtCategoria = new DTCategoria(p.getCategoria().getNombre());
            }

            DTEstadoPropuesta dtEstadoPropuesta = DTEstadoPropuesta.valueOf(p.getEstadoActual().name());

            List<DTPropuestaEstado> historial = new ArrayList<>();
            if (p.getHistorial() != null) {
                for (PropuestaEstado pe : p.getHistorial()) {
                    historial.add(new DTPropuestaEstado(
                            DTEstadoPropuesta.valueOf(pe.getEstado().name()),
                            pe.getFechaCambio()
                    ));
                }
            }

            List<DTColaboracion> colaboraciones = new ArrayList<>();
            if (p.getColaboraciones() != null) {
                for (Colaboracion c : p.getColaboraciones()) {
                    DTColaborador dtColab = new DTColaborador(
                            c.getColaborador().getNickname()
                    );

                    colaboraciones.add(new DTColaboracion(
                            dtColab,
                            c.getMonto()
                    ));
                }
            }

            List<DTTipoRetorno> tiposRetorno = new ArrayList<>();
            if (p.getTiposRetorno() != null) {
                for (TipoRetorno t : p.getTiposRetorno()) {
                    tiposRetorno.add(DTTipoRetorno.valueOf(t.name()));
                }
            }

            DTProponente dtProp = new DTProponente();
            dtProp.setNombre(p.getProponente().getNombre());
            dtProp.setApellido(p.getProponente().getApellido());
            dtProp.setNickname(p.getProponente().getNickname());

            DTPropuesta dt = new DTPropuesta();
            dt.setTitulo(p.getTitulo());
            dt.setDescripcion(p.getDescripcion());
            dt.setLugar(p.getLugar());
            dt.setFechaPrevista(p.getFechaPrevista());
            dt.setPrecioEntrada(p.getPrecioEntrada());
            dt.setMontoNecesario(p.getMontoNecesario());
            dt.setFechaPublicacion(p.getFechaPublicacion());
            dt.setImagen(p.getImagen());
            dt.setCategoria(dtCategoria);
            dt.setDTProponente(dtProp);
            dt.setEstadoActual(dtEstadoPropuesta);
            dt.setHistorial(historial);
            dt.setColaboraciones(colaboraciones);
            dt.setTiposRetorno(tiposRetorno);

            dtPropuestas.add(dt);
        }
        return dtPropuestas;
    }

    public Propuesta obtenerPropuesta(String titulo) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        Propuesta pro = null;
        try {
            TypedQuery<Propuesta> query = em.createQuery("SELECT p FROM Propuesta p WHERE p.titulo = :titulo", Propuesta.class).setParameter("titulo", titulo);
            pro = query.getSingleResult();
        } catch (NoResultException e) {
            pro = null;
        } finally {
            em.close();
        }
        return pro;
    }
    public List<DTPropuesta> obtenerPropuestasPorEstado(EstadoPropuesta estado) {
        EntityManager em = JPAUtil.getEntityManager();
        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        try {
            TypedQuery<Propuesta> query = em.createQuery("SELECT p FROM Propuesta p WHERE p.estadoActual = :estado", Propuesta.class);
            query.setParameter("estado", estado);
            List<Propuesta> propuestas = query.getResultList();

            for (Propuesta p : propuestas) {
                DTPropuesta dt = new DTPropuesta();
                dt.setTitulo(p.getTitulo());
                dt.setDescripcion(p.getDescripcion());
                dt.setLugar(p.getLugar());
                dt.setFechaPrevista(p.getFechaPrevista());
                dt.setPrecioEntrada(p.getPrecioEntrada());
                dt.setMontoNecesario(p.getMontoNecesario());
                dt.setImagen(p.getImagen());
                dt.setEstadoActual(DTEstadoPropuesta.valueOf(p.getEstadoActual().name()));

                DTProponente dtProp = new DTProponente();
                dtProp.setNombre(p.getProponente().getNombre());
                dtProp.setApellido(p.getProponente().getApellido());
                dtProp.setNickname(p.getProponente().getNickname());
                dt.setDTProponente(dtProp);

                dtPropuestas.add(dt);
            }
        } catch (Exception e) {
            // Log error if needed
            e.printStackTrace();
        } finally {
            em.close();
        }
        return dtPropuestas;
    }
    public void actualizarPropuesta(Propuesta pro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.merge(pro);  // sincroniza con la BD
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            e.printStackTrace(); // o lanzar excepción hacia arriba
        } finally {
            em.close();
        }
    }

}
