package culturarte.logica.manejadores;

import culturarte.logica.modelos.*;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioManejadorTest {

    private UsuarioManejador manejador;
    private EntityManager emMock;
    private EntityTransaction transactionMock;
    private MockedStatic<JPAUtil> jpaUtilMock;

    @BeforeEach
    void setUp() {
        manejador = UsuarioManejador.getInstance();
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
    void persistirUsuario_DatosValidos_PersistCorrectamente() {
        Usuario usuario = mock(Usuario.class);

        manejador.persistirUsuario(usuario);

        verify(transactionMock).begin();
        verify(emMock).persist(usuario);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void persistirUsuario_ErrorAlPersistir_HaceRollback() {
        Usuario usuario = mock(Usuario.class);
        doThrow(new RuntimeException("Error")).when(emMock).persist(any());
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () -> manejador.persistirUsuario(usuario));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void obtenerNicknamesUsuarios_ConUsuarios_RetornaLista() {
        TypedQuery<String> queryMock = mock(TypedQuery.class);
        List<String> nicknames = List.of("user1", "user2");

        when(emMock.createQuery(anyString(), eq(String.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(nicknames);

        List<String> resultado = manejador.obtenerNicknamesUsuarios();

        assertEquals(nicknames, resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerNicknamesProponentes_ConProponentes_RetornaLista() {
        TypedQuery<String> queryMock = mock(TypedQuery.class);
        List<String> nicknames = List.of("prop1", "prop2");

        when(emMock.createQuery(anyString(), eq(String.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(nicknames);

        List<String> resultado = manejador.obtenerNicknamesProponentes();

        assertEquals(nicknames, resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerNicknamesColaboradores_ConColaboradores_RetornaLista() {
        TypedQuery<String> queryMock = mock(TypedQuery.class);
        List<String> nicknames = List.of("colab1", "colab2");

        when(emMock.createQuery(anyString(), eq(String.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(nicknames);

        List<String> resultado = manejador.obtenerNicknamesColaboradores();

        assertEquals(nicknames, resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerUsuarioPorCorreo_UsuarioExiste_RetornaUsuario() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        Usuario usuario = mock(Usuario.class);

        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(usuario);

        Usuario resultado = manejador.obtenerUsuarioPorCorreo("test@email.com");

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerUsuarioPorCorreo_UsuarioNoExiste_RetornaNull() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        Usuario resultado = manejador.obtenerUsuarioPorCorreo("inexistente@email.com");

        assertNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerUsuarioPorNickname_UsuarioExiste_RetornaUsuario() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        Usuario usuario = mock(Usuario.class);

        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(usuario);

        Usuario resultado = manejador.obtenerUsuarioPorNickname("testUser");

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerUsuarioPorNickname_UsuarioNoExiste_RetornaNull() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        Usuario resultado = manejador.obtenerUsuarioPorNickname("inexistente");

        assertNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerProponentePorNickname_ProponenteExiste_RetornaProponente() {
        TypedQuery<Proponente> queryMock = mock(TypedQuery.class);
        Proponente proponente = mock(Proponente.class);

        when(emMock.createQuery(anyString(), eq(Proponente.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(proponente);
        when(proponente.getPropuestas()).thenReturn(new ArrayList<>());

        Proponente resultado = manejador.obtenerProponentePorNickname("proponente1");

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerProponentePorNickname_ProponenteNoExiste_RetornaNull() {
        TypedQuery<Proponente> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Proponente.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        Proponente resultado = manejador.obtenerProponentePorNickname("inexistente");

        assertNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerColaboradorPorNickname_ColaboradorExiste_RetornaColaborador() {
        TypedQuery<Colaborador> queryMock = mock(TypedQuery.class);
        Colaborador colaborador = mock(Colaborador.class);

        when(emMock.createQuery(anyString(), eq(Colaborador.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(colaborador);
        when(colaborador.getColaboraciones()).thenReturn(new ArrayList<>());

        Colaborador resultado = manejador.obtenerColaboradorPorNickname("colaborador1");

        assertNotNull(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerColaboradorPorNickname_ColaboradorNoExiste_RetornaNull() {
        TypedQuery<Colaborador> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Colaborador.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        Colaborador resultado = manejador.obtenerColaboradorPorNickname("inexistente");

        assertNull(resultado);
        verify(emMock).close();
    }

    @Test
    void persistirSeguimiento_DatosValidos_PersistCorrectamente() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        Usuario seguidor = mock(Usuario.class);
        Usuario seguido = mock(Usuario.class);

        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(seguidor, seguido);
        when(seguidor.getSeguidos()).thenReturn(new ArrayList<>());
        when(seguido.getSeguidores()).thenReturn(new ArrayList<>());

        manejador.persistirSeguimiento("seguidor1", "seguido1");

        verify(transactionMock).begin();
        verify(emMock).persist(any(Seguimiento.class));
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void persistirSeguimiento_ErrorAlPersistir_HaceRollback() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new RuntimeException("Error"));
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () ->
                manejador.persistirSeguimiento("seguidor1", "seguido1"));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void comprobarUsuarioUnoYaSigueUsuarioDos_YaSigue_RetornaTrue() {
        TypedQuery<Long> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(1L);

        boolean resultado = manejador.comprobarUsuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1");

        assertTrue(resultado);
        verify(emMock).close();
    }

    @Test
    void comprobarUsuarioUnoYaSigueUsuarioDos_NoSigue_RetornaFalse() {
        TypedQuery<Long> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(0L);

        boolean resultado = manejador.comprobarUsuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1");

        assertFalse(resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerUsuariosSeguidos_ConSeguidos_RetornaLista() {
        TypedQuery<String> queryMock = mock(TypedQuery.class);
        List<String> seguidos = List.of("seguido1", "seguido2");

        when(emMock.createQuery(anyString(), eq(String.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(seguidos);

        List<String> resultado = manejador.obtenerUsuariosSeguidos("seguidor1");

        assertEquals(seguidos, resultado);
        verify(emMock).close();
    }

    @Test
    void eliminarSeguimiento_SeguimientoExiste_EliminaCorrectamente() {
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        Usuario seguidor = mock(Usuario.class);
        Usuario seguido = mock(Usuario.class);
        Seguimiento seguimiento = mock(Seguimiento.class);
        List<Seguimiento> seguidos = new ArrayList<>();
        seguidos.add(seguimiento);

        when(emMock.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(seguidor, seguido);
        when(seguidor.getId()).thenReturn(1L);
        when(seguido.getId()).thenReturn(2L);
        when(seguidor.getSeguidos()).thenReturn(seguidos);
        when(seguido.getSeguidores()).thenReturn(new ArrayList<>());
        when(seguimiento.getSeguido()).thenReturn(seguido);
        when(emMock.contains(seguimiento)).thenReturn(true);

        manejador.eliminarSeguimiento("seguidor1", "seguido1");

        verify(transactionMock).begin();
        verify(emMock).remove(seguimiento);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void agregarPropuestaFavorita_DatosValidos_AgregaCorrectamente() {
        TypedQuery queryMock = mock(TypedQuery.class);
        Usuario usuario = mock(Usuario.class);
        Propuesta propuesta = mock(Propuesta.class);

        when(emMock.createQuery(anyString(), any(Class.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(usuario, propuesta);
        when(usuario.getPropuestasFavoritas()).thenReturn(new ArrayList<>());

        manejador.agregarPropuestaFavorita("user1", "Batman");

        verify(transactionMock).begin();
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void agregarPropuestaFavorita_ErrorAlAgregar_HaceRollback() {
        TypedQuery queryMock = mock(TypedQuery.class);
        when(emMock.createQuery(anyString(), any(Class.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new RuntimeException("Error"));
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () ->
                manejador.agregarPropuestaFavorita("user1", "Batman"));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void comprobarUsuarioYaTienePropuestaFavorita_TieneFavorita_RetornaTrue() {
        TypedQuery<Long> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(1L);

        boolean resultado = manejador.comprobarUsuarioYaTienePropuestaFavorita("user1", "Batman");

        assertTrue(resultado);
        verify(emMock).close();
    }

    @Test
    void comprobarUsuarioYaTienePropuestaFavorita_NoTieneFavorita_RetornaFalse() {
        TypedQuery<Long> queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(0L);

        boolean resultado = manejador.comprobarUsuarioYaTienePropuestaFavorita("user1", "Batman");

        assertFalse(resultado);
        verify(emMock).close();
    }

    @Test
    void eliminarPropuestaFavorita_DatosValidos_EliminaCorrectamente() {
        TypedQuery queryMock = mock(TypedQuery.class);
        Usuario usuario = mock(Usuario.class);
        Propuesta propuesta = mock(Propuesta.class);
        List<Propuesta> favoritas = new ArrayList<>();
        favoritas.add(propuesta);

        when(emMock.createQuery(anyString(), any(Class.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(usuario, propuesta);
        when(usuario.getPropuestasFavoritas()).thenReturn(favoritas);

        manejador.eliminarPropuestaFavorita("user1", "Batman");

        verify(transactionMock).begin();
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void obtenerFollowers_ConFollowers_RetornaLista() {
        TypedQuery<String> queryMock = mock(TypedQuery.class);
        List<String> followers = List.of("follower1", "follower2");

        when(emMock.createQuery(anyString(), eq(String.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(followers);

        List<String> resultado = manejador.obtenerFollowers("user1");

        assertEquals(followers, resultado);
        verify(emMock).close();
    }

    @Test
    void obtenerProponentesEliminados_ConProponentes_RetornaLista() {
        TypedQuery<Proponente> queryMock = mock(TypedQuery.class);
        Proponente proponente = mock(Proponente.class);
        List<Proponente> proponentes = List.of(proponente);

        when(emMock.createQuery(anyString(), eq(Proponente.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(proponentes);
        when(proponente.getPropuestas()).thenReturn(new ArrayList<>());

        List<Proponente> resultado = manejador.obtenerProponentesEliminados();

        assertEquals(proponentes, resultado);
        verify(emMock).close();
    }

    @Test
    void darDeBajaProponente_DatosValidos_DaDeBajaCorrectamente() {
        Proponente proponente = mock(Proponente.class);
        when(proponente.getId()).thenReturn(1L);
        when(emMock.find(Proponente.class, 1L)).thenReturn(proponente);

        TypedQuery queryMock = mock(TypedQuery.class);
        when(emMock.createNativeQuery(anyString())).thenReturn(queryMock);
        when(emMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);

        manejador.darDeBajaProponente(proponente);

        verify(transactionMock).begin();
        verify(proponente).setEliminado(true);
        verify(proponente).setFechaEliminacion(any(java.time.LocalDateTime.class));
        verify(emMock).merge(proponente);
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void darDeBajaProponente_ProponenteNoExiste_LanzaExcepcion() {
        Proponente proponente = mock(Proponente.class);
        when(proponente.getId()).thenReturn(1L);
        when(emMock.find(Proponente.class, 1L)).thenReturn(null);
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(jakarta.persistence.PersistenceException.class, () -> manejador.darDeBajaProponente(proponente));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void persistirAcceso_DatosValidos_PersistCorrectamente() {
        Query deleteQueryMock1 = mock(Query.class);
        Query deleteQueryMock2 = mock(Query.class);
        TypedQuery<Long> countQueryMock = mock(TypedQuery.class);
        TypedQuery<Long> idsQueryMock = mock(TypedQuery.class);

        when(emMock.createQuery(contains("DELETE FROM AccesoSitio a WHERE a.fechaHora"))).thenReturn(deleteQueryMock1);
        when(deleteQueryMock1.setParameter(anyString(), any())).thenReturn(deleteQueryMock1);
        when(deleteQueryMock1.executeUpdate()).thenReturn(1);
        when(emMock.createQuery(contains("SELECT COUNT(a) FROM AccesoSitio a"), eq(Long.class))).thenReturn(countQueryMock);
        when(countQueryMock.getSingleResult()).thenReturn(5000L);
        when(emMock.createQuery(contains("SELECT a.id FROM AccesoSitio a"), eq(Long.class))).thenReturn(idsQueryMock);
        when(idsQueryMock.setMaxResults(anyInt())).thenReturn(idsQueryMock);
        when(idsQueryMock.getResultList()).thenReturn(new ArrayList<>());
        when(emMock.createQuery(contains("DELETE FROM AccesoSitio a WHERE a.id IN"))).thenReturn(deleteQueryMock2);
        when(deleteQueryMock2.setParameter(anyString(), any())).thenReturn(deleteQueryMock2);
        when(deleteQueryMock2.executeUpdate()).thenReturn(1);

        manejador.persistirAcceso("192.168.1.1", "/home", "Chrome", "Windows");

        verify(transactionMock).begin();
        verify(emMock).persist(any(AccesoSitio.class));
        verify(transactionMock).commit();
        verify(emMock).close();
    }

    @Test
    void persistirAcceso_ErrorAlPersistir_HaceRollback() {
        doThrow(new RuntimeException("Error")).when(emMock).persist(any());
        when(transactionMock.isActive()).thenReturn(true);

        assertThrows(Exception.class, () ->
                manejador.persistirAcceso("192.168.1.1", "/home", "Chrome", "Windows"));

        verify(transactionMock).rollback();
        verify(emMock).close();
    }

    @Test
    void obtenerRegistroAccesos_ConAccesos_RetornaListaDT() {
        TypedQuery<AccesoSitio> queryMock = mock(TypedQuery.class);
        AccesoSitio acceso1 = mock(AccesoSitio.class);
        AccesoSitio acceso2 = mock(AccesoSitio.class);
        List<AccesoSitio> accesos = List.of(acceso1, acceso2);

        when(emMock.createQuery(anyString(), eq(AccesoSitio.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(accesos);
        when(acceso1.getId()).thenReturn(1L);
        when(acceso1.getIp()).thenReturn("192.168.1.1");
        when(acceso1.getUrl()).thenReturn("/home");
        when(acceso1.getBrowser()).thenReturn("Chrome");
        when(acceso1.getSistemaOperativo()).thenReturn("Windows");
        when(acceso1.getFechaHora()).thenReturn(java.time.LocalDateTime.now());
        when(acceso2.getId()).thenReturn(2L);
        when(acceso2.getIp()).thenReturn("192.168.1.2");
        when(acceso2.getUrl()).thenReturn("/about");
        when(acceso2.getBrowser()).thenReturn("Firefox");
        when(acceso2.getSistemaOperativo()).thenReturn("Linux");
        when(acceso2.getFechaHora()).thenReturn(java.time.LocalDateTime.now());

        List<culturarte.servicios.DTs.DTAccesoSitio> resultado = manejador.obtenerRegistroAccesos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(emMock).close();
    }
}