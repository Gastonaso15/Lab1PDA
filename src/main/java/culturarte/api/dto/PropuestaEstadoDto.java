package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class PropuestaEstadoDto {

    @JsonProperty("fechaCambio")
    private LocalDateTime fechaCambio;

    @JsonProperty("estado")
    private String estado;

    public PropuestaEstadoDto() {}

    public PropuestaEstadoDto(LocalDateTime fechaCambio, String estado) {
        this.fechaCambio = fechaCambio;
        this.estado = estado;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
