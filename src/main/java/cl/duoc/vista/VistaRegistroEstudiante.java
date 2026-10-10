package cl.duoc.vista;

import cl.duoc.controlador.EstudianteControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaRegistroEstudiante extends JPanel{
    private JPanel panelPrincipal;
    private JLabel lblGestionE;
    private JTextField txtRut;
    private JLabel lblRut;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JLabel lblCurso;
    private JTextField txtCurso;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JScrollPane scrollEstudiante;
    private JTable tablaEstudiantes;

    private final EstudianteControlador controlador;
    private int estudianteSeleccionadoId = -1;

    public VistaRegistroEstudiante() {
        this.controlador = new EstudianteControlador();

        setLayout(new BorderLayout());
        add(panelPrincipal,  BorderLayout.CENTER);

        controlador.cargarTabla(tablaEstudiantes);

        btnGuardar.addActionListener(e ->{
            controlador.guardarEstudiante(
                    txtRut.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCurso.getText().trim(),
                    txtCorreo.getText().trim(),
                    this
            );
            controlador.cargarTabla(tablaEstudiantes);
            limpiarFormulario();
        });

        btnEditar.addActionListener(e ->{
            if(estudianteSeleccionadoId == -1){
                JOptionPane.showMessageDialog(this, "Seleccione el estudiante que desea editar", "Avsiso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.editarEstudiante(
                    estudianteSeleccionadoId,
                    txtRut.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCurso.getText().trim(),
                    txtCorreo.getText().trim(),
                    this
            );
            controlador.cargarTabla(tablaEstudiantes);
            limpiarFormulario();
        });

        btnEliminar.addActionListener(e ->{
            if(estudianteSeleccionadoId == -1){
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla para eliminar", "Avsiso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.eliminarEstudiante(estudianteSeleccionadoId, this);
            controlador.cargarTabla(tablaEstudiantes);
            limpiarFormulario();
        });

        tablaEstudiantes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaEstudiantes.getSelectedRow();
                if(fila >= 0){
                    estudianteSeleccionadoId = Integer.parseInt(tablaEstudiantes.getModel().getValueAt(fila, 0).toString());
                    txtRut.setText(tablaEstudiantes.getModel().getValueAt(fila, 1).toString());
                    txtNombre.setText(tablaEstudiantes.getModel().getValueAt(fila, 2).toString());
                    txtCurso.setText(tablaEstudiantes.getModel().getValueAt(fila, 3).toString());
                    txtCorreo.setText(tablaEstudiantes.getModel().getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private void limpiarFormulario(){
        txtRut.setText(" ");
        txtNombre.setText(" ");
        txtCurso.setText(" ");
        txtCorreo.setText(" ");
        estudianteSeleccionadoId = -1;
        tablaEstudiantes.clearSelection();
    }
}
