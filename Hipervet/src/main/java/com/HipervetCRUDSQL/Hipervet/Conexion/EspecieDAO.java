package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Especie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO extends Conexion {

    // Crear
    public boolean crearEspecie(Especie especie) {
        String sql = "INSERT INTO Especie (CodigoEspecie, Descripcion) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, especie.getCodigoEspecie());
            statement.setString(2, especie.getDescripcion());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la especie: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las especies)
    public List<Especie> obtenerEspecies() {
        List<Especie> especies = new ArrayList<>();
        String sql = "SELECT CodigoEspecie, Descripcion FROM Especie";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Especie especie = new Especie();
                especie.setCodigoEspecie(rs.getInt("CodigoEspecie"));
                especie.setDescripcion(rs.getString("Descripcion"));
                especies.add(especie);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las especies: " + e.getMessage());
        }

        return especies;
    }

    // Obtener por ID
    public Especie obtenerEspeciePorId(int codigoEspecie) {
        Especie especie = null;
        String sql = "SELECT CodigoEspecie, Descripcion FROM Especie WHERE CodigoEspecie = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoEspecie);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    especie = new Especie();
                    especie.setCodigoEspecie(rs.getInt("CodigoEspecie"));
                    especie.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la especie: " + e.getMessage());
        }

        return especie;
    }

    // Actualizar
    public boolean actualizarEspecie(Especie especie) {
        String sql = "UPDATE Especie SET Descripcion = ? WHERE CodigoEspecie = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, especie.getDescripcion());
            statement.setInt(2, especie.getCodigoEspecie());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la especie: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarEspecie(int codigoEspecie) {
        String sql = "DELETE FROM Especie WHERE CodigoEspecie = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoEspecie);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la especie: " + e.getMessage());
            return false;
        }
    }
}
