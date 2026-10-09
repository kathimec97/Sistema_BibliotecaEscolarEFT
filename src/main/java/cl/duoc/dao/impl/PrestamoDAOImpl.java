package cl.duoc.dao.impl;

import cl.duoc.dao.EstudianteDAO;
import cl.duoc.dao.LibroDAO;
import cl.duoc.dao.PrestamoDAO;
import cl.duoc.modelo.Estudiante;
import cl.duoc.modelo.Libro;
import cl.duoc.modelo.Prestamo;
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
 * Implementación de la interfaz PrestamoDAO.
 * Gestiona los préstamos integrando EstudianteDAO y LibroDAO para resolver las relaciones
 * entre los objetos.
 * @author Katherine
 */
public class PrestamoDAOImpl implements PrestamoDAO {

    private static final Logger LOGGER = Logger.getLogger(PrestamoDAOImpl.class.getName());

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    /**
     * Inserta un nuevo registro préstamo en la base de datos.
     *
     * Extrae los identificadores desde los objetos Estudiante y libro encapsulados
     * dentro del objeto Prestamo.
     * @param prestamo Objeto con los datos del préstamo a registrar.
     * @throws SQLException si ocurre un error de restricción o conexión.
     */
    @Override
    public void insertar(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getEstudiante().getId());
            stmt.setInt(2, prestamo.getLibro().getId());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
            stmt.setBoolean(5, prestamo.isDevuelto());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar préstamo", e);
            throw e;
        }

    }

    /**
     * Actualiza los datos de un préstamo existente en la base de datos.
     *
     * @param prestamo Objeto 'Prestamo' con los datos actualizados.
     * @throws SQLException
     */
    @Override
    public void actualizar(Prestamo prestamo) throws SQLException {
        String sql = "UPDATE prestamos SET id_estudiante=?, id_libro=?, fecha_prestamo=?, fecha_devolucion=?, devuelto=? WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getEstudiante().getId());
            stmt.setInt(2, prestamo.getLibro().getId());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
            stmt.setBoolean(5, prestamo.isDevuelto());
            stmt.setInt(6, prestamo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar préstamo", e);
            throw e;
        }

    }

    /**
     * Elimina físicamente un registro de préstamo de la base de datos.
     * @param id El identificador del préstamo a eliminar
     * @throws SQLException
     */
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM prestamos WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al eliminar préstamo con ID: "+ id , e);
            throw e;
        }
    }

    /**
     * Obtiene una lista de todos los préstamos registrados en el sistema.
     *
     * @return Una lista de Objetos 'Prestamo'
     * @throws SQLException
     */
    @Override
    public List<Prestamo> listarTodos() throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();

        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estudiante estudianteObj = estudianteDAO.buscarPorId(rs.getInt("id_estudiante"));
                Libro libroObj = libroDAO.buscarPorId(rs.getInt("id_libro"));

                lista.add(new Prestamo(
                        rs.getInt("id"),
                        estudianteObj,
                        libroObj,
                        rs.getDate("fecha_prestamo"),
                        rs.getDate("fecha_devolucion"),
                        rs.getBoolean("devuelto")
                ));

            }
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al listar Prestamos", e);
            throw e;
        }
        return lista;
    }

    /**
     * Obtiene el historial de préstamos (activos y devueltos) de un estudiante.
     * Funcionalidad para la generación de reportes bibliotecarios.
     *
     * @param idEstudiante El ID del estudiante a consultar.
     * @return Una lista de objetos Préstamo asociados al estudiante.
     * @throws SQLException
     */
    @Override
    public List<Prestamo> historialPorEstudiante(int idEstudiante) throws SQLException {
        List<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT * FROM prestamos WHERE id_estudiante=?";
        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEstudiante);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estudiante estudianteObj = estudianteDAO.buscarPorId(rs.getInt("id_estudiante"));
                    Libro libroObj = libroDAO.buscarPorId(rs.getInt("id_libro"));
                    lista.add(new Prestamo(
                            rs.getInt("id"),
                            estudianteObj,
                            libroObj,
                            rs.getDate("fecha_prestamo"),
                            rs.getDate("fecha_devolucion"),
                            rs.getBoolean("devuelto")
                    ));
                }
            }
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al obtener historial del estudiante: " + idEstudiante, e);
            throw e;
        }
        return lista;
    }

    /**
     * Obtiene una lista de todos los préstamos que actualmente no han sido devueltos, funcionalidad requerida
     * para el control de inventario.
     * @return Una lista de objetos Préstamo activos (Pendientes de devolución).
     * @throws SQLException
     */
    @Override
    public List<Prestamo> listarLibrosEnPrestamo() throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE devuelto= 0";

        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();

        try(PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                Estudiante estudianteObj = estudianteDAO.buscarPorId(rs.getInt("id_estudiante"));
                Libro libroObj = libroDAO.buscarPorId(rs.getInt("id_libro"));
                lista.add(new Prestamo(
                        rs.getInt("id"),
                        estudianteObj,
                        libroObj,
                        rs.getDate("fecha_prestamo"),
                        rs.getDate("fecha_devolucion"),
                        rs.getBoolean("devuelto")
                ));

            }
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al obtener listar libros En Préstamo", e);
            throw e;
        }
        return lista;
    }
}
