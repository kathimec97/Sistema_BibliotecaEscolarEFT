package cl.duoc.controlador;

import cl.duoc.dao.UsuarioDAO;
import cl.duoc.dao.impl.UsuarioDAOImpl;
import cl.duoc.modelo.Usuario;
import cl.duoc.vista.VistaEstudiante;
import cl.duoc.vista.VistaLogin;
import cl.duoc.vista.VistaPrincipal;

import javax.swing.*;
import java.sql.SQLException;

public class LoginControlador {

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

public void procesarLogin(String correo, String contrasenia, VistaLogin vistaLogin){
    try{
        Usuario usuario  = usuarioDAO.autenticar(correo, contrasenia);

        if(usuario != null){
            if(usuario.getRol().name().equals("BIBLIOTECARIO")){
                JOptionPane.showMessageDialog(null, "Bienvenido Bibliotecario: " + usuario.getNombre());
                VistaPrincipal vistaPrincipal = new VistaPrincipal();
                vistaPrincipal.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Bienvenido Estudiante: " + usuario.getNombre());
                VistaEstudiante vistaEstudiante = new VistaEstudiante();
                vistaEstudiante.setVisible(true);
            }

            vistaLogin.dispose();
        }else{
            JOptionPane.showMessageDialog(vistaLogin, "Correo o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }catch (SQLException e) {
        JOptionPane.showMessageDialog(vistaLogin, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
