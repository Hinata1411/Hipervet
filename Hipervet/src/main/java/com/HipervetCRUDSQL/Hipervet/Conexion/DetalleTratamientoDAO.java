package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.DetalleTratamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleTratamientoDAO extends Conexion {

    // Crear
    public boolean crearDetalleDeTratamiento(DetalleTratamiento detalleDeTratamiento) {
        String sql = "INSERT INTO DetalleDeTratamiento (Correlativo, NumeroCita, NumeroFicha, CodigoDiagnostico, CodigoTratamiento, CodigoSucursal) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, detalleDeTratamiento.getCorrelativo());
            statement.setInt(2, detalleDeTratamiento.getNumeroCita());
            statement.setInt(3, detalleDeTratamiento.getNumeroFicha());
            statement.setInt(4, detalleDeTratamiento.getCodigoDiagnostico());
            statement.setInt(5, detalleDeTratamiento.getCodigoTratamiento());
            statement.setInt(6, detalleDeTratamiento.getCodigoSucursal());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el detalle del tratamiento: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los detalles de tratamiento)
    public List<DetalleTratamiento> obtenerDetallesDeTratamiento() {
        List<DetalleTratamiento> detallesDeTratamiento = new ArrayList<>();
        String sql = "SELECT Correlativo, NumeroCita, NumeroFicha, CodigoDiagnostico, CodigoTratamiento, CodigoSucursal FROM DetalleDeTratamiento";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetalleTratamiento detalleDeTratamiento = new DetalleTratamiento();
                detalleDeTratamiento.setCorrelativo(rs.getInt("Correlativo"));
                detalleDeTratamiento.setNumeroCita(rs.getInt("NumeroCita"));
                detalleDeTratamiento.setNumeroFicha(rs.getInt("NumeroFicha"));
                detalleDeTratamiento.setCodigoDiagnostico(rs.getInt("CodigoDiagnostico"));
                detalleDeTratamiento.setCodigoTratamiento(rs.getInt("CodigoTratamiento"));
                detalleDeTratamiento.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                detallesDeTratamiento.add(detalleDeTratamiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los detalles de los tratamientos: " + e.getMessage());
        }

        return detallesDeTratamiento;
    }

    // Obtener por ID
    public DetalleTratamiento obtenerDetalleDeTratamientoPorId(int correlativo, int numeroCita) {
        DetalleTratamiento detalleDeTratamiento = null;
        String sql = "SELECT Correlativo, NumeroCita, NumeroFicha, CodigoDiagnostico, CodigoTratamiento, CodigoSucursal " +
                "FROM DetalleDeTratamiento WHERE Correlativo = ? AND NumeroCita = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, correlativo);
            stmt.setInt(2, numeroCita);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detalleDeTratamiento = new DetalleTratamiento();
                    detalleDeTratamiento.setCorrelativo(rs.getInt("Correlativo"));
                    detalleDeTratamiento.setNumeroCita(rs.getInt("NumeroCita"));
                    detalleDeTratamiento.setNumeroFicha(rs.getInt("NumeroFicha"));
                    detalleDeTratamiento.setCodigoDiagnostico(rs.getInt("CodigoDiagnostico"));
                    detalleDeTratamiento.setCodigoTratamiento(rs.getInt("CodigoTratamiento"));
                    detalleDeTratamiento.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el detalle del tratamiento: " + e.getMessage());
        }

        return detalleDeTratamiento;
    }

    // Actualizar
    public boolean actualizarDetalleDeTratamiento(DetalleTratamiento detalleDeTratamiento) {
        String sql = "UPDATE DetalleDeTratamiento SET NumeroFicha = ?, CodigoDiagnostico = ?, CodigoTratamiento = ?, CodigoSucursal = ? " +
                "WHERE Correlativo = ? AND NumeroCita = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, detalleDeTratamiento.getNumeroFicha());
            statement.setInt(2, detalleDeTratamiento.getCodigoDiagnostico());
            statement.setInt(3, detalleDeTratamiento.getCodigoTratamiento());
            statement.setInt(4, detalleDeTratamiento.getCodigoSucursal());
            statement.setInt(5, detalleDeTratamiento.getCorrelativo());
            statement.setInt(6, detalleDeTratamiento.getNumeroCita());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el detalle del tratamiento: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarDetalleDeTratamiento(int correlativo, int numeroCita) {
        String sql = "DELETE FROM DetalleDeTratamiento WHERE Correlativo = ? AND NumeroCita = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, correlativo);
            statement.setInt(2, numeroCita);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el detalle del tratamiento: " + e.getMessage());
            return false;
        }
    }
}
