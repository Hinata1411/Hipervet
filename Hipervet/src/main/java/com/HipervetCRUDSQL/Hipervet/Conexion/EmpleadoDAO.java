package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;  // Para trabajar con fechas en el código en general
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends Conexion {

        // Método para crear un empleado
        public boolean crearEmpleado(Empleado empleado) {
            String sql = "INSERT INTO Empleado (CodigoEmpleado, CodigoPersona, CodigoPuesto) VALUES (?, ?, ?)";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, empleado.getCodigoEmpleado());
                statement.setInt(2, empleado.getCodigoPersona());
                statement.setInt(3, empleado.getCodigoPuesto());

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al crear el empleado: " + e.getMessage());
            }
            return false;
        }

        // Método para obtener todos los empleados (incluyendo los que no tienen puesto asignado)
        public List<Empleado> obtenerEmpleados() {
            List<Empleado> empleados = new ArrayList<>();
            String sql = "SELECT e.CodigoEmpleado, per.CodigoPersona, per.PrimerNombre, per.SegundoNombre, per.TercerNombre, " +
                    "per.PrimerApellido, per.SegundoApellido, per.TercerApellido, per.FechaNacimiento, p.CodigoPuesto, p.Descripcion " +
                    "FROM persona AS per " +
                    "LEFT JOIN empleado e ON per.CodigoPersona = e.CodigoPersona " +
                    "LEFT JOIN puesto p ON e.CodigoPuesto = p.CodigoPuesto " +
                    "WHERE tipoPersona = 'E'";  // Filtrar por empleados

            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setCodigoEmpleado(rs.getInt("CodigoEmpleado")); // Código de empleado (puede ser nulo si no tiene)
                    empleado.setCodigoPersona(rs.getInt("CodigoPersona")); // Código de persona
                    empleado.setPrimerNombre(rs.getString("PrimerNombre"));
                    empleado.setSegundoNombre(rs.getString("SegundoNombre"));
                    empleado.setTercerNombre(rs.getString("TercerNombre"));
                    empleado.setPrimerApellido(rs.getString("PrimerApellido"));
                    empleado.setSegundoApellido(rs.getString("SegundoApellido"));
                    empleado.setTercerApellido(rs.getString("TercerApellido"));
                    empleado.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                    empleado.setCodigoPuesto(rs.getInt("CodigoPuesto")); // Puede ser nulo
                    empleado.setDescripcionPuesto(rs.getString("Descripcion")); // Puede ser nulo

                    empleados.add(empleado);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los empleados: " + e.getMessage());
            }

            return empleados;
        }

        // Método para asignar un puesto a un empleado
        public boolean asignarPuesto(int codigoEmpleado, int codigoPuesto) {
            String sql = "UPDATE Empleado SET CodigoPuesto = ? WHERE CodigoEmpleado = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, codigoPuesto);  // Actualizar el puesto
                statement.setInt(2, codigoEmpleado);  // Usar el código del empleado

                return statement.executeUpdate() > 0; // Retorna true si se actualiza correctamente
            } catch (SQLException e) {
                System.err.println("Error al asignar el puesto: " + e.getMessage());
            }
            return false;
        }

        // Método para asignar o crear un código de empleado para una persona
        public boolean asignarCodigoEmpleado(int codigoPersona, int codigoEmpleado) {
            String verificarSql = "SELECT COUNT(*) FROM Empleado WHERE CodigoEmpleado = ?";
            String insertarSql = "INSERT INTO Empleado (CodigoEmpleado, CodigoPersona) VALUES (?, ?)";
            String actualizarSql = "UPDATE Empleado SET CodigoEmpleado = ? WHERE CodigoPersona = ?";

            try (Connection conexion = obtenerConexion();
                 PreparedStatement verificarStmt = conexion.prepareStatement(verificarSql)) {

                // Verificar si ya existe un empleado con el mismo codigoEmpleado
                verificarStmt.setInt(1, codigoEmpleado);
                ResultSet rs = verificarStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    System.err.println("El código de empleado ya existe.");
                    return false;
                }

                // Verificar si ya existe un registro para la persona y actualizarlo
                String verificarPersonaSql = "SELECT COUNT(*) FROM Empleado WHERE CodigoPersona = ?";
                try (PreparedStatement verificarPersonaStmt = conexion.prepareStatement(verificarPersonaSql)) {
                    verificarPersonaStmt.setInt(1, codigoPersona);
                    rs = verificarPersonaStmt.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        // Actualizar si ya existe
                        try (PreparedStatement actualizarStmt = conexion.prepareStatement(actualizarSql)) {
                            actualizarStmt.setInt(1, codigoEmpleado);
                            actualizarStmt.setInt(2, codigoPersona);
                            return actualizarStmt.executeUpdate() > 0;
                        }
                    } else {
                        // Insertar si no existe
                        try (PreparedStatement insertarStmt = conexion.prepareStatement(insertarSql)) {
                            insertarStmt.setInt(1, codigoEmpleado);
                            insertarStmt.setInt(2, codigoPersona);
                            return insertarStmt.executeUpdate() > 0;
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al asignar el código de empleado: " + e.getMessage());
            }
            return false;
        }


    // Método para obtener todos los puestos disponibles
        public List<String[]> obtenerPuestos() {
            List<String[]> puestos = new ArrayList<>();
            String sql = "SELECT CodigoPuesto, Descripcion FROM puesto";

            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    String[] puesto = new String[2];
                    puesto[0] = rs.getString("CodigoPuesto");
                    puesto[1] = rs.getString("Descripcion");
                    puestos.add(puesto);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los puestos: " + e.getMessage());
            }

            return puestos;
        }
    // Método para mostrar los resultados del reporte en una tabla
    private void mostrarResultadosReporte(List<String[]> resultados, JFrame reportesFrame) {
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(reportesFrame, "No se encontraron resultados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnas = {"Codigo Empleado", "Nombre Completo", "Puesto", "Promedio Duración (Horas)"};
        DefaultTableModel modeloReporte = new DefaultTableModel(columnas, 0);

        for (String[] resultado : resultados) {
            modeloReporte.addRow(resultado);
        }

        JTable tablaResultados = new JTable(modeloReporte);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        reportesFrame.add(scrollPane, BorderLayout.CENTER);
        reportesFrame.revalidate();
        reportesFrame.repaint();
        reportesFrame.setSize(600, 400);
    }


    public List<String[]> obtenerGromistaMasEficiente(Date fechaInicio, Date fechaFin) {
        List<String[]> resultados = new ArrayList<>();
        String sql = "SELECT TOP 1 e.CodigoEmpleado, " +  // Incluye el código de empleado
                "CONCAT(per.PrimerNombre, ' ', COALESCE(per.SegundoNombre, ''), ' ', COALESCE(per.TercerNombre, ''), ' ', " +
                "per.PrimerApellido, ' ', per.SegundoApellido, ' ', COALESCE(per.TercerApellido, '')) AS NombreCompleto, " +
                "p.Descripcion AS Puesto, AVG(DATEDIFF(MINUTE, dc.Inicio, dc.Fin) / 60.0) AS PromedioDuracionHoras " +
                "FROM persona per " +
                "INNER JOIN empleado e ON per.CodigoPersona = e.CodigoPersona " +
                "INNER JOIN puesto p ON e.CodigoPuesto = p.CodigoPuesto " +
                "INNER JOIN detallecita dc ON e.CodigoEmpleado = dc.CodigoEmpleado " +
                "INNER JOIN cita c ON dc.NumeroCita = c.NumeroCita " +
                "WHERE p.Descripcion = 'Groomista' " +
                "AND dc.Inicio BETWEEN ? AND ? " +
                "GROUP BY e.CodigoEmpleado, CONCAT(per.PrimerNombre, ' ', COALESCE(per.SegundoNombre, ''), ' ', COALESCE(per.TercerNombre, ''), ' ', " +
                "per.PrimerApellido, ' ', per.SegundoApellido, ' ', COALESCE(per.TercerApellido, '')), p.Descripcion " +
                "ORDER BY PromedioDuracionHoras ASC";

        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(2, new java.sql.Date(fechaFin.getTime()));

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] resultado = new String[4];  // Aumentamos el tamaño a 4
                resultado[0] = rs.getString("CodigoEmpleado");  // Incluye el código de empleado
                resultado[1] = rs.getString("NombreCompleto");
                resultado[2] = rs.getString("Puesto");
                resultado[3] = String.valueOf(rs.getDouble("PromedioDuracionHoras"));
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el gromista más eficiente: " + e.getMessage());
        }

        return resultados;
    }

    public List<String[]> obtenerVeterinarioMasPacientes() {
        List<String[]> resultados = new ArrayList<>();
        String sql = "SELECT TOP 1 e.CodigoEmpleado, " +
                "CONCAT(per.PrimerNombre, ' ', COALESCE(per.SegundoNombre, ''), ' ', COALESCE(per.TercerNombre, ''), ' ', " +
                "per.PrimerApellido, ' ', per.SegundoApellido, ' ', COALESCE(per.TercerApellido, '')) AS NombreCompleto, " +
                "p.Descripcion AS Puesto, COUNT(c.NumeroCita) AS TotalPacientesAtendidos " +
                "FROM persona per " +
                "INNER JOIN empleado e ON per.CodigoPersona = e.CodigoPersona " +
                "INNER JOIN puesto p ON e.CodigoPuesto = p.CodigoPuesto " +
                "INNER JOIN cita c ON e.CodigoEmpleado = c.CodigoEmpleado " +
                "WHERE p.CodigoPuesto = 1 " +
                "GROUP BY e.CodigoEmpleado, CONCAT(per.PrimerNombre, ' ', COALESCE(per.SegundoNombre, ''), ' ', COALESCE(per.TercerNombre, ''), ' ', " +
                "per.PrimerApellido, ' ', per.SegundoApellido, ' ', COALESCE(per.TercerApellido, '')), p.Descripcion " +
                "ORDER BY TotalPacientesAtendidos DESC";

        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] resultado = new String[4];
                resultado[0] = rs.getString("CodigoEmpleado");
                resultado[1] = rs.getString("NombreCompleto");
                resultado[2] = rs.getString("Puesto");
                resultado[3] = String.valueOf(rs.getInt("TotalPacientesAtendidos"));
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el veterinario con más pacientes: " + e.getMessage());
        }

        return resultados;
    }

    public List<String[]> obtenerEmpleadoQueAtiendeMenos() {
        List<String[]> resultados = new ArrayList<>();
        String sql = "SELECT TOP 1 e.CodigoEmpleado, " +
                "CONCAT(per.PrimerNombre, ' ', COALESCE(per.SegundoNombre, ''), ' ', COALESCE(per.TercerNombre, ''), ' ', " +
                "per.PrimerApellido, ' ', per.SegundoApellido, ' ', COALESCE(per.TercerApellido, '')) AS NombreCompleto, " +
                "p.Descripcion AS Puesto, COUNT(c.NumeroCita) AS TotalPacientesAtendidos " +
                "FROM persona per " +
                "INNER JOIN empleado e ON per.CodigoPersona = e.CodigoPersona " +
                "INNER JOIN puesto p ON e.CodigoPuesto = p.CodigoPuesto " +
                "INNER JOIN cita c ON e.CodigoEmpleado = c.CodigoEmpleado " +
                "GROUP BY e.CodigoEmpleado, CONCAT(per.PrimerNombre, ' ', COALESCE(per.SegundoNombre, ''), ' ', COALESCE(per.TercerNombre, ''), ' ', " +
                "per.PrimerApellido, ' ', per.SegundoApellido, ' ', COALESCE(per.TercerApellido, '')), p.Descripcion " +
                "ORDER BY TotalPacientesAtendidos ASC";

        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] resultado = new String[4];
                resultado[0] = rs.getString("CodigoEmpleado");
                resultado[1] = rs.getString("NombreCompleto");
                resultado[2] = rs.getString("Puesto");
                resultado[3] = String.valueOf(rs.getInt("TotalPacientesAtendidos"));
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el empleado que menos citas ha atendido: " + e.getMessage());
        }

        return resultados;
    }

    // Método para mostrar los resultados del reporte de veterinarios con más pacientes
    public void mostrarResultadosReporteVeterinario(List<String[]> resultados, JFrame reportesFrame) {
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(reportesFrame, "No se encontraron resultados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir las columnas específicas para este reporte
        String[] columnas = {"Codigo Empleado ", "Nombre Completo", "Puesto", "Total Pacientes Atendidos"};
        DefaultTableModel modeloReporte = new DefaultTableModel(columnas, 0);

        for (String[] resultado : resultados) {
            modeloReporte.addRow(resultado);
        }

        JTable tablaResultados = new JTable(modeloReporte);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        reportesFrame.add(scrollPane, BorderLayout.CENTER);
        reportesFrame.revalidate();
        reportesFrame.repaint();
        reportesFrame.setSize(600, 400);
    }

    // Método para eliminar la asignación de empleado y sus relaciones
    public boolean eliminarAsignacionEmpleado(int codigoEmpleado) {
        String sql = "DELETE FROM Empleado WHERE CodigoEmpleado = ?";
        try (Connection conexion = obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, codigoEmpleado);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar la asignación de empleado: " + e.getMessage());
        }
        return false;
    }
}
