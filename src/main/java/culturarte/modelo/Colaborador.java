package culturarte.modelo;

import jakarta.persistence.Entity;

@Entity
public class Colaborador extends Usuario {
    public Colaborador() {}
    public Colaborador(String nick, String nombre, String apellido, String correo, java.time.LocalDate nacimiento, String imagen) {
        super(nick, nombre, apellido, correo, nacimiento, imagen);
    }
}
