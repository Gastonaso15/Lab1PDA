package culturarte.logica.modelos;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Colaborador")
@Table(name = "colaboradores")
public class Colaborador extends Usuario {

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Colaboracion> colaboraciones = new ArrayList<>();

    // Constructores
    public Colaborador() {
        super();
    }
    public Colaborador(String nickname, String nombre, String apellido, String correo,String imagen, LocalDate fechaNacimiento) {
        super(nickname, nombre, apellido, correo,imagen,fechaNacimiento);
    }

    // Getters y Setters
    public List<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }
    public void setColaboraciones(List<Colaboracion> colaboraciones) {
        this.colaboraciones = colaboraciones;
    }
}
