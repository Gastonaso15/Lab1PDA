package culturarte.logica.manejador;

import culturarte.logica.modelo.Propuesta;
import jakarta.persistence.EntityManager;
import java.util.List;

import culturarte.persistencia.JPAUtil;


public class PropuestaService {

    public void crearPropuesta(Propuesta propuesta) throws Exception {
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
    }

    public List<Propuesta> obtenerTodasLasPropuestas() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Propuesta> propuestas = em.createQuery("SELECT p FROM Propuesta p", Propuesta.class)
                .getResultList();
        em.close();
        return propuestas;
    }
}
