package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ColaboracionDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("propuesta")
    private PropuestaDto propuesta;

    @JsonProperty("colaborador")
    @NotNull(message = "El colaborador es obligatorio")
    private PropuestaCategoriaDto colaborador;

    @JsonProperty("monto")
    @NotNull(message = "El monto es obligatorio")
    private Double monto;

    @JsonProperty("tipoRetorno")
    private TipoRetornoDto tipoRetorno;

    @JsonProperty("fechaColaboracion")
    private LocalDate fechaColaboracion;

    public ColaboracionDto() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropuestaDto getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(PropuestaDto propuesta) {
        this.propuesta = propuesta;
    }

    public PropuestaCategoriaDto getColaborador() {
        return colaborador;
    }

    public void setColaborador(PropuestaCategoriaDto colaborador) {
        this.colaborador = colaborador;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public TipoRetornoDto getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(TipoRetornoDto tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public LocalDate getFechaColaboracion() {
        return fechaColaboracion;
    }

    public void setFechaColaboracion(LocalDate fechaColaboracion) {
        this.fechaColaboracion = fechaColaboracion;
    }
}
