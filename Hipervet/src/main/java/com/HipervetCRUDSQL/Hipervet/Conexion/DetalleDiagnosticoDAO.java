package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.DetalleDiagnostico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleDiagnosticoDAO extends Conexion {

    // Crear
    public boolean crearDetalleDiagnostico(DetalleDiagnostico detalleDiagnostico) {
        String sql = "INSERT INTO DetalleDiagnostico (Correlativo, NumeroCita, NumeroFicha, CodigoDiagnostico, CodigoSucursal) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, detalleDiagnostico.getCorrelativo());
            statement.setInt(2, detalleDiagnostico.getNumeroCita());
            statement.setInt(3, detalleDiagnostico.getNumeroFicha());
            statement.setInt(4, detalleDiagnostico.getCodigoDiagnostico());
            statement.setInt(5, detalleDiagnostico.getCodigoSucursal());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el detalle del diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los detalles de diagnósticos)
    public List<DetalleDiagnostico> obtenerDetallesDiagnostico() {
        List<DetalleDiagnostico> detallesDiagnostico = new ArrayList<>();
        String sql = "SELECT Correlativo, NumeroCita, NumeroFicha, CodigoDiagnostico, CodigoSucursal FROM DetalleDiagnostico";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetalleDiagnostico detalleDiagnostico = new DetalleDiagnostico();
                detalleDiagnostico.setCorrelativo(rs.getInt("Correlativo"));
                detalleDiagnostico.setNumeroCita(rs.getInt("NumeroCita"));
                detalleDiagnostico.setNumeroFicha(rs.getInt("NumeroFicha"));
                detalleDiagnostico.setCodigoDiagnostico(rs.getInt("CodigoDiagnostico"));
                detalleDiagnostico.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                detallesDiagnostico.add(detalleDiagnostico);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los detalles de los diagnósticos: " + e.getMessage());
        }

        return detallesDiagnostico;
    }

    // Obtener por ID
    public DetalleDiagnostico obtenerDetalleDiagnosticoPorId(int correlativo, int numeroCita) {
        DetalleDiagnostico detalleDiagnostico = null;
        String sql = "SELECT Correlativo, NumeroCita, NumeroFicha, CodigoDiagnostico, CodigoSucursal " +
                "FROM DetalleDiagnostico WHERE Correlativo = ? AND NumeroCita = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, correlativo);
            stmt.setInt(2, numeroCita);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detalleDiagnostico = new DetalleDiagnostico();
                    detalleDiagnostico.setCorrelativo(rs.getInt("Correlativo"));
                    detalleDiagnostico.setNumeroCita(rs.getInt("NumeroCita"));
                    detalleDiagnostico.setNumeroFicha(rs.getInt("NumeroFicha"));
                    detalleDiagnostico.setCodigoDiagnostico(rs.getInt("CodigoDiagnostico"));
                    detalleDiagnostico.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el detalle del diagnóstico: " + e.getMessage());
        }

        return detalleDiagnostico;
    }

    // Actualizar
    public boolean actualizarDetalleDiagnostico(DetalleDiagnostico detalleDiagnostico) {
        String sql = "UPDATE DetalleDiagnostico SET NumeroFicha = ?, CodigoDiagnostico = ?, CodigoSucursal = ? " +
                "WHERE Correlativo = ? AND NumeroCita = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, detalleDiagnostico.getNumeroFicha());
            statement.setInt(2, detalleDiagnostico.getCodigoDiagnostico());
            statement.setInt(3, detalleDiagnostico.getCodigoSucursal());
            statement.setInt(4, detalleDiagnostico.getCorrelativo());
            statement.setInt(5, detalleDiagnostico.getNumeroCita());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el detalle del diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarDetalleDiagnostico(int correlativo, int numeroCita) {
        String sql = "DELETE FROM DetalleDiagnostico WHERE Correlativo = ? AND NumeroCita = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, correlativo);
            statement.setInt(2, numeroCita);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el detalle del diagnóstico: " + e.getMessage());
            return false;
        }
    }
}
