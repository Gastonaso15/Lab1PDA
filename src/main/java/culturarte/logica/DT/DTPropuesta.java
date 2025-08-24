package culturarte.logica.DT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DTPropuesta {
    private Long id;
    private String titulo;
    private String descripcion;
    private String lugar;
    private LocalDate fechaPrevista;
    private Double precioEntrada;
    private Double montoNecesario;
    private LocalDate fechaPublicacion;
    private byte[] imagen;
    private DTCategoria categoria;
    private DTProponente proponente;
    private DTEstadoPropuesta estadoActual;
    //private List<DTPropuestaEstado> historial = new ArrayList<>();
    private List<DTColaboracion> colaboraciones = new ArrayList<>();


    public DTPropuesta() {
        this.setTitulo(new String());
        this.setDescripcion(new String());
        this.setLugar(new String());
        this.setFechaPrevista(null);
        this.setPrecioEntrada(0.0);
        this.setMontoNecesario(0.0);
        this.setImagen(new byte[0]);
        this.setDTProponente(new DTProponente());
    }

    // Funciones
    @Override
    public String toString() {
        return titulo;
    }

    public DTPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,Double precioEnetrada, Double montoNecesario, byte[] imagen,DTProponente proponente) {
        this.setTitulo(titulo);
        this.setDescripcion(descripcion);
        this.setLugar(lugar);
        this.setFechaPrevista(fechaPrevista);
        this.setPrecioEntrada(precioEntrada);
        this.setMontoNecesario(montoNecesario);
        this.setImagen(imagen);
        this.setDTProponente(proponente);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public LocalDate getFechaPrevista() { return fechaPrevista; }
    public void setFechaPrevista(LocalDate fechaPrevista) { this.fechaPrevista = fechaPrevista; }

    public Double getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(Double precioEntrada) { this.precioEntrada = precioEntrada; }

    public Double getMontoNecesario() { return montoNecesario; }
    public void setMontoNecesario(Double montoNecesario) { this.montoNecesario = montoNecesario; }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    //public Categoria getCategoria() { return categoria; }
    //public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public DTProponente getDTProponente() { return proponente; }
    public void setDTProponente(DTProponente proponente) { this.proponente = proponente; }

    public DTEstadoPropuesta getEstadoActual() { return estadoActual; }
    public void setEstadoActual(DTEstadoPropuesta estadoActual) { this.estadoActual = estadoActual; }

    //public List<PropuestaEstado> getHistorial() { return historial; }
    //public void setHistorial(List<PropuestaEstado> historial) { this.historial = historial; }

    public List<DTColaboracion> getColaboraciones() { return colaboraciones; }
    public void setColaboraciones(List<DTColaboracion> colaboraciones) { this.colaboraciones = colaboraciones; }

}
