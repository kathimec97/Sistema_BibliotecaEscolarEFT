package cl.duoc.dao;

import cl.duoc.modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad estudiante.
 * Permite registrar, actualizar, eliminar y consultar a los estudiantes vinculados al sistema.
 * @author Katherine.
 */
public interface EstudianteDAO {
    void insertar(Estudiante estudiante) throws SQLException;
    void actualizar(Estudiante estudiante) throws SQLException;
    void eliminar(int id) throws SQLException;
    Estudiante buscarPorId(int id) throws SQLException;
    List<Estudiante> listarTodos() throws SQLException;
    Estudiante buscarPorRut(String rut) throws SQLException;
}
