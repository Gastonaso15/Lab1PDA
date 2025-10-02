package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public class PropuestaPutDto {

    @JsonProperty("titulo")
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("lugar")
    private String lugar;

    @JsonProperty("fechaPrevista")
    private LocalDate fechaPrevista;

    @JsonProperty("precioEntrada")
    private Double precioEntrada;

    @JsonProperty("montoNecesario")
    private Double montoNecesario;

    @JsonProperty("imagen")
    private String imagen;

    @JsonProperty("categoria")
    private IdDto categoria;

    @JsonProperty("proponente")
    private IdDto proponente;

    @JsonProperty("estadoActual")
    private String estadoActual;

    @JsonProperty("tiposRetorno")
    private List<TipoRetornoDto> tiposRetorno;

    public PropuestaPutDto() {}

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }

    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public Double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(Double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public Double getMontoNecesario() {
        return montoNecesario;
    }

    public void setMontoNecesario(Double montoNecesario) {
        this.montoNecesario = montoNecesario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public IdDto getCategoria() {
        return categoria;
    }

    public void setCategoria(IdDto categoria) {
        this.categoria = categoria;
    }

    public IdDto getProponente() {
        return proponente;
    }

    public void setProponente(IdDto proponente) {
        this.proponente = proponente;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public List<TipoRetornoDto> getTiposRetorno() {
        return tiposRetorno;
    }

    public void setTiposRetorno(List<TipoRetornoDto> tiposRetorno) {
        this.tiposRetorno = tiposRetorno;
    }
}
