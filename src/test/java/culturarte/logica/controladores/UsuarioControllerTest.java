package culturarte.logica.controladores;

import culturarte.logica.DTs.*;
import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.Colaborador;
import culturarte.logica.modelos.EstadoPropuesta;
import culturarte.logica.modelos.Proponente;
import culturarte.logica.modelos.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
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

    }


    @Test
    void devolverColaboradorPorNickname_ColaboradorNoExiste_LanzaExcepcion() {

    }

    @Test
    void devolverProponentePorNickname_DatosValidos_RetornaCorrectamente() {

    }

    @Test
    void devolverProponentePorNickname_ProponenteNoExiste_LanzaExcepcion() {

    }

    @Test
    void seguirUsuario_DatosValidos_SigueCorrectamente() {

    }

    @Test
    void seguirUsuario_UsuariosNoExisten_LanzaExcepcion() {

    }

    @Test
    void seguirUsuario_SeguirASiMismo_LanzaExcepcion() {

    }

    @Test
    void seguirUsuario_UsuarioYaSigue_LanzaExcepcion() {

    }

    @Test
    void devolverUsuariosSeguidos_DatosValidos_RetornaListaCorrectamente() {

    }

    @Test
    void dejarDeSeguirUsuario_DatosValidos_DejaSeguirCorrectamente() {

    }

    @Test
    void dejarDeSeguirUsuario_UsuariosNulos_LanzaExcepcion() {

    }

    @Test
    void dejarDeSeguirUsuario_SiMismo_LanzaExcepcion() {

    }

    @Test
    void usuarioUnoYaSigueUsuarioDos_DatosValidos_RetornaBoolCorresponde() {

    }

    @Test
    void marcarPropuestaFavorita_DatosValidos_MarcaCorrectamente() {

    }

    @Test
    void marcarPropuestaFavorita_UsuarioNoExiste_LanzaExcepcion() {

    }

    @Test
    void marcarPropuestaFavorita_PropuestaNoExiste_LanzaExcepcion() {

    }

    @Test
    void marcarPropuestaFavorita_YaMarcada_LanzaExcepcion() {

    }

    @Test
    void quitarPropuestaFavorita_DatosValidos_QuitaCorrectamente() {

    }

    @Test
    void quitarPropuestaFavorita_UsuarioNoExiste_LanzaExcepcion() {

    }

    @Test
    void quitarPropuestaFavorita_PropuestaNoExiste_LanzaExcepcion() {

    }

    @Test
    void usuarioYaTienePropuestaFavorita_DatosValidos_RetornaTrueCuandoCorresponde() {

    }

}
