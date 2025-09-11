package culturarte.logica.DTs;

import java.time.LocalDateTime;

public class DTColaboracion{
    private Long id;
    private DTPropuesta propuesta;
    private DTColaborador colaborador;
    private Double monto;
    private DTTipoRetorno tipoRetorno;
    private LocalDateTime fechaHora;

    // Constructores
    public DTColaboracion(){}
    public DTColaboracion(DTPropuesta propuesta, DTColaborador colaborador, Double monto, DTTipoRetorno tipoRetorno, LocalDateTime fechaHora) {
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
    }
    public DTColaboracion(DTColaborador colaborador, Double monto) {
        this.colaborador = colaborador;
        this.monto = monto;
    }
    public DTColaboracion(Long id,DTPropuesta propuesta, DTColaborador colaborador, Double monto, DTTipoRetorno tipoRetorno, LocalDateTime fechaHora) {
        this.id=id;
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
    }


    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DTPropuesta getPropuesta() { return propuesta; }
    public void setPropuesta(DTPropuesta propuesta) { this.propuesta = propuesta; }

    public DTColaborador getColaborador() { return colaborador; }
    public void setColaborador(DTColaborador colaborador) { this.colaborador = colaborador; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public DTTipoRetorno getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(DTTipoRetorno tipoRetorno) { this.tipoRetorno = tipoRetorno; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

}
