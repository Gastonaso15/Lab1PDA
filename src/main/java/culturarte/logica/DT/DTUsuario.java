package culturarte.logica.DT;

import java.time.LocalDate;

public class DTUsuario {
    private Long id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    //private List<DTSeguimiento> seguidores;
    private byte[] imagen;
    private LocalDate fechaNacimiento;

    public DTUsuario() {
        this.setNickname(new String());
        this.setNombre(new String());
        this.setApellido(new String());
        this.setCorreo(new String());
        this.setFechaNacimiento(null);
        this.setImagen(new byte[0]);
    }
    public DTUsuario(String nickname, String nombre, String apellido, String correo,LocalDate fechaNacimiento,byte [] imagen) {
        this.setNickname(nickname);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setCorreo(correo);
        this.setFechaNacimiento(fechaNacimiento);
        this.setImagen(imagen);
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

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento= fechaNacimiento;}

    //public List<Seguimiento> getSeguidores() { return seguidores; }
    //public void setSeguidores(List<Seguimiento> seguidores) { this.seguidores = seguidores; }
}

