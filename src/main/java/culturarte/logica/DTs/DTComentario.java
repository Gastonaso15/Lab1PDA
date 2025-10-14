package culturarte.logica.DTs;

import java.time.LocalDateTime;

public class DTComentario {
    private Long id;
    private String contenido;
    private LocalDateTime fechaHora;
    private DTUsuario usuario;
    private Long propuestaId;

    // Constructores
    public DTComentario() {
        this.setContenido("");
        this.setFechaHora(null);
        this.setUsuario(new DTUsuario());
        this.setPropuestaId(null);
    }

    public DTComentario(String contenido, DTUsuario usuario, Long propuestaId) {
        this.setContenido(contenido);
        this.setUsuario(usuario);
        this.setPropuestaId(propuestaId);
        this.setFechaHora(LocalDateTime.now());
    }

    public DTComentario(String contenido, LocalDateTime fechaHora, DTUsuario usuario, Long propuestaId) {
        this.setContenido(contenido);
        this.setFechaHora(fechaHora);
        this.setUsuario(usuario);
        this.setPropuestaId(propuestaId);
    }

    public DTComentario(culturarte.logica.modelos.Comentario comentario) {
        this.setId(comentario.getId());
        this.setContenido(comentario.getContenido());
        this.setFechaHora(comentario.getFechaHora());
        
        if (comentario.getUsuario() != null) {
            this.setUsuario(new DTUsuario(
                comentario.getUsuario().getNickname(),
                comentario.getUsuario().getNombre(),
                comentario.getUsuario().getApellido(),
                comentario.getUsuario().getCorreo(),
                comentario.getUsuario().getImagen()
            ));
        }
        
        if (comentario.getPropuesta() != null) {
            this.setPropuestaId(comentario.getPropuesta().getId());
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public DTUsuario getUsuario() { return usuario; }
    public void setUsuario(DTUsuario usuario) { this.usuario = usuario; }

    public Long getPropuestaId() { return propuestaId; }
    public void setPropuestaId(Long propuestaId) { this.propuestaId = propuestaId; }

    public String getUsuarioNickname() {
        return usuario != null ? usuario.getNickname() : "";
    }

    public String getUsuarioNombreCompleto() {
        if (usuario != null) {
            return usuario.getNombre() + " " + usuario.getApellido();
        }
        return "";
    }

    @Override
    public String toString() {
        return contenido != null ? contenido.substring(0, Math.min(contenido.length(), 50)) + "..." : "";
    }
}
