package cl.duoc;


import cl.duoc.controlador.LoginControlador;
import cl.duoc.dao.PrestamoDAO;
import cl.duoc.dao.impl.PrestamoDAOImpl;
import cl.duoc.modelo.Prestamo;
import cl.duoc.vista.VistaLogin;
import cl.duoc.vista.VistaPrestamos;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            LoginControlador controlador = new LoginControlador();

            VistaLogin ventanaLogin = new VistaLogin(controlador);

            ventanaLogin.setVisible(true);
        });





            }
        }

