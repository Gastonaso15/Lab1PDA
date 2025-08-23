package culturarte.logica.DT;

import java.time.LocalDate;

public class DTColaborador extends DTUsuario{

    public DTColaborador() {
        super();
    }

    public DTColaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, byte [] imagen) {
        super(nickname, nombre, apellido, correo,fechaNacimiento,imagen);
    }

}
