package culturarte.logica.controlador;

import culturarte.logica.DT.DTPropuesta;

import java.time.LocalDate;
import java.util.List;

public interface IPropuestaController {
    public abstract void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, byte[] imagen, String proponente) throws Exception;
    public abstract List<DTPropuesta> devolverTodasLasPropuestas();
}
