package cl.duoc.vista;

import cl.duoc.controlador.LibroControlador;
import cl.duoc.modelo.Categoria;
import cl.duoc.modelo.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Interfaz gráfica para la gestion de los libros.
 *
 * Delega todas las operaciones de guardado, edición y eliminación al LibroControlador
 * manteniendo la vista sin lógica de base de datos.
 * @author Katherine
 */
public class VistaLibros extends JPanel {
    private JPanel PanelLibros;
    private JLabel lblLibros;
    private JTextField txtIsbn;
    private JLabel lblIsbn;
    private JTextField txtTitulo;
    private JLabel lblTitulo;
    private JLabel lblAutor;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JLabel lblEditorial;
    private JComboBox comboCategoria;
    private JLabel lblCategoria;
    private JLabel lblStock;
    private JTextField txtStock;
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JScrollPane scrollLibros;
    private JTable tablaLibros;

    private final LibroControlador controlador;
    private int libroSeleccionadoId = -1;

    public VistaLibros() throws SQLException {
        this.controlador = new LibroControlador();

        setLayout(new BorderLayout());
        add(PanelLibros, BorderLayout.CENTER);

        controlador.cargarCategorias(comboCategoria);
        controlador.cargarTabla(tablaLibros);

        //Boton Guardar
        btnGuardar.addActionListener(e -> {
            Categoria categoriaSeleccionada = (Categoria) comboCategoria.getSelectedItem();
            try {
                controlador.guardarLibro(
                        txtIsbn.getText().trim(),
                        txtTitulo.getText().trim(),
                        txtAutor.getText().trim(),
                        txtEditorial.getText().trim(),
                        categoriaSeleccionada,
                        txtStock.getText().trim(),
                        this
                );
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                controlador.cargarTabla(tablaLibros);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            limpiarFormulario();
        });

        //Boton Editar
        btnEditar.addActionListener(e -> {
            if (libroSeleccionadoId == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla para editar",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Categoria categoriaSeleccionada = (Categoria) comboCategoria.getSelectedItem();
            try {
                controlador.editarLibro(
                        libroSeleccionadoId,
                        txtIsbn.getText().trim(),
                        txtTitulo.getText().trim(),
                        txtAutor.getText().trim(),
                        txtEditorial.getText().trim(),
                        categoriaSeleccionada,
                        txtStock.getText().trim(),
                        this

                );
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                controlador.cargarTabla(tablaLibros);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            limpiarFormulario();
        });

        //Boton Eliminar
        btnEliminar.addActionListener(e -> {
            if (libroSeleccionadoId == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.eliminarLibro(libroSeleccionadoId, this);
            try {
                controlador.cargarTabla(tablaLibros);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            limpiarFormulario();
        });

        tablaLibros.addMouseListener(new MouseAdapter() {


            public void mouseClicked(MouseEvent e) {
                int fila = tablaLibros.getSelectedRow();
                if (fila >= 0) {

                    libroSeleccionadoId = Integer.parseInt(tablaLibros.getModel().getValueAt(fila, 0).toString());

                    txtIsbn.setText(tablaLibros.getModel().getValueAt(fila, 1).toString());
                    txtTitulo.setText(tablaLibros.getModel().getValueAt(fila, 2).toString());
                    txtAutor.setText(tablaLibros.getModel().getValueAt(fila, 3).toString());
                    txtEditorial.setText(tablaLibros.getModel().getValueAt(fila, 4).toString());

                    String nombreCategoria = tablaLibros.getModel().getValueAt(fila, 5).toString();
                    for (int i = 0; i < comboCategoria.getItemCount(); i++) {

                        Categoria categoriaSeleccionada = (Categoria) comboCategoria.getItemAt(i);
                        if (categoriaSeleccionada.getNombre().equalsIgnoreCase(nombreCategoria)) {
                            comboCategoria.setSelectedIndex(i);
                            break;
                        }
                    }
                    txtStock.setText(tablaLibros.getModel().getValueAt(fila, 6).toString());
                }
            }
        });
    }
        /**
         * Metodo auxiliar para limpiar los campos de texto y resetear la selección.
         */
        private void limpiarFormulario() {
            txtIsbn.setText("");
            txtTitulo.setText("");
            txtAutor.setText("");
            txtEditorial.setText("");
            if (comboCategoria.getItemCount() > 0) {
                comboCategoria.setSelectedIndex(0); // Volver al primer elemento
            }
            txtStock.setText("");
            libroSeleccionadoId = -1; // Resetear el ID seleccionado
            tablaLibros.clearSelection();
        }
    }

