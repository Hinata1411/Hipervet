package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Telefono;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefonoDAO extends Conexion {

    // Crear
    public boolean crearTelefono(Telefono telefono) {
        String sql = "INSERT INTO Telefono (CorrelativoTelefono, NumeroTelefono) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, telefono.getCorrelativoTelefono());
            statement.setString(2, telefono.getNumeroTelefono());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el teléfono: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los teléfonos)
    public List<Telefono> obtenerTelefonos() {
        List<Telefono> telefonos = new ArrayList<>();
        String sql = "SELECT CorrelativoTelefono, NumeroTelefono FROM Telefono";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Telefono telefono = new Telefono();
                telefono.setCorrelativoTelefono(rs.getInt("CorrelativoTelefono"));
                telefono.setNumeroTelefono(rs.getString("NumeroTelefono"));
                telefonos.add(telefono);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los teléfonos: " + e.getMessage());
        }

        return telefonos;
    }

    // Obtener por ID
    public Telefono obtenerTelefonoPorId(int correlativoTelefono) {
        Telefono telefono = null;
        String sql = "SELECT CorrelativoTelefono, NumeroTelefono FROM Telefono WHERE CorrelativoTelefono = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, correlativoTelefono);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    telefono = new Telefono();
                    telefono.setCorrelativoTelefono(rs.getInt("CorrelativoTelefono"));
                    telefono.setNumeroTelefono(rs.getString("NumeroTelefono"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el teléfono: " + e.getMessage());
        }

        return telefono;
    }

    // Actualizar
    public boolean actualizarTelefono(Telefono telefono) {
        String sql = "UPDATE Telefono SET NumeroTelefono = ? WHERE CorrelativoTelefono = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, telefono.getNumeroTelefono());
            statement.setInt(2, telefono.getCorrelativoTelefono());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el teléfono: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarTelefono(int correlativoTelefono) {
        String sql = "DELETE FROM Telefono WHERE CorrelativoTelefono = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, correlativoTelefono);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el teléfono: " + e.getMessage());
            return false;
        }
    }
}
