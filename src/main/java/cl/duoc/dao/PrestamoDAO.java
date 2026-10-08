package cl.duoc.dao;

import cl.duoc.modelo.Prestamo;

import java.util.List;


/**
 * Interfaz que define las operaciones para gestionar la circulación de materiales.
 * Controla el registro de préstamos, devoluciones y la generación de historiales.
 */
public interface PrestamoDAO {
    void insertar(Prestamo prestamo); // registro de un nuevo prestamo
    void actualizar(Prestamo prestamo); //registro de devolución
    void eliminar(int id);
    List<Prestamo> listarTodos();

    //Para los reportes
    List<Prestamo> historialPorEstudiante(int idEstudiante);
    List<Prestamo> listarLibrosEnPrestamo();
}
