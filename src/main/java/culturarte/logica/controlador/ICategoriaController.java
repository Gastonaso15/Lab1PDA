package culturarte.logica.controlador;

import culturarte.logica.modelo.Categoria;

import java.util.List;

public interface ICategoriaController {
    public abstract List<String> listarNombreCategorias();
    public abstract void crearCategoria(String nombre, String padre) throws Exception;
    public abstract Categoria obtenerCategoriaPorNombre(String nombre);
}
