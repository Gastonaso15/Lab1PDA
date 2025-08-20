package culturarte.modelo;

import jakarta.persistence.Entity;

@Entity
public class Colaborador extends Usuario {

    // Constructor vacío necesario para JPA
    public Colaborador() {
        super();
    }

    // Constructor con 4 parámetros que llama al constructor de Usuario
    public Colaborador(String nickname, String nombre, String apellido, String correo) {
        super(nickname, nombre, apellido, correo);
    }

    // Por ahora no tiene campos adicionales
}
