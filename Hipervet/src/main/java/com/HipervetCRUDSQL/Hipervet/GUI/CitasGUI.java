package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.CitaDAO;
import com.HipervetCRUDSQL.Hipervet.Conexion.Conexion;
import com.HipervetCRUDSQL.Hipervet.Entidades.Cita;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CitasGUI extends JPanel {

    private CitaDAO citaDAO = new CitaDAO();
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JTextField numeroCitaField, horaInicioField, horaFinField, observacionesField, diagnosticoField, procedimientoField, medicinaField;
    private JComboBox<String> tipoCitaComboBox, planGroomingComboBox, sucursalComboBox, empleadoComboBox, clienteComboBox;
    private JDateChooser fechaCitaChooser, fechaInicioChooser, fechaFinChooser;

    public CitasGUI() {
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel formularioPanel = new JPanel(new GridLayout(14, 2, 10, 10));

        formularioPanel.add(new JLabel("Número Cita:"));
        numeroCitaField = new JTextField();
        numeroCitaField.setEnabled(false);
        formularioPanel.add(numeroCitaField);

        formularioPanel.add(new JLabel("Fecha Cita:"));
        fechaCitaChooser = new JDateChooser();
        formularioPanel.add(fechaCitaChooser);

        formularioPanel.add(new JLabel("Hora Inicio:"));
        horaInicioField = new JTextField();
        formularioPanel.add(horaInicioField);

        formularioPanel.add(new JLabel("Hora Fin:"));
        horaFinField = new JTextField();
        formularioPanel.add(horaFinField);

        formularioPanel.add(new JLabel("Tipo de Cita:"));
        tipoCitaComboBox = new JComboBox<>(new String[]{"M - Médica", "G - Grooming"});
        tipoCitaComboBox.addActionListener(e -> actualizarOpcionesGrooming());
        formularioPanel.add(tipoCitaComboBox);

        formularioPanel.add(new JLabel("Plan Grooming:"));
        planGroomingComboBox = new JComboBox<>(new String[]{"Grooming", "Grooming Pro", "Grooming Pro Max", "Grooming SPA"});
        formularioPanel.add(planGroomingComboBox);

        formularioPanel.add(new JLabel("Sucursal:"));
        sucursalComboBox = new JComboBox<>();
        cargarSucursales();
        formularioPanel.add(sucursalComboBox);

        formularioPanel.add(new JLabel("Empleado:"));
        empleadoComboBox = new JComboBox<>();
        cargarEmpleados();
        formularioPanel.add(empleadoComboBox);

        formularioPanel.add(new JLabel("Cliente:"));
        clienteComboBox = new JComboBox<>();
        cargarClientes();
        formularioPanel.add(clienteComboBox);

        formularioPanel.add(new JLabel("Observaciones:"));
        observacionesField = new JTextField();
        formularioPanel.add(observacionesField);

        formularioPanel.add(new JLabel("Diagnóstico:"));
        diagnosticoField = new JTextField();
        formularioPanel.add(diagnosticoField);

        formularioPanel.add(new JLabel("Procedimiento:"));
        procedimientoField = new JTextField();
        formularioPanel.add(procedimientoField);

        formularioPanel.add(new JLabel("Medicina Recetada:"));
        medicinaField = new JTextField();
        formularioPanel.add(medicinaField);

        // Panel de filtro de fechas
        JPanel filtroFechasPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        filtroFechasPanel.add(new JLabel("Fecha Inicio:"));
        fechaInicioChooser = new JDateChooser();
        filtroFechasPanel.add(fechaInicioChooser);

        filtroFechasPanel.add(new JLabel("Fecha Fin:"));
        fechaFinChooser = new JDateChooser();
        filtroFechasPanel.add(fechaFinChooser);

        JButton crearReporteButton = new JButton("Crear reporte de citas");
        crearReporteButton.addActionListener(e -> crearReporteCitas());
        filtroFechasPanel.add(crearReporteButton);

        // Panel para botones CRUD
        JPanel botonesPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(e -> agregarCita());
        botonesPanel.add(agregarButton);

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.addActionListener(e -> actualizarCita());
        botonesPanel.add(actualizarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(e -> eliminarCita());
        botonesPanel.add(eliminarButton);

        // Panel izquierdo con formulario y botones
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(formularioPanel, BorderLayout.NORTH);
        panelIzquierdo.add(botonesPanel, BorderLayout.CENTER);
        panelIzquierdo.add(filtroFechasPanel, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);

        // Tabla para mostrar las citas
        String[] columnas = {"Número Cita", "Fecha", "Hora Inicio", "Hora Fin", "Tipo", "Plan", "Sucursal", "Empleado", "Cliente", "Observaciones", "Diagnóstico", "Procedimiento", "Medicina"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCitas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        add(scrollPane, BorderLayout.CENTER);

        cargarCitas(); // Cargar citas al iniciar la GUI
    }

    private void cargarCitas() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<Cita> citas = citaDAO.obtenerCitas(); // Obtener citas desde CitaDAO
        for (Cita cita : citas) {
            modeloTabla.addRow(new Object[]{
                    cita.getNumeroCita(),
                    cita.getFechaCita(),
                    cita.getHoraInicio(),
                    cita.getHoraFin(),
                    cita.getTipoCita(),
                    cita.getPlanGrooming(),
                    cita.getCodigoSucursal(),
                    cita.getCodigoEmpleado(),
                    cita.getCodigoCliente(),
                    cita.getObservaciones(),
                    cita.getDiagnostico(),
                    cita.getProcedimiento(),
                    cita.getMedicinaRecetada()
            });
        }
    }

    private void cargarSucursales() {
        List<String[]> sucursales = citaDAO.obtenerSucursales();
        sucursalComboBox.removeAllItems();
        for (String[] sucursal : sucursales) {
            sucursalComboBox.addItem(sucursal[0] + " - " + sucursal[1]);
        }
    }

    private void cargarEmpleados() {
        List<String[]> empleados = citaDAO.obtenerEmpleados();
        empleadoComboBox.removeAllItems();
        for (String[] empleado : empleados) {
            empleadoComboBox.addItem(empleado[0] + " - " + empleado[1]);
        }
    }

    private void cargarClientes() {
        List<String[]> clientes = citaDAO.obtenerClientes();
        clienteComboBox.removeAllItems();
        for (String[] cliente : clientes) {
            clienteComboBox.addItem(cliente[0] + " - " + cliente[1]);
        }
    }

    private void actualizarOpcionesGrooming() {
        String tipoCita = (String) tipoCitaComboBox.getSelectedItem();
        planGroomingComboBox.setEnabled("G - Grooming".equals(tipoCita));
    }

    private void agregarCita() {
        // Implementar la lógica para agregar una cita
    }

    private void actualizarCita() {
        // Implementar la lógica para actualizar una cita
    }

    private void eliminarCita() {
        // Implementar la lógica para eliminar una cita
    }

    private void crearReporteCitas() {
        Date fechaInicio = fechaInicioChooser.getDate();
        Date fechaFin = fechaFinChooser.getDate();
        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(this, "Seleccione el rango de fechas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Cita> citasReporte = citaDAO.obtenerCitasPorPeriodo(fechaInicio, fechaFin);
        modeloTabla.setRowCount(0); // Limpiar la tabla
        for (Cita cita : citasReporte) {
            modeloTabla.addRow(new Object[]{
                    cita.getNumeroCita(),
                    cita.getFechaCita(),
                    cita.getHoraInicio(),
                    cita.getHoraFin(),
                    cita.getTipoCita(),
                    cita.getPlanGrooming(),
                    cita.getCodigoSucursal(),
                    cita.getCodigoEmpleado(),
                    cita.getCodigoCliente(),
                    cita.getObservaciones(),
                    cita.getDiagnostico(),
                    cita.getProcedimiento(),
                    cita.getMedicinaRecetada()
            });
        }
    }
}

