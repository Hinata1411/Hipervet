package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.TelefonoPersona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefonoPersonaDAO extends Conexion {

    // Crear
    public boolean crearTelefonoPersona(TelefonoPersona telefonoPersona) {
        String sql = "INSERT INTO TelefonoPersona (CorrelativoTelefono, CodigoPersona) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, telefonoPersona.getCorrelativoTelefono());
            statement.setInt(2, telefonoPersona.getCodigoPersona());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la relación Telefono-Persona: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las relaciones Telefono-Persona)
    public List<TelefonoPersona> obtenerTelefonosPersona() {
        List<TelefonoPersona> telefonoPersonas = new ArrayList<>();
        String sql = "SELECT CorrelativoTelefono, CodigoPersona FROM TelefonoPersona";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TelefonoPersona telefonoPersona = new TelefonoPersona();
                telefonoPersona.setCorrelativoTelefono(rs.getInt("CorrelativoTelefono"));
                telefonoPersona.setCodigoPersona(rs.getInt("CodigoPersona"));
                telefonoPersonas.add(telefonoPersona);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las relaciones Telefono-Persona: " + e.getMessage());
        }

        return telefonoPersonas;
    }

    // Obtener por ID
    public TelefonoPersona obtenerTelefonoPersonaPorId(int correlativoTelefono, int codigoPersona) {
        TelefonoPersona telefonoPersona = null;
        String sql = "SELECT CorrelativoTelefono, CodigoPersona FROM TelefonoPersona WHERE CorrelativoTelefono = ? AND CodigoPersona = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, correlativoTelefono);
            stmt.setInt(2, codigoPersona);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    telefonoPersona = new TelefonoPersona();
                    telefonoPersona.setCorrelativoTelefono(rs.getInt("CorrelativoTelefono"));
                    telefonoPersona.setCodigoPersona(rs.getInt("CodigoPersona"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la relación Telefono-Persona: " + e.getMessage());
        }

        return telefonoPersona;
    }

    // Actualizar
    public boolean actualizarTelefonoPersona(TelefonoPersona telefonoPersona) {
        String sql = "UPDATE TelefonoPersona SET CodigoPersona = ? WHERE CorrelativoTelefono = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, telefonoPersona.getCodigoPersona());
            statement.setInt(2, telefonoPersona.getCorrelativoTelefono());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la relación Telefono-Persona: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarTelefonoPersona(int correlativoTelefono, int codigoPersona) {
        String sql = "DELETE FROM TelefonoPersona WHERE CorrelativoTelefono = ? AND CodigoPersona = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, correlativoTelefono);
            statement.setInt(2, codigoPersona);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la relación Telefono-Persona: " + e.getMessage());
            return false;
        }
    }
}
