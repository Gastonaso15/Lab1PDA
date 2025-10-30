package culturarte.logica.manejadores;

import culturarte.logica.modelos.*;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
}