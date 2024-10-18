package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO extends Conexion {

        // Método para crear una persona
        public boolean crearPersona(Persona persona) {
            String sql = "INSERT INTO Persona (CodigoPersona, PrimerNombre, SegundoNombre, TercerNombre, " +
                    "PrimerApellido, SegundoApellido, TercerApellido, FechaNacimiento, TipoPersona) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                if (persona.getCodigoPersona() <= 0) {
                    throw new IllegalArgumentException("El código de persona no puede ser negativo o cero.");
                }

                statement.setInt(1, persona.getCodigoPersona());
                statement.setString(2, persona.getPrimerNombre());
                statement.setString(3, persona.getSegundoNombre());
                statement.setString(4, persona.getTercerNombre());
                statement.setString(5, persona.getPrimerApellido());
                statement.setString(6, persona.getSegundoApellido());
                statement.setString(7, persona.getTercerApellido());
                statement.setDate(8, java.sql.Date.valueOf(persona.getFechaNacimiento()));
                statement.setString(9, persona.getTipoPersona());

                int affectedRows = statement.executeUpdate();
                return affectedRows > 0;

            } catch (SQLException e) {
                System.err.println("Error al crear la persona: " + e.getMessage());
                return false;
            }
        }

        // Método para obtener todas las personas
        public List<Persona> obtenerPersonas() {
            List<Persona> personas = new ArrayList<>();
            String sql = "SELECT CodigoPersona, PrimerNombre, SegundoNombre, TercerNombre, " +
                    "PrimerApellido, SegundoApellido, TercerApellido, FechaNacimiento, TipoPersona " +
                    "FROM Persona";

            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Persona persona = new Persona();
                    persona.setCodigoPersona(rs.getInt("CodigoPersona"));
                    persona.setPrimerNombre(rs.getString("PrimerNombre"));
                    persona.setSegundoNombre(rs.getString("SegundoNombre"));
                    persona.setTercerNombre(rs.getString("TercerNombre"));
                    persona.setPrimerApellido(rs.getString("PrimerApellido"));
                    persona.setSegundoApellido(rs.getString("SegundoApellido"));
                    persona.setTercerApellido(rs.getString("TercerApellido"));
                    persona.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                    persona.setTipoPersona(rs.getString("TipoPersona"));

                    personas.add(persona);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener las personas: " + e.getMessage());
            }

            return personas;
        }

        // Método para actualizar una persona usando el códigoPersona
        public boolean actualizarPersona(int codigoPersona, Persona personaActualizada) {
            String sql = "UPDATE Persona SET PrimerNombre = ?, SegundoNombre = ?, TercerNombre = ?, " +
                    "PrimerApellido = ?, SegundoApellido = ?, TercerApellido = ?, FechaNacimiento = ?, " +
                    "TipoPersona = ? WHERE CodigoPersona = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setString(1, personaActualizada.getPrimerNombre());
                statement.setString(2, personaActualizada.getSegundoNombre());
                statement.setString(3, personaActualizada.getTercerNombre());
                statement.setString(4, personaActualizada.getPrimerApellido());
                statement.setString(5, personaActualizada.getSegundoApellido());
                statement.setString(6, personaActualizada.getTercerApellido());
                statement.setDate(7, java.sql.Date.valueOf(personaActualizada.getFechaNacimiento()));
                statement.setString(8, personaActualizada.getTipoPersona());
                statement.setInt(9, codigoPersona);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al actualizar la persona: " + e.getMessage());
                return false;
            }
        }

        // Método para eliminar una persona usando el códigoPersona
        public boolean eliminarPersona(int codigoPersona) {
            String sql = "DELETE FROM Persona WHERE CodigoPersona = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, codigoPersona);
                return statement.executeUpdate() > 0;

            } catch (SQLException e) {
                System.err.println("Error al eliminar la persona: " + e.getMessage());
                return false;
            }
        }

        // Método para obtener el siguiente CodigoPersona disponible
        public int obtenerSiguienteCodigoPersona() {
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

        // Metodo para verificar dependencias en la tabla Empleado
        public boolean tieneDependenciasEnEmpleado(int codigoPersona) {
            String sql = "SELECT COUNT(*) FROM Empleado WHERE CodigoPersona = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setInt(1, codigoPersona);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                System.err.println("Error al verificar dependencias en Empleado: " + e.getMessage());
            }
            return false;
        }
}
