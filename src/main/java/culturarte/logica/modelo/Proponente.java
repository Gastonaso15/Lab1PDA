package culturarte.logica.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Proponente")
public class Proponente extends Usuario {

    private String direccion;
    private String bio;
    private String sitioWeb;

    // Constructores
    public Proponente() {
        super();
    }
    public Proponente(String nickname, String nombre, String apellido, String correo,byte[] imagen, LocalDate fechaNacimiento, String direccion, String bio, String sitioWeb) {
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
}
