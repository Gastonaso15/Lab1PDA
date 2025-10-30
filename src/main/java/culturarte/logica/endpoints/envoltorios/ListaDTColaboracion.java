package culturarte.logica.endpoints.envoltorios;

import culturarte.servicios.DTs.DTColaboracion;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ListaDTColaboracion")
public class ListaDTColaboracion {
    private List<DTColaboracion> lista;

    public ListaDTColaboracion() {
    }

    public ListaDTColaboracion(List<DTColaboracion> lista) {
        this.lista = lista;
    }

    @XmlElement(name = "colaboracion")
    public List<DTColaboracion> getLista() {
        return lista;
    }

    public void setLista(List<DTColaboracion> lista) {
        this.lista = lista;
    }
}
