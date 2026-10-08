package cl.duoc.dao;

import cl.duoc.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Usuario.
 * Aplica el patrón DAO para separar la lógica de negocio del acceso a la base de datos.
 */
public interface UsuarioDAO {

    Usuario autenticar(String correo, String contrasenia);
    void insertar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(int id);
    Usuario buscarPorId(int id);
    List<Usuario> listarTodos();
}
