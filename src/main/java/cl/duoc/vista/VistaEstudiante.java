package cl.duoc.vista;

import cl.duoc.controlador.LibroControlador;
import cl.duoc.controlador.PrestamoControlador;
import cl.duoc.controlador.ReportesControlador;
import cl.duoc.dao.impl.EstudianteDAOImpl;
import cl.duoc.modelo.Estudiante;
import cl.duoc.modelo.Usuario;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class VistaEstudiante extends JFrame {
    private JPanel panelContenedorCard;
    private JPanel mainPanel;
    private JPanel panelMenu;
    private JPanel tarjetaCatalogo;
    private JLabel lblCatalogo;
    private JScrollPane scrInventario;
    private JTable tablaInventario;
    private JButton btnPedir;
    private JButton btnCatalogo;
    private JButton btnMisPrestamos;
    private JButton btnSalir;
    private JPanel tarjetaPrestamos;
    private JScrollPane scrollPrestamos;
    private JTable tablaMisPrestamos;

    private final Usuario usuarioLogueado;
    private final LibroControlador libroControlador = new LibroControlador();
    private final PrestamoControlador prestamoControlador = new PrestamoControlador();
    private final ReportesControlador reportesControlador = new ReportesControlador();
    private final EstudianteDAOImpl estudianteDAO = new EstudianteDAOImpl();
    private String isbnSeleccionado = " ";

    public VistaEstudiante( Usuario usuario) {
        this.usuarioLogueado = usuario;

        setTitle("Portal Estudiante - Bienvenido " + usuario.getNombre());
        setContentPane(mainPanel);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            libroControlador.cargarTabla(tablaInventario);
        } catch (SQLException e) {
            System.err.println("Error técnico al cargar tabla: " + e.getMessage());
            e.printStackTrace();

            // 2. Alerta gráfica y amigable para el estudiante (Pantalla)
            JOptionPane.showMessageDialog(mainPanel,
                    "Lo sentimos, hubo un problema al cargar el catálogo de libros.",
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
        tablaInventario.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                int fila =  tablaInventario.getSelectedRow();
                if(fila >= 0){
                    isbnSeleccionado = tablaInventario.getModel().getValueAt(fila, 1).toString().trim();
                }
            }
        });

        //Boton Pedir Prestamo
        btnPedir.addActionListener(e -> {
                if(isbnSeleccionado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Seleccione un libro del catálogo primero. ", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                prestamoControlador.registrarPrestamo(usuarioLogueado.getRut(), isbnSeleccionado, mainPanel);

            try {
                libroControlador.cargarTabla(tablaInventario);
            } catch (SQLException ex) {
                System.err.println("Error al cargar la tabla: " + ex.getMessage());
                ex.printStackTrace();

                JOptionPane.showMessageDialog(mainPanel, "Lo sentimos, hubo un problema al cargar el catálogo de libros.",
                        "Error de conexión", JOptionPane.ERROR_MESSAGE);
            }
            isbnSeleccionado = "";

        });

        //Boton ver catalogo
        btnCatalogo.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelContenedorCard.getLayout();
            cl.first(panelContenedorCard);
            try {
                libroControlador.cargarTabla(tablaInventario);
            } catch (SQLException ex) {
                System.err.println("Error técnico al cargar tabla: " + ex.getMessage());
                ex.printStackTrace();

                // 2. Alerta gráfica y amigable para el estudiante (Pantalla)
                JOptionPane.showMessageDialog(mainPanel,
                        "Lo sentimos, hubo un problema al cargar el catálogo de libros.",
                        "Error de Conexión",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        btnMisPrestamos.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelContenedorCard.getLayout();
            cl.last(panelContenedorCard);

            panelContenedorCard.revalidate();
            panelContenedorCard.repaint();

            try{
                int idRealEstudiante = -1;
                for(Estudiante est : estudianteDAO.listarTodos()) {
                    if(est.getRut().equals(usuarioLogueado.getRut())) {
                        idRealEstudiante = est.getId();
                        break;
                    }
                }

                if(idRealEstudiante != -1){
                    reportesControlador.cargarHistorialEstudiante(idRealEstudiante, tablaMisPrestamos, mainPanel);
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Error: tu cuenta no está enlazada a un registro valido de Estudiante.",
                            "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                System.err.print("Error al cargar mis Prestamos: " + ex.getMessage());
            }

        });


        btnSalir.addActionListener(e -> {
            System.exit(0);
        });
    }
}
