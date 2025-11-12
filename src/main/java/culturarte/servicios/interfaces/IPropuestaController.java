package culturarte.servicios.interfaces;

import culturarte.servicios.DTs.DTCategoria;
import culturarte.servicios.DTs.DTColaboracion;
import culturarte.servicios.DTs.DTComentario;
import culturarte.servicios.DTs.DTPropuesta;
import culturarte.servicios.DTs.DTEstadoPropuesta;
import culturarte.servicios.DTs.DTUsuario;
import culturarte.servicios.DTs.DTPago;

import java.time.LocalDate;
import java.util.List;

public interface IPropuestaController {
    DTPropuesta getDTPropuesta(String titulo);

    void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                                        Double precioEntrada, Double montoNecesario, String imagen,
                                        String proponente,String categoria, List<String> listaTipos) throws Exception;
    List<DTPropuesta> devolverTodasLasPropuestas();
    List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado);
    List<DTPropuesta> getPropuestasIngresadas();
    void modificarPropuesta(String titulo, String nuevaDescripcion, String nuevoLugar,
                                            LocalDate nuevaFechaPrevista, Double nuevoPrecioEntrada,
                                            Double nuevoMontoNecesario,
                                            String imagen, List<String> listaTipos, String categoria) throws Exception;
    void crearCategoria(String nombre, String padre) throws Exception;
    List<DTCategoria> devolverTodasLasCategorias();
    void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto,
                                               String tipoRetorno) throws Exception;
    void cancelarColaboracion(Long idColaboracion) throws Exception;
    void marcarConstanciaEmitida(Long idColaboracion) throws Exception;
    List<DTColaboracion> obtenerTodasLasColaboraciones();
    void evaluarPropuesta(String titulo, boolean publicar) throws Exception;
    void publicarPropuesta(String titulo) throws Exception;
    void agregarComentario(String tituloPropuesta, String nicknameUsuario, String contenido) throws Exception;
    List<DTComentario> obtenerComentariosPropuesta(String tituloPropuesta);
    void extenderFinanciacion(DTUsuario usuario, String tituloPropuesta);
    void modificarHistorialYEstadoPropuesta(DTPropuesta propuesta);
    List<DTColaboracion> devolverColaboracionesSinPago(String nicknameColaborador);
    void registrarPago(Long idColaboracion, DTPago dtPago) throws Exception;
}
