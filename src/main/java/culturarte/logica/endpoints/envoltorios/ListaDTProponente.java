package culturarte.logica.endpoints.envoltorios;

import culturarte.servicios.DTs.DTProponente;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ListaDTProponente")
public class ListaDTProponente {
    private List<DTProponente> lista;

    public ListaDTProponente() {
    }

    public ListaDTProponente(List<DTProponente> lista) {
        this.lista = lista;
    }

    @XmlElement(name = "proponente")
    public List<DTProponente> getLista() {
        return lista;
    }

    public void setLista(List<DTProponente> lista) {
        this.lista = lista;
    }
}






