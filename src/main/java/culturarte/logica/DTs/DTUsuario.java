package culturarte.logica.DTs;

import java.time.LocalDate;
import java.util.List;

public class DTUsuario {
    private Long id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private List<DTSeguimiento> seguidores;
    private String imagen;
    private LocalDate fechaNacimiento;

    // Constructores
    public DTUsuario() {
        this.setNickname("");
        this.setNombre("");
        this.setApellido("");
        this.setCorreo("");
        this.setFechaNacimiento(null);
        this.setImagen("");
    }
    public DTUsuario(String nickname, String nombre, String apellido, String correo,LocalDate fechaNacimiento,String imagen) {
        this.setNickname(nickname);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setCorreo(correo);
        this.setFechaNacimiento(fechaNacimiento);
        this.setImagen(imagen);
    }

    public DTUsuario(String nickname) {
        setNickname(nickname);
    }

    public DTUsuario(String nickname, String nombre, String apellido) {
        this.setNickname(nickname);
        this.setNombre(nombre);
        this.setApellido(apellido);
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento= fechaNacimiento;}

    public List<DTSeguimiento> getSeguidores() { return seguidores; }
    public void setSeguidores(List<DTSeguimiento> seguidores) { this.seguidores = seguidores; }
}

