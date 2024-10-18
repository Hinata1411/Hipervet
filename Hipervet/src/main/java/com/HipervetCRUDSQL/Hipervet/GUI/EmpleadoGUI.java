package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.EmpleadoDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;
import com.toedter.calendar.JDateChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class EmpleadoGUI extends JPanel {

        private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        private JTable tablaEmpleados;
        private DefaultTableModel modeloTabla;

        public EmpleadoGUI() {
            setLayout(new BorderLayout());

            JPanel tituloPanel = new JPanel(new BorderLayout());
            JLabel tituloLabel = new JLabel("Empleados HiperVet", JLabel.CENTER);
            tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
            tituloPanel.add(tituloLabel, BorderLayout.NORTH);

            JPanel botonArribaPanel = new JPanel();
            botonArribaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton exportarExcelButton = new JButton("Exportar a Excel");
            exportarExcelButton.addActionListener(e -> exportarTablaAExcel());
            botonArribaPanel.add(exportarExcelButton);

            JButton asignarPuestoButton = new JButton("Asignar Puesto");
            asignarPuestoButton.addActionListener(e -> asignarPuesto());
            botonArribaPanel.add(asignarPuestoButton);

            JButton asignarCodigoEmpleadoButton = new JButton("Asignar Código de Empleado");
            asignarCodigoEmpleadoButton.addActionListener(e -> asignarCodigoEmpleado());
            botonArribaPanel.add(asignarCodigoEmpleadoButton);

            tituloPanel.add(botonArribaPanel, BorderLayout.SOUTH);
            add(tituloPanel, BorderLayout.NORTH);

            String[] columnas = {"Codigo Empleado", "Codigo Persona", "Primer Nombre", "Segundo Nombre", "Tercer Nombre",
                    "Primer Apellido", "Segundo Apellido", "Tercer Apellido", "Fecha de Nacimiento", "Codigo Puesto", "Descripción Puesto"};
            modeloTabla = new DefaultTableModel(columnas, 0);
            tablaEmpleados = new JTable(modeloTabla);

            cargarEmpleados();
            JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
            add(scrollPane, BorderLayout.CENTER);

            JPanel botonAbajoPanel = new JPanel();
            botonAbajoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton actualizarButton = new JButton("Actualizar");
            actualizarButton.addActionListener(e -> cargarEmpleados());
            botonAbajoPanel.add(actualizarButton);

            JButton reportesButton = new JButton("Reportes");
            reportesButton.addActionListener(e -> mostrarVentanaReportes());
            botonAbajoPanel.add(reportesButton);

            add(botonAbajoPanel, BorderLayout.SOUTH);
        }

        private void cargarEmpleados() {
            modeloTabla.setRowCount(0);
            List<Empleado> empleados = empleadoDAO.obtenerEmpleados();
            for (Empleado empleado : empleados) {
                modeloTabla.addRow(new Object[]{
                        empleado.getCodigoEmpleado() != 0 ? empleado.getCodigoEmpleado() : "Sin asignar",
                        empleado.getCodigoPersona(),
                        empleado.getPrimerNombre(),
                        empleado.getSegundoNombre(),
                        empleado.getTercerNombre(),
                        empleado.getPrimerApellido(),
                        empleado.getSegundoApellido(),
                        empleado.getTercerApellido(),
                        empleado.getFechaNacimiento(),
                        empleado.getCodigoPuesto() != 0 ? empleado.getCodigoPuesto() : "Sin asignar",
                        empleado.getDescripcionPuesto() != null ? empleado.getDescripcionPuesto() : "Sin puesto asignado"
                });
            }
        }

        private void asignarPuesto() {
            int filaSeleccionada = tablaEmpleados.getSelectedRow();
            if (filaSeleccionada != -1) {
                String codigoEmpleado = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
                List<String[]> puestos = empleadoDAO.obtenerPuestos();

                JComboBox<String> comboPuestos = new JComboBox<>();
                for (String[] puesto : puestos) {
                    comboPuestos.addItem(puesto[0] + " - " + puesto[1]);
                }

                int resultado = JOptionPane.showConfirmDialog(this, comboPuestos, "Seleccione el puesto", JOptionPane.OK_CANCEL_OPTION);
                if (resultado == JOptionPane.OK_OPTION) {
                    String seleccionado = (String) comboPuestos.getSelectedItem();
                    String[] partes = seleccionado.split(" - ");
                    String codigoPuesto = partes[0];

                    if (empleadoDAO.asignarPuesto(Integer.parseInt(codigoEmpleado), Integer.parseInt(codigoPuesto))) {
                        JOptionPane.showMessageDialog(this, "Puesto asignado exitosamente.");
                        cargarEmpleados();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al asignar el puesto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void asignarCodigoEmpleado() {
            int filaSeleccionada = tablaEmpleados.getSelectedRow();
            if (filaSeleccionada != -1) {
                String nuevoCodigoEmpleado = JOptionPane.showInputDialog(this, "Ingrese el nuevo código de empleado:");
                if (nuevoCodigoEmpleado != null && !nuevoCodigoEmpleado.isEmpty()) {
                    String codigoPersona = modeloTabla.getValueAt(filaSeleccionada, 1).toString();

                    if (empleadoDAO.asignarCodigoEmpleado(Integer.parseInt(codigoPersona), Integer.parseInt(nuevoCodigoEmpleado))) {
                        JOptionPane.showMessageDialog(this, "Código de empleado asignado exitosamente.");
                        cargarEmpleados();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al asignar el código de empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una persona para asignar un código de empleado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void exportarTablaAExcel() {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Empleados");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(modeloTabla.getColumnName(i));
            }

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    Cell cell = dataRow.createCell(j);
                    Object value = modeloTabla.getValueAt(i, j);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar archivo Excel");
                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!filePath.endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }
                    FileOutputStream fileOut = new FileOutputStream(filePath);
                    workbook.write(fileOut);
                    fileOut.close();
                    workbook.close();
                    JOptionPane.showMessageDialog(this, "Archivo Excel exportado exitosamente.");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al exportar el archivo Excel: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void mostrarVentanaReportes() {
            JFrame reportesFrame = new JFrame("Reportes");
            reportesFrame.setLayout(new GridLayout(5, 2, 10, 10));

            String[] opcionesReportes = {"Gromista más eficiente", "Veterinario con más pacientes", "Empleado que atiende menos", "Empleado con más faltas"};
            JComboBox<String> comboBoxReportes = new JComboBox<>(opcionesReportes);

            JDateChooser fechaInicioChooser = new JDateChooser();
            JDateChooser fechaFinChooser = new JDateChooser();

            JLabel fechaInicioLabel = new JLabel("Fecha de Inicio:");
            JLabel fechaFinLabel = new JLabel("Fecha de Fin:");

            JButton generarReporteButton = new JButton("Generar Reporte");
            generarReporteButton.addActionListener(e -> {
                String reporteSeleccionado = (String) comboBoxReportes.getSelectedItem();
                Date fechaInicio = fechaInicioChooser.getDate();
                Date fechaFin = fechaFinChooser.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                if (fechaInicio != null && fechaFin != null) {
                    JOptionPane.showMessageDialog(reportesFrame, "Generando reporte: " + reporteSeleccionado +
                            "\nDesde: " + dateFormat.format(fechaInicio) + " Hasta: " + dateFormat.format(fechaFin));
                    // Lógica para generar los reportes específicos con el rango de fechas
                } else {
                    JOptionPane.showMessageDialog(reportesFrame, "Por favor seleccione las fechas de inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            reportesFrame.add(new JLabel("Seleccione un reporte:"));
            reportesFrame.add(comboBoxReportes);
            reportesFrame.add(fechaInicioLabel);
            reportesFrame.add(fechaInicioChooser);
            reportesFrame.add(fechaFinLabel);
            reportesFrame.add(fechaFinChooser);
            reportesFrame.add(new JLabel());
            reportesFrame.add(generarReporteButton);

            reportesFrame.pack();
            reportesFrame.setLocationRelativeTo(null);
            reportesFrame.setVisible(true);
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Empleado GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new EmpleadoGUI());
            frame.setVisible(true);
        }

}
