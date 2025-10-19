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
        // Arrange
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");
        
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);

        // Act
        DTUsuario result = controller.login("testUser", "password123");

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getNickname());
        assertEquals("Test", result.getNombre());
        assertEquals("User", result.getApellido());
        assertEquals("test@example.com", result.getCorreo());
        assertEquals("imagen.png", result.getImagen());
    }

    @Test
    void login_EntraConCorreo_RetornaDTCorrectamente() {
        // Arrange
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNickname()).thenReturn("testUser");
        when(usuarioMock.getNombre()).thenReturn("Test");
        when(usuarioMock.getApellido()).thenReturn("User");
        when(usuarioMock.getCorreo()).thenReturn("test@example.com");
        when(usuarioMock.getImagen()).thenReturn("imagen.png");
        when(usuarioMock.getPassword()).thenReturn("password123");
        
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("test@example.com")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("test@example.com")).thenReturn(usuarioMock);

        // Act
        DTUsuario result = controller.login("test@example.com", "password123");

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getNickname());
        assertEquals("Test", result.getNombre());
        assertEquals("User", result.getApellido());
        assertEquals("test@example.com", result.getCorreo());
        assertEquals("imagen.png", result.getImagen());
    }

    @Test
    void login_UsuarioNoExiste_LanzaExcepcion() {
        // Arrange
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("nonexistent")).thenReturn(null);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("nonexistent")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            controller.login("nonexistent", "password123"));
        
        assertEquals("Datos incorrectos", exception.getMessage());
    }

    @Test
    void login_ContraseniaIncorrecta_LanzaExcepcion() {
        // Arrange
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getPassword()).thenReturn("correctPassword");
        
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("testUser")).thenReturn(usuarioMock);
        when(usuarioManejadorMock.obtenerUsuarioPorCorreo("testUser")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            controller.login("testUser", "wrongPassword"));
        
        assertEquals("Datos incorrectos", exception.getMessage());
    }

    @Test
    void logout_DatosValidos_UsuarioActualQuedaNullCorrectamente() {
        // Arrange - First login to set usuarioActual
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

        // Act
        controller.logout();

        // Assert
        assertNull(controller.getUsuarioActual());
        assertFalse(controller.isLoggedIn());
    }

    @Test
    void getUsuarioActual_UsuarioActualNoNull_RetornaUsuarioCorrectamente() {
        // Arrange - First login to set usuarioActual
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

        // Act
        Usuario result = controller.getUsuarioActual();

        // Assert
        assertNotNull(result);
        assertEquals(usuarioMock, result);
    }

    @Test
    void getUsuarioActual_UsuarioActualNull_RetornaNullCorrectamente() {
        // Act
        Usuario result = controller.getUsuarioActual();

        // Assert
        assertNull(result);
    }

    @Test
    void isLoggedIn_UsuarioActualNoNull_RetornaTrueCorrectamente() {
        // Arrange - First login to set usuarioActual
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

        // Act
        boolean result = controller.isLoggedIn();

        // Assert
        assertTrue(result);
    }

    @Test
    void isLoggedIn_UsuarioActualNull_RetornaFalseCorrectamente() {
        // Act
        boolean result = controller.isLoggedIn();

        // Assert
        assertFalse(result);
    }
}
