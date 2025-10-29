package culturarte.logica.controladores;

import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.*;
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

    @AfterEach
    void tearDown() {
        propuestaManejadorStatic.close();
        usuarioManejadorStatic.close();
    }

    @Test
    void crearPropuesta_DatosValidos_CreaCorrectamente() throws Exception {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);
        Proponente proponenteMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Bob")).thenReturn(proponenteMock);
        Categoria categoriaMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Cine")).thenReturn(categoriaMock);

        controller.crearPropuesta("Batman", "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", "Bob", "Cine", List.of(TipoRetorno.ENTRADAS_GRATIS.name()));

        verify(propuestaManejadorMock).persistirPropuesta(any(Propuesta.class));
    }

    @Test
    void crearPropuesta_TituloYaExiste_LanzaExcepcion() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);

        Exception e = assertThrows(Exception.class, () -> controller.crearPropuesta("Batman",
                "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", "Bob", "Cine", List.of(TipoRetorno.ENTRADAS_GRATIS.name())));

        assertTrue(e.getMessage().contains("ya esta registrada"));
    }

    @Test
    void crearPropuesta_CategoriaNoExiste_LanzaExcepcion(){
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);
        Proponente proponenteMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Bob")).thenReturn(proponenteMock);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Cine")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.crearPropuesta("Batman",
                "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", "Bob", "Cine", List.of(TipoRetorno.ENTRADAS_GRATIS.name())));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void crearPropuesta_TipoRetornoInvalido_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);
        Proponente proponenteMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Bob")).thenReturn(proponenteMock);
        Categoria categoriaMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Cine")).thenReturn(categoriaMock);

        Exception e = assertThrows(Exception.class, () -> controller.crearPropuesta("Batman",
                "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", "Bob", "Cine", List.of("100%_GANANCIAS")));

        assertTrue(e.getMessage().contains("Tipo de retorno inválido"));
    }

    @Test
    void devolverTodasLasPropuestas_DatosValidos_RetornaListaCorrectamente() {
        List<DTPropuesta> lista = List.of(mock(DTPropuesta.class),mock(DTPropuesta.class));
        when(propuestaManejadorMock.obtenerTodasLasPropuestas()).thenReturn(lista);

        List<DTPropuesta> resultado = controller.devolverTodasLasPropuestas();
        assertEquals(lista, resultado);
    }

    @Test
    void devolverPropuestasPorEstado_DatosValidos_RetornaListaCorrectamente() {
        List<DTPropuesta> lista = List.of(mock(DTPropuesta.class),mock(DTPropuesta.class));
        when(propuestaManejadorMock.obtenerPropuestasPorEstado(EstadoPropuesta.INGRESADA)).thenReturn(lista);

        List<DTPropuesta> resultado = controller.devolverPropuestasPorEstado(DTEstadoPropuesta.INGRESADA);

        verify(propuestaManejadorMock).obtenerPropuestasPorEstado(EstadoPropuesta.INGRESADA);
        assertEquals(lista, resultado);
    }

    @Test
    void modificarPropuesta_DatosValidos_ModificaCorrectamente() throws Exception {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Categoria categoriaMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Cine")).thenReturn(categoriaMock);

        controller.modificarPropuesta("Batman", "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", List.of("ENTRADAS_GRATIS"), "Cine");

        verify(propuestaMock).setDescripcion("Batman vs Superman");
        verify(propuestaMock).setLugar("Ciudad Gótica");
        verify(propuestaMock).setPrecioEntrada(100.0);
        verify(propuestaMock).setMontoNecesario(1000.0);
        verify(propuestaMock).setCategoria(categoriaMock);
        verify(propuestaManejadorMock).actualizarPropuesta(propuestaMock);
    }

    @Test
    void modificarPropuesta_PropuestaNoExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.modificarPropuesta("Batman",
                "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", List.of("ENTRADAS_GRATIS"), "Cine"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void modificarPropuesta_CategoriaNoExiste_LanzaExcepcion() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Cine")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.modificarPropuesta("Batman",
                "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", List.of("ENTRADAS_GRATIS"), "Cine"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void modificarPropuesta_TipoRetornoInvalido_LanzaExcepcion() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Categoria categoriaMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Cine")).thenReturn(categoriaMock);

        Exception e = assertThrows(Exception.class, () -> controller.modificarPropuesta("Batman",
                "Batman vs Superman", "Ciudad Gótica",
                LocalDate.of(2030, 12, 25), 100.0, 1000.0,
                "imagen.png", List.of("100%_GANANCIAS"), "Cine"));

        assertTrue(e.getMessage().contains("Tipo de retorno inválido"));
    }

    @Test
    void crearCategoria_PadreExiste_CreaCorrectamente() throws Exception {
        Categoria padreMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Danza")).thenReturn(padreMock);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Tango")).thenReturn(null);

        controller.crearCategoria("Tango", "Danza");

        verify(propuestaManejadorMock).persistirCategoria(any(Categoria.class));
    }

    @Test
    void crearCategoria_PadreNoExiste_CreaCorrectamente() throws Exception {
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Danza")).thenReturn(null);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Tango")).thenReturn(null);
        Categoria raizMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Categoría")).thenReturn(raizMock);

        controller.crearCategoria("Tango", "Danza");

        verify(propuestaManejadorMock).persistirCategoria(any(Categoria.class));
    }

    @Test
    void crearCategoria_NombreYaExiste_LanzaExcepcion() {
        Categoria categoriaMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Danza")).thenReturn(categoriaMock);
        Categoria padreMock = mock(Categoria.class);
        when(propuestaManejadorMock.obtenerCategoriaPorNombre("Tango")).thenReturn(padreMock);

        Exception e = assertThrows(Exception.class, () -> controller.crearCategoria("Tango", "Danza"));

        assertTrue(e.getMessage().contains("ya existe"));
    }

    @Test
    void devolverTodasLasCategorias_DatosValidos_RetornaListaCorrectamente() {
        List<DTCategoria> lista = List.of(mock(DTCategoria.class),mock(DTCategoria.class));
        when(propuestaManejadorMock.obtenerTodasLasCategorias()).thenReturn(lista);

        List<DTCategoria> resultado = controller.devolverTodasLasCategorias();
        assertEquals(lista, resultado);
    }

    @Test
    void registrarColaboracion_DatosValidos_CreaCorrectamente() throws Exception {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Colaborador colaboradorMock = mock(Colaborador.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Joe")).thenReturn(colaboradorMock);

        controller.registrarColaboracion("Batman", "Joe", 500.0,
                "ENTRADAS_GRATIS");

        verify(propuestaManejadorMock).persistirColaboracion(any(Colaboracion.class));
    }

    @Test
    void registrarColaboracion_PropuestaNoExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.registrarColaboracion("Batman",
                "Joe", 500.0, "ENTRADAS_GRATIS"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void registrarColaboracion_UsuarioNoEsColaborador_LanzaExcepcion() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Usuario usuarioMock = mock(Proponente.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Joe")).thenReturn(usuarioMock);

        Exception e = assertThrows(Exception.class, () -> controller.registrarColaboracion("Batman",
                "Joe", 500.0, "ENTRADAS_GRATIS"));

        assertTrue(e.getMessage().contains("no es un colaborador válido"));
    }

    @Test
    void registrarColaboracion_TipoRetornoInvalido_LanzaExcepcion() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Colaborador colaboradorMock = mock(Colaborador.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Joe")).thenReturn(colaboradorMock);

        Exception e = assertThrows(Exception.class, () -> controller.registrarColaboracion("Batman",
                "Joe", 500.0, "100%_GANANCIAS"));

        assertTrue(e.getMessage().contains("El tipo de retorno ingresado no es válido"));
    }

    @Test
    void obtenerTodasLasColaboraciones_DatosValidos_RetornaListaCorrectamente() {
        List<DTColaboracion> lista = List.of(mock(DTColaboracion.class),mock(DTColaboracion.class));
        when(propuestaManejadorMock.obtenerTodasLasColaboraciones()).thenReturn(lista);

        List<DTColaboracion> resultado = controller.obtenerTodasLasColaboraciones();
        assertEquals(lista, resultado);
    }

    @Test
    void cancelarColaboracion_DatosValidos_CancelaCorrectamente() throws Exception {
        controller.cancelarColaboracion(1L);
        verify(propuestaManejadorMock).cancelarColaboracion(1L);
    }

    @Test
    void publicarPropuesta_DatosValidos_PublicaCorrectamente() throws Exception {
        Propuesta propuestaMock = new Propuesta();
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        propuestaMock.setHistorial(new ArrayList<>());

        controller.publicarPropuesta("Batman");

        assertEquals(EstadoPropuesta.PUBLICADA, propuestaMock.getEstadoActual());
        verify(propuestaManejadorMock).actualizarPropuesta(propuestaMock);
    }

    @Test
    void publicarPropuesta_PropuestaNoExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.publicarPropuesta("Batman"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void cancelarPropuesta_DatosValidos_CancelaCorrectamente() throws Exception {
        Propuesta propuestaMock = new Propuesta();
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        propuestaMock.setHistorial(new ArrayList<>());

        controller.cancelarPropuesta("Batman");

        assertEquals(EstadoPropuesta.CANCELADA, propuestaMock.getEstadoActual());
        verify(propuestaManejadorMock).actualizarPropuesta(propuestaMock);
    }

    @Test
    void cancelarPropuesta_PropuestaNoExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("TituloInexistente")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.cancelarPropuesta("Batman"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void getDTPropuesta_PropuestaExiste_RetornaDTCorrectamente() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        DTPropuesta dtMock = mock(DTPropuesta.class);
        when(propuestaMock.getDataType()).thenReturn(dtMock);

        assertEquals(dtMock, controller.getDTPropuesta("Batman"));
    }

    @Test
    void getDTPropuesta_PropuestaNoExiste_RetornaNullCorrectamente() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        assertNull(controller.getDTPropuesta("Batman"));
    }

    @Test
    void getPropuestasIngresadas_DatosValidos_RetornaListaCorrectamente() {
        List<DTPropuesta> lista = List.of(mock(DTPropuesta.class),mock(DTPropuesta.class));
        when(propuestaManejadorMock.obtenerPropuestasPorEstado(EstadoPropuesta.INGRESADA)).thenReturn(lista);

        List<DTPropuesta> resultado = controller.getPropuestasIngresadas();
        assertEquals(lista, resultado);
    }

    @Test
    void evaluarPropuesta_PublicarTrue_CambiaEstadoCorrectamente() throws Exception {
        Propuesta propuestaMock = new Propuesta();
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        propuestaMock.setHistorial(new ArrayList<>());

        controller.evaluarPropuesta("Batman", true);

        assertEquals(EstadoPropuesta.PUBLICADA, propuestaMock.getEstadoActual());
        verify(propuestaManejadorMock).actualizarPropuesta(propuestaMock);
    }

    @Test
    void evaluarPropuesta_PublicarFalse_CambiaEstadoCorrectamente() throws Exception {
        Propuesta propuestaMock = new Propuesta();
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        propuestaMock.setHistorial(new ArrayList<>());

        controller.evaluarPropuesta("Batman", false);

        assertEquals(EstadoPropuesta.CANCELADA, propuestaMock.getEstadoActual());
        verify(propuestaManejadorMock).actualizarPropuesta(propuestaMock);
    }

    @Test
    void evaluarPropuesta_PropuestaNoExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.evaluarPropuesta("Batman", true));

        assertTrue(e.getMessage().contains("La propuesta no existe"));
    }

    @Test
    void agregarComentario_DatosValidos_CreaCorrectamente() throws Exception {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Joe")).thenReturn(usuarioMock);

        controller.agregarComentario("Batman", "Joe", "Épico");

        verify(propuestaManejadorMock).persistirComentario(any(Comentario.class));
        verify(propuestaManejadorMock).actualizarPropuesta(propuestaMock);
    }

    @Test
    void agregarComentario_PropuestaNoExiste_LanzaExcepcion() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.agregarComentario("Batman",
                "Joe", "Épico"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void agregarComentario_UsuarioNoExiste_LanzaExcepcion() {
        Propuesta mockPropuesta = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(mockPropuesta);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Joe")).thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> controller.agregarComentario("Batman",
                "Joe", "Épico"));

        assertTrue(e.getMessage().contains("no existe"));
    }

    @Test
    void agregarComentario_ContenidoVacio_LanzaExcepcion() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioManejadorMock.obtenerUsuarioPorNickname("Joe")).thenReturn(usuarioMock);

        Exception e = assertThrows(Exception.class, () -> controller.agregarComentario("Batman",
                "Joe", ""));

        assertTrue(e.getMessage().contains("El contenido del comentario no puede estar vacío"));
    }

    @Test
    void obtenerComentariosPropuesta_PropuestaConComentarios_RetornaListaComentariosCorrectamente() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        Comentario comentarioMock = mock(Comentario.class);
        when(propuestaMock.getComentarios()).thenReturn(List.of(comentarioMock));
        DTComentario dtComentario = mock(DTComentario.class);
        when(comentarioMock.getDataType()).thenReturn(dtComentario);

        List<DTComentario> resultado = controller.obtenerComentariosPropuesta("Batman");

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerComentariosPropuesta_PropuestaNoExiste_RetornaListaVaciaCorrectamente() {
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(null);

        assertTrue(controller.obtenerComentariosPropuesta("Batman").isEmpty());
    }

    @Test
    void obtenerComentariosPropuesta_PropuestaSinComentarios_RetornaListaVaciaCorrectamente() {
        Propuesta propuestaMock = mock(Propuesta.class);
        when(propuestaManejadorMock.obtenerPropuestaPorTitulo("Batman")).thenReturn(propuestaMock);
        when(propuestaMock.getComentarios()).thenReturn(null);

        assertTrue(controller.obtenerComentariosPropuesta("Batman").isEmpty());
    }
}
