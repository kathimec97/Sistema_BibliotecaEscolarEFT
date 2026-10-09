package cl.duoc.dao.impl;

import cl.duoc.dao.CategoriaDAO;
import cl.duoc.modelo.Categoria;
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
 * Implementación de la interfaz CategoriaDAO.
 *
 * Maneja el historial de categorías en el sistema.
 * @author Katherine
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    private static final Logger LOGGER = Logger.getLogger(CategoriaDAOImpl.class.getName());

    private Connection getConnection() throws SQLException{
        return DatabaseConnection.getInstance();
    }


    /**
     * Permite insertar una nueva categoría en la base de datos.
     * @param categoria Un objeto 'Categoria'
     * @throws SQLException en el caso de que haya errores en la conexión con la base de datos.
     */
    @Override
    public void insertar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1, categoria.getNombre());
            stmt.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error al insertar categoría: " + categoria, e);
            throw e;
        }
    }


    /**
     * Actualiza los datos de una categoria existente.
     * @param categoria El objeto 'Categoria' modificado.
     * @throws SQLException en caso de errores al actualizar la categoria.
     */
    @Override
    public void actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error técnico al actualizar categoría con ID: " + categoria.getId(), e);
            throw e;
        }
    }

    /**
     * Elimina el registro de una categoria
     * @param id El identificador de la categoria que se quiere eliminar
     * @throws SQLException en caso de error en la eliminación
     */
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error técnico al eliminar categoría con ID: " + id, e);
            throw e;
        }

    }

    /**
     * Busca una categoría especifíca en la base de datos mediante su id.
     * @param id El identificador de la categoría que se quiere buscar.
     * @return Un objeto categoria con los datos encontrados.
     * @throws SQLException
     */
    @Override
    public Categoria buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        Categoria categoria = null;
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
            }
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error al buscar categoría con ID: " + id, e);
            throw e;
        }
        return categoria;
    }

    /**
     * Lista una colección de categorías almacenadas en la base de datos.
     * @return una lista que contiene objetos de tipo 'Categoria'.
     * @throws SQLException
     */
    @Override
    public List<Categoria> listarTodas() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar las categorías", e);
            throw e;
        }
        return lista;
    }
}
