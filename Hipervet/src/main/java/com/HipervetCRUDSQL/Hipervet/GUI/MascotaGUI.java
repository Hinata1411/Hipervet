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
        private JTextField numeroFichaField, nombreField;
        private JComboBox<String> especieComboBox; // JComboBox para especies
        private JComboBox<String> razaComboBox; // JComboBox para razas
        private JComboBox<String> tallaComboBox; // JComboBox para tallas
        private JComboBox<String> generoComboBox; // JComboBox para género
        private JDateChooser fechaNacimientoChooser;

        public MascotaGUI() {
            setLayout(new BorderLayout());

            // Panel principal para contener el formulario y los botones
            JPanel panelIzquierdo = new JPanel();
            panelIzquierdo.setLayout(new BorderLayout());

            // Formulario para ingresar los datos de la mascota
            JPanel formularioPanel = new JPanel(new GridLayout(8, 2, 5, 5));

            formularioPanel.add(new JLabel("Número Ficha:"));
            numeroFichaField = new JTextField();
            numeroFichaField.setEnabled(false);
            formularioPanel.add(numeroFichaField);

            formularioPanel.add(new JLabel("Especie:"));
            especieComboBox = new JComboBox<>();
            cargarEspeciesEnComboBox(); // Llenar el JComboBox con las especies
            formularioPanel.add(especieComboBox);

            formularioPanel.add(new JLabel("Raza:"));
            razaComboBox = new JComboBox<>();
            cargarRazasEnComboBox(); // Llenar el JComboBox con las razas
            formularioPanel.add(razaComboBox);

            formularioPanel.add(new JLabel("Nombre:"));
            nombreField = new JTextField();
            formularioPanel.add(nombreField);

            formularioPanel.add(new JLabel("Fecha de Nacimiento:"));
            fechaNacimientoChooser = new JDateChooser();
            formularioPanel.add(fechaNacimientoChooser);

            formularioPanel.add(new JLabel("Talla:"));
            tallaComboBox = new JComboBox<>(new String[]{"1 - Pequeña", "2 - Mediana", "3 - Grande"});
            formularioPanel.add(tallaComboBox);

            formularioPanel.add(new JLabel("Género:"));
            generoComboBox = new JComboBox<>(new String[]{"H", "M"}); // Opciones de género
            formularioPanel.add(generoComboBox);

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

            panelIzquierdo.add(formularioPanel, BorderLayout.CENTER);
            panelIzquierdo.add(botonesPanel, BorderLayout.SOUTH);
            add(panelIzquierdo, BorderLayout.WEST);

            // Tabla para mostrar las mascotas
            String[] columnas = {"Número Ficha", "Especie", "Raza", "Nombre", "Fecha de Nacimiento", "Talla", "Género"};
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
            add(scrollPane, BorderLayout.CENTER);

            cargarMascotas();
        }

        // Método para llenar el JComboBox con las especies
        private void cargarEspeciesEnComboBox() {
            List<String[]> especies = mascotaDAO.obtenerEspecies();
            especieComboBox.removeAllItems();
            for (String[] especie : especies) {
                especieComboBox.addItem(especie[0] + " - " + especie[1]);
            }
        }

        // Método para llenar el JComboBox con las razas
        private void cargarRazasEnComboBox() {
            List<String[]> razas = mascotaDAO.obtenerRazas();
            razaComboBox.removeAllItems();
            for (String[] raza : razas) {
                razaComboBox.addItem(raza[0] + " - " + raza[1]);
            }
        }

    private void cargarMascotas() {
        modeloTabla.setRowCount(0);
        List<FichaMascota> mascotas = mascotaDAO.obtenerMascotas();
        for (FichaMascota mascota : mascotas) {
            modeloTabla.addRow(new Object[]{
                    mascota.getNumeroFicha(),
                    mascota.getCodigoEspecie() != null ? mascota.getCodigoEspecie() : "Desconocido",
                    mascota.getCodigoRaza() != null ? mascota.getCodigoRaza() : "Desconocido",
                    mascota.getNombre() != null ? mascota.getNombre() : "Desconocido",
                    mascota.getFechaNacimiento() != null ? mascota.getFechaNacimiento() : "Desconocida",
                    getDescripcionTalla(mascota.getTalla()), // Obtener descripción de la talla
                    mascota.getGenero() != null ? mascota.getGenero() : "Desconocido"
            });
        }
    }


    private String getDescripcionTalla(String talla) {
        if (talla == null) {
            return "Desconocida";
        }
        switch (talla) {
            case "1":
                return "Pequeña";
            case "2":
                return "Mediana";
            case "3":
                return "Grande";
            default:
                return "Desconocida";
        }
    }


    private void cargarDatosEnFormulario() {
            int filaSeleccionada = tablaMascotas.getSelectedRow();
            if (filaSeleccionada != -1) {
                numeroFichaField.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                especieComboBox.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                razaComboBox.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                nombreField.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                fechaNacimientoChooser.setDate(Date.valueOf(modeloTabla.getValueAt(filaSeleccionada, 4).toString()));
                tallaComboBox.setSelectedItem(getDescripcionTalla(modeloTabla.getValueAt(filaSeleccionada, 5).toString()));
                generoComboBox.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            }
        }

        private void limpiarCampos() {
            numeroFichaField.setText("");
            especieComboBox.setSelectedIndex(-1);
            razaComboBox.setSelectedIndex(-1);
            nombreField.setText("");
            fechaNacimientoChooser.setDate(null);
            tallaComboBox.setSelectedIndex(-1);
            generoComboBox.setSelectedIndex(-1);
        }

        private void agregarMascota() {
            try {
                int numeroFicha = mascotaDAO.obtenerSiguienteNumeroFicha();
                numeroFichaField.setText(String.valueOf(numeroFicha));

                FichaMascota nuevaMascota = new FichaMascota();
                nuevaMascota.setNumeroFicha(numeroFicha);

                // Obtener el código de especie seleccionado en el JComboBox
                String seleccionEspecie = (String) especieComboBox.getSelectedItem();
                if (seleccionEspecie != null) {
                    String[] partesEspecie = seleccionEspecie.split(" - ");
                    nuevaMascota.setCodigoEspecie(partesEspecie[0]);
                }

                // Obtener el código de raza seleccionado en el JComboBox
                String seleccionRaza = (String) razaComboBox.getSelectedItem();
                if (seleccionRaza != null) {
                    String[] partesRaza = seleccionRaza.split(" - ");
                    nuevaMascota.setCodigoRaza(partesRaza[0]);
                }

                // Obtener el género seleccionado en el JComboBox
                String seleccionGenero = (String) generoComboBox.getSelectedItem();
                if (seleccionGenero != null) {
                    // Guardar solo la inicial del género
                    nuevaMascota.setGenero(seleccionGenero.substring(0, 1)); // Almacena 'H' o 'M'
                }

                nuevaMascota.setNombre(nombreField.getText());
                nuevaMascota.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());

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

                // Obtener el código de especie seleccionado en el JComboBox
                String seleccionEspecie = (String) especieComboBox.getSelectedItem();
                if (seleccionEspecie != null) {
                    String[] partesEspecie = seleccionEspecie.split(" - ");
                    mascotaActualizada.setCodigoEspecie(partesEspecie[0]);
                }

                // Obtener el código de raza seleccionado en el JComboBox
                String seleccionRaza = (String) razaComboBox.getSelectedItem();
                if (seleccionRaza != null) {
                    String[] partesRaza = seleccionRaza.split(" - ");
                    mascotaActualizada.setCodigoRaza(partesRaza[0]);
                }

                // Obtener la talla seleccionada en el JComboBox
                String seleccionTalla = (String) tallaComboBox.getSelectedItem();
                if (seleccionTalla != null) {
                    mascotaActualizada.setTalla(seleccionTalla.split(" - ")[0]);
                }

                // Obtener el género seleccionado en el JComboBox
                String seleccionGenero = (String) generoComboBox.getSelectedItem();
                if (seleccionGenero != null) {
                    mascotaActualizada.setGenero(seleccionGenero);
                }

                mascotaActualizada.setNombre(nombreField.getText());
                mascotaActualizada.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());

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



