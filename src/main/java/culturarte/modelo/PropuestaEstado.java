package culturarte.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PropuestaEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Propuesta propuesta;
    @Enumerated(EnumType.STRING)
    private EstadoPropuesta estado;
    private LocalDateTime fechaCambio;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Propuesta getPropuesta() { return propuesta; }
    public void setPropuesta(Propuesta propuesta) { this.propuesta = propuesta; }
    public EstadoPropuesta getEstado() { return estado; }
    public void setEstado(EstadoPropuesta estado) { this.estado = estado; }
    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }
}
