package culturarte.logica.DT;

import culturarte.logica.modelo.Seguimiento;

import java.util.List;

public class DTUsuario {
    private Long id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private List<Seguimiento> seguidores;

    public DTUsuario() {
        this.setNickname(new String());
        this.setNombre(new String());
        this.setApellido(new String());
        this.setCorreo(new String());
    }
    public DTUsuario(String nickname, String nombre, String apellido, String correo) {
        this.setNickname(nickname);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setCorreo(correo);
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

    public List<Seguimiento> getSeguidores() { return seguidores; }
    public void setSeguidores(List<Seguimiento> seguidores) { this.seguidores = seguidores; }
}

