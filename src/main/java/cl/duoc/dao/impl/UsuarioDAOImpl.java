package cl.duoc.dao.impl;

import cl.duoc.dao.UsuarioDAO;
import cl.duoc.modelo.Rol;
import cl.duoc.modelo.Usuario;
import cl.duoc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementación de la interfaz UsuarioDAO
 *
 * Concentra toda la lógica de acceso a la base de datos para la tabla usuarios, mediante operaciones
 * CRUD.
 *
 * @author Katherine
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private static final Logger LOGGER = Logger.getLogger(UsuarioDAOImpl.class.getName());

    /**
     * Método para obtener la conexión  a la base de datos.
     * @return Un objeto connection activo hacia la base de datos.
     * @throws SQLException en caso de cualquier error con la base de datos.
     */
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    /**
     * Valida las credenciales de acceso de un usuario en el sistema.
     *
     * @param correo El correo electronico ingresado por el usuario
     * @param contrasenia La contraseña ingresada por el usuario.
     * @return Un Objeto Usuario si las credenciales son correctas,
     * o 'null' si no encuentra coincidencia.
     * @throws SQLException Si ocurre un error durante la consulta
     */
    @Override
    public Usuario autenticar(String correo, String contrasenia) throws SQLException{
        String sql =  "select * from usuarios where correo = ? and contraseña = ?";
        Usuario usuario = null;

        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasenia);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        Rol.valueOf(rs.getString("rol").toUpperCase())
                );
            }
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al autenticar el usuario con correo: " + correo, e);
            throw e;
        }
        return usuario;
    }


    /**
     * Registra a un nuevo usuario en la base de datos.
     *
     * @param usuario Objeto 'Usuario' que contiene los datos a almacenar
     * @throws SQLException Si ocurren un error de conexion o por restricciones (correo duplicado o rut).
     */
    @Override
    public void insertar(Usuario usuario) throws  SQLException{
        String sql = "INSERT INTO usuarios (nombre, rut, correo, contraseña, rol) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasenia());
            stmt.setString(5, usuario.getRol().name());

            stmt.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el usuario: " + usuario.getNombre(), e);
            throw e;
        }
    }


    /**
     * Modifica los datos de un usuario existente.
     * El campo rut se excluye de la actualización para mantener la integridad de este identificador.
     *
     * @param usuario Objeto 'usuario' con los datos actualizados.
     * @throws SQLException si ocurre un error al ejecutar la sentencia en la base de datos.
     */
    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, contraseña=?, rol=? WHERE id=?";

        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasenia());
            stmt.setString(4, usuario.getRol().name());
            stmt.setInt(5, usuario.getId());

            stmt.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el usuario: " + usuario.getNombre(), e);
            throw e;
        }

    }


    /**
     * Elimina físicamente el registro de un usuario de la base de datos.
     *
     * @param id El identificador único del usuario a eliminar
     * @throws SQLException Si hay un problema de conexión con la base de datos.
     */
    @Override
    public void eliminar(int id) throws SQLException {

            String sql = "DELETE FROM usuarios WHERE id=?";

            try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error al eliminar el usuario: " + id, e);
                throw e;
            }
    }


    /**
     * Busca y recupera la información de un usuario especifico mediante su identificador.
     *
     * @param id El identificador único del usuario
     * @return Un objeto 'usuario' con los datos encontrados.
     * @throws SQLException Si la consulta falla debido a un error de conexión.
     */
    @Override
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id=?";
        Usuario usuario = null;

        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        Rol.valueOf(rs.getString("rol").toUpperCase())
                );
            }
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el usuario: " + id, e);
            throw e;
        }
        return usuario;
    }

    /**
     * Obtiene una colección con todos los usuarios registrados en el sistema.
     *
     * @return Una lista de objetos 'Usuario'
     * @throws SQLException si ocurre un error.
     */
    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try(PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("rut"),
                    rs.getString("correo"),
                    rs.getString("contraseña"),
                    Rol.valueOf(rs.getString("rol").toUpperCase())
            );
            usuarios.add(usuario);
        }
            }catch(SQLException e) {
    LOGGER.log(Level.SEVERE, "Error al listar todos los usuarios", e);
    throw e;
            }
            return usuarios;
        }
    }





