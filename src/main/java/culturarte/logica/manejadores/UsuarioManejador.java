package culturarte.logica.manejadores;

import culturarte.logica.modelos.*;
import culturarte.persistencia.JPAUtil;

import jakarta.persistence.*;

import java.util.List;

public class UsuarioManejador{
    private static UsuarioManejador instancia = null;

    public static UsuarioManejador getInstance() {
        if (instancia == null)
            instancia = new UsuarioManejador();
        return instancia;
    }

    public void persistirUsuario(Usuario usu) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(usu);
            t.commit();
        }
        catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al persistir usuario", e);
        } finally {
            em.close();
        }
    }

    public List<String> obtenerNicknamesUsuarios() {
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

    public List<String> obtenerNicknamesProponentes() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT nickname FROM Proponente p", String.class);
        List<String> proponentes = query.getResultList();
        em.close();
        return proponentes;
    }

    public List<String> obtenerNicknamesColaboradores() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT nickname FROM Colaborador c", String.class);
        List<String> colaboradores = query.getResultList();
        em.close();
        return colaboradores;
    }

    public Usuario obtenerUsuarioPorCorreo(String correo){
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usu;
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

    public Usuario obtenerUsuarioPorNickname(String nickname){
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usu;
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

    public Proponente obtenerProponentePorNickname(String nickname) {
        EntityManager em = JPAUtil.getEntityManager();
        Proponente prop;
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

    public Colaborador obtenerColaboradorPorNickname(String nickname) {
        EntityManager em = JPAUtil.getEntityManager();
        Colaborador colab;
        try {
            TypedQuery<Colaborador> query = em.createQuery(
                    "SELECT DISTINCT c FROM Colaborador c " +
                            "LEFT JOIN FETCH c.colaboraciones col " +
                            "LEFT JOIN FETCH col.propuesta pr " +
                            "LEFT JOIN FETCH pr.proponente " +
                            "WHERE c.nickname = :nick", Colaborador.class
            );
            query.setParameter("nick", nickname);
            colab = query.getSingleResult();

            for (Colaboracion col : colab.getColaboraciones()) {
                col.getPropuesta().getTitulo();
                col.getPropuesta().getProponente().getNickname();
                col.getPropuesta().getTiposRetorno().size();
                col.getPropuesta().getColaboraciones().size();
            }

        } catch (NoResultException e) {
            colab = null;
        } finally {
            em.close();
        }
        return colab;
    }

    public void persistirSeguimiento(String nicknameSeguidor, String nicknameSeguido) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();

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

            t.commit();
        }  catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al persistir seguimiento", e);
        } finally {
            em.close();
        }
    }

    public boolean usuarioUnoYaSigueUsuarioDos(String nicknameSeguidor, String nicknameSeguido) {
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

    public List<String> obtenerUsuariosSeguidos(String nicknameSeguidor) {
        EntityManager em = JPAUtil.getEntityManager();
        List<String> usuariosSeguidos;
        try {
            TypedQuery<String> query = em.createQuery("SELECT s.seguido.nickname FROM Seguimiento s WHERE s.seguidor.nickname = :nick", String.class);
            query.setParameter("nick", nicknameSeguidor);
            usuariosSeguidos = query.getResultList();
        } finally {
            em.close();
        }
        return usuariosSeguidos;
    }


    public void eliminarSeguimiento(String nicknameSeguidor, String nicknameSeguido) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

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

            t.commit();
        } catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al eliminar seguimiento", e);
        } finally {
            em.close();
        }
    }
}
