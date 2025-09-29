package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalAportesDto {

    @JsonProperty("total")
    private Double total;

    public TotalAportesDto() {}

    public TotalAportesDto(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
