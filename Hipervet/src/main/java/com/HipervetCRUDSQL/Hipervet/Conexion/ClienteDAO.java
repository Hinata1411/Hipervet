package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteDAO extends Conexion {


        // Método para crear un empleado
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

        // Método para obtener todos los empleados (incluyendo los que no tienen puesto asignado)
        public List<Cliente> obtenerEmpleados() {
            List<Cliente> empleados = new ArrayList<>();
            String sql = "SELECT e.CodigoCliente, per.CodigoPersona, per.PrimerNombre, per.SegundoNombre, per.TercerNombre, " +
                    "per.PrimerApellido, per.SegundoApellido, per.TercerApellido, per.FechaNacimiento " +
                    "FROM persona AS per " +
                    "LEFT JOIN empleado e ON per.CodigoPersona = e.CodigoPersona " +
                    "WHERE tipoPersona = 'C'";  // Filtrar por empleados

            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setCodigoEmpleado(rs.getInt("CodigoCliente")); // Código de empleado (puede ser nulo si no tiene)
                    cliente.setCodigoPersona(rs.getInt("CodigoPersona")); // Código de persona
                    cliente.setPrimerNombre(rs.getString("PrimerNombre"));
                    cliente.setSegundoNombre(rs.getString("SegundoNombre"));
                    cliente.setTercerNombre(rs.getString("TercerNombre"));
                    cliente.setPrimerApellido(rs.getString("PrimerApellido"));
                    cliente.setSegundoApellido(rs.getString("SegundoApellido"));
                    cliente.setTercerApellido(rs.getString("TercerApellido"));
                    cliente.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());

                    cliente.add(cliente);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los clientes: " + e.getMessage());
            }

            return empleados;
        }

        // Método para asignar una mascota a un cliente
        public boolean asignarMascota(int codigoCliente, int codigoPersona) {
            String sql = "UPDATE Cliente SET CodigoCliente = ? WHERE CodigoPersona = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, CodigoCliente);  // Actualizar la mascota
                statement.setInt(2, codigoEmpleado);  // Usar el código del empleado

                return statement.executeUpdate() > 0; // Retorna true si se actualiza correctamente
            } catch (SQLException e) {
                System.err.println("Error al asignar el puesto: " + e.getMessage());
            }
            return false;
        }

        // Método para asignar o crear un código de empleado para una persona
        public boolean asignarCodigoCliente(int codigoPersona, int codigoCliente) {
            String verificarSql = "SELECT COUNT(*) FROM Cliente WHERE CodigoPersona = ?";
            String insertarSql = "INSERT INTO Cliente (CodigoCliente, CodigoPersona) VALUES (?, ?)";
            String actualizarSql = "UPDATE Cliente SET CodigoCliente = ? WHERE CodigoPersona = ?";

            try (Connection conexion = obtenerConexion();
                 PreparedStatement verificarStatement = conexion.prepareStatement(verificarSql)) {

                // Verificar si ya existe un empleado con el código de persona
                verificarStatement.setInt(1, codigoPersona);
                ResultSet rs = verificarStatement.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // Si ya existe, actualizar el código de empleado
                    try (PreparedStatement actualizarStatement = conexion.prepareStatement(actualizarSql)) {
                        actualizarStatement.setInt(1, codigoCliente);
                        actualizarStatement.setInt(2, codigoPersona);
                        return actualizarStatement.executeUpdate() > 0;
                    }
                } else {
                    // Si no existe, insertar un nuevo registro de empleado
                    try (PreparedStatement insertarStatement = conexion.prepareStatement(insertarSql)) {
                        insertarStatement.setInt(1, codigoCliente);
                        insertarStatement.setInt(2, codigoPersona);
                        return insertarStatement.executeUpdate() > 0;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al asignar el código de empleado: " + e.getMessage());
            }
            return false;
        }
}