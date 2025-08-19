package culturarte.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Proponente extends Usuario {
    private String direccion;
    private String biografia;
    private String web;
    
    @OneToMany(mappedBy = "proponente", cascade = CascadeType.ALL)
    private List<Propuesta> propuestas;

    public Proponente() {}
    
    public Proponente(String nick, String nombre, String apellido, String correo, LocalDate nacimiento,
                      String imagen, String direccion, String biografia, String web) {
        super(nick, nombre, apellido, correo, nacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.web = web;
    }
    
    // Getters y Setters
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    
    public String getWeb() { return web; }
    public void setWeb(String web) { this.web = web; }
    
    public List<Propuesta> getPropuestas() { return propuestas; }
    public void setPropuestas(List<Propuesta> propuestas) { this.propuestas = propuestas; }
}
