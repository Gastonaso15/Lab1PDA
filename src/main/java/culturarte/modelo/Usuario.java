package culturarte.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;

    // Relación con Seguimiento: usuarios que sigo
    @OneToMany(mappedBy = "seguido", cascade = CascadeType.ALL)
    private List<Seguimiento> seguidores;

    // Constructor vacío obligatorio para JPA
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nickname, String nombre, String apellido, String correo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
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
