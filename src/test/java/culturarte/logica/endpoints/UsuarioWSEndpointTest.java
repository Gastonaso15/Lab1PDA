package culturarte.logica.endpoints;

import culturarte.logica.endpoints.envoltorios.ListaDTProponente;
import culturarte.logica.endpoints.envoltorios.ListaStrings;
import culturarte.servicios.Fabrica;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.servicios.DTs.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioWSEndpointTest {

    private UsuarioWSEndpoint endpoint;
    private IUsuarioController controllerMock;
    private MockedStatic<Fabrica> fabricaStatic;

    @BeforeEach
    void setUp() {
        controllerMock = mock(IUsuarioController.class);
        fabricaStatic = mockStatic(Fabrica.class);
        Fabrica fabricaMock = mock(Fabrica.class);
        fabricaStatic.when(Fabrica::getInstance).thenReturn(fabricaMock);
        when(fabricaMock.getIUsuarioController()).thenReturn(controllerMock);
        endpoint = new UsuarioWSEndpoint();
    }

    @AfterEach
    void tearDown() {
        fabricaStatic.close();
    }

    @Test
    void crearUsuario_DatosValidos_CreaCorrectamente() throws Exception {
        DTProponente dtProponente = new DTProponente("Notch", "Markus", "Persson",
                "1234", "notch@gmail.com", LocalDate.of(1979, 6, 1),
                "imagen.png", "Estocolmo", "Desarrollador", "www.notch.com");

        endpoint.crearUsuario(dtProponente);

        verify(controllerMock).crearUsuario(dtProponente);
    }

    @Test
    void devolverNicknamesUsuarios_DatosValidos_RetornaListaCorrectamente() {
        List<String> lista = List.of("Notch", "Jeb");
        when(controllerMock.devolverNicknamesUsuarios()).thenReturn(lista);

        ListaStrings resultado = endpoint.devolverNicknamesUsuarios();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void devolverNicknamesProponentes_DatosValidos_RetornaListaCorrectamente() {
        List<String> lista = List.of("Notch", "Jeb");
        when(controllerMock.devolverNicknamesProponentes()).thenReturn(lista);

        ListaStrings resultado = endpoint.devolverNicknamesProponentes();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void devolverNicknamesColaboradores_DatosValidos_RetornaListaCorrectamente() {
        List<String> lista = List.of("Notch", "Jeb");
        when(controllerMock.devolverNicknamesColaboradores()).thenReturn(lista);

        ListaStrings resultado = endpoint.devolverNicknamesColaboradores();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void devolverProponentePorNickname_DatosValidos_RetornaCorrectamente() throws Exception {
        DTProponente dtProponente = new DTProponente("proponente1", "María", "García",
                "password123", "maria@example.com", LocalDate.of(1985, 5, 15),
                "imagen.png", "Calle 123", "Biografía", "www.maria.com", new ArrayList<>());
        when(controllerMock.devolverProponentePorNickname("proponente1")).thenReturn(dtProponente);

        DTProponente resultado = endpoint.devolverProponentePorNickname("proponente1");

        assertEquals(dtProponente, resultado);
    }

    @Test
    void devolverColaboradorPorNickname_DatosValidos_RetornaCorrectamente() throws Exception {
        DTColaborador dtColaborador = new DTColaborador("colaborador1", "Juan", "Pérez",
                "password123", "juan@example.com", LocalDate.of(1990, 1, 1),
                "imagen.png", new ArrayList<>());
        when(controllerMock.devolverColaboradorPorNickname("colaborador1")).thenReturn(dtColaborador);

        DTColaborador resultado = endpoint.devolverColaboradorPorNickname("colaborador1");

        assertEquals(dtColaborador, resultado);
    }

    @Test
    void seguirUsuario_DatosValidos_SigueCorrectamente() throws Exception {
        endpoint.seguirUsuario("seguidor1", "seguido1");

        verify(controllerMock).seguirUsuario("seguidor1", "seguido1");
    }

    @Test
    void devolverUsuariosSeguidos_DatosValidos_RetornaListaCorrectamente() {
        List<String> seguidos = List.of("usuario1", "usuario2");
        when(controllerMock.devolverUsuariosSeguidos("seguidor1")).thenReturn(seguidos);

        ListaStrings resultado = endpoint.devolverUsuariosSeguidos("seguidor1");

        assertEquals(seguidos, resultado.getLista());
    }

    @Test
    void dejarDeSeguirUsuario_DatosValidos_DejaSeguirCorrectamente() {
        endpoint.dejarDeSeguirUsuario("seguidor1", "seguido1");

        verify(controllerMock).dejarDeSeguirUsuario("seguidor1", "seguido1");
    }

    @Test
    void usuarioUnoYaSigueUsuarioDos_DatosValidos_RetornaBoolCorresponde() {
        when(controllerMock.usuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1")).thenReturn(true);

        boolean resultado = endpoint.usuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1");

        assertTrue(resultado);
    }

    @Test
    void marcarPropuestaFavorita_DatosValidos_MarcaCorrectamente() {
        endpoint.marcarPropuestaFavorita("usuario1", "Propuesta Test");

        verify(controllerMock).marcarPropuestaFavorita("usuario1", "Propuesta Test");
    }

    @Test
    void quitarPropuestaFavorita_DatosValidos_QuitaCorrectamente() {
        endpoint.quitarPropuestaFavorita("usuario1", "Propuesta Test");

        verify(controllerMock).quitarPropuestaFavorita("usuario1", "Propuesta Test");
    }

    @Test
    void usuarioYaTienePropuestaFavorita_DatosValidos_RetornaTrueCuandoCorresponde() {
        when(controllerMock.usuarioYaTienePropuestaFavorita("usuario1", "Propuesta Test")).thenReturn(true);

        boolean resultado = endpoint.usuarioYaTienePropuestaFavorita("usuario1", "Propuesta Test");

        assertTrue(resultado);
    }

    @Test
    void login_DatosValidos_RetornaDTCorrectamente() {
        DTUsuario dtUsuario = new DTUsuario("testUser", "Test", "User",
                "test@example.com", "imagen.png");
        when(controllerMock.login("testUser", "password123")).thenReturn(dtUsuario);

        DTUsuario resultado = endpoint.login("testUser", "password123");

        assertEquals(dtUsuario, resultado);
    }

    @Test
    void devolverUsuariosSeguidores_DatosValidos_RetornaListaCorrectamente() {
        List<String> seguidores = List.of("seguidor1", "seguidor2");
        when(controllerMock.devolverUsuariosSeguidores("seguido1")).thenReturn(seguidores);

        ListaStrings resultado = endpoint.devolverUsuariosSeguidores("seguido1");

        assertEquals(seguidores, resultado.getLista());
    }

    @Test
    void getDTUsuario_DatosValidos_RetornaDTCorrectamente() {
        DTUsuario dtUsuario = new DTUsuario("testUser", "Test", "User",
                "test@example.com", "imagen.png");
        when(controllerMock.getDTUsuario("testUser")).thenReturn(dtUsuario);

        DTUsuario resultado = endpoint.getDTUsuario("testUser");

        assertEquals(dtUsuario, resultado);
    }

    @Test
    void devolverProponentesEliminados_DatosValidos_RetornaListaCorrectamente() {
        DTProponente dtProponente = new DTProponente("proponente1", "Juan", "Pérez",
                "password123", "juan@example.com", LocalDate.of(1990, 1, 1),
                "imagen.png", "Calle 123", "Biografía", "www.juan.com", new ArrayList<>());
        List<DTProponente> lista = List.of(dtProponente);
        when(controllerMock.devolverProponentesEliminados()).thenReturn(lista);

        ListaDTProponente resultado = endpoint.devolverProponentesEliminados();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void bajaProponente_DatosValidos_DaDeBajaCorrectamente() throws Exception {
        endpoint.bajaProponente("proponente1");

        verify(controllerMock).bajaProponente("proponente1");
    }

    @Test
    void registrarAcceso_DatosValidos_RegistraCorrectamente() {
        endpoint.registrarAcceso("192.168.1.1", "/home", "Chrome", "Windows");

        verify(controllerMock).registrarAcceso("192.168.1.1", "/home", "Chrome", "Windows");
    }
}

