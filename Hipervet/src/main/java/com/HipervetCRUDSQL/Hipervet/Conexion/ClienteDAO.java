package com.HipervetCRUDSQL.Hipervet.Conexion;


import com.HipervetCRUDSQL.Hipervet.Entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends Conexion {

        // Método para crear un cliente
        public boolean crearCliente(Cliente cliente) {
            String sql = "INSERT INTO Cliente (CodigoCliente, CodigoPersona) VALUES (?, ?)";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, cliente.getCodigoCliente());
                statement.setInt(2, cliente.getCodigoPersona());

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al crear el cliente: " + e.getMessage());
            }
            return false;
        }

        // Método para obtener todos los clientes
        public List<Cliente> obtenerClientes() {
            List<Cliente> clientes = new ArrayList<>();
            String sql = "SELECT c.CodigoCliente, per.CodigoPersona, per.PrimerNombre, per.SegundoNombre, per.TercerNombre, " +
                    "per.PrimerApellido, per.SegundoApellido, per.TercerApellido, per.FechaNacimiento " +
                    "FROM persona AS per " +
                    "LEFT JOIN cliente c ON per.CodigoPersona = c.CodigoPersona " +
                    "WHERE per.tipoPersona = 'C' " +
                    "ORDER BY c.CodigoCliente ASC";
            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setCodigoCliente(rs.getInt("CodigoCliente"));
                    cliente.setCodigoPersona(rs.getInt("CodigoPersona"));
                    cliente.setPrimerNombre(rs.getString("PrimerNombre"));
                    cliente.setSegundoNombre(rs.getString("SegundoNombre"));
                    cliente.setTercerNombre(rs.getString("TercerNombre"));
                    cliente.setPrimerApellido(rs.getString("PrimerApellido"));
                    cliente.setSegundoApellido(rs.getString("SegundoApellido"));
                    cliente.setTercerApellido(rs.getString("TercerApellido"));
                    cliente.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());

                    clientes.add(cliente);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los clientes: " + e.getMessage());
            }
            return clientes;
        }

        // Método para asignar o crear un código de cliente para una persona
        public boolean asignarCodigoCliente(int codigoPersona, int codigoCliente) {
            String verificarSql = "SELECT COUNT(*) FROM Cliente WHERE CodigoCliente = ?";
            String insertarSql = "INSERT INTO Cliente (CodigoCliente, CodigoPersona) VALUES (?, ?)";
            String actualizarSql = "UPDATE Cliente SET CodigoCliente = ? WHERE CodigoPersona = ?";

            try (Connection conexion = obtenerConexion();
                 PreparedStatement verificarStatement = conexion.prepareStatement(verificarSql)) {

                // Verificar si el código ya existe
                verificarStatement.setInt(1, codigoCliente);
                ResultSet rs = verificarStatement.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    System.err.println("El código de cliente ya existe.");
                    return false;  // El código ya está en uso
                }

                // Verificar si la persona ya es un cliente
                String verificarPersonaSql = "SELECT COUNT(*) FROM Cliente WHERE CodigoPersona = ?";
                try (PreparedStatement verificarPersonaStatement = conexion.prepareStatement(verificarPersonaSql)) {
                    verificarPersonaStatement.setInt(1, codigoPersona);
                    rs = verificarPersonaStatement.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        // Si ya existe un cliente con este CodigoPersona, actualizamos el CodigoCliente
                        try (PreparedStatement actualizarStatement = conexion.prepareStatement(actualizarSql)) {
                            actualizarStatement.setInt(1, codigoCliente);
                            actualizarStatement.setInt(2, codigoPersona);
                            return actualizarStatement.executeUpdate() > 0;
                        }
                    } else {
                        // Si no existe, insertamos un nuevo cliente
                        try (PreparedStatement insertarStatement = conexion.prepareStatement(insertarSql)) {
                            insertarStatement.setInt(1, codigoCliente);
                            insertarStatement.setInt(2, codigoPersona);
                            return insertarStatement.executeUpdate() > 0;
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al asignar el código de cliente: " + e.getMessage());
            }
            return false;
        }

        // Método para eliminar la asignación de cliente
        public boolean eliminarAsignacionCliente(int codigoCliente) {
            String eliminarSql = "DELETE FROM Cliente WHERE CodigoCliente = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(eliminarSql)) {

                statement.setInt(1, codigoCliente);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al eliminar la asignación de cliente: " + e.getMessage());
            }
            return false;
        }

}