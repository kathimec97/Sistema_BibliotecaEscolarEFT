package cl.duoc.vista;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VistaPrincipal extends JFrame {
    private JPanel mainPanel;
    private JPanel panelContenedor;
    private JPanel panelMenu;
    private JButton btnLibros;
    private JButton btnEstudiantes;
    private JButton btnPrestamos;
    private JButton btnReportes;
    private JButton btnSalir;



    public VistaPrincipal() throws SQLException {


        setTitle("Sistema de Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 850);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);

        btnPrestamos.addActionListener(e -> {
            mostrarPanel(new VistaPrestamos());
        });

        btnLibros.addActionListener(e -> {

            try {
                mostrarPanel(new VistaLibros());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar el módulo de libros: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnEstudiantes.addActionListener(e -> {
            try{
                mostrarPanel(new VistaRegistroEstudiante());
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,
                        "Error al cargar el modulo de estudiantes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
                });

        btnReportes.addActionListener(e -> {
            try{
                mostrarPanel(new VistaReportes());
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Error al cargar el modulo de reportes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSalir.addActionListener(e -> {
            System.exit(0);
        });

        mostrarPanel(new VistaLibros());
    }
        private void mostrarPanel(JPanel panelNuevo) {

        panelContenedor.removeAll();

        panelContenedor.setLayout(new BorderLayout());

        panelContenedor.add(panelNuevo, BorderLayout.CENTER);

        panelContenedor.revalidate();
        panelContenedor.repaint();


    }
}
