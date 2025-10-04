package culturarte.logica.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario",discriminatorType = DiscriminatorType.STRING)
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nickname;
    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false)
    private String correo;
    private String password;
    private String imagen;
    private LocalDate fechaNacimiento;
    @OneToMany(mappedBy = "seguido", cascade = CascadeType.ALL)
    private List<Seguimiento> seguidores;
    @OneToMany(mappedBy = "seguidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> seguidos;
    @ManyToMany
    @JoinTable
    private List<Propuesta> propuestasFavoritas;

    // Constructores
    public Usuario() {}
    public Usuario(String nickname, String nombre, String apellido, String correo,String imagen,LocalDate fechaNacimiento) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
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

    public List<Seguimiento> getSeguidores() { return seguidores; }
    public void setSeguidores(List<Seguimiento> seguidores) { this.seguidores = seguidores; }

    public List<Seguimiento> getSeguidos() { return seguidos; }
    public void setSeguidos(List<Seguimiento> seguidos) { this.seguidos = seguidos; }

    public List<Propuesta> getPropuestasFavoritas() { return propuestasFavoritas; }
    public void setPropuestasFavoritas(List<Propuesta> propuestasFavoritas) {  this.propuestasFavoritas = propuestasFavoritas; }
}
