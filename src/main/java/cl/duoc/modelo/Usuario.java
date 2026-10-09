package cl.duoc.modelo;

/**
 * Representa a un usuario administrativo del sistema (como un bibliotecario).
 * Hereda los datos personales de la clase Persona y añade las credenciales necesarias para el módulo de autenticación.
 */
public class Usuario extends Persona{

    private String contrasenia;
    private Rol rol;



    public Usuario(int id, String nombre, String rut, String correo, String contrasenia, Rol rol) {
        super(id, nombre, rut, correo);
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario: " + getNombre() + "| Rol: " + getRol();
    }
}