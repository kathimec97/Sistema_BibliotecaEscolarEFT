package cl.duoc.controlador;

import cl.duoc.dao.impl.CategoriaDAOImpl;
import cl.duoc.dao.impl.LibroDAOImpl;
import cl.duoc.modelo.Categoria;
import cl.duoc.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador para gestionar la lógica de los libros.
 * Conecta la VistaLibros con LibroDAOImpl.
 */
public class LibroControlador {

    private final LibroDAOImpl libroDAO = new LibroDAOImpl();
    private final CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();

    /**
     * Carga las categorías desde la Base de datos al JComboBox de la vista
     */

    public void cargarCategorias(JComboBox<Categoria> combo) throws SQLException {
        try {
            combo.removeAllItems();
            List<Categoria> categorias = categoriaDAO.listarTodas();
            for (Categoria categoria : categorias) {
                combo.addItem(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
        }
    }

    /**
     * Cargar todos los libros registrados en la JTable de la vista
     */
    public void cargarTabla(JTable tabla) throws SQLException {
        String[] columnas = {"ID", "ISBN", "Título", "Autor", "Editorial", "Categoría", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            List<Libro> libros = libroDAO.listarTodos();
            for (Libro libro : libros) {
                modelo.addRow(new Object[]{
                        libro.getId(),
                        libro.getIsbn(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getEditorial(),
                        libro.getCategoria().getNombre(),
                        libro.getStock()
                });
            }
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (SQLException e) {
            System.out.println("Error al cargar tabla: " + e.getMessage());
        }
    }

    /**
     * Valida los datos y registra un nuevo libro en la base de datos.
     */
    public void guardarLibro(String isbn, String titulo, String autor, String editorial, Categoria categoria, String stockTxt, JPanel panelPadre) throws SQLException {
        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || stockTxt.isEmpty() || categoria == null) {
            JOptionPane.showMessageDialog(panelPadre, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int stock = Integer.parseInt(stockTxt);
            if (stock < 0) {
                JOptionPane.showMessageDialog(panelPadre, "El stock no puede ser negativo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Libro nuevoLibro = new Libro(0, titulo, autor, isbn, editorial, stock, categoria);
            libroDAO.insertar(nuevoLibro);
            JOptionPane.showMessageDialog(panelPadre, "Libro guardado con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panelPadre, "El stock deber ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panelPadre, "Error de base de datos al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Válida los datos y actualiza un libro existente
     */
    public void editarLibro(int id, String isbn, String titulo, String autor, String editorial, Categoria categoria, String stockTxt, JPanel panelPadre) throws SQLException {
        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || stockTxt.isEmpty() || categoria == null) {
            JOptionPane.showMessageDialog(panelPadre, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int stock = Integer.parseInt(stockTxt);
            Libro libroEditado = new Libro(id, titulo, autor, isbn, editorial, stock, categoria);
            libroDAO.actualizar(libroEditado);
            JOptionPane.showMessageDialog(panelPadre, "Libro actualizado con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panelPadre, "El stock debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panelPadre, "Error en la base de datos al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina un libro de la base de datos usando su ID
     */

    public void eliminarLibro(int id, JPanel panelPadre) {
        int confirmacion = JOptionPane.showConfirmDialog(panelPadre, "¿Estás seguro que deseas eliminar este libro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                libroDAO.eliminar(id);
                JOptionPane.showMessageDialog(panelPadre, "Libro eliminado con éxito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                if (e.getErrorCode() == 1451 || e.getMessage().contains("foreign key constraint fails")) {
                    JOptionPane.showMessageDialog(panelPadre, "No se puede eliminar este libro porque tiene un historial de préstamos asociado\n",
                            "Opción Denegada", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panelPadre, "Error al eliminar este libro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
