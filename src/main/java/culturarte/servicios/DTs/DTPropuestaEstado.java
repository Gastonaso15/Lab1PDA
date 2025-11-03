package culturarte.servicios.DTs;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DTPropuestaEstado {
    private Long id;
    private DTPropuesta propuesta;
    private DTEstadoPropuesta estado;
    private LocalDate fechaCambio;

    // Constructores

    public DTPropuestaEstado(DTEstadoPropuesta estado, LocalDate fechaCambio) {
        this.estado = estado;
        this.fechaCambio = fechaCambio;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DTPropuesta getPropuesta() { return propuesta; }
    public void setPropuesta(DTPropuesta propuesta) { this.propuesta = propuesta; }

    public DTEstadoPropuesta getEstado() { return estado; }
    public void setEstado(DTEstadoPropuesta estado) { this.estado = estado; }

    @XmlJavaTypeAdapter(AdaptadorLocalDate.class)
    public LocalDate getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDate fechaCambio) { this.fechaCambio = fechaCambio; }
}
