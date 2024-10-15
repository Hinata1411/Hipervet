package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Diagnostico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO extends Conexion {

    // Crear
    public boolean crearDiagnostico(Diagnostico diagnostico) {
        String sql = "INSERT INTO Diagnostico (CodigoDiagnostico, Descripcion) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, diagnostico.getCodigoDiagnostico());
            statement.setString(2, diagnostico.getDescripcion());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los diagnósticos)
    public List<Diagnostico> obtenerDiagnosticos() {
        List<Diagnostico> diagnosticos = new ArrayList<>();
        String sql = "SELECT CodigoDiagnostico, Descripcion FROM Diagnostico";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Diagnostico diagnostico = new Diagnostico();
                diagnostico.setCodigoDiagnostico(rs.getInt("CodigoDiagnostico"));
                diagnostico.setDescripcion(rs.getString("Descripcion"));
                diagnosticos.add(diagnostico);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los diagnósticos: " + e.getMessage());
        }

        return diagnosticos;
    }

    // Obtener por ID
    public Diagnostico obtenerDiagnosticoPorId(int codigoDiagnostico) {
        Diagnostico diagnostico = null;
        String sql = "SELECT CodigoDiagnostico, Descripcion FROM Diagnostico WHERE CodigoDiagnostico = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoDiagnostico);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    diagnostico = new Diagnostico();
                    diagnostico.setCodigoDiagnostico(rs.getInt("CodigoDiagnostico"));
                    diagnostico.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el diagnóstico: " + e.getMessage());
        }

        return diagnostico;
    }

    // Actualizar
    public boolean actualizarDiagnostico(Diagnostico diagnostico) {
        String sql = "UPDATE Diagnostico SET Descripcion = ? WHERE CodigoDiagnostico = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, diagnostico.getDescripcion());
            statement.setInt(2, diagnostico.getCodigoDiagnostico());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarDiagnostico(int codigoDiagnostico) {
        String sql = "DELETE FROM Diagnostico WHERE CodigoDiagnostico = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoDiagnostico);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el diagnóstico: " + e.getMessage());
            return false;
        }
    }
}
