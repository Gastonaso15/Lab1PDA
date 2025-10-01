package culturarte.logica.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "propuestas_estados")
public class PropuestaEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Propuesta propuesta;
    @Enumerated(EnumType.STRING)
    private EstadoPropuesta estado;
    private LocalDate fechaCambio;

    // Constructores
    public PropuestaEstado() {}

    public PropuestaEstado(Propuesta propuesta, EstadoPropuesta estado, LocalDate fechaCambio) {
        this.propuesta = propuesta;
        this.estado = estado;
        this.fechaCambio = fechaCambio;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Propuesta getPropuesta() { return propuesta; }
    public void setPropuesta(Propuesta propuesta) { this.propuesta = propuesta; }

    public EstadoPropuesta getEstado() { return estado; }
    public void setEstado(EstadoPropuesta estado) { this.estado = estado; }

    public LocalDate getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDate fechaCambio) { this.fechaCambio = fechaCambio; }
}
