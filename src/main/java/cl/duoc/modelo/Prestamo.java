package cl.duoc.modelo;

import java.util.Date;

/**
 * Representa una transacción de préstamo de un libro a un estudiante.
 * Registra las fechas de entrega y devolución, permitiendo controlar disponibilidad del material y detectar atrasos.
 * @author Katherine
 */
public class Prestamo {

    private int id;
    private Estudiante estudiante;
    private Libro libro;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int id, Estudiante estudiante, Libro libro, Date fechaPrestamo, Date fechaDevolucion, boolean devuelto) {
        this.id = id;
        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo: " + '\n' +
                "ID: " + id + '\n' +
                "ID estudiante: " + estudiante + '\n' +
                "ID libro: " + libro + '\n' +
                "Fecha prestamo: " + fechaPrestamo + '\n' +
                "Fecha devolucion: " + fechaDevolucion + '\n' +
                "Devuelto: " + devuelto;
    }
}
