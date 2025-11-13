package culturarte.logica.manejadores;

import culturarte.logica.modelos.*;
import culturarte.persistencia.JPAUtil;

import culturarte.servicios.DTs.DTAccesoSitio;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            TypedQuery<String> query = em.createQuery("SELECT u.nickname FROM Usuario u WHERE" +
                    "(u.eliminado = false OR u.eliminado IS NULL)", String.class);
            nicknames = query.getResultList();
        } finally {
            em.close();
        }
        return nicknames;
    }

    public List<String> obtenerNicknamesProponentes() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT nickname FROM Proponente p WHERE " +
                "(p.eliminado = false OR p.eliminado IS NULL)", String.class);
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
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo AND " +
                    "(u.eliminado = false OR u.eliminado IS NULL)", Usuario.class).setParameter("correo", correo);
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
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :nick AND " +
                    "(u.eliminado = false OR u.eliminado IS NULL)", Usuario.class).setParameter("nick", nickname);
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
                            "WHERE p.nickname = :nick AND (p.eliminado = false OR p.eliminado IS NULL)",
                    Proponente.class
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
                            "LEFT JOIN FETCH col.pago " +
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

    public boolean comprobarUsuarioUnoYaSigueUsuarioDos(String nicknameSeguidor, String nicknameSeguido) {
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

    public void agregarPropuestaFavorita(String nickname,String titulo){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();

            Usuario usuario = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nickname)
                    .getSingleResult();

            Propuesta propuesta = em.createQuery("SELECT p FROM Propuesta p WHERE p.titulo = :titulo", Propuesta.class)
                    .setParameter("titulo", titulo)
                    .getSingleResult();

            usuario.getPropuestasFavoritas().add(propuesta);

            t.commit();
        }  catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al agregar propuesta favorita", e);
        } finally {
            em.close();
        }
    }

    public boolean comprobarUsuarioYaTienePropuestaFavorita(String nickname,String titulo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(pf) FROM Usuario u JOIN u.propuestasFavoritas pf " +
                            "WHERE u.nickname = :nick AND pf.titulo = :titulo", Long.class);
            query.setParameter("nick", nickname);
            query.setParameter("titulo", titulo);

            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public void eliminarPropuestaFavorita(String nickname,String titulo){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();

            Usuario usuario = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nickname)
                    .getSingleResult();

            Propuesta propuesta = em.createQuery("SELECT p FROM Propuesta p WHERE p.titulo = :titulo", Propuesta.class)
                    .setParameter("titulo", titulo)
                    .getSingleResult();

            usuario.getPropuestasFavoritas().remove(propuesta);

            t.commit();
        }  catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al eliminar propuesta favorita", e);
        } finally {
            em.close();
        }
    }
    public List<String> obtenerFollowers(String nicknameSeguido) {
        EntityManager em = JPAUtil.getEntityManager();
        List<String> followers;
        try {
            TypedQuery<String> query = em.createQuery(
                    "SELECT s.seguidor.nickname " +
                            "FROM Seguimiento s " +
                            "WHERE s.seguido.nickname = :nick", String.class
            );
            query.setParameter("nick", nicknameSeguido);
            followers = query.getResultList(); // si no hay, devuelve lista vacía
        } finally {
            em.close();
        }
        return followers;
    }

    public List<Proponente> obtenerProponentesEliminados() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Proponente> proponentesEliminados;
        try {
            TypedQuery<Proponente> query = em.createQuery(
                    "SELECT DISTINCT p FROM Proponente p " +
                            "LEFT JOIN FETCH p.propuestas pr " +
                            "WHERE (p.eliminado = true) " +
                            "ORDER BY p.fechaEliminacion DESC", Proponente.class
            );
            proponentesEliminados = query.getResultList();

            for (Proponente prop : proponentesEliminados) {
                for (Propuesta p : prop.getPropuestas()) {
                    p.getColaboraciones().size();
                    p.getTiposRetorno().size();
                    p.getHistorial().size();
                    if (p.getCategoria() != null) {
                        p.getCategoria().getNombre();
                    }
                    for (Colaboracion c : p.getColaboraciones()) {
                        if (c.getColaborador() != null) {
                            c.getColaborador().getNickname();
                        }
                    }
                }
            }
        } finally {
            em.close();
        }
        return proponentesEliminados;
    }

    public void darDeBajaProponente(Proponente proponente) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Proponente prop = em.find(Proponente.class, proponente.getId());

            if (prop == null) {
                throw new IllegalArgumentException("El proponente no existe");
            }

            em.createNativeQuery("DELETE FROM usuarios_propuestas WHERE propuestasFavoritas_id  IN (SELECT id FROM " +
                            "propuestas WHERE proponente_id = :propId)").setParameter("propId", prop.getId())
                    .executeUpdate();

            em.createQuery("DELETE FROM Seguimiento s WHERE s.seguidor.id = :uid").setParameter
                            ("uid", prop.getId()).executeUpdate();

            em.createQuery("DELETE FROM Seguimiento s WHERE s.seguido.id = :uid").setParameter
                            ("uid", prop.getId()).executeUpdate();

            prop.setEliminado(true);
            prop.setFechaEliminacion(java.time.LocalDateTime.now());

            em.merge(prop);
            t.commit();
        } catch(Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            throw new PersistenceException("Error al dar de baja proponente", e);
        } finally {
            em.close();
        }
    }

    public void persistirAcceso(String ip, String url, String browser, String sistemaOperativo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            AccesoSitio acceso = new AccesoSitio(ip, url, browser, sistemaOperativo);
            em.persist(acceso);

            LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);
            em.createQuery("DELETE FROM AccesoSitio a WHERE a.fechaHora < :fechaLimite").setParameter
                            ("fechaLimite", fechaLimite).executeUpdate();

            Long count = em.createQuery("SELECT COUNT(a) FROM AccesoSitio a", Long.class).getSingleResult();

            if (count > 10000) {
                int exceso = (int)(count - 10000);
                List<Long> idsAEliminar = em.createQuery("SELECT a.id FROM AccesoSitio a ORDER BY a.fechaHora ASC",
                        Long.class).setMaxResults(exceso).getResultList();

                if (!idsAEliminar.isEmpty()) {
                    em.createQuery("DELETE FROM AccesoSitio a WHERE a.id IN :ids").setParameter
                                    ("ids", idsAEliminar).executeUpdate();
                }
            }
            t.commit();
        } catch(Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            throw new PersistenceException("Error al registrar acceso: " + e);
        } finally {
            em.close();
        }
    }

    public List<DTAccesoSitio> obtenerRegistroAccesos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<DTAccesoSitio> resultado = new ArrayList<>();
        try {
            List<AccesoSitio> accesos = em.createQuery("SELECT a FROM AccesoSitio a ORDER BY a.fechaHora DESC",
                            AccesoSitio.class).getResultList();

            for (AccesoSitio acceso : accesos) {
                resultado.add(new DTAccesoSitio(acceso.getId(), acceso.getIp(), acceso.getUrl(), acceso.getBrowser(),
                        acceso.getSistemaOperativo(), acceso.getFechaHora()));
            }
        } finally {
            em.close();
        }
        return resultado;
    }

}
