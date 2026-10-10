package cl.duoc.dao.impl;

import cl.duoc.dao.EstudianteDAO;
import cl.duoc.modelo.Estudiante;
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
 * Implementación de la interfaz EstudianteDAO.
 *
 * Concentra toda la lógica de acceso a la base de datos para la tabla estudiantes, mediante
 * las operaciones CRUD.
 * @author Katherine
 */
public class EstudianteDAOImpl implements EstudianteDAO {

    private static final Logger LOGGER = Logger.getLogger(EstudianteDAOImpl.class.getName());

    /**
     * Metodo para obtener la conexion a la base de datos.
     * @return Un objeto Connection activo hacia la base de datos.
     * @throws SQLException si ocurre un error de conexion.
     */
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }


    /**
     * Inserta un nuevo registro de estudiante en la base de datos.
     * Captura y registra cualquier excepción sql a traves del Logger.
     *
     * @param estudiante El objeto estudiante que contiene los datos a registrar.
     * @throws SQLException
     */
    @Override
    public void insertar(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar estudiante: " + estudiante.getNombre(), e);
            throw e;
        }
    }


    /**
     * Actualiza los datos de un estudiante existente en la base de datos,
     * utiliza su ID para localizar el registro a modificar.
     * @param estudiante
     * @throws SQLException
     */
    @Override
    public void actualizar(Estudiante estudiante) throws SQLException {
        String sql = "UPDATE estudiantes SET nombre=?, rut=?, curso=?, correo=? WHERE id=?";


        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());
            stmt.setInt(5, estudiante.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar estudiante: " + estudiante.getNombre(), e);
            throw e;
        }
    }


    /**
     * Elimina físicamente el registro de un estudiante en la base de datos.
     * @param id El identificador único del estudiante que se desea eliminar.
     */
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM estudiantes WHERE id=?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar estudiante: " + id, e);
            throw e;
        }
    }


    /**
     * Busca a un estudiante específico en la base de datos mediante su ID.
     *
     * @param id El identificador único del estudiante a buscar
     * @return un Objeto Estudiante con los datos encontrados.
     * @throws SQLException
     */
    @Override
    public Estudiante buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM estudiantes WHERE id=?";
        Estudiante estudiante = null;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("correo"),
                        rs.getString("curso")
                );
            }
        } catch(SQLException e)  {
            LOGGER.log(Level.SEVERE, "Error al buscar estudiante: " + id, e);
            throw e;
        }
    return estudiante;
}


    /**
     * Recupera la lista completa con todos los estudiantes registrados en el sistema
     * @return Una lista que contiene objetos de tipo Estudiante.
     * @throws SQLException
     */
    @Override
    public List<Estudiante> listarTodos() throws SQLException {

        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";

        try(PreparedStatement stmt = getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery()){

            while(rs.next()) {
                Estudiante estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("curso"),
                        rs.getString("correo")
                );
                lista.add(estudiante);
            }

            }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error al listar todos los estudiantes", e);
            throw e;
        }
        return lista;
        }

    /**
     * Busca a un estudiante específico en la base de datos mediante su RUT.
     * @param rut El Rut del estudiante a buscar
     * @return un Objeto Estudiante con los datos encontrados.
     * @throws SQLException
     */
    public Estudiante buscarPorRut(String rut) throws SQLException {
        String sql = "SELECT * FROM estudiantes WHERE rut=?";
        Estudiante estudiante = null;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                estudiante = new Estudiante(
                        rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("curso"),
                        rs.getString("correo")
                );
            }
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error al buscar estudiante: " + rut, e);
        }
        return estudiante;
        }

    }

