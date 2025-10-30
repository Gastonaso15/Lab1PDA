package culturarte.logica.manejadores;

import culturarte.logica.modelos.*;
import culturarte.persistencia.JPAUtil;
import culturarte.servicios.DTs.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PropuestaManejadorTest {

    private PropuestaManejador manejador;
    private EntityManager emMock;
    private EntityTransaction transactionMock;
    private MockedStatic<JPAUtil> jpaUtilMock;

    @BeforeEach
    void setUp() {
        manejador = PropuestaManejador.getInstance();
        emMock = mock(EntityManager.class);
        transactionMock = mock(EntityTransaction.class);

        jpaUtilMock = mockStatic(JPAUtil.class);
        jpaUtilMock.when(JPAUtil::getEntityManager).thenReturn(emMock);

        when(emMock.getTransaction()).thenReturn(transactionMock);
    }

    @AfterEach
    void tearDown() {
        jpaUtilMock.close();
    }

    @Test
    void persistirPropuesta_DatosValidos_PersistCorrectamente() {
        Propuesta propuesta = mock(Propuesta.class);

        manejador.persistirPropuesta(propuesta);

        verify(transactionMock).begin();
        verify(emMock).persist(propuesta);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void persistirPropuesta_ErrorAlPersistir_HaceRollback() {
        Propuesta propuesta = mock(Propuesta.class);
        doThrow(new RuntimeException("Error")).when(emMock).persist(any());
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () -> manejador.persistirPropuesta(propuesta));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void obtenerPropuestaPorTitulo_PropuestaExiste_RetornaPropuesta() {
        TypedQuery<Propuesta> queryMock = mock(TypedQuery.class);
        Propuesta propuesta = mock(Propuesta.class);

        when(emMock.createQuery(anyString(), eq(Propuesta.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(propuesta);
        when(propuesta.getHistorial()).thenReturn(new ArrayList<>());
        when(propuesta.getColaboraciones()).thenReturn(new ArrayList<>());
        when(propuesta.getComentarios()).thenReturn(new ArrayList<>());
        when(propuesta.getTiposRetorno()).thenReturn(new ArrayList<>());

        Propuesta resultado = manejador.obtenerPropuestaPorTitulo("Batman");

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerPropuestaPorTitulo_PropuestaNoExiste_RetornaNull() {
        TypedQuery<Propuesta> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Propuesta.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        Propuesta resultado = manejador.obtenerPropuestaPorTitulo("Inexistente");

        assertNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerTodasLasPropuestas_ConPropuestas_RetornaListaDT() {
        TypedQuery<Propuesta> queryMock = mock(TypedQuery.class);
        Propuesta propuesta = crearPropuestaMock();

        when(emMock.createQuery(anyString(), eq(Propuesta.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(propuesta));

        List<DTPropuesta> resultado = manejador.obtenerTodasLasPropuestas();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(emMock).close();
    }

    @Test
    void obtenerPropuestasPorEstado_ConEstado_RetornaListaFiltrada() {
        TypedQuery<Propuesta> queryMock = mock(TypedQuery.class);
        Propuesta propuesta = crearPropuestaMock();

        when(emMock.createQuery(anyString(), eq(Propuesta.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(propuesta));

        List<DTPropuesta> resultado = manejador.obtenerPropuestasPorEstado(EstadoPropuesta.PUBLICADA);

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void actualizarPropuesta_DatosValidos_ActualizaCorrectamente() {
        Propuesta propuesta = mock(Propuesta.class);

        manejador.actualizarPropuesta(propuesta);

        verify(transactionMock).begin();
        verify(emMock).merge(propuesta);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void actualizarPropuesta_ErrorAlActualizar_HaceRollback() {
        Propuesta propuesta = mock(Propuesta.class);
        doThrow(new RuntimeException("Error")).when(emMock).merge(any());
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () -> manejador.actualizarPropuesta(propuesta));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void persistirCategoria_DatosValidos_PersistCorrectamente() {
        Categoria categoria = mock(Categoria.class);

        manejador.persistirCategoria(categoria);

        verify(transactionMock).begin();
        verify(emMock).persist(categoria);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void obtenerCategoriaPorNombre_CategoriaExiste_RetornaCategoria() {
        TypedQuery<Categoria> queryMock = mock(TypedQuery.class);
        Categoria categoria = mock(Categoria.class);

        when(emMock.createQuery(anyString(), eq(Categoria.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(categoria);

        Categoria resultado = manejador.obtenerCategoriaPorNombre("Cine");

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerCategoriaPorNombre_CategoriaNoExiste_RetornaNull() {
        TypedQuery<Categoria> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Categoria.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        Categoria resultado = manejador.obtenerCategoriaPorNombre("Inexistente");

        assertNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerTodasLasCategorias_ConCategorias_RetornaListaDT() {
        TypedQuery<Categoria> queryMock = mock(TypedQuery.class);
        Categoria categoria = mock(Categoria.class);
        when(categoria.getNombre()).thenReturn("Cine");
        when(categoria.getCategoriaPadre()).thenReturn(null);

        when(emMock.createQuery(anyString(), eq(Categoria.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(categoria));

        List<DTCategoria> resultado = manejador.obtenerTodasLasCategorias();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(emMock).close();
    }

    @Test
    void persistirColaboracion_DatosValidos_PersistCorrectamente() {
        Colaboracion colaboracion = mock(Colaboracion.class);

        manejador.persistirColaboracion(colaboracion);

        verify(transactionMock).begin();
        verify(emMock).persist(colaboracion);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void cancelarColaboracion_ColaboracionExiste_EliminaCorrectamente() throws Exception {
        Colaboracion colaboracion = mock(Colaboracion.class);
        when(emMock.find(Colaboracion.class, 1L)).thenReturn(colaboracion);

        manejador.cancelarColaboracion(1L);

        verify(transactionMock).begin();
        verify(emMock).remove(colaboracion);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void cancelarColaboracion_ColaboracionNoExiste_LanzaExcepcion() {
        when(emMock.find(Colaboracion.class, 1L)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                manejador.cancelarColaboracion(1L));

        assertTrue(exception.getMessage().contains("No existe la colaboración"));
        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void obtenerTodasLasColaboraciones_ConColaboraciones_RetornaListaDT() {
        TypedQuery<Colaboracion> queryMock = mock(TypedQuery.class);
        Colaboracion colaboracion = crearColaboracionMock();

        when(emMock.createQuery(anyString(), eq(Colaboracion.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(colaboracion));

        List<DTColaboracion> resultado = manejador.obtenerTodasLasColaboraciones();

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void persistirComentario_DatosValidos_PersistCorrectamente() {
        Comentario comentario = mock(Comentario.class);

        manejador.persistirComentario(comentario);

        verify(transactionMock).begin();
        verify(emMock).persist(comentario);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void persistirComentario_ErrorAlPersistir_HaceRollback() {
        Comentario comentario = mock(Comentario.class);
        doThrow(new RuntimeException("Error")).when(emMock).persist(any());
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () -> manejador.persistirComentario(comentario));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    // Métodos auxiliares
    private Propuesta crearPropuestaMock() {
        Propuesta propuesta = mock(Propuesta.class);
        Proponente proponente = mock(Proponente.class);
        Categoria categoria = mock(Categoria.class);

        when(propuesta.getTitulo()).thenReturn("Batman");
        when(propuesta.getDescripcion()).thenReturn("Descripción");
        when(propuesta.getLugar()).thenReturn("Gotham");
        when(propuesta.getFechaPrevista()).thenReturn(LocalDate.now());
        when(propuesta.getPrecioEntrada()).thenReturn(100.0);
        when(propuesta.getMontoNecesario()).thenReturn(1000.0);
        when(propuesta.getFechaPublicacion()).thenReturn(LocalDate.now());
        when(propuesta.getImagen()).thenReturn("imagen.png");
        when(propuesta.getCategoria()).thenReturn(categoria);
        when(propuesta.getProponente()).thenReturn(proponente);
        when(propuesta.getEstadoActual()).thenReturn(EstadoPropuesta.PUBLICADA);
        when(propuesta.getHistorial()).thenReturn(new ArrayList<>());
        when(propuesta.getColaboraciones()).thenReturn(new ArrayList<>());
        when(propuesta.getComentarios()).thenReturn(new ArrayList<>());
        when(propuesta.getTiposRetorno()).thenReturn(List.of(TipoRetorno.ENTRADAS_GRATIS));

        when(proponente.getNickname()).thenReturn("Bruce");
        when(proponente.getNombre()).thenReturn("Bruce");
        when(proponente.getApellido()).thenReturn("Wayne");
        when(categoria.getNombre()).thenReturn("Cine");

        return propuesta;
    }

    private Colaboracion crearColaboracionMock() {
        Colaboracion colaboracion = mock(Colaboracion.class);
        Propuesta propuesta = crearPropuestaMock();
        Colaborador colaborador = mock(Colaborador.class);

        when(colaboracion.getId()).thenReturn(1L);
        when(colaboracion.getPropuesta()).thenReturn(propuesta);
        when(colaboracion.getColaborador()).thenReturn(colaborador);
        when(colaboracion.getMonto()).thenReturn(500.0);
        when(colaboracion.getTipoRetorno()).thenReturn(TipoRetorno.ENTRADAS_GRATIS);
        when(colaboracion.getFechaHora()).thenReturn(LocalDateTime.now());

        when(colaborador.getNickname()).thenReturn("Joe");
        when(propuesta.getColaboraciones()).thenReturn(new ArrayList<>());

        return colaboracion;
    }
}