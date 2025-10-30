package culturarte.logica.endpoints.envoltorios;

import culturarte.servicios.DTs.DTCategoria;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ListaDTCategoria")
public class ListaDTCategoria {
    private List<DTCategoria> lista;

    public ListaDTCategoria() {
    }

    public ListaDTCategoria(List<DTCategoria> lista) {
        this.lista = lista;
    }

    @XmlElement(name = "categoria")
    public List<DTCategoria> getLista() {
        return lista;
    }

    public void setLista(List<DTCategoria> lista) {
        this.lista = lista;
    }
}
