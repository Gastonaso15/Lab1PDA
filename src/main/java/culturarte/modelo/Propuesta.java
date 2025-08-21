package culturarte.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propuestas")
public class Propuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    private String descripcion;
    private String lugar;
    private LocalDate fechaPrevista;
    private Double precioEntrada;
    private Double montoNecesario;
    private LocalDate fechaPublicacion;
    @Lob // indica que es un tipo grande de datos (BLOB)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "categoria_id") // referencia la columna en la tabla propuesta
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "proponente_id")
    private Proponente proponente;

    @Enumerated(EnumType.STRING)
    private EstadoPropuesta estadoActual;

    @OneToMany(mappedBy = "propuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropuestaEstado> historial = new ArrayList<>();

    @OneToMany(mappedBy = "propuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboracion> colaboraciones = new ArrayList<>();

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

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Proponente getProponente() { return proponente; }
    public void setProponente(Proponente proponente) { this.proponente = proponente; }

    public EstadoPropuesta getEstadoActual() { return estadoActual; }
    public void setEstadoActual(EstadoPropuesta estadoActual) { this.estadoActual = estadoActual; }

    public List<PropuestaEstado> getHistorial() { return historial; }
    public void setHistorial(List<PropuestaEstado> historial) { this.historial = historial; }

    public List<Colaboracion> getColaboraciones() { return colaboraciones; }
    public void setColaboraciones(List<Colaboracion> colaboraciones) { this.colaboraciones = colaboraciones; }

    @Override
    public String toString() {
        return titulo;
    }
}
