package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdDto {

    @JsonProperty("id")
    private Long id;

    public IdDto() {}

    public IdDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
