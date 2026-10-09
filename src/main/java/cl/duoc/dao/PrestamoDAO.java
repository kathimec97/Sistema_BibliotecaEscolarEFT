package cl.duoc.dao;

import cl.duoc.modelo.Prestamo;

import java.sql.SQLException;
import java.util.List;


/**
 * Interfaz que define las operaciones para gestionar la circulación de materiales.
 * Controla el registro de préstamos, devoluciones y la generación de historiales.
 */
public interface PrestamoDAO {
    void insertar(Prestamo prestamo) throws SQLException; // registro de un nuevo prestamo
    void actualizar(Prestamo prestamo) throws SQLException; //registro de devolución
    void eliminar(int id) throws SQLException;
    List<Prestamo> listarTodos() throws SQLException;

    //Para los reportes
    List<Prestamo> historialPorEstudiante(int idEstudiante) throws SQLException;
    List<Prestamo> listarLibrosEnPrestamo() throws SQLException;
}
