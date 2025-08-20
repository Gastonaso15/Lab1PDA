// Proponente.java
package culturarte.modelo;

import jakarta.persistence.Entity;

@Entity
public class Proponente extends Usuario {

    private String direccion;
    private String bio;
    private String sitioWeb;

    // Constructor vacío (necesario para JPA)
    public Proponente() {
        super();
    }

    // Constructor con parámetros
    public Proponente(String nickname, String nombre, String apellido, String correo) {
        super(nickname, nombre, apellido, correo);
    }

    // Getters y setters adicionales
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
}
