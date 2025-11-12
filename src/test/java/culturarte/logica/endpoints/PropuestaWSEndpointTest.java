package culturarte.logica.endpoints;

import culturarte.logica.endpoints.envoltorios.*;
import culturarte.servicios.Fabrica;
import culturarte.servicios.interfaces.IPropuestaController;
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

public class PropuestaWSEndpointTest {

    private PropuestaWSEndpoint endpoint;
    private IPropuestaController controllerMock;
    private MockedStatic<Fabrica> fabricaStatic;

    @BeforeEach
    void setUp() {
        controllerMock = mock(IPropuestaController.class);
        fabricaStatic = mockStatic(Fabrica.class);
        Fabrica fabricaMock = mock(Fabrica.class);
        fabricaStatic.when(Fabrica::getInstance).thenReturn(fabricaMock);
        when(fabricaMock.getIPropuestaController()).thenReturn(controllerMock);
        endpoint = new PropuestaWSEndpoint();
    }

    @AfterEach
    void tearDown() {
        fabricaStatic.close();
    }

    @Test
    void getDTPropuesta_DatosValidos_RetornaDTCorrectamente() {
        DTPropuesta dtPropuesta = mock(DTPropuesta.class);
        when(controllerMock.getDTPropuesta("Batman")).thenReturn(dtPropuesta);

        DTPropuesta resultado = endpoint.getDTPropuesta("Batman");

        assertEquals(dtPropuesta, resultado);
    }

    @Test
    void crearPropuesta_DatosValidos_CreaCorrectamente() throws Exception {
        ListaStrings listaTipos = new ListaStrings(List.of("ENTRADAS_GRATIS"));
        LocalDate fecha = LocalDate.of(2030, 12, 25);

        endpoint.crearPropuesta("Batman", "Batman vs Superman", "Ciudad Gótica",
                fecha, 100.0, 1000.0, "imagen.png", "Bob", "Cine", listaTipos);

        verify(controllerMock).crearPropuesta("Batman", "Batman vs Superman", "Ciudad Gótica",
                fecha, 100.0, 1000.0, "imagen.png", "Bob", "Cine", List.of("ENTRADAS_GRATIS"));
    }

    @Test
    void devolverTodasLasPropuestas_DatosValidos_RetornaListaCorrectamente() {
        List<DTPropuesta> lista = List.of(mock(DTPropuesta.class), mock(DTPropuesta.class));
        when(controllerMock.devolverTodasLasPropuestas()).thenReturn(lista);

        ListaDTPropuesta resultado = endpoint.devolverTodasLasPropuestas();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void devolverPropuestasPorEstado_DatosValidos_RetornaListaCorrectamente() {
        List<DTPropuesta> lista = List.of(mock(DTPropuesta.class), mock(DTPropuesta.class));
        when(controllerMock.devolverPropuestasPorEstado(DTEstadoPropuesta.INGRESADA)).thenReturn(lista);

        ListaDTPropuesta resultado = endpoint.devolverPropuestasPorEstado(DTEstadoPropuesta.INGRESADA);

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void getPropuestasIngresadas_DatosValidos_RetornaListaCorrectamente() {
        List<DTPropuesta> lista = List.of(mock(DTPropuesta.class), mock(DTPropuesta.class));
        when(controllerMock.getPropuestasIngresadas()).thenReturn(lista);

        ListaDTPropuesta resultado = endpoint.getPropuestasIngresadas();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void modificarPropuesta_DatosValidos_ModificaCorrectamente() throws Exception {
        ListaStrings listaTipos = new ListaStrings(List.of("ENTRADAS_GRATIS"));
        LocalDate fecha = LocalDate.of(2030, 12, 25);

        endpoint.modificarPropuesta("Batman", "Batman vs Superman", "Ciudad Gótica",
                fecha, 100.0, 1000.0, "imagen.png", listaTipos, "Cine");

        verify(controllerMock).modificarPropuesta("Batman", "Batman vs Superman", "Ciudad Gótica",
                fecha, 100.0, 1000.0, "imagen.png", List.of("ENTRADAS_GRATIS"), "Cine");
    }

    @Test
    void crearCategoria_DatosValidos_CreaCorrectamente() throws Exception {
        endpoint.crearCategoria("Tango", "Danza");

        verify(controllerMock).crearCategoria("Tango", "Danza");
    }

    @Test
    void devolverTodasLasCategorias_DatosValidos_RetornaListaCorrectamente() {
        List<DTCategoria> lista = List.of(mock(DTCategoria.class), mock(DTCategoria.class));
        when(controllerMock.devolverTodasLasCategorias()).thenReturn(lista);

        ListaDTCategoria resultado = endpoint.devolverTodasLasCategorias();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void registrarColaboracion_DatosValidos_CreaCorrectamente() throws Exception {
        endpoint.registrarColaboracion("Batman", "Joe", 500.0, "ENTRADAS_GRATIS");

        verify(controllerMock).registrarColaboracion("Batman", "Joe", 500.0, "ENTRADAS_GRATIS");
    }

    @Test
    void cancelarColaboracion_DatosValidos_CancelaCorrectamente() throws Exception {
        endpoint.cancelarColaboracion(1L);

        verify(controllerMock).cancelarColaboracion(1L);
    }

    @Test
    void obtenerTodasLasColaboraciones_DatosValidos_RetornaListaCorrectamente() {
        List<DTColaboracion> lista = List.of(mock(DTColaboracion.class), mock(DTColaboracion.class));
        when(controllerMock.obtenerTodasLasColaboraciones()).thenReturn(lista);

        ListaDTColaboracion resultado = endpoint.obtenerTodasLasColaboraciones();

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void evaluarPropuesta_DatosValidos_EvaluaCorrectamente() throws Exception {
        endpoint.evaluarPropuesta("Batman", true);

        verify(controllerMock).evaluarPropuesta("Batman", true);
    }

    @Test
    void publicarPropuesta_DatosValidos_PublicaCorrectamente() throws Exception {
        endpoint.publicarPropuesta("Batman");

        verify(controllerMock).publicarPropuesta("Batman");
    }

    @Test
    void agregarComentario_DatosValidos_CreaCorrectamente() throws Exception {
        endpoint.agregarComentario("Batman", "Joe", "Épico");

        verify(controllerMock).agregarComentario("Batman", "Joe", "Épico");
    }

    @Test
    void obtenerComentariosPropuesta_DatosValidos_RetornaListaCorrectamente() {
        List<DTComentario> lista = List.of(mock(DTComentario.class), mock(DTComentario.class));
        when(controllerMock.obtenerComentariosPropuesta("Batman")).thenReturn(lista);

        ListaDTComentario resultado = endpoint.obtenerComentariosPropuesta("Batman");

        assertEquals(lista, resultado.getLista());
    }

    @Test
    void extenderFinanciacion_DatosValidos_ExtiendeCorrectamente() {
        DTUsuario usuarioMock = mock(DTUsuario.class);
        endpoint.extenderFinanciacion(usuarioMock, "Batman");

        verify(controllerMock).extenderFinanciacion(usuarioMock, "Batman");
    }

    @Test
    void modificarHistorialYEstadoPropuesta_DatosValidos_ModificaCorrectamente() {
        DTPropuesta dtPropuesta = mock(DTPropuesta.class);
        endpoint.modificarHistorialYEstadoPropuesta(dtPropuesta);

        verify(controllerMock).modificarHistorialYEstadoPropuesta(dtPropuesta);
    }
}

