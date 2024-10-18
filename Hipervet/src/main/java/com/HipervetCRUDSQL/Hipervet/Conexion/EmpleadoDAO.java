package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;

import java.util.Date;  // Para trabajar con fechas en el código en general
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends Conexion {

        // Método para crear un empleado
        public boolean crearEmpleado(Empleado empleado) {
            String sql = "INSERT INTO Empleado (CodigoEmpleado, CodigoPersona, CodigoPuesto) VALUES (?, ?, ?)";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, empleado.getCodigoEmpleado());
                statement.setInt(2, empleado.getCodigoPersona());
                statement.setInt(3, empleado.getCodigoPuesto());

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al crear el empleado: " + e.getMessage());
            }
            return false;
        }

        // Método para obtener todos los empleados (incluyendo los que no tienen puesto asignado)
        public List<Empleado> obtenerEmpleados() {
            List<Empleado> empleados = new ArrayList<>();
            String sql = "SELECT e.CodigoEmpleado, per.CodigoPersona, per.PrimerNombre, per.SegundoNombre, per.TercerNombre, " +
                    "per.PrimerApellido, per.SegundoApellido, per.TercerApellido, per.FechaNacimiento, p.CodigoPuesto, p.Descripcion " +
                    "FROM persona AS per " +
                    "LEFT JOIN empleado e ON per.CodigoPersona = e.CodigoPersona " +
                    "LEFT JOIN puesto p ON e.CodigoPuesto = p.CodigoPuesto " +
                    "WHERE tipoPersona = 'E'";  // Filtrar por empleados

            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setCodigoEmpleado(rs.getInt("CodigoEmpleado")); // Código de empleado (puede ser nulo si no tiene)
                    empleado.setCodigoPersona(rs.getInt("CodigoPersona")); // Código de persona
                    empleado.setPrimerNombre(rs.getString("PrimerNombre"));
                    empleado.setSegundoNombre(rs.getString("SegundoNombre"));
                    empleado.setTercerNombre(rs.getString("TercerNombre"));
                    empleado.setPrimerApellido(rs.getString("PrimerApellido"));
                    empleado.setSegundoApellido(rs.getString("SegundoApellido"));
                    empleado.setTercerApellido(rs.getString("TercerApellido"));
                    empleado.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                    empleado.setCodigoPuesto(rs.getInt("CodigoPuesto")); // Puede ser nulo
                    empleado.setDescripcionPuesto(rs.getString("Descripcion")); // Puede ser nulo

                    empleados.add(empleado);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los empleados: " + e.getMessage());
            }

            return empleados;
        }

        // Método para asignar un puesto a un empleado
        public boolean asignarPuesto(int codigoEmpleado, int codigoPuesto) {
            String sql = "UPDATE Empleado SET CodigoPuesto = ? WHERE CodigoEmpleado = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, codigoPuesto);  // Actualizar el puesto
                statement.setInt(2, codigoEmpleado);  // Usar el código del empleado

                return statement.executeUpdate() > 0; // Retorna true si se actualiza correctamente
            } catch (SQLException e) {
                System.err.println("Error al asignar el puesto: " + e.getMessage());
            }
            return false;
        }

        // Método para asignar o crear un código de empleado para una persona
        public boolean asignarCodigoEmpleado(int codigoPersona, int codigoEmpleado) {
            String verificarSql = "SELECT COUNT(*) FROM Empleado WHERE CodigoPersona = ?";
            String insertarSql = "INSERT INTO Empleado (CodigoEmpleado, CodigoPersona) VALUES (?, ?)";
            String actualizarSql = "UPDATE Empleado SET CodigoEmpleado = ? WHERE CodigoPersona = ?";

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
                        actualizarStatement.setInt(1, codigoEmpleado);
                        actualizarStatement.setInt(2, codigoPersona);
                        return actualizarStatement.executeUpdate() > 0;
                    }
                } else {
                    // Si no existe, insertar un nuevo registro de empleado
                    try (PreparedStatement insertarStatement = conexion.prepareStatement(insertarSql)) {
                        insertarStatement.setInt(1, codigoEmpleado);
                        insertarStatement.setInt(2, codigoPersona);
                        return insertarStatement.executeUpdate() > 0;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al asignar el código de empleado: " + e.getMessage());
            }
            return false;
        }

        // Método para obtener todos los puestos disponibles
        public List<String[]> obtenerPuestos() {
            List<String[]> puestos = new ArrayList<>();
            String sql = "SELECT CodigoPuesto, Descripcion FROM puesto";

            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    String[] puesto = new String[2];
                    puesto[0] = rs.getString("CodigoPuesto");
                    puesto[1] = rs.getString("Descripcion");
                    puestos.add(puesto);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los puestos: " + e.getMessage());
            }

            return puestos;
        }

        // Método para obtener el gromista más eficiente entre las fechas proporcionadas
        public List<String[]> obtenerGromistaMasEficiente(Date fechaInicio, Date fechaFin) {
            List<String[]> resultados = new ArrayList<>();
            String sql = "SELECT e.CodigoEmpleado, CONCAT(p.PrimerNombre, ' ', p.PrimerApellido) AS NombreCompleto, COUNT(c.CodigoCita) AS TotalCitas " +
                    "FROM empleado e " +
                    "JOIN cita c ON e.CodigoEmpleado = c.CodigoEmpleado " +
                    "JOIN persona p ON e.CodigoPersona = p.CodigoPersona " +
                    "WHERE e.CodigoPuesto = ? AND c.FechaCita BETWEEN ? AND ? " +  // Filtrar por gromista y fechas
                    "GROUP BY e.CodigoEmpleado, p.PrimerNombre, p.PrimerApellido " +
                    "ORDER BY TotalCitas DESC LIMIT 1";

            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, 2); // Suponiendo que el CódigoPuesto 2 es el de Gromista
                statement.setDate(2, new java.sql.Date(fechaInicio.getTime()));
                statement.setDate(3, new java.sql.Date(fechaFin.getTime()));
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String[] resultado = new String[3];
                    resultado[0] = rs.getString("CodigoEmpleado");
                    resultado[1] = rs.getString("NombreCompleto");
                    resultado[2] = rs.getString("TotalCitas");
                    resultados.add(resultado);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener el gromista más eficiente: " + e.getMessage());
            }

            return resultados;
        }
}
