package culturarte.logica.DTs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DTProponente extends DTUsuario {
    private String direccion;
    private String bio;
    private String sitioWeb;
    private List<DTPropuesta> propuestas = new ArrayList<>();

    // Constructores
    public DTProponente() {
        super();
        this.setDireccion("");
        this.setBio("");
        this.setSitioWeb("");
    }
    public DTProponente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String imagen,String direccion, String bio, String sitioWeb) {
        super(nickname, nombre, apellido, correo,fechaNacimiento,imagen);
        this.setDireccion(direccion);
        this.setBio(bio);
        this.setSitioWeb(sitioWeb);
    }
    public DTProponente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String imagen,String direccion, String bio, String sitioWeb,List<DTPropuesta> propuestas) {
        super(nickname, nombre, apellido, correo,fechaNacimiento,imagen);
        this.setDireccion(direccion);
        this.setBio(bio);
        this.setSitioWeb(sitioWeb);
        this.setPropuestas(propuestas);
    }

    public DTProponente(String nickname, String nombre, String apellido) {
        super(nickname, nombre, apellido);
    }

    // Getters y setters adicionales
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    public List<DTPropuesta> getPropuestas() { return propuestas; }
    public void setPropuestas(List<DTPropuesta> propuestas) { this.propuestas = propuestas; }

}
