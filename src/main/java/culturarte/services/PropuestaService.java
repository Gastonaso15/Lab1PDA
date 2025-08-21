package culturarte.services;

import culturarte.modelo.Propuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;


public class PropuestaService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("culturartePU");

    public void crearPropuesta(Propuesta propuesta) throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Validar unicidad de título
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
        EntityManager em = emf.createEntityManager();
        List<Propuesta> propuestas = em.createQuery("SELECT p FROM Propuesta p", Propuesta.class)
                .getResultList();
        em.close();
        return propuestas;
    }
}
