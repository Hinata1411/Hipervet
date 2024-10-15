package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Raza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO extends Conexion {

    // Crear
    public boolean crearRaza(Raza raza) {
        String sql = "INSERT INTO Raza (CodigoRaza, Descripcion) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, raza.getCodigoRaza());
            statement.setString(2, raza.getDescripcion());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la raza: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las razas)
    public List<Raza> obtenerRazas() {
        List<Raza> razas = new ArrayList<>();
        String sql = "SELECT CodigoRaza, Descripcion FROM Raza";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Raza raza = new Raza();
                raza.setCodigoRaza(rs.getInt("CodigoRaza"));
                raza.setDescripcion(rs.getString("Descripcion"));
                razas.add(raza);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las razas: " + e.getMessage());
        }

        return razas;
    }

    // Obtener por ID
    public Raza obtenerRazaPorId(int codigoRaza) {
        Raza raza = null;
        String sql = "SELECT CodigoRaza, Descripcion FROM Raza WHERE CodigoRaza = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoRaza);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    raza = new Raza();
                    raza.setCodigoRaza(rs.getInt("CodigoRaza"));
                    raza.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la raza: " + e.getMessage());
        }

        return raza;
    }

    // Actualizar
    public boolean actualizarRaza(Raza raza) {
        String sql = "UPDATE Raza SET Descripcion = ? WHERE CodigoRaza = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, raza.getDescripcion());
            statement.setInt(2, raza.getCodigoRaza());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la raza: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarRaza(int codigoRaza) {
        String sql = "DELETE FROM Raza WHERE CodigoRaza = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoRaza);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la raza: " + e.getMessage());
            return false;
        }
    }
}
