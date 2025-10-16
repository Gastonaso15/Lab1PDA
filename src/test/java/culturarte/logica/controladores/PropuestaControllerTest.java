package culturarte.logica.controladores;

import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PropuestaControllerTest {

    private PropuestaController controller;
    private PropuestaManejador propuestaManejadorMock;
    private UsuarioManejador usuarioManejadorMock;

    private MockedStatic<PropuestaManejador> propuestaManejadorStatic;
    private MockedStatic<UsuarioManejador> usuarioManejadorStatic;

    @BeforeEach
    void setUp() {
        controller = new PropuestaController();

        propuestaManejadorMock = mock(PropuestaManejador.class);
        usuarioManejadorMock = mock(UsuarioManejador.class);

        propuestaManejadorStatic = mockStatic(PropuestaManejador.class);
        usuarioManejadorStatic = mockStatic(UsuarioManejador.class);

        propuestaManejadorStatic.when(PropuestaManejador::getInstance).thenReturn(propuestaManejadorMock);
        usuarioManejadorStatic.when(UsuarioManejador::getInstance).thenReturn(usuarioManejadorMock);
    }

    @Test
    void crearPropuesta_CuandoTituloYaExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Test")).thenReturn(mock(Propuesta.class));

        Exception e = assertThrows(Exception.class, () -> controller.crearPropuesta(
                "Test", "desc", "Maldonado", LocalDate.now(),
                100.0, 1000.0, "img.png", "usu1", "Arte", List.of("ENTRADA_GRATIS")
        ));

        assertTrue(e.getMessage().contains("ya esta registrada"));
    }

    @Test
    void crearPropuesta_Correctamente() throws Exception {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Nueva")).thenReturn(null);
        Proponente mockProponente = mock(Proponente.class);
        Categoria mockCategoria = mock(Categoria.class);

        when(usuarioManejadorMock.obtenerUsuarioPorNickname("usu1")).thenReturn(mockProponente);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Arte")).thenReturn(mockCategoria);

        controller.crearPropuesta(
                "Nueva", "desc", "Maldonado", LocalDate.now(),
                100.0, 1000.0, "img.png", "usu1", "Arte",
                List.of(TipoRetorno.ENTRADAS_GRATIS.name())
        );

        verify(propuestaManejadorMock).persistirPropuesta(any(Propuesta.class));
    }

    @AfterEach
    void tearDown() {
        propuestaManejadorStatic.close();
        usuarioManejadorStatic.close();
    }
}
