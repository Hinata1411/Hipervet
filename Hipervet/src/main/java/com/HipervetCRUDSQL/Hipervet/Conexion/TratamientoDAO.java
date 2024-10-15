package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Tratamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO extends Conexion {

    // Crear
    public boolean crearTratamiento(Tratamiento tratamiento) {
        String sql = "INSERT INTO Tratamiento (CodigoTratamiento, Descripcion) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, tratamiento.getCodigoTratamiento());
            statement.setString(2, tratamiento.getDescripcion());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el tratamiento: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los tratamientos)
    public List<Tratamiento> obtenerTratamientos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT CodigoTratamiento, Descripcion FROM Tratamiento";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setCodigoTratamiento(rs.getInt("CodigoTratamiento"));
                tratamiento.setDescripcion(rs.getString("Descripcion"));
                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los tratamientos: " + e.getMessage());
        }

        return tratamientos;
    }

    // Obtener por ID
    public Tratamiento obtenerTratamientoPorId(int codigoTratamiento) {
        Tratamiento tratamiento = null;
        String sql = "SELECT CodigoTratamiento, Descripcion FROM Tratamiento WHERE CodigoTratamiento = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoTratamiento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tratamiento = new Tratamiento();
                    tratamiento.setCodigoTratamiento(rs.getInt("CodigoTratamiento"));
                    tratamiento.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el tratamiento: " + e.getMessage());
        }

        return tratamiento;
    }

    // Actualizar
    public boolean actualizarTratamiento(Tratamiento tratamiento) {
        String sql = "UPDATE Tratamiento SET Descripcion = ? WHERE CodigoTratamiento = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, tratamiento.getDescripcion());
            statement.setInt(2, tratamiento.getCodigoTratamiento());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el tratamiento: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarTratamiento(int codigoTratamiento) {
        String sql = "DELETE FROM Tratamiento WHERE CodigoTratamiento = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoTratamiento);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el tratamiento: " + e.getMessage());
            return false;
        }
    }
}
