package culturarte.logica.endpoints.envoltorios;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "listaStrings")
public class ListaStrings {

    private List<String> items;

    public ListaStrings() {
    }

    public ListaStrings(List<String> items) {
        this.items = items;
    }

    @XmlElement(name = "item")
    public List<String> getLista() {
        return items;
    }

    public void setLista(List<String> items) {
        this.items = items;
    }
}
