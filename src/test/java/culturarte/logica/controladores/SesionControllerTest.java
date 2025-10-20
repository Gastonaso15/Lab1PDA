package culturarte.logica.controladores;

import culturarte.logica.DTs.DTUsuario;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SesionControllerTest {
    private SesionController controller;

    private UsuarioManejador usuarioManejadorMock;

    private MockedStatic<UsuarioManejador> usuarioManejadorStatic;

    @BeforeEach
    void setUp() {
        controller = new SesionController();

        usuarioManejadorMock = mock(UsuarioManejador.class);

        usuarioManejadorStatic = mockStatic(UsuarioManejador.class);

        usuarioManejadorStatic.when(UsuarioManejador::getInstance).thenReturn(usuarioManejadorMock);
    }

    @AfterEach
    void tearDown() {
        usuarioManejadorStatic.close();
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
    void logout_DatosValidos_UsuarioActualQuedaNullCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");
        
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);
        
        controller.login("testUser", "password123");
        assertNotNull(controller.getUsuarioActual());

        controller.logout();

        assertNull(controller.getUsuarioActual());
        assertFalse(controller.isLoggedIn());
    }

    @Test
    void getUsuarioActual_UsuarioActualNoNull_RetornaUsuarioCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");
        
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);
        
        controller.login("testUser", "password123");

        Usuario result = controller.getUsuarioActual();

        assertNotNull(result);
        assertEquals(usuarioMock, result);
    }

    @Test
    void getUsuarioActual_UsuarioActualNull_RetornaNullCorrectamente() {
        Usuario result = controller.getUsuarioActual();

        assertNull(result);
    }

    @Test
    void isLoggedIn_UsuarioActualNoNull_RetornaTrueCorrectamente() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");
        
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);
        
        controller.login("testUser", "password123");

        boolean result = controller.isLoggedIn();

        assertTrue(result);
    }

    @Test
    void isLoggedIn_UsuarioActualNull_RetornaFalseCorrectamente() {
        boolean result = controller.isLoggedIn();

        assertFalse(result);
    }
}
