package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoRetornoDto {

    @JsonProperty("tipo")
    private String tipo;

    public TipoRetornoDto() {}

    public TipoRetornoDto(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
