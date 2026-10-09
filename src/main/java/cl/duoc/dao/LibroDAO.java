package cl.duoc.dao;

import cl.duoc.modelo.Libro;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones de gestión de inventario de libros en la base de datos.
 * Permite a los bibliotecarios mantener actualizado el catálogo.
 * @author Katherine
 */
public interface LibroDAO {
    void insertar(Libro libro) throws SQLException;
    void actualizar(Libro libro) throws SQLException;
    void eliminar(int id) throws SQLException;
    Libro buscarPorId(int id) throws SQLException;
    List<Libro> listarTodos() throws SQLException;
    List<Libro> listarLibroMasPrestado() throws SQLException;
}
