package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.DetalleCita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleCitaDAO extends Conexion {

    // Crear
    public boolean crearDetalleCita(DetalleCita detalleCita) {
        String sql = "INSERT INTO DetalleCita (NumeroCita, NumeroFicha, Correlativo, CodigoServicio, Inicio, Fin, CodigoEmpleado, CodigoSucursal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, detalleCita.getNumeroCita());
            statement.setInt(2, detalleCita.getNumeroFicha());
            statement.setInt(3, detalleCita.getCorrelativo());
            statement.setInt(4, detalleCita.getCodigoServicio());
            statement.setString(5, detalleCita.getInicio());
            statement.setString(6, detalleCita.getFin());
            statement.setInt(7, detalleCita.getCodigoEmpleado());
            statement.setInt(8, detalleCita.getCodigoSucursal());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el detalle de cita: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los detalles de citas)
    public List<DetalleCita> obtenerDetallesCita() {
        List<DetalleCita> detallesCita = new ArrayList<>();
        String sql = "SELECT NumeroCita, NumeroFicha, Correlativo, CodigoServicio, Inicio, Fin, CodigoEmpleado, CodigoSucursal FROM DetalleCita";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetalleCita detalleCita = new DetalleCita();
                detalleCita.setNumeroCita(rs.getInt("NumeroCita"));
                detalleCita.setNumeroFicha(rs.getInt("NumeroFicha"));
                detalleCita.setCorrelativo(rs.getInt("Correlativo"));
                detalleCita.setCodigoServicio(rs.getInt("CodigoServicio"));
                detalleCita.setInicio(rs.getString("Inicio"));
                detalleCita.setFin(rs.getString("Fin"));
                detalleCita.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                detalleCita.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                detallesCita.add(detalleCita);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los detalles de las citas: " + e.getMessage());
        }

        return detallesCita;
    }

    // Obtener por ID
    public DetalleCita obtenerDetalleCitaPorId(int numeroCita, int correlativo) {
        DetalleCita detalleCita = null;
        String sql = "SELECT NumeroCita, NumeroFicha, Correlativo, CodigoServicio, Inicio, Fin, CodigoEmpleado, CodigoSucursal " +
                "FROM DetalleCita WHERE NumeroCita = ? AND Correlativo = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroCita);
            stmt.setInt(2, correlativo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detalleCita = new DetalleCita();
                    detalleCita.setNumeroCita(rs.getInt("NumeroCita"));
                    detalleCita.setNumeroFicha(rs.getInt("NumeroFicha"));
                    detalleCita.setCorrelativo(rs.getInt("Correlativo"));
                    detalleCita.setCodigoServicio(rs.getInt("CodigoServicio"));
                    detalleCita.setInicio(rs.getString("Inicio"));
                    detalleCita.setFin(rs.getString("Fin"));
                    detalleCita.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                    detalleCita.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el detalle de cita: " + e.getMessage());
        }

        return detalleCita;
    }

    // Actualizar
    public boolean actualizarDetalleCita(DetalleCita detalleCita) {
        String sql = "UPDATE DetalleCita SET NumeroFicha = ?, CodigoServicio = ?, Inicio = ?, Fin = ?, CodigoEmpleado = ?, CodigoSucursal = ? " +
                "WHERE NumeroCita = ? AND Correlativo = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, detalleCita.getNumeroFicha());
            statement.setInt(2, detalleCita.getCodigoServicio());
            statement.setString(3, detalleCita.getInicio());
            statement.setString(4, detalleCita.getFin());
            statement.setInt(5, detalleCita.getCodigoEmpleado());
            statement.setInt(6, detalleCita.getCodigoSucursal());
            statement.setInt(7, detalleCita.getNumeroCita());
            statement.setInt(8, detalleCita.getCorrelativo());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el detalle de cita: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarDetalleCita(int numeroCita, int correlativo) {
        String sql = "DELETE FROM DetalleCita WHERE NumeroCita = ? AND Correlativo = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, numeroCita);
            statement.setInt(2, correlativo);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el detalle de cita: " + e.getMessage());
            return false;
        }
    }
}
