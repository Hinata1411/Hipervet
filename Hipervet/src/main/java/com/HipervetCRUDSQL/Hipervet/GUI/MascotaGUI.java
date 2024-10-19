package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.FichaMascotaDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.FichaMascota;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MascotaGUI extends JPanel {

        private FichaMascotaDAO mascotaDAO = new FichaMascotaDAO();
        private JTable tablaMascotas;
        private DefaultTableModel modeloTabla;
        private JTextField numeroFichaField, especieField, codigoRazaField, nombreField, tallaField, generoField;
        private JDateChooser fechaNacimientoChooser;

        public MascotaGUI() {
            setLayout(new BorderLayout());

            // Panel principal para contener el formulario y los botones
            JPanel panelIzquierdo = new JPanel();
            panelIzquierdo.setLayout(new BorderLayout()); // Usamos BorderLayout para organizar el formulario y los botones

            // Formulario para ingresar los datos de la mascota
            JPanel formularioPanel = new JPanel(new GridLayout(7, 2, 5, 5));

            formularioPanel.add(new JLabel("Número Ficha:"));
            numeroFichaField = new JTextField();
            numeroFichaField.setEnabled(false); // Deshabilitar ya que se generará automáticamente
            formularioPanel.add(numeroFichaField);

            formularioPanel.add(new JLabel("Especie:"));
            especieField = new JTextField();
            formularioPanel.add(especieField);

            formularioPanel.add(new JLabel("Código Raza:"));
            codigoRazaField = new JTextField();
            formularioPanel.add(codigoRazaField);

            formularioPanel.add(new JLabel("Nombre:"));
            nombreField = new JTextField();
            formularioPanel.add(nombreField);

            formularioPanel.add(new JLabel("Fecha de Nacimiento:"));
            fechaNacimientoChooser = new JDateChooser();
            formularioPanel.add(fechaNacimientoChooser);

            formularioPanel.add(new JLabel("Talla:"));
            tallaField = new JTextField();
            formularioPanel.add(tallaField);

            formularioPanel.add(new JLabel("Género:"));
            generoField = new JTextField();
            formularioPanel.add(generoField);

            // Panel para los botones de agregar, actualizar y eliminar
            JPanel botonesPanel = new JPanel(new GridLayout(3, 1, 10, 10));

            JButton agregarButton = new JButton("Agregar");
            agregarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    agregarMascota();
                }
            });
            botonesPanel.add(agregarButton);

            JButton actualizarButton = new JButton("Actualizar");
            actualizarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    actualizarMascota();
                }
            });
            botonesPanel.add(actualizarButton);

            JButton eliminarButton = new JButton("Eliminar");
            eliminarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eliminarMascota();
                }
            });
            botonesPanel.add(eliminarButton);

            // Añadimos los paneles de formulario y botones al panel izquierdo
            panelIzquierdo.add(formularioPanel, BorderLayout.CENTER); // Formulario arriba
            panelIzquierdo.add(botonesPanel, BorderLayout.SOUTH); // Botones abajo

            add(panelIzquierdo, BorderLayout.WEST); // Añadimos el panel izquierdo a la interfaz

            // Tabla para mostrar las mascotas
            String[] columnas = {"Número Ficha", "Especie", "Código Raza", "Nombre", "Fecha de Nacimiento", "Talla", "Género"};
            modeloTabla = new DefaultTableModel(columnas, 0);
            tablaMascotas = new JTable(modeloTabla);

            tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tablaMascotas.setRowSelectionAllowed(true);

            tablaMascotas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting()) {
                        cargarDatosEnFormulario();
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(tablaMascotas);
            add(scrollPane, BorderLayout.CENTER); // La tabla ocupará el espacio central

            cargarMascotas();
        }

        private void cargarMascotas() {
            modeloTabla.setRowCount(0);
            List<FichaMascota> mascotas = mascotaDAO.obtenerMascotas();
            for (FichaMascota mascota : mascotas) {
                modeloTabla.addRow(new Object[]{
                        mascota.getNumeroFicha(),
                        mascota.getCodigoEspecie(),
                        mascota.getCodigoRaza(),
                        mascota.getNombre(),
                        mascota.getFechaNacimiento(),
                        mascota.getTalla(),
                        mascota.getGenero()
                });
            }
        }

        private void cargarDatosEnFormulario() {
            int filaSeleccionada = tablaMascotas.getSelectedRow();
            if (filaSeleccionada != -1) {
                numeroFichaField.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                especieField.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                codigoRazaField.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                nombreField.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                fechaNacimientoChooser.setDate(Date.valueOf(modeloTabla.getValueAt(filaSeleccionada, 4).toString()));
                tallaField.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
                generoField.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            }
        }

        private void limpiarCampos() {
            numeroFichaField.setText("");
            especieField.setText("");
            codigoRazaField.setText("");
            nombreField.setText("");
            fechaNacimientoChooser.setDate(null);
            tallaField.setText("");
            generoField.setText("");
        }

        private void agregarMascota() {
            try {
                int numeroFicha = mascotaDAO.obtenerSiguienteNumeroFicha();
                numeroFichaField.setText(String.valueOf(numeroFicha));

                FichaMascota nuevaMascota = new FichaMascota();
                nuevaMascota.setNumeroFicha(numeroFicha);
                nuevaMascota.setCodigoEspecie(especieField.getText());
                nuevaMascota.setCodigoRaza(codigoRazaField.getText());
                nuevaMascota.setNombre(nombreField.getText());
                nuevaMascota.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());
                nuevaMascota.setTalla(tallaField.getText());
                nuevaMascota.setGenero(generoField.getText());

                if (mascotaDAO.crearMascota(nuevaMascota)) {
                    JOptionPane.showMessageDialog(this, "Mascota agregada exitosamente.");
                    cargarMascotas();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void actualizarMascota() {
            int filaSeleccionada = tablaMascotas.getSelectedRow();
            if (filaSeleccionada != -1) {
                int numeroFicha = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());

                FichaMascota mascotaActualizada = new FichaMascota();
                mascotaActualizada.setNumeroFicha(numeroFicha);
                mascotaActualizada.setCodigoEspecie(especieField.getText());
                mascotaActualizada.setCodigoRaza(codigoRazaField.getText());
                mascotaActualizada.setNombre(nombreField.getText());
                mascotaActualizada.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());
                mascotaActualizada.setTalla(tallaField.getText());
                mascotaActualizada.setGenero(generoField.getText());

                if (mascotaDAO.actualizarMascota(numeroFicha, mascotaActualizada)) {
                    JOptionPane.showMessageDialog(this, "Mascota actualizada exitosamente.");
                    cargarMascotas();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void eliminarMascota() {
            int filaSeleccionada = tablaMascotas.getSelectedRow();
            if (filaSeleccionada != -1) {
                int numeroFicha = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                if (mascotaDAO.eliminarMascota(numeroFicha)) {
                    JOptionPane.showMessageDialog(this, "Mascota eliminada exitosamente.");
                    cargarMascotas();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Mascota GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new MascotaGUI());
            frame.setVisible(true);
        }
}
