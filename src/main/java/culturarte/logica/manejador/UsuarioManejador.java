package culturarte.logica.manejador;

import culturarte.persistencia.JPAUtil;
import culturarte.logica.modelo.Proponente;
import culturarte.logica.modelo.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class UsuarioManejador{
    private static UsuarioManejador instancia = null;
    private EntityManager em;


    public static UsuarioManejador getinstance() {
        if (instancia == null)
            instancia = new UsuarioManejador();
        return instancia;
    }

    public void addUsuario(Usuario usu) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(usu);
            t.commit();
        }
        catch(Exception e){
            t.rollback();
        }
        em.close();
    }

    /*
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
    }*/

    public List<Proponente> obtenerTodosLosProponentes() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Proponente> query = em.createQuery("SELECT p FROM Proponente p", Proponente.class);
        List<Proponente> proponentes = query.getResultList();
        em.close();
        return proponentes;
    }

    public Usuario obtenerUsuario(String nickname){
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usu = null;
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class).setParameter("nick", nickname);
            usu = query.getSingleResult();
        } catch (NoResultException e) {
            usu = null;
        } finally {
            em.close();
        }
        return usu;
}
}
