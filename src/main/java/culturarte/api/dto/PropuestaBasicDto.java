package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropuestaBasicDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("titulo")
    private String titulo;

    public PropuestaBasicDto() {}

    public PropuestaBasicDto(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
