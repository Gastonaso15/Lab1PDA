package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropuestaTotalDto {

    @JsonProperty("propuesta")
    private PropuestaBasicDto propuesta;

    @JsonProperty("aporteTotal")
    private Double aporteTotal;

    public PropuestaTotalDto() {}

    public PropuestaTotalDto(PropuestaBasicDto propuesta, Double aporteTotal) {
        this.propuesta = propuesta;
        this.aporteTotal = aporteTotal;
    }

    public PropuestaBasicDto getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(PropuestaBasicDto propuesta) {
        this.propuesta = propuesta;
    }

    public Double getAporteTotal() {
        return aporteTotal;
    }

    public void setAporteTotal(Double aporteTotal) {
        this.aporteTotal = aporteTotal;
    }
}
