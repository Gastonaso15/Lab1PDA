package culturarte.logica.controlador;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTEstadoPropuesta;
import java.time.LocalDate;
import java.util.List;

public interface IPropuestaController {
    public abstract void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, byte[] imagen, String proponente,String categoria, List<String> listaTipos) throws Exception;
    public abstract List<DTPropuesta> devolverTodasLasPropuestas();
    public abstract List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado);
}
