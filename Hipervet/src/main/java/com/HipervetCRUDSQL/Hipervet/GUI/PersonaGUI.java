package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.PersonaDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;
import com.HipervetCRUDSQL.Hipervet.Entidades.Persona;
import com.toedter.calendar.JDateChooser;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class PersonaGUI extends JPanel {

        private PersonaDAO personaDAO = new PersonaDAO();
        private JTable tablaPersonas;
        private DefaultTableModel modeloTabla;
        private JTextField codigoPersonaField, primerNombreField, segundoNombreField, tercerNombreField;
        private JTextField primerApellidoField, segundoApellidoField, tercerApellidoField;
        private JDateChooser fechaNacimientoChooser;
        private JComboBox<String> tipoPersonaComboBox;

        public PersonaGUI() {
            setLayout(new BorderLayout());

            JPanel formularioPanel = new JPanel(new GridLayout(10, 2, 5, 5));

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

            formularioPanel.add(new JLabel("Fecha de Nacimiento:"));
            fechaNacimientoChooser = new JDateChooser();
            formularioPanel.add(fechaNacimientoChooser);

            formularioPanel.add(new JLabel("Tipo de Persona:"));
            tipoPersonaComboBox = new JComboBox<>(new String[]{"Empleado", "Cliente"});
            formularioPanel.add(tipoPersonaComboBox);

            JPanel botonesPanel = new JPanel(new GridLayout(3, 1, 10, 10));

            JButton agregarButton = new JButton("Agregar");
            agregarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    agregarPersona();
                }
            });
            botonesPanel.add(agregarButton);

            JButton actualizarButton = new JButton("Actualizar");
            actualizarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    actualizarPersona();
                }
            });
            botonesPanel.add(actualizarButton);

            JButton eliminarButton = new JButton("Eliminar");
            eliminarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eliminarPersona();
                }
            });
            botonesPanel.add(eliminarButton);

            add(botonesPanel, BorderLayout.EAST);

            String[] columnas = {"Código Persona", "Primer Nombre", "Segundo Nombre", "Tercer Nombre",
                    "Primer Apellido", "Segundo Apellido", "Tercer Apellido", "Fecha de Nacimiento", "Tipo Persona"};
            modeloTabla = new DefaultTableModel(columnas, 0);
            tablaPersonas = new JTable(modeloTabla);

            tablaPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tablaPersonas.setRowSelectionAllowed(true);

            tablaPersonas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting()) {
                        cargarDatosEnFormulario();
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(tablaPersonas);
            add(scrollPane, BorderLayout.CENTER);
            add(formularioPanel, BorderLayout.WEST);

            cargarPersonas();
        }

        private void cargarPersonas() {
            modeloTabla.setRowCount(0);
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
                        persona.getTipoPersona().equals("E") ? "Empleado" : "Cliente"
                });
            }
        }

        private void cargarDatosEnFormulario() {
            int filaSeleccionada = tablaPersonas.getSelectedRow();
            if (filaSeleccionada != -1) {
                codigoPersonaField.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                primerNombreField.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                segundoNombreField.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                tercerNombreField.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                primerApellidoField.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                segundoApellidoField.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
                tercerApellidoField.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
                fechaNacimientoChooser.setDate(Date.valueOf(modeloTabla.getValueAt(filaSeleccionada, 7).toString()));
                tipoPersonaComboBox.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 8));
            }
        }

        private void limpiarCampos() {
            codigoPersonaField.setText("");
            primerNombreField.setText("");
            segundoNombreField.setText("");
            tercerNombreField.setText("");
            primerApellidoField.setText("");
            segundoApellidoField.setText("");
            tercerApellidoField.setText("");
            fechaNacimientoChooser.setDate(null);
            tipoPersonaComboBox.setSelectedIndex(0);
        }

        private void agregarPersona() {
            try {
                int codigoPersona = personaDAO.obtenerSiguienteCodigoPersona();
                codigoPersonaField.setText(String.valueOf(codigoPersona));

                Persona nuevaPersona = new Persona();
                nuevaPersona.setCodigoPersona(codigoPersona);
                nuevaPersona.setPrimerNombre(primerNombreField.getText());
                nuevaPersona.setSegundoNombre(segundoNombreField.getText());
                nuevaPersona.setTercerNombre(tercerNombreField.getText());
                nuevaPersona.setPrimerApellido(primerApellidoField.getText());
                nuevaPersona.setSegundoApellido(segundoApellidoField.getText());
                nuevaPersona.setTercerApellido(tercerApellidoField.getText());
                nuevaPersona.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());
                nuevaPersona.setTipoPersona(tipoPersonaComboBox.getSelectedItem().toString().equals("Empleado") ? "E" : "C");

                if (personaDAO.crearPersona(nuevaPersona)) {
                    JOptionPane.showMessageDialog(this, "Persona agregada exitosamente.");
                    cargarPersonas();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la persona.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void actualizarPersona() {
            int filaSeleccionada = tablaPersonas.getSelectedRow();
            if (filaSeleccionada != -1) {
                int codigoPersona = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());

                Persona personaActualizada = new Persona();
                personaActualizada.setCodigoPersona(codigoPersona);
                personaActualizada.setPrimerNombre(primerNombreField.getText());
                personaActualizada.setSegundoNombre(segundoNombreField.getText());
                personaActualizada.setTercerNombre(tercerNombreField.getText());
                personaActualizada.setPrimerApellido(primerApellidoField.getText());
                personaActualizada.setSegundoApellido(segundoApellidoField.getText());
                personaActualizada.setTercerApellido(tercerApellidoField.getText());
                personaActualizada.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());
                personaActualizada.setTipoPersona(tipoPersonaComboBox.getSelectedItem().toString().equals("Empleado") ? "E" : "C");

                if (personaDAO.actualizarPersona(codigoPersona, personaActualizada)) {
                    JOptionPane.showMessageDialog(this, "Actualizado con éxito.");
                    cargarPersonas();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la persona.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void eliminarPersona() {
            int filaSeleccionada = tablaPersonas.getSelectedRow();
            if (filaSeleccionada != -1) {
                int codigoPersona = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());

                if (!personaDAO.tieneDependenciasEnEmpleado(codigoPersona)) {
                    if (personaDAO.eliminarPersona(codigoPersona)) {
                        JOptionPane.showMessageDialog(this, "Eliminado con éxito.");
                        cargarPersonas();
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar la persona.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar la persona porque tiene dependencias.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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




