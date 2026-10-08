package cl.duoc.dao;

import cl.duoc.modelo.Libro;

import java.util.List;

/**
 * Interfaz que define las operaciones de gestión de inventario de libros en la base de datos.
 * Permite a los bibliotecarios mantener actualizado el catálogo.
 * @author Katherine
 */
public interface LibroDAO {
    void insertar(Libro libro);
    void actualizar(Libro libro);
    void eliminar(int id);
    Libro buscarPorId(int id);
    List<Libro> listarTodos();
    List<Libro> listarLibroMasPrestado();
}
