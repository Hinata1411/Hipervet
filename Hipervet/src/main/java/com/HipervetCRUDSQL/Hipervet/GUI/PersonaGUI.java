package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.EmpleadoDAO;
import com.HipervetCRUDSQL.Hipervet.Conexion.PersonaDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;
import com.HipervetCRUDSQL.Hipervet.Entidades.Persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class PersonaGUI extends JPanel {
    private PersonaDAO personaDAO = new PersonaDAO(); // Instancia del DAO
    private JTable tablaPersonas;
    private DefaultTableModel modeloTabla;
    private JTextField codigoPersonaField, primerNombreField, segundoNombreField, tercerNombreField;
    private JTextField primerApellidoField, segundoApellidoField, tercerApellidoField, fechaNacimientoField;
    private JTextField tipoPersonaField;

    // Declarar el JComboBox aquí
    private JComboBox<String> tipoPersonaComboBox;

    public PersonaGUI() {
        setLayout(new BorderLayout());

        // Crear formulario para ingresar datos de la persona
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

        formularioPanel.add(new JLabel("Tipo de Persona:"));
        // Sustituyendo el campo de texto con un JComboBox para seleccionar el tipo de persona
        tipoPersonaComboBox = new JComboBox<>(new String[]{"Empleado", "Cliente"});
        formularioPanel.add(tipoPersonaComboBox);

        // Panel para los botones
        JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton agregarClienteButton = new JButton("Agregar Cliente");
        agregarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPersona(); // Método para agregar una persona
            }
        });
        botonesPanel.add(agregarClienteButton);

        JButton agregarEmpleadoButton = new JButton("Agregar Empleado");
        agregarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPersona(); // Método para agregar una persona
            }
        });
        botonesPanel.add(agregarEmpleadoButton);

        // Añadir el panel de botones al formulario
        formularioPanel.add(botonesPanel);

        // Crear la tabla de personas con las columnas correspondientes
        String[] columnas = {"Código Persona", "Primer Nombre", "Segundo Nombre", "Tercer Nombre",
                "Primer Apellido", "Segundo Apellido", "Tercer Apellido", "Fecha de Nacimiento", "Tipo Persona"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPersonas = new JTable(modeloTabla);

        // Cargar personas desde la base de datos
        cargarPersonas();

        // Panel para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPersonas);

        // Crear un JSplitPane para dividir el formulario y la tabla
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formularioPanel, scrollPane);
        splitPane.setDividerLocation(400); // Ajustar el tamaño del divisor

        add(splitPane, BorderLayout.CENTER);
    }

    // Método para cargar las personas desde la base de datos
    private void cargarPersonas() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar
        List<Persona> personas = personaDAO.obtenerPersonas();
        for (Persona persona : personas) {
            modeloTabla.addRow(new Object[]{
                    persona.getCodigoPersona(),
                    persona.getPrimerNombre(),
                    persona.getSegundoNombre(),
                    persona.getTercerNombre(),
                    persona.getPrimerApellido(),
                    persona.getSegundoApellido(),
                    persona.getTercerApellido(),
                    persona.getFechaNacimiento(),
                    persona.getTipoPersona()
            });
        }
    }

    // Método para agregar una persona a la base de datos
    private void agregarPersona() {
        try {
            // Validaciones para el código de persona
            int codigoPersona = Integer.parseInt(codigoPersonaField.getText());
            if (codigoPersona <= 0) {
                throw new IllegalArgumentException("El código de persona debe ser mayor que cero.");
            }

            // Validación para el Primer Apellido
            String primerApellido = primerApellidoField.getText();
            if (primerApellido == null || primerApellido.isEmpty()) {
                throw new IllegalArgumentException("El Primer Apellido es obligatorio.");
            }

            // Obtener los demás datos
            String primerNombre = primerNombreField.getText();
            String segundoNombre = segundoNombreField.getText();
            String tercerNombre = tercerNombreField.getText();
            String segundoApellido = segundoApellidoField.getText();
            String tercerApellido = tercerApellidoField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();

            // Obtener el tipo de persona desde el JComboBox y utilizar valores cortos
            String tipoPersona = (String) tipoPersonaComboBox.getSelectedItem();
            if (tipoPersona.equals("Empleado")) {
                tipoPersona = "E"; // Valor corto para Empleado
            } else if (tipoPersona.equals("Cliente")) {
                tipoPersona = "C"; // Valor corto para Cliente
            }

            // Crear una nueva persona
            Persona nuevaPersona = new Persona();
            nuevaPersona.setCodigoPersona(codigoPersona);
            nuevaPersona.setPrimerNombre(primerNombre);
            nuevaPersona.setSegundoNombre(segundoNombre);
            nuevaPersona.setTercerNombre(tercerNombre);
            nuevaPersona.setPrimerApellido(primerApellido);
            nuevaPersona.setSegundoApellido(segundoApellido);
            nuevaPersona.setTercerApellido(tercerApellido);
            nuevaPersona.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento).toLocalDate());
            nuevaPersona.setTipoPersona(tipoPersona);

            // Llamar al DAO para insertar la nueva persona
            if (personaDAO.crearPersona(nuevaPersona)) {
                JOptionPane.showMessageDialog(this, "Persona agregada exitosamente.");
                cargarPersonas(); // Refrescar la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la persona.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código de persona válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Persona GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(new PersonaGUI());
        frame.setVisible(true);
    }
}



