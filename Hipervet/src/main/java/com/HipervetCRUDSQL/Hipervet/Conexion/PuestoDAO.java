package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Puesto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuestoDAO extends Conexion {

    // Crear
    public boolean crearPuesto(Puesto puesto) {
        String sql = "INSERT INTO Puesto (CodigoPuesto, Descripcion) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, puesto.getCodigoPuesto());
            statement.setString(2, puesto.getDescripcion());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el puesto: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los puestos)
    public List<Puesto> obtenerPuestos() {
        List<Puesto> puestos = new ArrayList<>();
        String sql = "SELECT CodigoPuesto, Descripcion FROM Puesto";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Puesto puesto = new Puesto();
                puesto.setCodigoPuesto(rs.getInt("CodigoPuesto"));
                puesto.setDescripcion(rs.getString("Descripcion"));
                puestos.add(puesto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los puestos: " + e.getMessage());
        }

        return puestos;
    }

    // Obtener por ID
    public Puesto obtenerPuestoPorId(int codigoPuesto) {
        Puesto puesto = null;
        String sql = "SELECT CodigoPuesto, Descripcion FROM Puesto WHERE CodigoPuesto = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoPuesto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    puesto = new Puesto();
                    puesto.setCodigoPuesto(rs.getInt("CodigoPuesto"));
                    puesto.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el puesto: " + e.getMessage());
        }

        return puesto;
    }

    // Actualizar
    public boolean actualizarPuesto(Puesto puesto) {
        String sql = "UPDATE Puesto SET Descripcion = ? WHERE CodigoPuesto = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, puesto.getDescripcion());
            statement.setInt(2, puesto.getCodigoPuesto());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el puesto: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarPuesto(int codigoPuesto) {
        String sql = "DELETE FROM Puesto WHERE CodigoPuesto = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoPuesto);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el puesto: " + e.getMessage());
            return false;
        }
    }
}
