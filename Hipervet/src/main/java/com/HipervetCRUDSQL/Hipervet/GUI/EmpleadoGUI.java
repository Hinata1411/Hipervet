package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.EmpleadoDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class EmpleadoGUI extends JPanel {
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO(); // Instancia del DAO
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JTextField codigoPersonaField, primerNombreField, segundoNombreField, tercerNombreField;
    private JTextField primerApellidoField, segundoApellidoField, tercerApellidoField, fechaNacimientoField;
    private JTextField codigoPuestoField;

    public EmpleadoGUI() {
        setLayout(new BorderLayout());

        // Crear formulario para ingresar datos del empleado
        JPanel formularioPanel = new JPanel(new GridLayout(10, 2, 10, 10));

        formularioPanel.add(new JLabel("Código Persona:"));
        codigoPersonaField = new JTextField();
        formularioPanel.add(codigoPersonaField);

        formularioPanel.add(new JLabel("Primer Nombre:"));
        primerNombreField = new JTextField();
        formularioPanel.add(primerNombreField);

        formularioPanel.add(new JLabel("Segundo Nombre:"));
        segundoNombreField = new JTextField();
        formularioPanel.add(segundoNombreField);

        formularioPanel.add(new JLabel("Tercer Nombre:"));
        tercerNombreField = new JTextField();
        formularioPanel.add(tercerNombreField);

        formularioPanel.add(new JLabel("Primer Apellido:"));
        primerApellidoField = new JTextField();
        formularioPanel.add(primerApellidoField);

        formularioPanel.add(new JLabel("Segundo Apellido:"));
        segundoApellidoField = new JTextField();
        formularioPanel.add(segundoApellidoField);

        formularioPanel.add(new JLabel("Tercer Apellido:"));
        tercerApellidoField = new JTextField();
        formularioPanel.add(tercerApellidoField);

        formularioPanel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"));
        fechaNacimientoField = new JTextField();
        formularioPanel.add(fechaNacimientoField);

        formularioPanel.add(new JLabel("Código de Puesto:"));
        codigoPuestoField = new JTextField();
        formularioPanel.add(codigoPuestoField);

        // Panel para los botones
        JPanel botonesPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton agregarButton = new JButton("Agregar Empleado");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado(); // Método para agregar empleado
            }
        });
        botonesPanel.add(agregarButton);

        JButton eliminarButton = new JButton("Eliminar Empleado");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado(); // Método para eliminar empleado
            }
        });
        botonesPanel.add(eliminarButton);

        JButton actualizarButton = new JButton("Actualizar Empleado");
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEmpleado(); // Método para actualizar empleado
            }
        });
        botonesPanel.add(actualizarButton);

        // Añadir el panel de botones al lado del formulario
        formularioPanel.add(botonesPanel);

        // Crear la tabla de empleados con las nuevas columnas
        String[] columnas = {"Codigo Empleado", "Primer Nombre", "Segundo Nombre", "Tercer Nombre",
                "Primer Apellido", "Segundo Apellido", "Tercer Apellido",
                "Fecha de Nacimiento", "Codigo Puesto", "Descripción Puesto"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaEmpleados = new JTable(modeloTabla);

        // Cargar empleados desde la base de datos
        cargarEmpleados();

        // Panel para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);

        // Crear un JSplitPane para dividir el formulario y la tabla
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formularioPanel, scrollPane);
        splitPane.setDividerLocation(400); // Ajustar el tamaño del divisor

        add(splitPane, BorderLayout.CENTER);
    }

    // Método para cargar los empleados desde la base de datos
    private void cargarEmpleados() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar
        List<Empleado> empleados = empleadoDAO.obtenerEmpleados();
        for (Empleado empleado : empleados) {
            modeloTabla.addRow(new Object[]{
                    empleado.getCodigoEmpleado(),
                    empleado.getPrimerNombre(),
                    empleado.getSegundoNombre(),
                    empleado.getTercerNombre(),
                    empleado.getPrimerApellido(),
                    empleado.getSegundoApellido(),
                    empleado.getTercerApellido(),
                    empleado.getFechaNacimiento(),
                    empleado.getCodigoPuesto(),
                    empleado.getDescripcionPuesto()
            });
        }
    }

    // Método para agregar un empleado a la base de datos
    // Método para agregar un empleado a la base de datos
    private void agregarEmpleado() {
        try {
            // Validaciones
            if (codigoPersonaField.getText().isEmpty() || codigoPuestoField.getText().isEmpty()) {
                throw new IllegalArgumentException("El código de persona y el código de puesto son obligatorios.");
            }

            // Obtener los datos del formulario
            int codigoPersona = Integer.parseInt(codigoPersonaField.getText()); // CodigoPersona que se pasa
            String primerNombre = primerNombreField.getText();
            String segundoNombre = segundoNombreField.getText();
            String tercerNombre = tercerNombreField.getText();
            String primerApellido = primerApellidoField.getText();
            String segundoApellido = segundoApellidoField.getText();
            String tercerApellido = tercerApellidoField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();
            int codigoPuesto = Integer.parseInt(codigoPuestoField.getText());

            // Crear un nuevo empleado
            Empleado nuevoEmpleado = new Empleado();
            nuevoEmpleado.setCodigoPersona(codigoPersona); // Asignar el código persona
            nuevoEmpleado.setPrimerNombre(primerNombre);
            nuevoEmpleado.setSegundoNombre(segundoNombre);
            nuevoEmpleado.setTercerNombre(tercerNombre);
            nuevoEmpleado.setPrimerApellido(primerApellido);
            nuevoEmpleado.setSegundoApellido(segundoApellido);
            nuevoEmpleado.setTercerApellido(tercerApellido);
            nuevoEmpleado.setFechaNacimiento(java.time.LocalDate.parse(fechaNacimiento));
            nuevoEmpleado.setCodigoPuesto(codigoPuesto);

            // Llamar al DAO para insertar el nuevo empleado
            if (empleadoDAO.crearEmpleado(nuevoEmpleado)) {
                JOptionPane.showMessageDialog(this, "Empleado agregado exitosamente.");
                cargarEmpleados(); // Refrescar la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos en los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Método para eliminar un empleado
    private void eliminarEmpleado() {
        // Lógica para eliminar un empleado
    }

    // Método para actualizar un empleado
    private void actualizarEmpleado() {
        // Lógica para actualizar un empleado
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Empleado GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(new EmpleadoGUI());
        frame.setVisible(true);
    }
}
