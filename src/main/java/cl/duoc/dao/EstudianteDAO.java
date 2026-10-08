package cl.duoc.dao;

import cl.duoc.modelo.Estudiante;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad estudiante.
 * Permite registrar, actualizar, eliminar y consultar a los estudiantes vinculados al sistema.
 * @author Katherine.
 */
public interface EstudianteDAO {
    void insertar(Estudiante estudiante);
    void actualizar(Estudiante estudiante);
    void eliminar(int id);
    Estudiante buscarPorId(int id);
    List<Estudiante> listarTodos();
}
