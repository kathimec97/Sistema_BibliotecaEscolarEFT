package cl.duoc.controlador;

import cl.duoc.dao.impl.LibroDAOImpl;
import cl.duoc.dao.impl.PrestamoDAOImpl;
import cl.duoc.modelo.Libro;
import cl.duoc.modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ReportesControlador {

    private final LibroDAOImpl libroDAO = new LibroDAOImpl();
    private final PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl();

    public void cargarLibrosPrestados(JTable tabla, JPanel panel){
        String[] columnas = {"ID Libro", "ISBN", "Título", "Autor", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        try{
            List<Libro> libros = libroDAO.listarLibrosMasPrestados();
            for (Libro libro : libros) {
                modelo.addRow(new Object[]{
                        libro.getId(),
                        libro.getIsbn(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getStock()
                });
            }
            tabla.setModel(modelo);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(panel, "Error al cargar libros prestados: " + e.getMessage());
        }

    }

    public void cargarPrestamosActivos(JTable tabla, JPanel panel){
        String[] columnas = {"ID Prestamo", "RUT Alumno", "Nombre Alumno", "Libro", "F. Devolución"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        try{
            List<Prestamo> prestamos = prestamoDAO.listarLibrosEnPrestamo();
            for (Prestamo prestamo : prestamos) {
                modelo.addRow(new Object[]{
                        prestamo.getId(),
                        prestamo.getEstudiante().getRut(),
                        prestamo.getEstudiante().getNombre(),
                        prestamo.getLibro().getTitulo(),
                        prestamo.getFechaDevolucion()
                });
            }
            tabla.setModel(modelo);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(panel, "Error al cargar libros prestados: " + e.getMessage());
        }
    }

    public void cargarHistorialEstudiante(int idEstudiante, JTable tabla, JPanel panel){
        try{
        String[] columnas = {"ID Prestamo", "Libro", "Fecha Prestamo", "F. Devolución", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            List<Prestamo> prestamos = prestamoDAO.historialPorEstudiante(idEstudiante);
            if(prestamos.isEmpty()){
            JOptionPane.showMessageDialog(panel, "No se ha encontrado prestamos para este estudiante ");

        }

            for(Prestamo prestamo : prestamos){
            String estado = prestamo.isDevuelto() ? "devuelto" : " En prestamo";
            modelo.addRow(new Object[]{
                    prestamo.getId(),
                    prestamo.getLibro().getTitulo(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    estado
            });

            }
            tabla.setModel(modelo);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(panel, "Error al cargar historial estudiante: " + e.getMessage());
        }

    }
}
