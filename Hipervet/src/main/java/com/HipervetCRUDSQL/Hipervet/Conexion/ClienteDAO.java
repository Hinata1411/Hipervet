package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Cliente;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends Conexion {

    // Crear
    public boolean crearCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (CodigoCliente, CodigoPersona) VALUES (?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, cliente.getCodigoCliente());
            statement.setInt(2, cliente.getCodigoPersona());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el cliente: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los clientes)
    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Connection conn = Conexion.obtenerConexion();

        String sql = "SELECT CodigoCliente, CodigoPersona FROM Cliente";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodigoCliente(rs.getInt("CodigoCliente"));
                cliente.setCodigoPersona(rs.getInt("CodigoPersona"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los clientes: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion();
        }

        return clientes;
    }

    // Obtener por ID
    public Cliente obtenerClientePorId(int codigoCliente) {
        Cliente cliente = null;
        String sql = "SELECT CodigoCliente, CodigoPersona FROM Cliente WHERE CodigoCliente = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setCodigoCliente(rs.getInt("CodigoCliente"));
                    cliente.setCodigoPersona(rs.getInt("CodigoPersona"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el cliente: " + e.getMessage());
        }
        return cliente;
    }

    // Actualizar
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET CodigoPersona = ? WHERE CodigoCliente = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, cliente.getCodigoPersona());
            statement.setInt(2, cliente.getCodigoCliente());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarCliente(int codigoCliente) {
        String sql = "DELETE FROM Cliente WHERE CodigoCliente = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoCliente);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el cliente: " + e.getMessage());
            return false;
        }
    }
}