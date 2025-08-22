package culturarte.logica.DT;

import java.time.LocalDateTime;

public class DTColaboracion{
    private Long id;
    private DTPropuesta propuesta;
    private DTColaborador colaborador;
    private Double monto;
    private DTColaboracion.TipoRetorno tipoRetorno; // ENTRADAS | PORCENTAJE
    private LocalDateTime fechaHora;
    public enum TipoRetorno { ENTRADAS, PORCENTAJE }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DTPropuesta getPropuesta() { return propuesta; }
    public void setPropuesta(DTPropuesta propuesta) { this.propuesta = propuesta; }

    public DTColaborador getColaborador() { return colaborador; }
    public void setColaborador(DTColaborador colaborador) { this.colaborador = colaborador; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public DTColaboracion.TipoRetorno getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(DTColaboracion.TipoRetorno tipoRetorno) { this.tipoRetorno = tipoRetorno; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

}
