package culturarte.logica.endpoints.envoltorios;

import culturarte.servicios.DTs.DTComentario;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ListaDTComentario")
public class ListaDTComentario {
    private List<DTComentario> lista;

    public ListaDTComentario() {
    }

    public ListaDTComentario(List<DTComentario> lista) {
        this.lista = lista;
    }

    @XmlElement(name = "comentario")
    public List<DTComentario> getLista() {
        return lista;
    }

    public void setLista(List<DTComentario> lista) {
        this.lista = lista;
    }
}
