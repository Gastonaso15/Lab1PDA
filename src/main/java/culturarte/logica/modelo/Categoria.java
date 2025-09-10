package culturarte.logica.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL)
    private List<Categoria> subCategorias = new ArrayList<>();
    @ManyToOne
    private Categoria categoriaPadre;


    // Constructores
    public Categoria() {}
    public Categoria(String nombre,Categoria categoriaPadre) {
        this.nombre = nombre;
        this.categoriaPadre = categoriaPadre;
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Categoria> getSubCategorias() { return subCategorias; }
    public Categoria getCategoriaPadre() { return categoriaPadre; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setSubCategorias(List<Categoria> subCategorias){ this.subCategorias = subCategorias; }
    public void setCategoriaPadre(Categoria categoriaPadre) { this.categoriaPadre = categoriaPadre; }
}
