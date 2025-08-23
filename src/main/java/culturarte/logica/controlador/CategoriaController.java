package culturarte.logica.controlador;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.manejador.CategoriaManejador;
import culturarte.logica.manejador.PropuestaManejador;
import culturarte.logica.modelo.Categoria;
import culturarte.logica.modelo.Propuesta;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoriaController implements ICategoriaController{

    public CategoriaController() {
        EntityManager em = JPAUtil.getEntityManager();
    }

    @Override
    public List<String> listarNombreCategorias(){
        CategoriaManejador mc = CategoriaManejador.getinstance();
        List<String> cats = mc.listarCategorias();
        return cats;
    }

    @Override
    public void crearCategoria(String nombre, String padre) throws Exception {
        CategoriaManejador mc = CategoriaManejador.getinstance();
        Categoria catPadre = mc.obtenerPorNombre(padre);
        if (mc.obtenerPorNombre(nombre) != null) {
            throw new Exception("La categoría ya existe.");
        }
        if (catPadre == null) {
            catPadre = mc.obtenerPorNombre("Categoría");
        }
        Categoria nueva = new Categoria(nombre,catPadre);
        mc.addCategoria(nueva);
    }

    @Override
    public Categoria obtenerCategoriaPorNombre(String nombre) {
        CategoriaManejador mc = CategoriaManejador.getinstance();
        return mc.obtenerPorNombre(nombre);
    }


}
