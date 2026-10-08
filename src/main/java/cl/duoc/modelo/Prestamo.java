package cl.duoc.modelo;

import java.util.Date;

/**
 * Representa una transacción de préstamo de un libro a un estudiante.
 * Registra las fechas de entrega y devolución, permitiendo controlar disponibilidad del material y detectar atrasos.
 * @author Katherine
 */
public class Prestamo {

    private int id;
    private Estudiante id_estudiante;
    private Libro id_libro;
    private Date fecha_prestamo;
    private Date fecha_devolucion;
    private int devuelto;

    public Prestamo(int id, Estudiante id_estudiante, Libro id_libro, Date fecha_prestamo, Date fecha_devolucion, int devuelto) {
        this.id = id;
        this.id_estudiante = id_estudiante;
        this.id_libro = id_libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.devuelto = devuelto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estudiante getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(Estudiante id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public Libro getId_libro() {
        return id_libro;
    }

    public void setId_libro(Libro id_libro) {
        this.id_libro = id_libro;
    }

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public int getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(int devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo: " + '\n' +
                "ID: " + id + '\n' +
                "ID estudiante: " + id_estudiante + '\n' +
                "ID libro: " + id_libro + '\n' +
                "Fecha prestamo: " + fecha_prestamo + '\n' +
                "Fecha devolucion: " + fecha_devolucion + '\n' +
                "Devuelto: " + devuelto;
    }
}
