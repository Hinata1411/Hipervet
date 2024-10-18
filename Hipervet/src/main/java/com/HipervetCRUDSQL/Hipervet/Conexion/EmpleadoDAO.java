package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends Conexion {

    // Crear (ingresando manualmente el CodigoEmpleado y auto-incrementando CodigoPersona)
    public boolean crearEmpleado(Empleado empleado) {
        String sql = "INSERT INTO Empleado (CodigoEmpleado, CodigoPersona, CodigoPuesto) VALUES (?, ?, ?)";
        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Validaciones previas
            if (empleado.getCodigoEmpleado() <= 0 || empleado.getCodigoPuesto() <= 0) {
                throw new IllegalArgumentException("El código de empleado y de puesto no pueden ser negativos o cero.");
            }

            // Obtener el siguiente CodigoPersona disponible
            int siguienteCodigoPersona = obtenerSiguienteCodigoPersona();

            // Asignar correctamente los valores
            statement.setInt(1, empleado.getCodigoEmpleado()); // Código del empleado
            statement.setInt(2, siguienteCodigoPersona);       // Código de persona (incrementado automáticamente)
            statement.setInt(3, empleado.getCodigoPuesto());   // Código del puesto

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                empleado.setCodigoPersona(siguienteCodigoPersona); // Asignar el código de persona generado
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear el empleado: " + e.getMessage());
        }
        return false;
    }

    // Método para obtener el siguiente CodigoPersona disponible (incrementado automáticamente)
    private int obtenerSiguienteCodigoPersona() {
        String sql = "SELECT ISNULL(MAX(CodigoPersona), 0) + 1 AS SiguienteCodigoPersona FROM Persona";
        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("SiguienteCodigoPersona");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el siguiente código de persona: " + e.getMessage());
        }
        return 1; // Retornar 1 si no hay personas en la tabla
    }

    // Leer (obtener todos los empleados)
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT e.CodigoEmpleado, p.PrimerNombre, p.SegundoNombre, p.TercerNombre, " +
                "p.PrimerApellido, p.SegundoApellido, p.TercerApellido, p.FechaNacimiento, " +
                "e.CodigoPuesto, pu.Descripcion " +
                "FROM Empleado e " +
                "JOIN Persona p ON e.CodigoPersona = p.CodigoPersona " +
                "JOIN Puesto pu ON e.CodigoPuesto = pu.CodigoPuesto";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                empleado.setPrimerNombre(rs.getString("PrimerNombre"));
                empleado.setSegundoNombre(rs.getString("SegundoNombre"));
                empleado.setTercerNombre(rs.getString("TercerNombre"));
                empleado.setPrimerApellido(rs.getString("PrimerApellido"));
                empleado.setSegundoApellido(rs.getString("SegundoApellido"));
                empleado.setTercerApellido(rs.getString("TercerApellido"));
                empleado.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                empleado.setCodigoPuesto(rs.getInt("CodigoPuesto"));
                empleado.setDescripcionPuesto(rs.getString("Descripcion"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los empleados: " + e.getMessage());
        }

        return empleados;
    }


    // Actualizar
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE Empleado SET CodigoPersona = ?, CodigoPuesto = ? WHERE CodigoEmpleado = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            // Si CodigoPersona es un int, mantenemos setInt; de lo contrario, usamos setString
            statement.setInt(1, empleado.getCodigoEmpleado()); // Si CodigoPersona es un entero
            statement.setInt(2, empleado.getCodigoPuesto());  // Si CodigoPuesto es un entero
            statement.setInt(3, empleado.getCodigoEmpleado()); // Este es probablemente un entero

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el empleado: " + e.getMessage());
            return false;
        }
    }


    // Eliminar
    public boolean eliminarEmpleado(int codigoEmpleado) {
        String sql = "DELETE FROM Empleado WHERE CodigoEmpleado = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoEmpleado);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el empleado: " + e.getMessage());
            return false;
        }
    }
}
