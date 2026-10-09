package cl.duoc.dao;

import cl.duoc.modelo.Categoria;
import cl.duoc.modelo.Libro;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que gestiona las operaciones de acceso a datos para las
 * categorias de los libros.
 * @author katherine
 */
public interface CategoriaDAO {
    void insertarLibro(Categoria categoria) throws SQLException;
    void actualizarLibro(Categoria categoria)   throws SQLException;
    void eliminarLibro(int id) throws SQLException;
   Categoria buscarPorId(int id) throws SQLException;
   List<Libro> listarTodos() throws SQLException;
}
