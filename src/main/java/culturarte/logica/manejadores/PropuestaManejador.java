package culturarte.logica.manejadores;

import culturarte.logica.modelos.*;
import culturarte.servicios.DTs.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import culturarte.persistencia.JPAUtil;


public class PropuestaManejador {
    private static PropuestaManejador instancia = null;

    public static PropuestaManejador getInstance() {
        if (instancia == null)
            instancia = new PropuestaManejador();
        return instancia;
    }

    public void persistirPropuesta(Propuesta pro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(pro);
            t.commit();
        }
        catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al persistir propuesta", e);
        } finally {
            em.close();
        }
    }

    public Propuesta obtenerPropuestaPorTitulo(String titulo) {
        EntityManager em = JPAUtil.getEntityManager();
        Propuesta pro;
        try {
            TypedQuery<Propuesta> query = em.createQuery("SELECT p FROM Propuesta p WHERE p.titulo = :titulo AND " +
                    "(p.proponente.eliminado = false OR p.proponente.eliminado IS NULL)",
                    Propuesta.class).setParameter("titulo", titulo);
            pro = query.getSingleResult();
            
            // Cargar las colecciones lazy antes de cerrar la sesión
            if (pro != null) {
                pro.getHistorial().size();
                pro.getColaboraciones().size();
                pro.getComentarios().size();
                pro.getTiposRetorno().size();
            }
        } catch (NoResultException e) {
            pro = null;
        } finally {
            em.close();
        }
        return pro;
    }

    public List<DTPropuesta> obtenerTodasLasPropuestas() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Propuesta> propuestas = em.createQuery("SELECT p FROM Propuesta p WHERE p.proponente.eliminado = false " +
                        "OR p.proponente.eliminado IS NULL", Propuesta.class)
                .getResultList();

        for (Propuesta p : propuestas) {
            p.getHistorial().size();
            p.getColaboraciones().size();
            p.getComentarios().size();
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

            List<DTComentario> comentarios = new ArrayList<>();
            if (p.getComentarios() != null) {
                for (Comentario c : p.getComentarios()) {
                    comentarios.add(c.getDataType());
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
            dt.setComentarios(comentarios);
            dt.setTiposRetorno(tiposRetorno);

            dtPropuestas.add(dt);
        }
        return dtPropuestas;
    }

    public List<DTPropuesta> obtenerPropuestasPorEstado(EstadoPropuesta estado) {
        EntityManager em = JPAUtil.getEntityManager();
        List<DTPropuesta> dtPropuestas = new ArrayList<>();
        try {
            TypedQuery<Propuesta> query = em.createQuery("SELECT p FROM Propuesta p WHERE p.estadoActual = :estado" +
                    " AND (p.proponente.eliminado = false OR p.proponente.eliminado IS NULL)", Propuesta.class);
            query.setParameter("estado", estado);
            List<Propuesta> propuestas = query.getResultList();

            // Cargar las colecciones lazy antes de procesarlas
            for (Propuesta p : propuestas) {
                p.getHistorial().size();
                p.getColaboraciones().size();
                p.getComentarios().size();
                p.getTiposRetorno().size();
            }

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

                List<DTComentario> comentarios = new ArrayList<>();
                if (p.getComentarios() != null) {
                    for (Comentario c : p.getComentarios()) {
                        comentarios.add(c.getDataType());
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
                dt.setComentarios(comentarios);
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
        } catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al actualizar propuesta", e);
        } finally {
            em.close();
        }
    }

    public void persistirCategoria(Categoria cat) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(cat);
            t.commit();
        }
        catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al persistir categoria", e);
        } finally {
            em.close();
        }
    }

    public Categoria obtenerCategoriaPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        Categoria cat;
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

    public List<DTCategoria> obtenerTodasLasCategorias() {
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

    public void persistirColaboracion(Colaboracion colaboracion) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(colaboracion);
            t.commit();
        }  catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al persistir colaboracion", e);
        } finally {
            em.close();
        }
    }

    public void marcarConstanciaEmitida(Long idColaboracion) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            Colaboracion colaboracion = em.find(Colaboracion.class, idColaboracion);
            if (colaboracion == null) {
                throw new Exception("No existe la colaboración con id " + idColaboracion);
            }
            colaboracion.setConstanciaEmitida(true);
            em.merge(colaboracion);
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al marcar constancia como emitida", e);
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

    public List<DTColaboracion> obtenerTodasLasColaboraciones() {
        //1
        EntityManager em = JPAUtil.getEntityManager();
        //2
        List<Colaboracion> colabs = em.createQuery("SELECT c FROM Colaboracion c", Colaboracion.class).getResultList();

        //3: Obtengo el tamanio que debo recorrer
        for (Colaboracion c : colabs) {
            c.getPropuesta().getColaboraciones().size();
        }

        em.close();

        //4: creo la lista que voy a devolver de colaboraciones
        List<DTColaboracion> dtColaboraciones = new ArrayList<>();

        //5: recorro las colaboraciones de la base de datos para rellenar la lista
        for (Colaboracion c : colabs) {

            List<DTColaboracion> dtColabsPropuesta = new ArrayList<>();

            //list<Colaboracion> col = c.getPropuesta().getColaboraciones()
            //5*.3*
            for (Colaboracion col : c.getPropuesta().getColaboraciones()) {
                DTColaborador dtColab = new DTColaborador(col.getColaborador().getNickname());//5*.3*.2
                dtColabsPropuesta.add(new DTColaboracion(dtColab, col.getMonto())); //5*.3*.3
            }

            DTEstadoPropuesta dtEstadoPropuesta = DTEstadoPropuesta.valueOf(c.getPropuesta().getEstadoActual().name());

            DTPropuesta dtPropuesta = new DTPropuesta(
                    c.getPropuesta().getTitulo(),
                    c.getPropuesta().getMontoNecesario(),
                    c.getPropuesta().getProponente() != null ? new DTProponente(c.getPropuesta().getProponente().getNickname(), c.getPropuesta().getProponente().getNombre(), c.getPropuesta().getProponente().getApellido()) : null,
                    dtEstadoPropuesta,
                    dtColabsPropuesta
            );

            DTColaborador dtColaborador = new DTColaborador(c.getColaborador().getNickname());
            DTTipoRetorno dtTipoRetorno = DTTipoRetorno.valueOf(c.getTipoRetorno().name());

            dtColaboraciones.add(new DTColaboracion(
                    c.getId(),
                    dtPropuesta,
                    dtColaborador,
                    c.getMonto(),
                    dtTipoRetorno,
                    c.getFechaHora(),
                    c.getConstanciaEmitida()
            ));

        };

        return dtColaboraciones;
    }

    public void persistirComentario(Comentario comentario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(comentario);
            t.commit();
        } catch(Exception e) {
            if (t.isActive()) t.rollback();
            throw new PersistenceException("Error al persistir comentario", e);
        } finally {
            em.close();
        }
    }


}
