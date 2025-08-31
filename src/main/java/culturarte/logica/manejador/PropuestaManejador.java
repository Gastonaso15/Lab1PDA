package culturarte.logica.manejador;

import culturarte.logica.DT.*;
import culturarte.logica.modelo.*;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<DTPropuesta> obtenerTodasLasPropuestas() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Propuesta> propuestas = em.createQuery("SELECT p FROM Propuesta p", Propuesta.class)
                .getResultList();
        for (Propuesta p : propuestas) {
            p.getHistorial().size();
            p.getColaboraciones().size();
            p.getTiposRetorno().size();
        }
        em.close();
        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        for (Propuesta p : propuestas) {

            DTCategoria dtCategoria = null;
            if (p.getCategoria() != null) {
                dtCategoria = new DTCategoria(p.getCategoria().getNombre());
            }

            DTEstadoPropuesta dtEstadoPropuesta = DTEstadoPropuesta.valueOf(p.getEstadoActual().name());

            List<DTPropuestaEstado> historial = new ArrayList<>();
            if (p.getHistorial() != null) {
                for (PropuestaEstado pe : p.getHistorial()) {
                    historial.add(new DTPropuestaEstado(
                            DTEstadoPropuesta.valueOf(pe.getEstado().name()),
                            pe.getFechaCambio()
                    ));
                }
            }

            List<DTColaboracion> colaboraciones = new ArrayList<>();
            if (p.getColaboraciones() != null) {
                for (Colaboracion c : p.getColaboraciones()) {
                    DTColaborador dtColab = new DTColaborador(
                            c.getColaborador().getNickname()
                    );

                    colaboraciones.add(new DTColaboracion(
                            dtColab,
                            c.getMonto()
                    ));
                }
            }

            List<DTTipoRetorno> tiposRetorno = new ArrayList<>();
            if (p.getTiposRetorno() != null) {
                for (TipoRetorno t : p.getTiposRetorno()) {
                    tiposRetorno.add(DTTipoRetorno.valueOf(t.name()));
                }
            }

            DTProponente dtProp = new DTProponente();
            dtProp.setNombre(p.getProponente().getNombre());
            dtProp.setApellido(p.getProponente().getApellido());
            dtProp.setNickname(p.getProponente().getNickname());

            DTPropuesta dt = new DTPropuesta();
            dt.setTitulo(p.getTitulo());
            dt.setDescripcion(p.getDescripcion());
            dt.setLugar(p.getLugar());
            dt.setFechaPrevista(p.getFechaPrevista());
            dt.setPrecioEntrada(p.getPrecioEntrada());
            dt.setMontoNecesario(p.getMontoNecesario());
            dt.setFechaPublicacion(p.getFechaPublicacion());
            dt.setImagen(p.getImagen());
            dt.setCategoria(dtCategoria);
            dt.setDTProponente(dtProp);
            dt.setEstadoActual(dtEstadoPropuesta);
            dt.setHistorial(historial);
            dt.setColaboraciones(colaboraciones);
            dt.setTiposRetorno(tiposRetorno);

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
    public List<DTPropuesta> obtenerPropuestasPorEstado(EstadoPropuesta estado) {
        EntityManager em = JPAUtil.getEntityManager();
        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        try {
            TypedQuery<Propuesta> query = em.createQuery("SELECT p FROM Propuesta p WHERE p.estadoActual = :estado", Propuesta.class);
            query.setParameter("estado", estado);
            List<Propuesta> propuestas = query.getResultList();

            for (Propuesta p : propuestas) {
                DTCategoria dtCategoria = null;
                if (p.getCategoria() != null) {
                    dtCategoria = new DTCategoria(p.getCategoria().getNombre());
                }

                DTEstadoPropuesta dtEstadoPropuesta = DTEstadoPropuesta.valueOf(p.getEstadoActual().name());

                List<DTPropuestaEstado> historial = new ArrayList<>();
                if (p.getHistorial() != null) {
                    for (PropuestaEstado pe : p.getHistorial()) {
                        historial.add(new DTPropuestaEstado(
                                DTEstadoPropuesta.valueOf(pe.getEstado().name()),
                                pe.getFechaCambio()
                        ));
                    }
                }

                List<DTColaboracion> colaboraciones = new ArrayList<>();
                if (p.getColaboraciones() != null) {
                    for (Colaboracion c : p.getColaboraciones()) {
                        DTColaborador dtColab = new DTColaborador(
                                c.getColaborador().getNickname()
                        );

                        colaboraciones.add(new DTColaboracion(
                                dtColab,
                                c.getMonto()
                        ));
                    }
                }

                List<DTTipoRetorno> tiposRetorno = new ArrayList<>();
                if (p.getTiposRetorno() != null) {
                    for (TipoRetorno t : p.getTiposRetorno()) {
                        tiposRetorno.add(DTTipoRetorno.valueOf(t.name()));
                    }
                }

                DTProponente dtProp = new DTProponente();
                dtProp.setNombre(p.getProponente().getNombre());
                dtProp.setApellido(p.getProponente().getApellido());
                dtProp.setNickname(p.getProponente().getNickname());

                DTPropuesta dt = new DTPropuesta();
                dt.setTitulo(p.getTitulo());
                dt.setDescripcion(p.getDescripcion());
                dt.setLugar(p.getLugar());
                dt.setFechaPrevista(p.getFechaPrevista());
                dt.setPrecioEntrada(p.getPrecioEntrada());
                dt.setMontoNecesario(p.getMontoNecesario());
                dt.setFechaPublicacion(p.getFechaPublicacion());
                dt.setImagen(p.getImagen());
                dt.setCategoria(dtCategoria);
                dt.setDTProponente(dtProp);
                dt.setEstadoActual(dtEstadoPropuesta);
                dt.setHistorial(historial);
                dt.setColaboraciones(colaboraciones);
                dt.setTiposRetorno(tiposRetorno);

                dtPropuestas.add(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return dtPropuestas;
    }
    public void actualizarPropuesta(Propuesta pro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.merge(pro);
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
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

    public void agregarColaboracion(Colaboracion colaboracion) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(colaboracion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    public void cancelarColaboracion(Long idColaboracion) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Colaboracion colab = em.find(Colaboracion.class, idColaboracion);
            if (colab == null) {
                throw new Exception("No existe la colaboración con id " + idColaboracion);
            }

            em.remove(colab);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<DTColaboracion> getColaboraciones() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Colaboracion> colabs = em.createQuery("SELECT c FROM Colaboracion c", Colaboracion.class)
                .getResultList();

        for (Colaboracion c : colabs) {
            c.getPropuesta().getColaboraciones().size();
        }

        em.close();

        List<DTColaboracion> dtColaboraciones = colabs.stream().map(c -> {

            List<DTColaboracion> dtColabsPropuesta = c.getPropuesta().getColaboraciones().stream()
                    .map(col -> new DTColaboracion(
                            new DTColaborador(col.getColaborador().getNickname()),
                            col.getMonto()
                    ))
                    .toList();

            DTEstadoPropuesta dtEstadoPropuesta = DTEstadoPropuesta.valueOf(c.getPropuesta().getEstadoActual().name());
            DTPropuesta dtPropuesta = new DTPropuesta(
                    c.getPropuesta().getTitulo(),
                    c.getPropuesta().getMontoNecesario(),
                    c.getPropuesta().getProponente() != null ? new DTProponente(c.getPropuesta().getProponente().getNickname(), c.getPropuesta().getProponente().getNombre(), c.getPropuesta().getProponente().getApellido()) : null,
                    dtEstadoPropuesta,
                    dtColabsPropuesta
            );

            DTColaborador dtColaborador = new DTColaborador(
                    c.getColaborador().getNickname()
            );

            DTTipoRetorno dtTipoRetorno = DTTipoRetorno.valueOf(c.getTipoRetorno().name());
            return new DTColaboracion(
                    c.getId(),
                    dtPropuesta,
                    dtColaborador,
                    c.getMonto(),
                    dtTipoRetorno,
                    c.getFechaHora()
            );

        }).collect(Collectors.toList());

        return dtColaboraciones;
    }

}
