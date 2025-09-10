package culturarte.logica.modelo;

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

    // Constructores
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
    public Colaborador getColaborador() { return colaborador; }
    public Propuesta getPropuesta() { return propuesta; }
    public Double getMonto() { return monto; }
    public TipoRetorno getTipoRetorno() { return tipoRetorno; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    public void setPropuesta(Propuesta propuesta) { this.propuesta = propuesta; }
    public void setId(Long id) { this.id = id; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }
    public void setMonto(Double monto) { this.monto = monto; }
    public void setTipoRetorno(TipoRetorno tipoRetorno) { this.tipoRetorno = tipoRetorno; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
