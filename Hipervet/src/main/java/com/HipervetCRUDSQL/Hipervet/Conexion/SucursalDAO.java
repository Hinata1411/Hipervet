package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Sucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SucursalDAO extends Conexion {

    // Crear
    public boolean crearSucursal(Sucursal sucursal) {
        String sql = "INSERT INTO Sucursal (CodigoSucursal, NombreSucursal, Direccion) VALUES (?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, sucursal.getCodigoSucursal());
            statement.setString(2, sucursal.getNombreSucursal());
            statement.setString(3, sucursal.getDireccion());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la sucursal: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las sucursales)
    public List<Sucursal> obtenerSucursales() {
        List<Sucursal> sucursales = new ArrayList<>();
        String sql = "SELECT CodigoSucursal, NombreSucursal, Direccion FROM Sucursal";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                sucursal.setNombreSucursal(rs.getString("NombreSucursal"));
                sucursal.setDireccion(rs.getString("Direccion"));
                sucursales.add(sucursal);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las sucursales: " + e.getMessage());
        }

        return sucursales;
    }

    // Obtener por ID
    public Sucursal obtenerSucursalPorId(int codigoSucursal) {
        Sucursal sucursal = null;
        String sql = "SELECT CodigoSucursal, NombreSucursal, Direccion FROM Sucursal WHERE CodigoSucursal = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoSucursal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sucursal = new Sucursal();
                    sucursal.setCodigoSucursal(rs.getInt("CodigoSucursal"));
                    sucursal.setNombreSucursal(rs.getString("NombreSucursal"));
                    sucursal.setDireccion(rs.getString("Direccion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la sucursal: " + e.getMessage());
        }

        return sucursal;
    }

    // Actualizar
    public boolean actualizarSucursal(Sucursal sucursal) {
        String sql = "UPDATE Sucursal SET NombreSucursal = ?, Direccion = ? WHERE CodigoSucursal = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, sucursal.getNombreSucursal());
            statement.setString(2, sucursal.getDireccion());
            statement.setInt(3, sucursal.getCodigoSucursal());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la sucursal: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarSucursal(int codigoSucursal) {
        String sql = "DELETE FROM Sucursal WHERE CodigoSucursal = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoSucursal);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la sucursal: " + e.getMessage());
            return false;
        }
    }
}
