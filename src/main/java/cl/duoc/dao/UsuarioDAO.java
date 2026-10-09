package cl.duoc.dao;

import cl.duoc.modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Usuario.
 * Aplica el patrón DAO para separar la lógica de negocio del acceso a la base de datos.
 */
public interface UsuarioDAO {

    Usuario autenticar(String correo, String contrasenia) throws SQLException;
    void insertar(Usuario usuario) throws SQLException;
    void actualizar(Usuario usuario) throws SQLException;
    void eliminar(int id) throws SQLException;
    Usuario buscarPorId(int id) throws SQLException;
    List<Usuario> listarTodos() throws SQLException;
}
