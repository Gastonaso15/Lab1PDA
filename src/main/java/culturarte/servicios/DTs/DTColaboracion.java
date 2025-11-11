package culturarte.servicios.DTs;

import java.time.LocalDateTime;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DTColaboracion{
    private Long id;
    private DTPropuesta propuesta;
    private DTColaborador colaborador;
    private Double monto;
    private DTTipoRetorno tipoRetorno;
    private LocalDateTime fechaHora;
    private Boolean constanciaEmitida;

    // Constructores
    public DTColaboracion(){}
    public DTColaboracion(DTPropuesta propuesta, DTColaborador colaborador, Double monto, DTTipoRetorno tipoRetorno, LocalDateTime fechaHora) {
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
        this.constanciaEmitida = false;
    }
    public DTColaboracion(DTColaborador colaborador, Double monto) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.constanciaEmitida = false;
    }
    public DTColaboracion(Long id,DTPropuesta propuesta, DTColaborador colaborador, Double monto, DTTipoRetorno tipoRetorno, LocalDateTime fechaHora) {
        this.id=id;
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
        this.constanciaEmitida = false;
    }
    public DTColaboracion(Long id,DTPropuesta propuesta, DTColaborador colaborador, Double monto, DTTipoRetorno tipoRetorno, LocalDateTime fechaHora, Boolean constanciaEmitida) {
        this.id=id;
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
        this.constanciaEmitida = constanciaEmitida != null ? constanciaEmitida : false;
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

    @XmlJavaTypeAdapter(AdaptadorLocalDateTime.class)
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Boolean getConstanciaEmitida() { 
        return constanciaEmitida != null ? constanciaEmitida : false; 
    }
    public void setConstanciaEmitida(Boolean constanciaEmitida) { 
        this.constanciaEmitida = constanciaEmitida != null ? constanciaEmitida : false; 
    }

}
