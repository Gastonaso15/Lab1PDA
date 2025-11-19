package culturarte.logica.controladores;

import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;
import culturarte.servicios.DTs.*;
import culturarte.servicios.DTs.DTAccesoSitio;
import culturarte.servicios.DTs.DTColaborador;
import culturarte.servicios.DTs.DTProponente;
import culturarte.servicios.DTs.DTUsuario;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsuarioControllerTest {
    private UsuarioController controller;

    private PropuestaManejador propuestaManejadorMock;
    private UsuarioManejador usuarioManejadorMock;

    private MockedStatic<PropuestaManejador> propuestaManejadorStatic;
    private MockedStatic<UsuarioManejador> usuarioManejadorStatic;

    @BeforeEach
    void setUp() {
        controller = new UsuarioController();

        propuestaManejadorMock = mock(PropuestaManejador.class);
        usuarioManejadorMock = mock(UsuarioManejador.class);

        propuestaManejadorStatic = mockStatic(PropuestaManejador.class);
        usuarioManejadorStatic = mockStatic(UsuarioManejador.class);

        propuestaManejadorStatic.when(PropuestaManejador::getInstance).thenReturn(propuestaManejadorMock);
        usuarioManejadorStatic.when(UsuarioManejador::getInstance).thenReturn(usuarioManejadorMock);
    }

    @AfterEach
    void tearDown() {
        propuestaManejadorStatic.close();
        usuarioManejadorStatic.close();
    }

    @Test
    void crearUsuario_DatosValidos_CreaCorrectamente() throws Exception {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Notch")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("notch@gmail.com")).thenReturn(null);

        DTProponente dtProponente = new DTProponente("Notch", "Markus", "Persson",
                "1234", "notch@gmail.com", LocalDate.of(1979, 6, 1),
                "imagen.png", "Estocolmo", "Desarrollador de Aplicaciones",
                "www.notch.com");

        controller.crearUsuario(dtProponente);

        verify(usuarioManejadorMock).persistirUsuario(any(Proponente.class));
    }

    @Test
    void crearUsuario_NicknameYaExiste_LanzaExcepcion() {
        Proponente proponenteMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Notch")).thenReturn(proponenteMock);

        DTProponente dtProponente = new DTProponente("Notch", "Markus", "Persson",
                "1234", "notch@gmail.com", LocalDate.of(1979, 6, 1),
                "imagen.png", "Estocolmo", "Desarrollador de Aplicaciones",
                "www.notch.com");

        Exception e = assertThrows(Exception.class, () -> controller.crearUsuario(dtProponente));

        assertTrue(e.getMessage().contains("ya esta registrado"));
    }

    @Test
    void crearUsuario_CorreoYaExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Notch")).thenReturn(null);
        Proponente proponenteMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("notch@gmail.com")).thenReturn(proponenteMock);

        DTProponente dtProponente = new DTProponente("Notch", "Markus", "Persson",
                "1234", "notch@gmail.com", LocalDate.of(1979, 6, 1),
                "imagen.png", "Estocolmo", "Desarrollador de Aplicaciones",
                "www.notch.com");

        Exception e = assertThrows(Exception.class, () -> controller.crearUsuario(dtProponente));

        assertTrue(e.getMessage().contains("ya esta registrado"));
    }

    @Test
    void crearUsuario_TipoUsuarioInvalido_LanzaExcepcion() {
        DTUsuario usuarioMock = mock(DTUsuario.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Notch")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("notch@gmail.com")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.crearUsuario(usuarioMock));

        assertTrue(e.getMessage().contains("Tipo de usuario no reconocido"));
    }

    @Test
    void devolverNicknamesUsuarios_DatosValidos_RetornaListaCorrectamente() {
        List<String> lista = List.of("Notch","Jeb");
        when(usuarioManejadorMock.obtenerNicknamesUsuarios()).thenReturn((lista));

        List<String> resultado = controller.devolverNicknamesUsuarios();

        assertEquals(lista, resultado);
    }

    @Test
    void devolverNicknamesProponentes_DatosValidos_RetornaListaCorrectamente() {
        List<String> lista = List.of("Notch","Jeb");
        when(usuarioManejadorMock.obtenerNicknamesProponentes()).thenReturn(lista);

        List<String> resultado = controller.devolverNicknamesProponentes();

        assertEquals(lista, resultado);
    }

    @Test
    void devolverNicknamesColaboradores_DatosValidos_RetornaListaCorrectamente() {
        List<String> lista = List.of("Notch","Jeb");
        when(usuarioManejadorMock.obtenerNicknamesColaboradores()).thenReturn(lista);

        List<String> resultado = controller.devolverNicknamesColaboradores();

        assertEquals(lista, resultado);
    }

    @Test
    void devolverColaboradorPorNickname_DatosValidos_RetornaCorrectamente() throws Exception {
        Colaborador colaboradorMock = mock(Colaborador.class);
        when(colaboradorMock.getNickname()).thenReturn("colaborador1");
        when(colaboradorMock.getNombre()).thenReturn("Juan");
        when(colaboradorMock.getApellido()).thenReturn("Pérez");
        when(colaboradorMock.getPassword()).thenReturn("password123");
        when(colaboradorMock.getCorreo()).thenReturn("juan@example.com");
        when(colaboradorMock.getFechaNacimiento()).thenReturn(LocalDate.of(1990, 1, 1));
        when(colaboradorMock.getImagen()).thenReturn("imagen.png");
        when(colaboradorMock.getColaboraciones()).thenReturn(new ArrayList<>());

        when(usuarioManejadorMock.obtenerColaboradorPorNickname("colaborador1")).thenReturn(colaboradorMock);

        DTColaborador result = controller.devolverColaboradorPorNickname("colaborador1");

        assertNotNull(result);
        assertEquals("colaborador1", result.getNickname());
        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellido());
        assertEquals("password123", result.getPassword());
        assertEquals("juan@example.com", result.getCorreo());
        assertEquals(LocalDate.of(1990, 1, 1), result.getFechaNacimiento());
        assertEquals("imagen.png", result.getImagen());
        assertNotNull(result.getColaboraciones());
    }

    @Test
    void devolverColaboradorPorNickname_ColaboradorNoExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerColaboradorPorNickname("inexistente")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
            controller.devolverColaboradorPorNickname("inexistente"));

        assertTrue(exception.getMessage().contains("El colaborador inexistente no existe"));
    }

    @Test
    void devolverProponentePorNickname_DatosValidos_RetornaCorrectamente() throws Exception {
        Proponente proponenteMock = mock(Proponente.class);
        when(proponenteMock.getNickname()).thenReturn("proponente1");
        when(proponenteMock.getNombre()).thenReturn("María");
        when(proponenteMock.getApellido()).thenReturn("García");
        when(proponenteMock.getPassword()).thenReturn("password123");
        when(proponenteMock.getCorreo()).thenReturn("maria@example.com");
        when(proponenteMock.getFechaNacimiento()).thenReturn(LocalDate.of(1985, 5, 15));
        when(proponenteMock.getImagen()).thenReturn("imagen.png");
        when(proponenteMock.getDireccion()).thenReturn("Calle 123");
        when(proponenteMock.getBio()).thenReturn("Biografía de María");
        when(proponenteMock.getSitioWeb()).thenReturn("www.maria.com");
        when(proponenteMock.getPropuestas()).thenReturn(new ArrayList<>());

        when(usuarioManejadorMock.obtenerProponentePorNickname("proponente1")).thenReturn(proponenteMock);

        DTProponente result = controller.devolverProponentePorNickname("proponente1");

        assertNotNull(result);
        assertEquals("proponente1", result.getNickname());
        assertEquals("María", result.getNombre());
        assertEquals("García", result.getApellido());
        assertEquals("password123", result.getPassword());
        assertEquals("maria@example.com", result.getCorreo());
        assertEquals(LocalDate.of(1985, 5, 15), result.getFechaNacimiento());
        assertEquals("imagen.png", result.getImagen());
        assertEquals("Calle 123", result.getDireccion());
        assertEquals("Biografía de María", result.getBio());
        assertEquals("www.maria.com", result.getSitioWeb());
        assertNotNull(result.getPropuestas());
    }

    @Test
    void devolverProponentePorNickname_ProponenteNoExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerProponentePorNickname("inexistente")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
            controller.devolverProponentePorNickname("inexistente"));

        assertTrue(exception.getMessage().contains("El proponente inexistente no existe"));
    }

    @Test
    void seguirUsuario_DatosValidos_SigueCorrectamente() {
        Usuario seguidorMock = mock(Usuario.class);
        Usuario seguidoMock = mock(Usuario.class);
        when(seguidorMock.getNickname()).thenReturn("seguidor1");
        when(seguidoMock.getNickname()).thenReturn("seguido1");

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguidor1")).thenReturn(seguidorMock);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguido1")).thenReturn(seguidoMock);
        when(usuarioManejadorMock.comprobarUsuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1")).thenReturn(false);

        controller.seguirUsuario("seguidor1", "seguido1");

        verify(usuarioManejadorMock).persistirSeguimiento("seguidor1", "seguido1");
    }

    @Test
    void seguirUsuario_UsuariosNoExisten_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguidor1")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguido1")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.seguirUsuario("seguidor1", "seguido1"));

        assertEquals("Los usuarios no pueden ser nulos.", exception.getMessage());
    }

    @Test
    void seguirUsuario_SeguirASiMismo_LanzaExcepcion() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("usuario1");

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.seguirUsuario("usuario1", "usuario1"));

        assertEquals("Un usuario no puede seguirse a sí mismo.", exception.getMessage());
    }

    @Test
    void seguirUsuario_UsuarioYaSigue_LanzaExcepcion() {
        Usuario seguidorMock = mock(Usuario.class);
        Usuario seguidoMock = mock(Usuario.class);
        when(seguidorMock.getNickname()).thenReturn("seguidor1");
        when(seguidoMock.getNickname()).thenReturn("seguido1");

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguidor1")).thenReturn(seguidorMock);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguido1")).thenReturn(seguidoMock);
        when(usuarioManejadorMock.comprobarUsuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1")).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            controller.seguirUsuario("seguidor1", "seguido1"));

        assertEquals("El usuario ya sigue a este usuario.", exception.getMessage());
    }

    @Test
    void devolverUsuariosSeguidos_DatosValidos_RetornaListaCorrectamente() {
        List<String> usuariosSeguidos = List.of("usuario1", "usuario2", "usuario3");
        when(usuarioManejadorMock.obtenerUsuariosSeguidos("seguidor1")).thenReturn(usuariosSeguidos);

        List<String> result = controller.devolverUsuariosSeguidos("seguidor1");

        assertEquals(usuariosSeguidos, result);
    }

    @Test
    void dejarDeSeguirUsuario_DatosValidos_DejaSeguirCorrectamente() {
        Usuario seguidorMock = mock(Usuario.class);
        Usuario seguidoMock = mock(Usuario.class);
        when(seguidorMock.getId()).thenReturn(1L);
        when(seguidoMock.getId()).thenReturn(2L);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguidor1")).thenReturn(seguidorMock);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguido1")).thenReturn(seguidoMock);

        controller.dejarDeSeguirUsuario("seguidor1", "seguido1");

        verify(usuarioManejadorMock).eliminarSeguimiento("seguidor1", "seguido1");
    }

    @Test
    void dejarDeSeguirUsuario_UsuariosNulos_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguidor1")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("seguido1")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.dejarDeSeguirUsuario("seguidor1", "seguido1"));

        assertEquals("Los usuarios no pueden ser nulos.", exception.getMessage());
    }

    @Test
    void dejarDeSeguirUsuario_SiMismo_LanzaExcepcion() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getId()).thenReturn(1L);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.dejarDeSeguirUsuario("usuario1", "usuario1"));

        assertEquals("Un usuario no puede dejar de seguirse a sí mismo.", exception.getMessage());
    }

    @Test
    void usuarioUnoYaSigueUsuarioDos_DatosValidos_RetornaBoolCorresponde() {
        when(usuarioManejadorMock.comprobarUsuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1")).thenReturn(true);

        boolean result = controller.usuarioUnoYaSigueUsuarioDos("seguidor1", "seguido1");

        assertTrue(result);
    }

    @Test
    void marcarPropuestaFavorita_DatosValidos_MarcaCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        Propuesta propuestaMock = mock(Propuesta.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Propuesta Test")).thenReturn(propuestaMock);
        when(usuarioManejadorMock.comprobarUsuarioYaTienePropuestaFavorita("usuario1", "Propuesta Test")).thenReturn(false);

        controller.marcarPropuestaFavorita("usuario1", "Propuesta Test");

        verify(usuarioManejadorMock).agregarPropuestaFavorita("usuario1", "Propuesta Test");
    }

    @Test
    void marcarPropuestaFavorita_UsuarioNoExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.marcarPropuestaFavorita("usuario1", "Propuesta Test"));

        assertEquals("El usuario no puede ser nulo.", exception.getMessage());
    }

    @Test
    void marcarPropuestaFavorita_PropuestaNoExiste_LanzaExcepcion() {
        Usuario usuarioMock = mock(Usuario.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Propuesta Test")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.marcarPropuestaFavorita("usuario1", "Propuesta Test"));

        assertEquals("La propuesta no puede ser nula.", exception.getMessage());
    }

    @Test
    void marcarPropuestaFavorita_YaMarcada_LanzaExcepcion() {
        Usuario usuarioMock = mock(Usuario.class);
        Propuesta propuestaMock = mock(Propuesta.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Propuesta Test")).thenReturn(propuestaMock);
        when(usuarioManejadorMock.comprobarUsuarioYaTienePropuestaFavorita("usuario1", "Propuesta Test")).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            controller.marcarPropuestaFavorita("usuario1", "Propuesta Test"));

        assertEquals("El usuario ya tiene marcada como favorita esta propuesta.", exception.getMessage());
    }

    @Test
    void quitarPropuestaFavorita_DatosValidos_QuitaCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        Propuesta propuestaMock = mock(Propuesta.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Propuesta Test")).thenReturn(propuestaMock);

        controller.quitarPropuestaFavorita("usuario1", "Propuesta Test");

        verify(usuarioManejadorMock).eliminarPropuestaFavorita("usuario1", "Propuesta Test");
    }

    @Test
    void quitarPropuestaFavorita_UsuarioNoExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.quitarPropuestaFavorita("usuario1", "Propuesta Test"));

        assertEquals("El usuario no puede ser nulo.", exception.getMessage());
    }

    @Test
    void quitarPropuestaFavorita_PropuestaNoExiste_LanzaExcepcion() {
        Usuario usuarioMock = mock(Usuario.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usuario1")).thenReturn(usuarioMock);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Propuesta Test")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            controller.quitarPropuestaFavorita("usuario1", "Propuesta Test"));

        assertEquals("La propuesta no puede ser nula.", exception.getMessage());
    }

    @Test
    void usuarioYaTienePropuestaFavorita_DatosValidos_RetornaTrueCuandoCorresponde() {
        when(usuarioManejadorMock.comprobarUsuarioYaTienePropuestaFavorita("usuario1", "Propuesta Test")).thenReturn(true);

        boolean result = controller.usuarioYaTienePropuestaFavorita("usuario1", "Propuesta Test");

        assertTrue(result);
    }

    @Test
    void login_EntraConNickname_RetornaDTCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);

        DTUsuario result = controller.login("testUser", "password123");

        assertNotNull(result);
        assertEquals("testUser", result.getNickname());
        assertEquals("Test", result.getNombre());
        assertEquals("User", result.getApellido());
        assertEquals("test@example.com", result.getCorreo());
        assertEquals("imagen.png", result.getImagen());
    }

    @Test
    void login_EntraConCorreo_RetornaDTCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("test@example.com")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("test@example.com")).thenReturn(usuarioMock);

        DTUsuario result = controller.login("test@example.com", "password123");

        assertNotNull(result);
        assertEquals("testUser", result.getNickname());
        assertEquals("Test", result.getNombre());
        assertEquals("User", result.getApellido());
        assertEquals("test@example.com", result.getCorreo());
        assertEquals("imagen.png", result.getImagen());
    }

    @Test
    void login_UsuarioNoExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("nonexistent")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("nonexistent")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.login("nonexistent", "password123"));

        assertEquals("Datos incorrectos", exception.getMessage());
    }

    @Test
    void login_ContraseniaIncorrecta_LanzaExcepcion() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getPassword()).thenReturn("correctPassword");

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.login("testUser", "wrongPassword"));

        assertEquals("Datos incorrectos", exception.getMessage());
    }

    @Test
    void devolverUsuariosSeguidores_DatosValidos_RetornaListaCorrectamente() {
        List<String> seguidores = List.of("seguidor1", "seguidor2");
        when(usuarioManejadorMock.obtenerFollowers("seguido1")).thenReturn(seguidores);

        List<String> resultado = controller.devolverUsuariosSeguidores("seguido1");

        assertEquals(seguidores, resultado);
    }

    @Test
    void getDTUsuario_UsuarioExiste_RetornaDTCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        DTUsuario dtUsuarioMock = mock(DTUsuario.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioMock.getDataType()).thenReturn(dtUsuarioMock);

        DTUsuario resultado = controller.getDTUsuario("testUser");

        assertEquals(dtUsuarioMock, resultado);
    }

    @Test
    void getDTUsuario_UsuarioNoExiste_RetornaNull() {
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("inexistente")).thenReturn(null);

        DTUsuario resultado = controller.getDTUsuario("inexistente");

        assertNull(resultado);
    }

    @Test
    void devolverProponentesEliminados_DatosValidos_RetornaListaCorrectamente() {
        Proponente proponenteMock = mock(Proponente.class);
        when(proponenteMock.getNickname()).thenReturn("proponente1");
        when(proponenteMock.getNombre()).thenReturn("Juan");
        when(proponenteMock.getApellido()).thenReturn("Pérez");
        when(proponenteMock.getCorreo()).thenReturn("juan@example.com");
        when(proponenteMock.getPassword()).thenReturn("password123");
        when(proponenteMock.getFechaNacimiento()).thenReturn(LocalDate.of(1990, 1, 1));
        when(proponenteMock.getDireccion()).thenReturn("Calle 123");
        when(proponenteMock.getBio()).thenReturn("Biografía");
        when(proponenteMock.getSitioWeb()).thenReturn("www.juan.com");
        when(proponenteMock.getImagen()).thenReturn("imagen.png");
        when(proponenteMock.getFechaEliminacion()).thenReturn(LocalDateTime.now());
        when(proponenteMock.getPropuestas()).thenReturn(new ArrayList<>());

        when(usuarioManejadorMock.obtenerProponentesEliminados()).thenReturn(List.of(proponenteMock));

        List<DTProponente> resultado = controller.devolverProponentesEliminados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("proponente1", resultado.get(0).getNickname());
    }

    @Test
    void bajaProponente_DatosValidos_DaDeBajaCorrectamente() throws Exception {
        Proponente proponenteMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerProponentePorNickname("proponente1")).thenReturn(proponenteMock);

        controller.bajaProponente("proponente1");

        verify(proponenteMock).setEliminado(true);
        verify(proponenteMock).setFechaEliminacion(any(LocalDateTime.class));
        verify(usuarioManejadorMock).darDeBajaProponente(proponenteMock);
    }

    @Test
    void bajaProponente_ProponenteNoExiste_LanzaExcepcion() {
        when(usuarioManejadorMock.obtenerProponentePorNickname("inexistente")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                controller.bajaProponente("inexistente"));

        assertTrue(exception.getMessage().contains("no existe"));
    }

    @Test
    void registrarAcceso_DatosValidos_RegistraCorrectamente() {
        controller.registrarAcceso("192.168.1.1", "/home", "Chrome", "Windows");

        verify(usuarioManejadorMock).persistirAcceso("192.168.1.1", "/home", "Chrome", "Windows");
    }

    @Test
    void devolverRegistroAccesos_DatosValidos_RetornaListaCorrectamente() {
        DTAccesoSitio dtAcceso1 = mock(DTAccesoSitio.class);
        DTAccesoSitio dtAcceso2 = mock(DTAccesoSitio.class);
        List<DTAccesoSitio> accesos = List.of(dtAcceso1, dtAcceso2);

        when(usuarioManejadorMock.obtenerRegistroAccesos()).thenReturn(accesos);

        List<DTAccesoSitio> resultado = controller.devolverRegistroAccesos();

        assertEquals(accesos, resultado);
    }

    @Test
    void devolverColaboracionesConPagoPorNickname_DatosValidos_RetornaListaCorrectamente() throws Exception {
        Colaboracion colaboracion1 = mock(Colaboracion.class);
        Colaboracion colaboracion2 = mock(Colaboracion.class);
        List<Colaboracion> colaboraciones = List.of(colaboracion1, colaboracion2);

        Propuesta propuesta1 = mock(Propuesta.class);
        Propuesta propuesta2 = mock(Propuesta.class);
        when(colaboracion1.getPropuesta()).thenReturn(propuesta1);
        when(colaboracion2.getPropuesta()).thenReturn(propuesta2);

        Proponente proponente1 = mock(Proponente.class);
        Proponente proponente2 = mock(Proponente.class);
        when(propuesta1.getProponente()).thenReturn(proponente1);
        when(propuesta2.getProponente()).thenReturn(proponente2);
        when(proponente1.getEliminado()).thenReturn(false);
        when(proponente2.getEliminado()).thenReturn(false);

        Categoria categoria1 = mock(Categoria.class);
        Categoria categoria2 = mock(Categoria.class);
        when(propuesta1.getCategoria()).thenReturn(categoria1);
        when(propuesta2.getCategoria()).thenReturn(categoria2);
        when(categoria1.getCategoriaPadre()).thenReturn(null);
        when(categoria2.getCategoriaPadre()).thenReturn(null);

        when(propuesta1.getTiposRetorno()).thenReturn(new ArrayList<>());
        when(propuesta2.getTiposRetorno()).thenReturn(new ArrayList<>());
        when(propuesta1.getEstadoActual()).thenReturn(EstadoPropuesta.PUBLICADA);
        when(propuesta2.getEstadoActual()).thenReturn(EstadoPropuesta.PUBLICADA);

        when(propuesta1.getColaboraciones()).thenReturn(new ArrayList<>());
        when(propuesta2.getColaboraciones()).thenReturn(new ArrayList<>());

        when(colaboracion1.getTipoRetorno()).thenReturn(TipoRetorno.ENTRADAS_GRATIS);
        when(colaboracion2.getTipoRetorno()).thenReturn(TipoRetorno.ENTRADAS_GRATIS);
        when(colaboracion1.getMonto()).thenReturn(500.0);
        when(colaboracion2.getMonto()).thenReturn(300.0);
        when(colaboracion1.getFechaHora()).thenReturn(LocalDateTime.now());
        when(colaboracion2.getFechaHora()).thenReturn(LocalDateTime.now());
        when(colaboracion1.getConstanciaEmitida()).thenReturn(false);
        when(colaboracion2.getConstanciaEmitida()).thenReturn(true);
        when(colaboracion1.getId()).thenReturn(1L);
        when(colaboracion2.getId()).thenReturn(2L);

        when(usuarioManejadorMock.obtenerColaboracionesConPagoPorNickname("Pepe")).thenReturn(colaboraciones);

        List<DTColaboracion> resultado = controller.devolverColaboracionesConPagoPorNickname("Pepe");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioManejadorMock).obtenerColaboracionesConPagoPorNickname("Pepe");
    }

    @Test
    void devolverColaboracionesConPagoPorNickname_SinColaboraciones_RetornaListaVacia() throws Exception {
        when(usuarioManejadorMock.obtenerColaboracionesConPagoPorNickname("Pepe")).thenReturn(new ArrayList<>());

        List<DTColaboracion> resultado = controller.devolverColaboracionesConPagoPorNickname("Pepe");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioManejadorMock).obtenerColaboracionesConPagoPorNickname("Pepe");
    }

    @Test
    void devolverColaboracionesConPagoPorNickname_ColaboracionesNull_RetornaListaVacia() throws Exception {
        when(usuarioManejadorMock.obtenerColaboracionesConPagoPorNickname("Pepe")).thenReturn(null);

        List<DTColaboracion> resultado = controller.devolverColaboracionesConPagoPorNickname("Pepe");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioManejadorMock).obtenerColaboracionesConPagoPorNickname("Pepe");
    }

    @Test
    void devolverColaboracionesConPagoPorNickname_ColaboracionConProponenteEliminado_RetornaCorrectamente() throws Exception {
        Colaboracion colaboracion1 = mock(Colaboracion.class);
        List<Colaboracion> colaboraciones = List.of(colaboracion1);

        Propuesta propuesta1 = mock(Propuesta.class);
        when(colaboracion1.getPropuesta()).thenReturn(propuesta1);

        Proponente proponente1 = mock(Proponente.class);
        when(propuesta1.getProponente()).thenReturn(proponente1);
        when(proponente1.getEliminado()).thenReturn(true);
        when(proponente1.getFechaEliminacion()).thenReturn(LocalDateTime.now());

        Categoria categoria1 = mock(Categoria.class);
        when(propuesta1.getCategoria()).thenReturn(categoria1);
        when(categoria1.getCategoriaPadre()).thenReturn(null);

        when(propuesta1.getTiposRetorno()).thenReturn(new ArrayList<>());
        when(propuesta1.getEstadoActual()).thenReturn(EstadoPropuesta.PUBLICADA);
        when(propuesta1.getColaboraciones()).thenReturn(new ArrayList<>());

        when(colaboracion1.getTipoRetorno()).thenReturn(TipoRetorno.ENTRADAS_GRATIS);
        when(colaboracion1.getMonto()).thenReturn(500.0);
        when(colaboracion1.getFechaHora()).thenReturn(LocalDateTime.now());
        when(colaboracion1.getConstanciaEmitida()).thenReturn(false);
        when(colaboracion1.getId()).thenReturn(1L);

        when(usuarioManejadorMock.obtenerColaboracionesConPagoPorNickname("Pepe")).thenReturn(colaboraciones);

        List<DTColaboracion> resultado = controller.devolverColaboracionesConPagoPorNickname("Pepe");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuarioManejadorMock).obtenerColaboracionesConPagoPorNickname("Pepe");
    }

}
