package culturarte.logica.manejador;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.modelo.Categoria;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

public class CategoriaManejador {
    private static CategoriaManejador instancia = null;
    private EntityManager em;

    public static CategoriaManejador getinstance() {
        if (instancia == null)
            instancia = new CategoriaManejador();
        return instancia;
    }

    public void addCategoria(Categoria cat) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(cat);
            t.commit();
        }
        catch(Exception e){
            t.rollback();
            e.printStackTrace();
        }
        em.close();
    }

    public List<String> listarCategorias() {
        EntityManager em = JPAUtil.getEntityManager();
        List<String> lista = em.createQuery("SELECT nombre FROM Categoria c", String.class).getResultList();
        em.close();
        return lista;
    }

    public Categoria obtenerPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        Categoria cat = null;
        try {
            cat = em.createQuery("SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            cat = null;
        } finally {
            em.close();
        }
        return cat;
    }

    public List<DTCategoria> listarDTCategorias() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Categoria> lista = em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
        em.close();
        List<DTCategoria> listaDT = new ArrayList<>();
        for (Categoria c : lista) {
            DTCategoria padre = null;
            if (c.getCategoriaPadre() != null) {
                padre = new DTCategoria(c.getCategoriaPadre().getNombre(), null);
            }
            listaDT.add(new DTCategoria(
                    c.getNombre(),
                    padre
            ));
        }
        return listaDT;
    }

}
