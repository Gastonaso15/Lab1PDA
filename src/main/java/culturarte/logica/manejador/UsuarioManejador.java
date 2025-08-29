package culturarte.logica.manejador;

import culturarte.logica.modelo.Proponente;
import culturarte.logica.modelo.Propuesta;
import culturarte.logica.modelo.Seguimiento;
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

    public List<String> obtenerNicknameColaboradores() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT nickname FROM Colaborador c", String.class);
        List<String> colaboradores = query.getResultList();
        em.close();
        return colaboradores;
    }

    public culturarte.logica.modelo.Colaborador obtenerColaboradorNick(String nickname) {
        EntityManager em = JPAUtil.getEntityManager();
        culturarte.logica.modelo.Colaborador colab = null;
        try {
            TypedQuery<culturarte.logica.modelo.Colaborador> query = em.createQuery(
                    "SELECT DISTINCT c FROM Colaborador c " +
                            "LEFT JOIN FETCH c.colaboraciones col " +
                            "LEFT JOIN FETCH col.propuesta pr " +
                            "LEFT JOIN FETCH pr.proponente " +
                            "WHERE c.nickname = :nick", culturarte.logica.modelo.Colaborador.class
            );
            query.setParameter("nick", nickname);
            colab = query.getSingleResult();

            // Forzar la carga de las colaboraciones y sus propuestas
            for (culturarte.logica.modelo.Colaboracion col : colab.getColaboraciones()) {
                col.getPropuesta().getTitulo(); // Forzar carga
                col.getPropuesta().getProponente().getNickname(); // Forzar carga del proponente
            }

        } catch (NoResultException e) {
            colab = null;
        } finally {
            em.close();
        }
        return colab;
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

    public List<String> devolverNicksUsuarios() {
        EntityManager em = JPAUtil.getEntityManager();
        List<String> nicknames;
        try {
            TypedQuery<String> query = em.createQuery("SELECT u.nickname FROM Usuario u", String.class);
            nicknames = query.getResultList();
        } finally {
            em.close();
        }
        return nicknames;
    }

    public void agregarSeguimiento(String nicknameSeguidor, String nicknameSeguido) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Usuario seguidor = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nicknameSeguidor)
                    .getSingleResult();

            Usuario seguido = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nicknameSeguido)
                    .getSingleResult();

            Seguimiento s = new Seguimiento();
            s.setSeguidor(seguidor);
            s.setSeguido(seguido);

            seguidor.getSeguidos().add(s);
            seguido.getSeguidores().add(s);

            em.persist(s);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean yaSigue(String nicknameSeguidor, String nicknameSeguido) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(s) FROM Seguimiento s " +
                            "WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido", Long.class);
            query.setParameter("seguidor", nicknameSeguidor);
            query.setParameter("seguido", nicknameSeguido);

            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public void eliminarSeguimiento(String nicknameSeguidor, String nicknameSeguido) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Usuario seguidor = em.createQuery(
                            "SELECT u FROM Usuario u LEFT JOIN FETCH u.seguidos s LEFT JOIN FETCH s.seguido WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nicknameSeguidor)
                    .getSingleResult();

            Usuario seguido = em.createQuery(
                            "SELECT u FROM Usuario u LEFT JOIN FETCH u.seguidores s LEFT JOIN FETCH s.seguidor WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nicknameSeguido)
                    .getSingleResult();

            Seguimiento relacion = null;
            for (Seguimiento s : seguidor.getSeguidos()) {
                if (s.getSeguido().getId().equals(seguido.getId())) {
                    relacion = s;
                    break;
                }
            }

            if (relacion != null) {
                seguidor.getSeguidos().remove(relacion);
                seguido.getSeguidores().remove(relacion);
                em.remove(em.contains(relacion) ? relacion : em.merge(relacion));
            } else {
                throw new IllegalStateException("El usuario no sigue a este usuario.");
            }

            em.merge(seguidor);
            em.merge(seguido);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
