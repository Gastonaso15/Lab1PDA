package culturarte.logica.DTs;

import java.util.ArrayList;
import java.util.List;

public class DTCategoria {
    private Long id;
    private String nombre;
    private List<DTCategoria> subCategorias = new ArrayList<>();
    private DTCategoria categoriaPadre;

    // Constructores
    public DTCategoria(String nombre, DTCategoria categoriaPadre) {
        this.setNombre(nombre);
        this.setCategoriaPadre(categoriaPadre);
    }
    public DTCategoria(String nombre, List<DTCategoria> subCategorias, DTCategoria categoriaPadre) {
        this.setNombre(nombre);
        this.setCategoriaPadre(categoriaPadre);
        this.setSubCategorias(subCategorias);
    }
    public DTCategoria(String nombre) {
        this.setNombre(nombre);
    }


    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<DTCategoria> getSubCategorias() { return subCategorias; }
    public void setSubCategorias(List<DTCategoria> subCategorias){ this.subCategorias = subCategorias; }

    public DTCategoria getCategoriaPadre() { return categoriaPadre; }
    public void setCategoriaPadre(DTCategoria categoriaPadre) { this.categoriaPadre = categoriaPadre; }

}
