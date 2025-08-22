package culturarte.logica.modelo;

import jakarta.persistence.Entity;

@Entity
public class Colaborador extends Usuario {

    // Constructores
    public Colaborador() {
        super();
    }
    public Colaborador(String nickname, String nombre, String apellido, String correo) {
        super(nickname, nombre, apellido, correo);
    }


}
