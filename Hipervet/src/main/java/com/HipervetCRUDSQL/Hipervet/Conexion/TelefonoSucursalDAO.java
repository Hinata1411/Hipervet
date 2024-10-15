package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.TelefonoSucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefonoSucursalDAO extends Conexion {

    // Crear
    public boolean crearTelefonoSucursal(TelefonoSucursal telefonoSucursal) {
        String sql = "INSERT INTO TelefonoSucursal (CorrelativoTelefono, CodigoSucursal) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, telefonoSucursal.getCorrelativoTelefono());
            statement.setInt(2, telefonoSucursal.getCodigoSucursal());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la relación Telefono-Sucursal: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las relaciones Telefono-Sucursal)
    public List<TelefonoSucursal> obtenerTelefonosSucursal() {
        List<TelefonoSucursal> telefonoSucursales = new ArrayList<>();
        String sql = "SELECT CorrelativoTelefono, CodigoSucursal FROM TelefonoSucursal";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TelefonoSucursal telefonoSucursal = new TelefonoSucursal();
                telefonoSucursal.setCorrelativoTelefono(rs.getInt("CorrelativoTelefono"));
                telefonoSucursal.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                telefonoSucursales.add(telefonoSucursal);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las relaciones Telefono-Sucursal: " + e.getMessage());
        }

        return telefonoSucursales;
    }

    // Obtener por ID
    public TelefonoSucursal obtenerTelefonoSucursalPorId(int correlativoTelefono, int codigoSucursal) {
        TelefonoSucursal telefonoSucursal = null;
        String sql = "SELECT CorrelativoTelefono, CodigoSucursal FROM TelefonoSucursal WHERE CorrelativoTelefono = ? AND CodigoSucursal = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, correlativoTelefono);
            stmt.setInt(2, codigoSucursal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    telefonoSucursal = new TelefonoSucursal();
                    telefonoSucursal.setCorrelativoTelefono(rs.getInt("CorrelativoTelefono"));
                    telefonoSucursal.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la relación Telefono-Sucursal: " + e.getMessage());
        }

        return telefonoSucursal;
    }

    // Actualizar
    public boolean actualizarTelefonoSucursal(TelefonoSucursal telefonoSucursal) {
        String sql = "UPDATE TelefonoSucursal SET CodigoSucursal = ? WHERE CorrelativoTelefono = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, telefonoSucursal.getCodigoSucursal());
            statement.setInt(2, telefonoSucursal.getCorrelativoTelefono());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la relación Telefono-Sucursal: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarTelefonoSucursal(int correlativoTelefono, int codigoSucursal) {
        String sql = "DELETE FROM TelefonoSucursal WHERE CorrelativoTelefono = ? AND CodigoSucursal = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, correlativoTelefono);
            statement.setInt(2, codigoSucursal);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la relación Telefono-Sucursal: " + e.getMessage());
            return false;
        }
    }
}
