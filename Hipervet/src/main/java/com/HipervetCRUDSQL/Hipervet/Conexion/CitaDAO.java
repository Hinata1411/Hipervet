package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO extends Conexion {

    // Crear
    public boolean crearCita(Cita cita) {
        String sql = "INSERT INTO Cita (CodigoSucursal, NumeroCita, FechaCita, Observaciones, CodigoEmpleado, TipoCita, CodigoCliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, cita.getCodigoSucursal());
            statement.setInt(2, cita.getNumeroCita());
            statement.setDate(3, new java.sql.Date(cita.getFechaCita().getTime()));
            statement.setString(4, cita.getObservaciones());
            statement.setInt(5, cita.getCodigoEmpleado());
            statement.setString(6, cita.getTipoCita());
            statement.setInt(7, cita.getCodigoCliente());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la cita: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las citas)
    public List<Cita> obtenerCitas() {
        List<Cita> citas = new ArrayList<>();
        Connection conn = Conexion.obtenerConexion();

        String sql = "SELECT CodigoSucursal, NumeroCita, FechaCita, Observaciones, CodigoEmpleado, TipoCita, CodigoCliente FROM Cita";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                cita.setNumeroCita(rs.getInt("NumeroCita"));
                cita.setFechaCita(rs.getDate("FechaCita"));
                cita.setObservaciones(rs.getString("Observaciones"));
                cita.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                cita.setTipoCita(rs.getString("TipoCita"));
                cita.setCodigoCliente(rs.getInt("CodigoCliente"));

                citas.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las citas: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion();
        }

        return citas;
    }

    // Obtener por ID
    public Cita obtenerCitaPorId(int numeroCita) {
        Cita cita = null;
        String sql = "SELECT CodigoSucursal, NumeroCita, FechaCita, Observaciones, CodigoEmpleado, TipoCita, CodigoCliente FROM Cita WHERE NumeroCita = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroCita);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cita = new Cita();
                    cita.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                    cita.setNumeroCita(rs.getInt("NumeroCita"));
                    cita.setFechaCita(rs.getDate("FechaCita"));
                    cita.setObservaciones(rs.getString("Observaciones"));
                    cita.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                    cita.setTipoCita(rs.getString("TipoCita"));
                    cita.setCodigoCliente(rs.getInt("CodigoCliente"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la cita: " + e.getMessage());
        }
        return cita;
    }

    // Actualizar
    public boolean actualizarCita(Cita cita) {
        String sql = "UPDATE Cita SET CodigoSucursal = ?, FechaCita = ?, Observaciones = ?, CodigoEmpleado = ?, TipoCita = ?, CodigoCliente = ? WHERE NumeroCita = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, cita.getCodigoSucursal());
            statement.setDate(2, new java.sql.Date(cita.getFechaCita().getTime()));
            statement.setString(3, cita.getObservaciones());
            statement.setInt(4, cita.getCodigoEmpleado());
            statement.setString(5, cita.getTipoCita());
            statement.setInt(6, cita.getCodigoCliente());
            statement.setInt(7, cita.getNumeroCita());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la cita: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarCita(int numeroCita) {
        String sql = "DELETE FROM Cita WHERE NumeroCita = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, numeroCita);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la cita: " + e.getMessage());
            return false;
        }
    }
}
