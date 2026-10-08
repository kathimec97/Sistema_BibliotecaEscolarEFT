package cl.duoc.modelo;


/**
 * Representa a un estudiante de la institución registrado en el sistema.
 * Hereda de la clase Persona y contiene la información necesaria paa gestionar sus préstamos y llevar un historial individual.
 * @author Katherine
 */
public class Estudiante extends Persona{
    private String curso;

    public Estudiante(int id, String nombre, String rut, String correo, String curso) {
        super(id, nombre, rut, correo);
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Estudiante: " + getNombre() + "| Curso: " + curso;
    }
}
