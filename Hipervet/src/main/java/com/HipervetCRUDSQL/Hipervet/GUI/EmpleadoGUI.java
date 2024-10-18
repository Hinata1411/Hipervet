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
        reportesFrame.setLayout(new BorderLayout());

        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Componente lleno horizontalmente
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0

        JLabel fechaInicioLabel = new JLabel("Fecha de Inicio:");
        panelFiltros.add(fechaInicioLabel, gbc);

        gbc.gridx = 1; // Columna 1
        JDateChooser fechaInicioChooser = new JDateChooser();
        panelFiltros.add(fechaInicioChooser, gbc);

        gbc.gridx = 0; // Regresar a columna 0
        gbc.gridy = 1; // Siguiente fila
        JLabel fechaFinLabel = new JLabel("Fecha de Fin:");
        panelFiltros.add(fechaFinLabel, gbc);

        gbc.gridx = 1; // Columna 1
        JDateChooser fechaFinChooser = new JDateChooser();
        panelFiltros.add(fechaFinChooser, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 2; // Siguiente fila
        JLabel reporteLabel = new JLabel("Seleccione un reporte:");
        panelFiltros.add(reporteLabel, gbc);

        gbc.gridx = 1; // Columna 1
        JComboBox<String> comboBoxReportes = new JComboBox<>(new String[]{
                "Gromista más eficiente",
                "Veterinario con más pacientes",
                "Empleado que atiende menos",
                "Empleado con más faltas"});
        panelFiltros.add(comboBoxReportes, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 3; // Siguiente fila
        gbc.gridwidth = 2; // Extender botón en ambas columnas
        JButton generarReporteButton = new JButton("Generar Reporte");
        panelFiltros.add(generarReporteButton, gbc);

        generarReporteButton.addActionListener(e -> {
            if (comboBoxReportes.getItemCount() > 0) {  // Verificar si el comboBox tiene elementos
                String reporteSeleccionado = (String) comboBoxReportes.getSelectedItem();

                if (reporteSeleccionado != null) {  // Verificar si un valor fue seleccionado
                    Date fechaInicio = fechaInicioChooser.getDate();
                    Date fechaFin = fechaFinChooser.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    if (fechaInicio != null && fechaFin != null) {
                        if ("Gromista más eficiente".equals(reporteSeleccionado)) {
                            List<String[]> resultados = empleadoDAO.obtenerGromistaMasEficiente(fechaInicio, fechaFin);
                            String[] columnas = {"Código Empleado", "Nombre Completo", "Puesto", "Promedio Duración (Horas)"};
                            mostrarResultadosReporte(resultados, reportesFrame, columnas);
                        } else if ("Veterinario con más pacientes".equals(reporteSeleccionado)) {
                            List<String[]> resultados = empleadoDAO.obtenerVeterinarioMasPacientes();
                            String[] columnas = {"Nombre Completo", "Puesto", "Total Pacientes Atendidos"};
                            mostrarResultadosReporte(resultados, reportesFrame, columnas);
                        } else if ("Empleado que atiende menos".equals(reporteSeleccionado)) {
                            List<String[]> resultados = empleadoDAO.obtenerEmpleadoQueAtiendeMenos();
                            String[] columnas = {"Nombre Completo", "Puesto", "Total Pacientes Atendidos"};
                            mostrarResultadosReporte(resultados, reportesFrame, columnas);
                        } else {
                            JOptionPane.showMessageDialog(reportesFrame, "Opción de reporte no implementada", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(reportesFrame, "Por favor seleccione las fechas de inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(reportesFrame, "Seleccione un reporte.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(reportesFrame, "No hay reportes disponibles para seleccionar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        reportesFrame.add(panelFiltros, BorderLayout.NORTH);

        reportesFrame.pack();
        reportesFrame.setLocationRelativeTo(null);
        reportesFrame.setVisible(true);
    }

    // Método para mostrar los resultados del reporte en una tabla
    private void mostrarResultadosReporte(List<String[]> resultados, JFrame reportesFrame, String[] columnas) {
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(reportesFrame, "No se encontraron resultados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel modeloReporte = new DefaultTableModel(columnas, 0);

        for (String[] resultado : resultados) {
            modeloReporte.addRow(resultado);  // Añadir cada fila al modelo de la tabla
        }

        JTable tablaResultados = new JTable(modeloReporte);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        reportesFrame.getContentPane().removeAll();  // Limpiar el contenido actual del frame para evitar superposición
        reportesFrame.add(scrollPane, BorderLayout.CENTER);
        reportesFrame.revalidate();  // Actualizar el frame
        reportesFrame.repaint();  // Redibujar el frame
        reportesFrame.setSize(600, 400);  // Ajustar el tamaño del frame
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame("Empleado GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new EmpleadoGUI());
            frame.setVisible(true);
        }

}
