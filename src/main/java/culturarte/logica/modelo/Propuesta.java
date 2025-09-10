package culturarte.logica.modelo;

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
        @Column(length = 2000)
        private String descripcion;
        private String lugar;
        private LocalDate fechaPrevista;
        private Double precioEntrada;
        private Double montoNecesario;
        private LocalDate fechaPublicacion;
        private String imagen;
        @ManyToOne
        @JoinColumn(name = "categoria_id")
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
        @ElementCollection(targetClass = TipoRetorno.class)
        @Enumerated(EnumType.STRING)
        private List<TipoRetorno> tiposRetorno = new ArrayList<>();

        // Constructores
        public Propuesta() {}
        public Propuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, String imagen, Proponente proponente,Categoria categoria, List<TipoRetorno> tiposRetorno) {
            this.setTitulo(titulo);
            this.setDescripcion(descripcion);
            this.setLugar(lugar);
            this.setFechaPrevista(fechaPrevista);
            this.setPrecioEntrada(precioEntrada);
            this.setMontoNecesario(montoNecesario);
            this.setImagen(imagen);
            this.setProponente(proponente);
            this.setCategoria(categoria);
            this.tiposRetorno = tiposRetorno;
            this.estadoActual = EstadoPropuesta.INGRESADA;
            PropuestaEstado inicial = new PropuestaEstado(this, EstadoPropuesta.INGRESADA, LocalDate.now());
            this.historial.add(inicial);
        }

        // Funciones
        @Override
        public String toString() {
            return titulo;
        }

        public void modificarDatos(String descripcion, String lugar, LocalDate fechaPrevista,
                               Double precioEntrada, Double montoNecesario, LocalDate fechaPublicacion) {
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fechaPrevista = fechaPrevista;
        this.precioEntrada = precioEntrada;
        this.montoNecesario = montoNecesario;
        this.fechaPublicacion = fechaPublicacion;
        }

        // Getters y Setters
        public Long getId() { return id; }
        public String getTitulo() { return titulo; }
        public String getDescripcion() { return descripcion; }
        public String getLugar() { return lugar; }
        public Double getPrecioEntrada() { return precioEntrada; }
        public LocalDate getFechaPrevista() { return fechaPrevista; }
        public Double getMontoNecesario() { return montoNecesario; }
        public LocalDate getFechaPublicacion() { return fechaPublicacion; }
        public String getImagen() { return imagen; }
        public Categoria getCategoria() { return categoria; }
        public Proponente getProponente() { return proponente; }
        public EstadoPropuesta getEstadoActual() { return estadoActual; }
        public List<Colaboracion> getColaboraciones() { return colaboraciones; }
        public List<PropuestaEstado> getHistorial() { return historial; }
        public List<TipoRetorno> getTiposRetorno() { return tiposRetorno; }

        public void setId(Long id) { this.id = id; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setLugar(String lugar) { this.lugar = lugar; }
        public void setFechaPrevista(LocalDate fechaPrevista) { this.fechaPrevista = fechaPrevista; }
        public void setPrecioEntrada(Double precioEntrada) { this.precioEntrada = precioEntrada; }
        public void setMontoNecesario(Double montoNecesario) { this.montoNecesario = montoNecesario; }
        public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
        public void setImagen(String imagen) { this.imagen = imagen; }
        public void setCategoria(Categoria categoria) { this.categoria = categoria; }
        public void setProponente(Proponente proponente) { this.proponente = proponente; }
        public void setEstadoActual(EstadoPropuesta estadoActual) { this.estadoActual = estadoActual; }
        public void setHistorial(List<PropuestaEstado> historial) { this.historial = historial; }
        public void setColaboraciones(List<Colaboracion> colaboraciones) { this.colaboraciones = colaboraciones; }
        public void setTiposRetorno(List<TipoRetorno> tiposRetorno) { this.tiposRetorno = tiposRetorno; }

}
