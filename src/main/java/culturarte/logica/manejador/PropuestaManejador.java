package culturarte.logica.manejador;

import culturarte.logica.DT.DTProponente;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.modelo.Propuesta;
import culturarte.logica.modelo.Usuario;
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

    /*public void crearPropuesta(Propuesta propuesta) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Long count = em.createQuery("SELECT COUNT(p) FROM Propuesta p WHERE p.titulo = :titulo", Long.class)
                .setParameter("titulo", propuesta.getTitulo())
                .getSingleResult();
        if (count > 0) {
            em.getTransaction().rollback();
            em.close();
            throw new Exception("Ya existe una propuesta con ese título");
        }
        em.persist(propuesta);
        em.getTransaction().commit();
        em.close();
    }*/


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
            dtPropuestas.add(dt);

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
}
