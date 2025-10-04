package culturarte.logica.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Proponente")
@Table(name = "proponentes")
public class Proponente extends Usuario {

    private String direccion;
    @Column(length = 2000)
    private String bio;
    private String sitioWeb;
    @OneToMany(mappedBy = "proponente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Propuesta> propuestas = new ArrayList<>();

    // Constructores
    public Proponente() {
        super();
    }
    public Proponente(String nickname, String nombre, String apellido, String correo,String imagen, LocalDate fechaNacimiento, String direccion, String bio, String sitioWeb) {
        super(nickname, nombre, apellido, correo,imagen,fechaNacimiento);
        this.direccion = direccion;
        this.bio = bio;
        this.sitioWeb = sitioWeb;
    }

    // Funciones
    @Override
    public String toString() {
        return this.getNickname();
    }

    // Getters y setters adicionales
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    public List<Propuesta> getPropuestas() { return propuestas; }
    public void setPropuestas(List<Propuesta> propuestas) { this.propuestas = propuestas; }
}
