package com.HipervetCRUDSQL.Hipervet.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;  // Asegúrate de importar esta clase
import java.io.FileOutputStream;
import java.io.IOException;

import com.HipervetCRUDSQL.Hipervet.Conexion.ClienteDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Cliente;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ClienteGUI extends JPanel {


        private ClienteDAO clienteDAO = new ClienteDAO();
        private JTable tablaClientes;
        private DefaultTableModel modeloTabla;

        public ClienteGUI() {
            setLayout(new BorderLayout());

            JPanel tituloPanel = new JPanel(new BorderLayout());
            JLabel tituloLabel = new JLabel("Clientes HiperVet", JLabel.CENTER);
            tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
            tituloPanel.add(tituloLabel, BorderLayout.NORTH);

            JPanel botonArribaPanel = new JPanel();
            botonArribaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton exportarExcelButton = new JButton("Exportar a Excel");
            exportarExcelButton.addActionListener(e -> exportarTablaAExcel());
            botonArribaPanel.add(exportarExcelButton);

            JButton asignarCodigoClienteButton = new JButton("Asignar Código de Cliente");
            asignarCodigoClienteButton.addActionListener(e -> asignarCodigoCliente());
            botonArribaPanel.add(asignarCodigoClienteButton);

            tituloPanel.add(botonArribaPanel, BorderLayout.SOUTH);
            add(tituloPanel, BorderLayout.NORTH);

            String[] columnas = {"Código Cliente", "Código Persona", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Fecha de Nacimiento"};
            modeloTabla = new DefaultTableModel(columnas, 0);
            tablaClientes = new JTable(modeloTabla);

            cargarClientes();
            JScrollPane scrollPane = new JScrollPane(tablaClientes);
            add(scrollPane, BorderLayout.CENTER);

            JPanel botonAbajoPanel = new JPanel();
            botonAbajoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton actualizarButton = new JButton("Actualizar");
            actualizarButton.addActionListener(e -> cargarClientes());
            botonAbajoPanel.add(actualizarButton);

            JButton eliminarAsignacionButton = new JButton("Eliminar Asignación");
            eliminarAsignacionButton.addActionListener(e -> eliminarAsignacionCliente());
            botonAbajoPanel.add(eliminarAsignacionButton);

            add(botonAbajoPanel, BorderLayout.SOUTH);
        }

        // Método para cargar los clientes en la tabla
        private void cargarClientes() {
            modeloTabla.setRowCount(0);
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            for (Cliente cliente : clientes) {
                modeloTabla.addRow(new Object[]{
                        cliente.getCodigoCliente() != 0 ? cliente.getCodigoCliente() : "Sin asignar",
                        cliente.getCodigoPersona(),
                        cliente.getPrimerNombre(),
                        cliente.getSegundoNombre(),
                        cliente.getPrimerApellido(),
                        cliente.getSegundoApellido(),
                        cliente.getFechaNacimiento()
                });
            }
        }

        // Método para asignar un código de cliente
        private void asignarCodigoCliente() {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            if (filaSeleccionada != -1) {
                String nuevoCodigoCliente = JOptionPane.showInputDialog(this, "Ingrese el nuevo código de cliente:");
                if (nuevoCodigoCliente != null && !nuevoCodigoCliente.isEmpty()) {
                    String codigoPersona = modeloTabla.getValueAt(filaSeleccionada, 1).toString();

                    if (clienteDAO.asignarCodigoCliente(Integer.parseInt(codigoPersona), Integer.parseInt(nuevoCodigoCliente))) {
                        JOptionPane.showMessageDialog(this, "Código de cliente asignado exitosamente.");
                        cargarClientes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al asignar el código de cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para asignar un código.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        // Método para eliminar la asignación de cliente
        private void eliminarAsignacionCliente() {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            if (filaSeleccionada != -1) {
                String codigoClienteStr = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
                if (!codigoClienteStr.equals("Sin asignar")) {
                    try {
                        int codigoCliente = Integer.parseInt(codigoClienteStr);
                        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar la asignación?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                        if (confirmacion == JOptionPane.YES_OPTION) {
                            if (clienteDAO.eliminarAsignacionCliente(codigoCliente)) {
                                JOptionPane.showMessageDialog(this, "Asignación de cliente eliminada exitosamente.");
                                cargarClientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Error al eliminar la asignación del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Código de cliente inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El cliente seleccionado no tiene un código asignado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para eliminar la asignación.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        // Método para exportar la tabla a un archivo Excel
        private void exportarTablaAExcel() {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Clientes");

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

        public static void main(String[] args) {
            JFrame frame = new JFrame("Cliente GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new ClienteGUI());
            frame.setVisible(true);
        }
    }



