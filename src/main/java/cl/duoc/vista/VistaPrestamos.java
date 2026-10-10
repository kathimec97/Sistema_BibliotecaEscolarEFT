package cl.duoc.vista;

import cl.duoc.controlador.PrestamoControlador;
import cl.duoc.modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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
        cargarTablaPrestamosActivo();


        btnPrestar.addActionListener(e -> ejecutarPrestamo());

        btnConfirmarDevolucion.addActionListener(e -> {
            int filaSeleccionada = tablaPrestamosActivos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debo seleccionar un préstamo de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idPrestamo = Integer.parseInt(tablaPrestamosActivos.getValueAt(filaSeleccionada, 0).toString());
            controlador.procesarDevolucion(idPrestamo, this);
            cargarTablaPrestamosActivo();
        });


        btnBuscarId.addActionListener(e -> {
            String textoId = txtFiltroId.getText().trim();
            if (textoId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese el ID del prestamo", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int idBuscado = Integer.parseInt(textoId);

                Prestamo p = controlador.buscarPrestamoPorId(idBuscado);

                if (p != null && !p.isDevuelto()) {
                    boolean encontrado = false;
                    for (int i = 0; i < tablaPrestamosActivos.getRowCount(); i++) {
                        int idTabla = Integer.parseInt(tablaPrestamosActivos.getValueAt(i, 0).toString());
                        if (idTabla == idBuscado) {
                            tablaPrestamosActivos.setRowSelectionInterval(i, i);
                            tablaPrestamosActivos.scrollRectToVisible(tablaPrestamosActivos.getCellRect(i, 0, true));
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        JOptionPane.showMessageDialog(this, "El préstamo existe, pero ya fue devuelto o no está en la lista activa.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún préstamo activo con el ID: " + idBuscado, "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    /**
     * Válida los campos de texto del formulario (RUT e ISBN) y delega el
     * registro del nuevo préstamo al controlador
     */
    private void ejecutarPrestamo() {
        String rut = txtRut.getText().trim();
        String isbn = txtIsbn.getText().trim();

        if (rut.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el RUT del estudiante y el ISBN del libro.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        controlador.registrarPrestamo(rut, isbn, this);

        txtRut.setText("");
        txtIsbn.setText("");
    }

    /**
     * Carga y actualiza los datos de la tabla visual con la lista de préstamos activos pendientes
     * de devolución, utilizando DefaultTableModel
     */
    public void cargarTablaPrestamosActivo(){
            String[] columnas = {"ID Préstamo", "Rut Estudiante", "Nombre Estudiante", "ISBN", "Libro", "F. Préstamo", "F. Devolución"};
            DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
        };

            List<Prestamo> lista = controlador.obtenerPrestamosActivos();

            for(Prestamo p : lista){
                modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getEstudiante().getRut(),
                        p.getEstudiante().getNombre(),
                        p.getLibro().getIsbn(),
                        p.getLibro().getTitulo(),
                        p.getFechaPrestamo(),
                        p.getFechaDevolucion()
                });
            }
            tablaPrestamosActivos.setModel(modeloTabla);
    }
}
