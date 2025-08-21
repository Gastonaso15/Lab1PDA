package culturarte.services;

import culturarte.config.JPAUtil;
import culturarte.modelo.Colaborador;
import culturarte.modelo.Proponente;
import culturarte.modelo.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsuarioService {
    public void crearUsuario(String nickname, String nombre, String apellido, String correo, String tipo) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM Usuario u WHERE u.nickname = :nick OR u.correo = :correo", Long.class)
                .setParameter("nick", nickname)
                .setParameter("correo", correo)
                .getSingleResult();
        if (count > 0) {
            em.getTransaction().rollback();
            em.close();
            throw new Exception("Ya existe un usuario con ese nickname o correo");
        }
        Usuario usuario;
        if ("Proponente".equals(tipo)) {
            usuario = new Proponente();
        } else {
            usuario = new Colaborador();
        }
        usuario.setNickname(nickname);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public void crearUsuario(Usuario usuario) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM Usuario u WHERE u.nickname = :nick OR u.correo = :correo", Long.class)
                .setParameter("nick", usuario.getNickname())
                .setParameter("correo", usuario.getCorreo())
                .getSingleResult();
        if (count > 0) {
            em.getTransaction().rollback();
            em.close();
            throw new Exception("Ya existe un usuario con ese nickname o correo");
        }
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public List<Proponente> obtenerTodosLosProponentes() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Proponente> query = em.createQuery("SELECT p FROM Proponente p", Proponente.class);
        List<Proponente> proponentes = query.getResultList();
        em.close();
        return proponentes;
    }
}
