package cl.duoc.dao;

import cl.duoc.modelo.Categoria;
import cl.duoc.modelo.Libro;

import java.util.List;

/**
 * Interfaz que gestiona las operaciones de acceso a datos para las
 * categorias de los libros.
 * @author katherine
 */
public interface CategoriaDAO {
    void insertarLibro(Categoria categoria);
    void actualizarLibro(Categoria categoria);
    void eliminarLibro(int id);
   Categoria buscarPorId(int id);
   List<Libro> listarTodos();
}
