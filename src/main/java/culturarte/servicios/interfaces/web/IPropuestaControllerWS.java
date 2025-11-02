package culturarte.servicios.interfaces.web;

import culturarte.logica.endpoints.envoltorios.*;
import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.servicios.DTs.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.time.LocalDate;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IPropuestaControllerWS {

    @WebMethod
    DTPropuesta getDTPropuesta(String titulo);

    @WebMethod
    void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                        Double precioEntrada, Double montoNecesario, String imagen,
                        String proponente, String categoria, ListaStrings listaTipos) throws Exception;

    @WebMethod
    ListaDTPropuesta devolverTodasLasPropuestas();

    @WebMethod
    ListaDTPropuesta devolverPropuestasPorEstado(DTEstadoPropuesta estado);

    @WebMethod
    ListaDTPropuesta getPropuestasIngresadas();

    @WebMethod
    void modificarPropuesta(String titulo, String nuevaDescripcion, String nuevoLugar,
                            LocalDate nuevaFechaPrevista, Double nuevoPrecioEntrada,
                            Double nuevoMontoNecesario, String imagen,
                            ListaStrings listaTipos, String categoria) throws Exception;

    @WebMethod
    void crearCategoria(String nombre, String padre) throws Exception;

    @WebMethod
    ListaDTCategoria devolverTodasLasCategorias();

    @WebMethod
    void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto,
                               String tipoRetorno) throws Exception;

    @WebMethod
    void cancelarColaboracion(Long idColaboracion) throws Exception;

    @WebMethod
    ListaDTColaboracion obtenerTodasLasColaboraciones();

    @WebMethod
    void evaluarPropuesta(String titulo, boolean publicar) throws Exception;

    @WebMethod
    void publicarPropuesta(String titulo) throws Exception;

    @WebMethod
    void agregarComentario(String tituloPropuesta, String nicknameUsuario, String contenido) throws Exception;

    @WebMethod
    ListaDTComentario obtenerComentariosPropuesta(String tituloPropuesta);

    @WebMethod
    void extenderFinanciacion(DTUsuario usuario, String tituloPropuesta);

    @WebMethod
    DTPropuesta obtenerPropuestaPorTitulo(String titulo);
}
