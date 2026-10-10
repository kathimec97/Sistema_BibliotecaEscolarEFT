package cl.duoc.controlador;

import cl.duoc.dao.impl.EstudianteDAOImpl;
import cl.duoc.dao.impl.LibroDAOImpl;
import cl.duoc.dao.impl.PrestamoDAOImpl;
import cl.duoc.modelo.Estudiante;
import cl.duoc.modelo.Libro;
import cl.duoc.modelo.Prestamo;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Controlador encargado de gestionar la lógica de negocio para los préstamos de libros.
 *
 * Este controlador implementa mecanismos de concurrencia mediante el uso de hilos para
 * evitar el bloqueo de la interfaz del usuario. Utiliza métodos sincronizados para garantizar la consistencia
 * del stock en la base de datos.
 * @author Katherine
 */

public class PrestamoControlador {
    private final LibroDAOImpl libroDAO = new LibroDAOImpl();
    private final EstudianteDAOImpl estudianteDAO = new EstudianteDAOImpl();
    private final PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl();

    /**
     * Inicia el proceso de registro de un préstamo ejecutando la lógica pesada
     * en un hilo secundario.
     * @param rutEstudiante EL rut del estudiante que solicita el prestamo
     * @param isbnLibro El ISBN del libro a prestar
     * @param panelPadre El panel desde el cual se invoca.
     */
    public void registrarPrestamo(String rutEstudiante, String isbnLibro, JPanel panelPadre) {
        Thread hiloProcesamiento = new Thread(() -> {
            ejecutarTransaccionSincronizada(rutEstudiante, isbnLibro, panelPadre);
        });
        hiloProcesamiento.start();
    }

    /**
     * Ejecuta la transacción en la base de datos de forma sincronizada.
     * Con synchronized nos aseguramos que si multiples hilos intentan ejecutar este metodo al mismo tiempo, pasarán de uno
     * a la vez.
     * @param rutEstudiante EL RUT del estudiante validado
     * @param isbnLibro El código ISBN del libro a prestar.
     * @param panelPadre El componente gráfico para mostrar alertas.
     */
    private synchronized void ejecutarTransaccionSincronizada(String rutEstudiante, String isbnLibro, JPanel panelPadre) {

        try{
            Estudiante estudiante = estudianteDAO.buscarPorRut(rutEstudiante);
            Libro libro = libroDAO.buscarPorIsbn(isbnLibro);

            if(estudiante == null){
                mostrarMensaje(panelPadre, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(libro == null){
                mostrarMensaje(panelPadre, "Libro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(libro.getStock() > 0) {

                Thread.sleep(1000);

                int nuevoStock = libro.getStock() -1;
                libroDAO.actualizarStock(libro.getId(), nuevoStock);

                Date fechaActual = new Date();

              java.util.Calendar calendario = java.util.Calendar.getInstance();
              calendario.setTime(fechaActual);
              calendario.add(Calendar.DAY_OF_YEAR, 7);

              Date fechaDevolucion = calendario.getTime();

              Prestamo prestamo = new Prestamo(0, estudiante, libro, fechaActual, fechaDevolucion, false);
              prestamoDAO.insertar(prestamo);

              mostrarMensaje(panelPadre, "¡Prestamo exitoso! \nStock restante de " + libro.getTitulo() + ": " + nuevoStock, "Exito", JOptionPane.INFORMATION_MESSAGE);

            }else{
                mostrarMensaje(panelPadre, "El libro " + libro.getTitulo() + " no tiene stock disponible.", "Sin Stock", JOptionPane.WARNING_MESSAGE);
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            mostrarMensaje(panelPadre, "El proceso fue interrumpido.", "Error", JOptionPane.ERROR_MESSAGE);


        }catch (Exception e) {
            mostrarMensaje(panelPadre, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método auxiliar para mostrar alertas o mensajes en la pantalla.
     *
     * @param panel Panel principal donde se centrará el mensaje.
     * @param mensaje El texto que queremos mostrar al usuario.
     * @param titulo El título que tendrá la ventanita del mensaje
     * @param tipo El tipo de icono.
     */
    private void mostrarMensaje(JPanel panel, String mensaje, String titulo, int tipo) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(panel, mensaje, titulo, tipo);
        });
    }
}
