package culturarte.logica.DT;


public class DTProponente extends DTUsuario {
    private String direccion;
    private String bio;
    private String sitioWeb;

    public DTProponente() {
        this.setDireccion(new String());
        this.setBio(new String());
        this.setSitioWeb(new String());
    }
    public DTProponente(String direccion, String bio, String sitioWeb) {
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
