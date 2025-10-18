package culturarte.logica.controladores;

import culturarte.logica.manejadores.UsuarioManejador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

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
    void login_EntraConNickname_RetornaDTCorrectamente() { }

    @Test
    void login_EntraConCorreo_RetornaDTCorrectamente() { }

    @Test
    void login_UsuarioNoExiste_LanzaExcepcion() { }

    @Test
    void login_ContraseniaIncorrecta_LanzaExcepcion() { }

    @Test
    void logout_DatosValidos_UsuarioActualQuedaNullCorrectamente() { }

    @Test
    void getUsuarioActual_UsuarioActualNoNull_RetornaUsuarioCorrectamente() { }

    @Test
    void getUsuarioActual_UsuarioActualNull_RetornaNullCorrectamente() { }

    @Test
    void isLoggedIn_UsuarioActualNoNull_RetornaTrueCorrectamente() { }

    @Test
    void isLoggedIn_UsuarioActualNull_RetornaFalseCorrectamente() { }
}
