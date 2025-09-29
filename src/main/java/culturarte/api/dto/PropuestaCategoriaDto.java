package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropuestaCategoriaDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String nombre;

    public PropuestaCategoriaDto() {}

    public PropuestaCategoriaDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
