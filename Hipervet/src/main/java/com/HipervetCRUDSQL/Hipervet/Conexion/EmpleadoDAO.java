package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends Conexion {

    // Crear
    public boolean crearEmpleado(Empleado empleado) {
        String sql = "INSERT INTO Empleado (CodigoEmpleado, CodigoPersona, CodigoPuesto) VALUES (?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, empleado.getCodigoEmpleado());
            statement.setInt(2, empleado.getCodigoPersona());
            statement.setInt(3, empleado.getCodigoPuesto());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear el empleado: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todos los empleados)
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT CodigoEmpleado, CodigoPersona, CodigoPuesto FROM Empleado";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                empleado.setCodigoPersona(rs.getInt("CodigoPersona"));
                empleado.setCodigoPuesto(rs.getInt("CodigoPuesto"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los empleados: " + e.getMessage());
        }

        return empleados;
    }

    // Obtener por ID
    public Empleado obtenerEmpleadoPorId(int codigoEmpleado) {
        Empleado empleado = null;
        String sql = "SELECT CodigoEmpleado, CodigoPersona, CodigoPuesto FROM Empleado WHERE CodigoEmpleado = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = new Empleado();
                    empleado.setCodigoEmpleado(rs.getInt("CodigoEmpleado"));
                    empleado.setCodigoPersona(rs.getInt("CodigoPersona"));
                    empleado.setCodigoPuesto(rs.getInt("CodigoPuesto"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el empleado: " + e.getMessage());
        }

        return empleado;
    }

    // Actualizar
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE Empleado SET CodigoPersona = ?, CodigoPuesto = ? WHERE CodigoEmpleado = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, empleado.getCodigoPersona());
            statement.setInt(2, empleado.getCodigoPuesto());
            statement.setInt(3, empleado.getCodigoEmpleado());
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
