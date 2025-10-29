package culturarte.servicios.interfaces;

import culturarte.servicios.DTs.DTCategoria;
import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTComentario;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.DTs.DTEstadoPropuesta;
import culturarte.servicios.DTs.DTUsuario;

import java.time.LocalDate;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IPropuestaController {
    DTPropuesta getDTPropuesta(String titulo);

    @WebMethod
    void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                                        Double precioEntrada, Double montoNecesario, String imagen,
                                        String proponente,String categoria, List<String> listaTipos) throws Exception;
    @WebMethod
    List<DTPropuesta> devolverTodasLasPropuestas();
    @WebMethod
    List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado);
    @WebMethod
    List<DTPropuesta> getPropuestasIngresadas();
    @WebMethod
    void modificarPropuesta(String titulo, String nuevaDescripcion, String nuevoLugar,
                                            LocalDate nuevaFechaPrevista, Double nuevoPrecioEntrada,
                                            Double nuevoMontoNecesario,
                                            String imagen, List<String> listaTipos, String categoria) throws Exception;
    @WebMethod
    void crearCategoria(String nombre, String padre) throws Exception;
    @WebMethod
    List<DTCategoria> devolverTodasLasCategorias();
    @WebMethod
    void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto,
                                               String tipoRetorno) throws Exception;
    @WebMethod
    void cancelarColaboracion(Long idColaboracion) throws Exception;
    @WebMethod
    List<DTColaboracion> obtenerTodasLasColaboraciones();
    @WebMethod
    void evaluarPropuesta(String titulo, boolean publicar) throws Exception;
    @WebMethod
    void publicarPropuesta(String titulo) throws Exception;
    @WebMethod
    void agregarComentario(String tituloPropuesta, String nicknameUsuario, String contenido) throws Exception;
    @WebMethod
    List<DTComentario> obtenerComentariosPropuesta(String tituloPropuesta);
    @WebMethod
    void extenderFinanciacion(DTUsuario usuario, String tituloPropuesta);
}
