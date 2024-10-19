package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Cita;
import com.HipervetCRUDSQL.Hipervet.Entidades.DetalleCita;
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

import static com.sun.javafx.fxml.expression.Expression.add;

public class CitaDAO extends Conexion {

    public List<Cita> obtenerCitas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.CodigoSucursal, c.NumeroCita, c.FechaCita, " +
                "dc.Inicio AS horaInicio, dc.Fin AS horaFin, c.Observaciones, " +
                "c.CodigoEmpleado, c.TipoCita, c.CodigoCliente, c.CodigoServicio " +
                "FROM Cita c " +
                "LEFT JOIN DetalleCita dc ON c.NumeroCita = dc.NumeroCita " +
                "ORDER BY c.FechaCita";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setNumeroCita(rs.getInt("NumeroCita"));
                cita.setFechaCita(rs.getDate("FechaCita"));
                cita.setHoraInicio(rs.getString("horaInicio"));
                cita.setHoraFin(rs.getString("horaFin"));
                cita.setTipoCita(rs.getString("TipoCita"));
                cita.setPlanGrooming(rs.getString("Observaciones"));
                cita.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                cita.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                cita.setCodigoCliente(rs.getInt("CodigoCliente"));
                citas.add(cita);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener citas: " + e.getMessage());
        }
        return citas;
    }

    public List<String[]> obtenerSucursales() {
            List<String[]> sucursales = new ArrayList<>();
            String sql = "SELECT codigoSucursal, nombreSucursal FROM Sucursal";

            try (Connection conn = Conexion.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    sucursales.add(new String[]{rs.getString("codigoSucursal"), rs.getString("nombreSucursal")});
                }

            } catch (SQLException e) {
                System.err.println("Error al obtener sucursales: " + e.getMessage());
            }
            return sucursales;
        }

        public List<String[]> obtenerEmpleados() {
            List<String[]> empleados = new ArrayList<>();
            String sql = "SELECT idEmpleado, nombreEmpleado FROM Empleados";

            try (Connection conn = Conexion.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    empleados.add(new String[]{rs.getString("idEmpleado"), rs.getString("nombreEmpleado")});
                }

            } catch (SQLException e) {
                System.err.println("Error al obtener empleados: " + e.getMessage());
            }
            return empleados;
        }

        public List<String[]> obtenerClientes() {
            List<String[]> clientes = new ArrayList<>();
            String sql = "SELECT idCliente, nombreCliente FROM Clientes";

            try (Connection conn = Conexion.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    clientes.add(new String[]{rs.getString("idCliente"), rs.getString("nombreCliente")});
                }

            } catch (SQLException e) {
                System.err.println("Error al obtener clientes: " + e.getMessage());
            }
            return clientes;
        }

        public List<Cita> obtenerCitasPorPeriodo(Date fechaInicio, Date fechaFin) {
            List<Cita> citas = new ArrayList<>();
            String sql = "SELECT numeroCita, fechaCita, horaInicio, horaFin, tipoCita, planGrooming, " +
                    "codigoSucursal, codigoEmpleado, codigoCliente, observaciones, diagnostico, procedimiento, medicinaRecetada " +
                    "FROM Citas WHERE fechaCita BETWEEN ? AND ?";

            try (Connection conn = Conexion.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
                stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setNumeroCita(rs.getInt("numeroCita"));
                    cita.setFechaCita(rs.getDate("fechaCita"));
                    cita.setHoraInicio(rs.getString("horaInicio"));
                    cita.setHoraFin(rs.getString("horaFin"));
                    cita.setTipoCita(rs.getString("tipoCita"));
                    cita.setPlanGrooming(rs.getString("planGrooming"));
                    cita.setCodigoSucursal(rs.getInt("codigoSucursal"));
                    cita.setCodigoEmpleado(rs.getInt("codigoEmpleado"));
                    cita.setCodigoCliente(rs.getInt("codigoCliente"));
                    cita.setObservaciones(rs.getString("observaciones"));
                    cita.setDiagnostico(rs.getString("diagnostico"));
                    cita.setProcedimiento(rs.getString("procedimiento"));
                    cita.setMedicinaRecetada(rs.getString("medicinaRecetada"));

                    citas.add(cita);
                }

            } catch (SQLException e) {
                System.err.println("Error al obtener citas por periodo: " + e.getMessage());
            }
            return citas;
        }
}
    


