package cl.duoc.vista;

import cl.duoc.controlador.PrestamoControlador;

import javax.swing.*;
import java.awt.*;

public class VistaPrestamos extends JPanel {
    private JPanel panelPrincipalPrestamos;
    private JPanel panelNuevoPrestamo;
    private JLabel lblRegistrar;
    private JTextField txtRut;
    private JTextField txtIsbn;
    private JButton btnPrestar;
    private JLabel lblRut;
    private JLabel lblIsbn;
    private JPanel panelDevolucion;
    private JTextField txtFiltroId;
    private JButton btnBuscarId;
    private JLabel lblId;
    private JTable tablaPrestamosActivos;
    private JScrollPane scrollPrestamosActivos;
    private JPanel panelBotones;
    private JButton btnConfirmarDevolucion;

    private final PrestamoControlador controlador;

    public VistaPrestamos() {
        this.controlador = new PrestamoControlador();

        setLayout(new BorderLayout());
        add(panelPrincipalPrestamos, BorderLayout.CENTER);


        btnPrestar.addActionListener(e -> ejecutarPrestamo());


        btnBuscarId.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de devolución en construcción... ", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        });
    }

    private void ejecutarPrestamo() {
        String rut = txtRut.getText().trim();
        String isbn = txtIsbn.getText().trim();

        if(rut.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el RUT del estudiante y el ISBN del libro.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        controlador.registrarPrestamo(rut, isbn, this);

        txtRut.setText("");
        txtIsbn.setText("");

    }
}
