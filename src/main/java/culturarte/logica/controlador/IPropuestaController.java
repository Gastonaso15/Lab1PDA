package culturarte.logica.controlador;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.modelo.Categoria;

import java.time.LocalDate;
import java.util.List;

public interface IPropuestaController {
    public abstract void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, byte[] imagen, String proponente,String categoria, List<String> listaTipos) throws Exception;
    public abstract List<DTPropuesta> devolverTodasLasPropuestas();
    public abstract List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado);
    public abstract void modificarPropuesta(String titulo, String nuevaDescripcion, String nuevoLugar,
                                            LocalDate nuevaFechaPrevista, Double nuevoPrecioEntrada,
                                            Double nuevoMontoNecesario,
                                            byte[] imagen, List<String> listaTipos, String categoria) throws Exception;
    public abstract List<String> listarNombreCategorias();
    public abstract void crearCategoria(String nombre, String padre) throws Exception;
    public abstract Categoria obtenerCategoriaPorNombre(String nombre);
    public abstract List<DTCategoria> listarDTCategorias();
    public abstract void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto, String tipoRetorno) throws Exception;
}
