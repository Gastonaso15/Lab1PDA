package culturarte.logica.manejador;

import culturarte.logica.modelo.Proponente;
import culturarte.logica.modelo.Propuesta;
import culturarte.persistencia.JPAUtil;
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
            e.printStackTrace();
        }
        em.close();
    }

    public List<String> obtenerNicknameProponentes() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT nickname FROM Proponente p", String.class);
        List<String> proponentes = query.getResultList();
        em.close();
        return proponentes;
    }

    public Usuario obtenerUsuarioNick(String nickname){
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

    public Usuario obtenerUsuarioCorreo(String correo){
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usu = null;
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class).setParameter("correo", correo);
            usu = query.getSingleResult();
        } catch (NoResultException e) {
            usu = null;
        } finally {
            em.close();
        }
        return usu;
    }

    public Proponente obtenerProponenteNick(String nickname) {
        EntityManager em = JPAUtil.getEntityManager();
        Proponente prop = null;
        try {
            TypedQuery<Proponente> query = em.createQuery(
                    "SELECT DISTINCT p FROM Proponente p " +
                            "LEFT JOIN FETCH p.propuestas pr " +
                            "WHERE p.nickname = :nick", Proponente.class
            );
            query.setParameter("nick", nickname);
            prop = query.getSingleResult();
            for (Propuesta p : prop.getPropuestas()) {
                p.getTiposRetorno().size();
                p.getColaboraciones().size();
            }

        } catch (NoResultException e) {
            prop = null;
        } finally {
            em.close();
        }
        return prop;
    }
}
