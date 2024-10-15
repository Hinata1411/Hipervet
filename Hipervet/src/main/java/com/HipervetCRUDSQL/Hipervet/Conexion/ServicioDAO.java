package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO extends Conexion {

    // Crear
    public boolean crearServicio(Servicio servicio) {
        String sql = "INSERT INTO Servicio (CodigoServicio, Descripcion, Tipo, Precio) VALUES (?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, servicio.getCodigoServicio());
            statement.setString(2, servicio.getDescripcion());
            statement.setString(3, servicio.getTipo());
            statement.setDouble(4, servicio.getPrecio());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el servicio: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los servicios)
    public List<Servicio> obtenerServicios() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT CodigoServicio, Descripcion, Tipo, Precio FROM Servicio";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setCodigoServicio(rs.getInt("CodigoServicio"));
                servicio.setDescripcion(rs.getString("Descripcion"));
                servicio.setTipo(rs.getString("Tipo"));
                servicio.setPrecio(rs.getDouble("Precio"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los servicios: " + e.getMessage());
        }

        return servicios;
    }

    // Obtener por ID
    public Servicio obtenerServicioPorId(int codigoServicio) {
        Servicio servicio = null;
        String sql = "SELECT CodigoServicio, Descripcion, Tipo, Precio FROM Servicio WHERE CodigoServicio = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoServicio);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servicio = new Servicio();
                    servicio.setCodigoServicio(rs.getInt("CodigoServicio"));
                    servicio.setDescripcion(rs.getString("Descripcion"));
                    servicio.setTipo(rs.getString("Tipo"));
                    servicio.setPrecio(rs.getDouble("Precio"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el servicio: " + e.getMessage());
        }

        return servicio;
    }

    // Actualizar
    public boolean actualizarServicio(Servicio servicio) {
        String sql = "UPDATE Servicio SET Descripcion = ?, Tipo = ?, Precio = ? WHERE CodigoServicio = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, servicio.getDescripcion());
            statement.setString(2, servicio.getTipo());
            statement.setDouble(3, servicio.getPrecio());
            statement.setInt(4, servicio.getCodigoServicio());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el servicio: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarServicio(int codigoServicio) {
        String sql = "DELETE FROM Servicio WHERE CodigoServicio = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoServicio);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el servicio: " + e.getMessage());
            return false;
        }
    }
}