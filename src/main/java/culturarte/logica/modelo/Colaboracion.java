package culturarte.logica.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    private TipoRetorno tipoRetorno; // ENTRADAS | PORCENTAJE
    private LocalDateTime fechaHora;

    public Colaboracion(){}
    public Colaboracion(Propuesta propuesta, Colaborador colaborador, Double monto, TipoRetorno tipoRetorno, LocalDateTime fechaHora) {
        this.propuesta = propuesta;
        this.colaborador = colaborador;
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.fechaHora = fechaHora;
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
}
