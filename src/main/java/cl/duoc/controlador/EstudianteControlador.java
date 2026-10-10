package cl.duoc.controlador;

import cl.duoc.dao.impl.EstudianteDAOImpl;
import cl.duoc.modelo.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador para gestionar la lógica de los estudiantes.
 * Conecta la vistaEstudiantes con EstudianteDAOImpl.
 */
public class EstudianteControlador {

    private final EstudianteDAOImpl estudianteDAO = new EstudianteDAOImpl();

    /**
     * Carga todos los estudiantes registrados en la JTable de la vista
     */
    public void cargarTabla(JTable tabla){
        String[] columnas = {"ID", "RUT", "Nombre", "Curso", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            List<Estudiante> estudiantes = estudianteDAO.listarTodos();
            for (Estudiante estudiante : estudiantes) {
                modelo.addRow(new Object[]{
                        estudiante.getId(),
                        estudiante.getRut(),
                        estudiante.getNombre(),
                        estudiante.getCurso(),
                        estudiante.getCorreo()
                });
            }
            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(1).setWidth(0);
        }catch (SQLException e){
            System.err.println("Error al obtener datos de la tabla: " + e.getMessage());
        }


    }

    /**
     * Valida y guarda un nuevo Estudiante
     */
    public void guardarEstudiante(String rut, String nombre, String curso, String correo, JPanel panelPadre){
        if(rut.isEmpty() || nombre.isEmpty() || curso.isEmpty() || correo.isEmpty()){
            JOptionPane.showMessageDialog(panelPadre, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            Estudiante nuevoEstudiante = new Estudiante(0, nombre, rut, correo, curso);
            estudianteDAO.insertar(nuevoEstudiante);
            JOptionPane.showMessageDialog(panelPadre, "Estudiante guardado con éxito", "Exito", JOptionPane.INFORMATION_MESSAGE);

        }catch (SQLException e){
            JOptionPane.showMessageDialog(panelPadre, "Error al guardar estudiante: " + e.getMessage());
        }
    }

    /**
     * Válida y actualiza los datos de un estudiante
     */
    public void editarEstudiante(int id, String rut, String nombre, String curso, String correo, JPanel panelPadre){
      if(rut.isEmpty() || nombre.isEmpty() || curso.isEmpty() || correo.isEmpty()){
          JOptionPane.showMessageDialog(panelPadre, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
          return;
      }

      try{
          Estudiante estudianteEditado = new Estudiante(id, nombre, rut, correo, curso);
          estudianteDAO.actualizar(estudianteEditado);
          JOptionPane.showMessageDialog(panelPadre, "Estudiante actualizado con éxito", "Exito", JOptionPane.INFORMATION_MESSAGE);

      }catch (SQLException e){
          JOptionPane.showMessageDialog(panelPadre, "Error al actualizar estudiante: " + e.getMessage());
      }
    }

    /**
     * Eliminar estudiante por su ID
     */

    public void eliminarEstudiante(int id, JPanel panelPadre){
        int confirmacion = JOptionPane.showConfirmDialog(panelPadre, "¿Estás seguro que desea eliminar este estudiante?", "Confirmar",  JOptionPane.YES_NO_OPTION);

        if(confirmacion == JOptionPane.YES_OPTION){
            try {
                estudianteDAO.eliminar(id);
                JOptionPane.showMessageDialog(panelPadre, "Estudiante eliminado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);

            }catch (SQLException e) {
                if(e.getErrorCode() == 1451 || e.getMessage().contains("foreign key constraint fails" )){
                    JOptionPane.showMessageDialog(panelPadre,
                            "No se puede eliminar este estudiante porque tiene préstamos registrados\n",
                            "Operación denegada",
                            JOptionPane.WARNING_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(panelPadre, "Error al eliminar estudiante: " + e.getMessage());
                }
            }
        }
    }


}
