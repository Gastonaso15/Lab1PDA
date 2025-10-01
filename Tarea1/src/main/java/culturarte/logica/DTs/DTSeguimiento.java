package culturarte.logica.DTs;

public class DTSeguimiento {
    private Long id;
    private DTUsuario seguido;
    private DTUsuario seguidor;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DTUsuario getSeguido() { return seguido; }
    public void setSeguido(DTUsuario seguido) { this.seguido = seguido; }

    public DTUsuario getSeguidor() { return seguidor; }
    public void setSeguidor(DTUsuario seguidor) { this.seguidor = seguidor; }
}
