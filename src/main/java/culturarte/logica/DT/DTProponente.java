package culturarte.logica.DT;


import java.time.LocalDate;

public class DTProponente extends DTUsuario {
    private String direccion;
    private String bio;
    private String sitioWeb;

    public DTProponente() {
        super();
        this.setDireccion(new String());
        this.setBio(new String());
        this.setSitioWeb(new String());
    }
    public DTProponente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, byte [] imagen,String direccion, String bio, String sitioWeb) {
        super(nickname, nombre, apellido, correo,fechaNacimiento,imagen);
        this.setDireccion(direccion);
        this.setBio(bio);
        this.setSitioWeb(sitioWeb);
    }

    // Getters y setters adicionales
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

}
