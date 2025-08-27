package culturarte.logica.manejador;

import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.modelo.EstadoPropuesta;
import culturarte.logica.modelo.Propuesta;
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
        em.close();
        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        for (Propuesta p : propuestas) {
            DTPropuesta dt = new DTPropuesta();
            dt.setTitulo(p.getTitulo());
            dt.setDescripcion(p.getDescripcion());
            dt.setLugar(p.getLugar());
            dt.setFechaPrevista(p.getFechaPrevista());
            dt.setPrecioEntrada(p.getPrecioEntrada());
            dt.setMontoNecesario(p.getMontoNecesario());
            dt.setImagen(p.getImagen());

            DTProponente dtProp = new DTProponente();
            dtProp.setNombre(p.getProponente().getNombre());
            dtProp.setApellido(p.getProponente().getApellido());
            dtProp.setNickname(p.getProponente().getNickname());
            dt.setDTProponente(dtProp);

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
