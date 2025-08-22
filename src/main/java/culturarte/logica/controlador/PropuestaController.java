package culturarte.logica.controlador;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.manejador.PropuestaManejador;
import culturarte.logica.manejador.UsuarioManejador;
import culturarte.logica.modelo.Proponente;
import culturarte.logica.modelo.Propuesta;
import culturarte.logica.modelo.Usuario;
import culturarte.logica.DT.DTProponente;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class PropuestaController implements IPropuestaController {
    private EntityManager em;

    @Override
    public  void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,Double precioEntrada, Double montoNecesario, byte[] imagen,String proponente) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);
        if (p != null)
            throw new Exception("La propuesta con el titulo " + titulo + " ya esta registrada");
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Proponente prop = (Proponente) mu.obtenerUsuarioNick(proponente);
        p = new Propuesta(titulo,descripcion,lugar,fechaPrevista,precioEntrada,montoNecesario,imagen,prop);
        mp.addPropuesta(p);
    }

    @Override
    public List<DTPropuesta> devolverTodasLasPrpuestas(){
        PropuestaManejador mp = PropuestaManejador.getinstance();
        List<DTPropuesta> props = mp.obtenerTodasLasPropuestas();
        return props;
    }
}
