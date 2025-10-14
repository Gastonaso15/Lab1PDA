package culturarte.logica.modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 1000, nullable = false)
    private String contenido;
    
    private LocalDateTime fechaHora;
    
    @ManyToOne
    @JoinColumn(name = "propuesta_id", nullable = false)
    private Propuesta propuesta;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Comentario() {}
    
    public Comentario(String contenido, Propuesta propuesta, Usuario usuario) {
        this.contenido = contenido;
        this.propuesta = propuesta;
        this.usuario = usuario;
        this.fechaHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Propuesta getPropuesta() { return propuesta; }
    public void setPropuesta(Propuesta propuesta) { this.propuesta = propuesta; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
