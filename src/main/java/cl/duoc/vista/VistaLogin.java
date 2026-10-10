package cl.duoc.vista;

import cl.duoc.controlador.LoginControlador;
import cl.duoc.dao.impl.UsuarioDAOImpl;
import cl.duoc.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VistaLogin extends JFrame {
    private JPanel mainPanel;
    private JTextField txtCorreo;
    private JButton btnIngresar;
    private JLabel lblSistema;
    private JLabel lblCorreo;
    private JLabel lblContrasenia;
    private JPasswordField txtContrasenia;

private  LoginControlador controlador;

public VistaLogin() {}

    public VistaLogin(LoginControlador controlador) {
        this.controlador = controlador;

        setTitle("Sistema de Bibliotecas - Login");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        btnIngresar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

    }

    private void autenticarUsuario() {
        String correo = txtCorreo.getText().trim();
        String contrasenia = new String(txtContrasenia.getPassword());

        if (correo.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Po favor ingrese todos los campos", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        controlador.procesarLogin(correo, contrasenia, this);
    }


}
