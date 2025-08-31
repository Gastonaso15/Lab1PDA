package culturarte.logica.DT;

import java.time.LocalDate;
import java.util.List;

public class DTColaborador extends DTUsuario{
    private List<DTColaboracion> colaboraciones;

    // Constructores
    public DTColaborador(String nickname) {
        super(nickname);
    }
    public DTColaborador() {
        super();
    }

    public DTColaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, byte [] imagen) {
        super(nickname, nombre, apellido, correo,fechaNacimiento,imagen);
    }

    public DTColaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, byte [] imagen, List<DTColaboracion> colaboraciones) {
        super(nickname, nombre, apellido, correo,fechaNacimiento,imagen);
        this.colaboraciones = colaboraciones;
    }

    // Getters y Setters
    public List<DTColaboracion> getColaboraciones() {
        return colaboraciones;
    }
    public void setColaboraciones(List<DTColaboracion> colaboraciones) {
        this.colaboraciones = colaboraciones;
    }
}
