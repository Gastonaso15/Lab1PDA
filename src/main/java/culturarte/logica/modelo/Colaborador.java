package culturarte.logica.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Colaborador")
public class Colaborador extends Usuario {

    // Constructores
    public Colaborador() {
        super();
    }
    public Colaborador(String nickname, String nombre, String apellido, String correo,byte[] imagen, LocalDate fechaNacimiento) {
        super(nickname, nombre, apellido, correo,imagen,fechaNacimiento);
    }


}
