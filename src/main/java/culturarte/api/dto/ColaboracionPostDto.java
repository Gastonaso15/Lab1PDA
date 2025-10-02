package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ColaboracionPostDto {

    @JsonProperty("idPropuesta")
    @NotNull(message = "La propuesta es obligatoria")
    private IdDto idPropuesta;

    @JsonProperty("colaborador")
    @NotNull(message = "El colaborador es obligatorio")
    private PropuestaCategoriaDto colaborador;

    @JsonProperty("monto")
    @NotNull(message = "El monto es obligatorio")
    private Double monto;

    @JsonProperty("tipoRetorno")
    private TipoRetornoDto tipoRetorno;

    public ColaboracionPostDto() {}

    // Getters y Setters
    public IdDto getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(IdDto idPropuesta) {
        this.idPropuesta = idPropuesta;
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
}
