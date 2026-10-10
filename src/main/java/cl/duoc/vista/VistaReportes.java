package cl.duoc.vista;

import cl.duoc.controlador.ReportesControlador;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VistaReportes extends JPanel {
    private JPanel PanelReportes;
    private JLabel lblReporte;
    private JComboBox comboTipoReporte;
    private JButton btnGenerar;
    private JScrollPane scrollResultados;
    private JTable tablaResultados;

private final ReportesControlador controlador;
public VistaReportes() {
    this.controlador = new ReportesControlador();

    setLayout(new BorderLayout());
    add(PanelReportes, BorderLayout.CENTER);

    comboTipoReporte.addItem("Libros en Préstamo (Activos)");
    comboTipoReporte.addItem("Libros más prestados");
    comboTipoReporte.addItem("Historial por Estudiante");

    controlador.cargarPrestamosActivos(tablaResultados, this);

    btnGenerar.addActionListener(e -> {
        int seleccion = comboTipoReporte.getSelectedIndex();

        if (seleccion ==  0) {
            controlador.cargarPrestamosActivos(tablaResultados, this);
        }else if (seleccion ==  1) {
            controlador.cargarLibrosPrestados(tablaResultados, this);
        }else if (seleccion ==  2) {

            String input = JOptionPane.showInputDialog(this, "Ingrese el ID del estudiante a consultar: ");
            if (input != null && !input.trim().isEmpty()) {
                try{
                    int idEstudiante = Integer.parseInt(input.trim());
                    controlador.cargarHistorialEstudiante(idEstudiante, tablaResultados, this);
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(this, "El ID del estudiante no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    });
}

}
