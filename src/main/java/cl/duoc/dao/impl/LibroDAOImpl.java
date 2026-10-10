package cl.duoc.dao.impl;

import cl.duoc.dao.CategoriaDAO;
import cl.duoc.dao.LibroDAO;
import cl.duoc.modelo.Categoria;
import cl.duoc.modelo.Libro;
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
 * Implementación de la interfaz LibroDAO.
 *
 * Maneja el registro, actualización, eliminación y listado de libros para la base de datos.
 * @author Katherine
 */
public class LibroDAOImpl implements LibroDAO {

    private static final Logger LOGGER = Logger.getLogger(LibroDAOImpl.class.getName());

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    /**
     * Permite registrar un nuevo libro en la base de datos
     * @param libro Un objeto 'Libro'
     * @throws SQLException En el caso de que falle la conexión con la base de datos
     */
    @Override
    public void insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getCategoria().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar libro: " + libro.getTitulo(), e);
            throw e;
        }
    }

    /**
     * Para actualizar los datos de los libros previamente registrados.
     * @param libro Un objeto 'Libro' modificado
     * @throws SQLException
     */
    @Override
    public void actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET titulo=?, autor=?, isbn=?, editorial=?, stock=?, id_categoria=?  WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getCategoria().getId());
            stmt.setInt(7, libro.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar libro: " + libro.getId(), e);
            throw e;
        }
    }

    /**
     * Elimina de forma permanente un registro en la tabla Libros
     * @param id Identificador del libro que se quiere eliminar
     * @throws SQLException
     */
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar libro: " + id, e);
            throw e;
        }
    }

    /**
     * Busca un libro especifico en la base de datos
     * mediante su ID.
     * @param id Identificador del libro que se quiere encontrar
     * @return un Objeto de tipo Libro con sus datos.
     * @throws SQLException
     */
    @Override
    public Libro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM libros WHERE id=?";
        Libro libro = null;

        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                int id_categoria = rs.getInt("id_categoria");
                Categoria categoriaObj = categoriaDAO.buscarPorId(id_categoria);

                libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"),
                        categoriaObj
                );
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar libro: " + id, e);
            throw e;
        }
        return libro;
    }

    /**
     * Recupera la lista completa de Libros guardada en la base de datos.
     * @return Una lista que contiene objetos de tipo Libro.
     * @throws SQLException
     */
    @Override
    public List<Libro> listarTodos() throws SQLException {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id_categoria = rs.getInt("id_categoria");

                Categoria categoriaObj = categoriaDAO.buscarPorId(id_categoria);
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"),
                        categoriaObj

                );
                lista.add(libro);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error técnico al listar libros", e);
            throw e;
        }
        return lista;
    }

    /**
     * Obtiene la lista de los libros más prestados, ordenados de forma
     * descendente según la cantidad de veces que han sido prestados.
     * @return Una lista de objetos ordenados de mayor a menor.
     * @throws SQLException
     */
    @Override
    public List<Libro> listarLibrosMasPrestados() throws SQLException {
        List<Libro> lista = new ArrayList<>();

        String sql = "SELECT id_libro FROM prestamos GROUP BY id_libro ORDER BY COUNT(id_libro) DESC";
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id_libro = rs.getInt("id_libro");

                Libro libroEncontrado = this.buscarPorId(id_libro);

                if(libroEncontrado != null) {
                    lista.add(libroEncontrado);
                }
            }
        }catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al lista los libros más prestados", e);
            throw e;
        }
return lista;
    }

    /**
     * Busca un libro especifico en la base de datos mediante su ISBN.
     * @param isbn El codigo ISBN del libro
     * @return un Objeto de tipo libro con sus datos
     * @throws SQLException
     */
    @Override
    public Libro buscarPorIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM libros WHERE isbn=?";
        Libro libro = null;
        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id_categoria = rs.getInt("id_categoria");
                Categoria categoriaObj = categoriaDAO.buscarPorId(id_categoria);
                libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"),
                        categoriaObj
                );
            }
        }catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar libro: " + isbn, e);
            throw e;
        }
        return libro;
    }

    /**
     * Actualiza únicamente el stock de un libro especifico.
     * @param id identificador del Libro
     * @param nuevoStock la cantidad de stock que quedará disponible
     *
     */
  @Override
    public void actualizarStock(int id, int nuevoStock) throws SQLException {
        String sql = "UPDATE libros SET stock=? WHERE id=?";
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        }catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar libro: " + id, e);
            throw e;
        }
    }
}
