package culturarte.logica.endpoints.envoltorios;

import culturarte.servicios.DTs.DTPropuesta;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ListaDTPropuesta")
public class ListaDTPropuesta {
    private List<DTPropuesta> lista;

    public ListaDTPropuesta() {
    }

    public ListaDTPropuesta(List<DTPropuesta> lista) {
        this.lista = lista;
    }

    @XmlElement(name = "propuesta")
    public List<DTPropuesta> getLista() {
        return lista;
    }

    public void setLista(List<DTPropuesta> lista) {
        this.lista = lista;
    }
}
