package culturarte.logica.modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "colaboraciones")
public class Colaboracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Propuesta propuesta;
    @ManyToOne
    private Colaborador colaborador;
    private Double monto;
    @Enumerated(EnumType.STRING)
    private TipoRetorno tipoRetorno;
    private LocalDateTime fechaHora;
    private Boolean constanciaEmitida;
    @OneToOne(mappedBy = "colaboracion", cascade = CascadeType.ALL)
    private Pago pago;

    // Constructores
    public Colaboracion(){}
    public Colaboracion(Propuesta propuesta, Colaborador colaborador, Double monto, TipoRetorno tipoRetorno, LocalDateTime fechaHora) {
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
        this.constanciaEmitida = false; // Por defecto, no se ha emitido constancia
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Propuesta getPropuesta() { return propuesta; }
    public void setPropuesta(Propuesta propuesta) { this.propuesta = propuesta; }

    public Colaborador getColaborador() { return colaborador; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public TipoRetorno getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(TipoRetorno tipoRetorno) { this.tipoRetorno = tipoRetorno; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Boolean getConstanciaEmitida() { 
        return constanciaEmitida != null ? constanciaEmitida : false; 
    }
    public void setConstanciaEmitida(Boolean constanciaEmitida) { 
        this.constanciaEmitida = constanciaEmitida != null ? constanciaEmitida : false; 
    }
    
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
}
