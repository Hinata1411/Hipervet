package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Empleado;
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

                // Validaciones previas
                if (persona.getCodigoPersona() <= 0) {
                    throw new IllegalArgumentException("El código de persona no puede ser negativo o cero.");
                }

                // Asignar los valores correctos para cada columna
                statement.setInt(1, persona.getCodigoPersona()); // Código de la persona
                statement.setString(2, persona.getPrimerNombre()); // Primer Nombre
                statement.setString(3, persona.getSegundoNombre()); // Segundo Nombre
                statement.setString(4, persona.getTercerNombre()); // Tercer Nombre
                statement.setString(5, persona.getPrimerApellido()); // Primer Apellido
                statement.setString(6, persona.getSegundoApellido()); // Segundo Apellido
                statement.setString(7, persona.getTercerApellido()); // Tercer Apellido
                statement.setDate(8, java.sql.Date.valueOf(persona.getFechaNacimiento())); // Fecha de Nacimiento
                statement.setString(9, persona.getTipoPersona()); // Tipo de Persona (Empleado o Cliente)

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    return true;
                }
            } catch (SQLException e) {
                System.err.println("Error al crear la persona: " + e.getMessage());
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

        // Leer (obtener todas las personas)
        public List<Persona> obtenerPersonas() {
            List<Persona> personas = new ArrayList<>();
            String sql = "SELECT CodigoPersona, PrimerNombre, SegundoNombre, TercerNombre, " +
                    "PrimerApellido, SegundoApellido, TercerApellido, FechaNacimiento, TipoPersona " +
                    "FROM Persona";  // Consulta corregida para obtener los datos de Persona

            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    // Crear un nuevo objeto Persona por cada fila
                    Persona persona = new Persona();
                    persona.setCodigoPersona(rs.getInt("CodigoPersona"));
                    persona.setPrimerNombre(rs.getString("PrimerNombre"));
                    persona.setSegundoNombre(rs.getString("SegundoNombre"));
                    persona.setTercerNombre(rs.getString("TercerNombre"));
                    persona.setPrimerApellido(rs.getString("PrimerApellido"));
                    persona.setSegundoApellido(rs.getString("SegundoApellido"));
                    persona.setTercerApellido(rs.getString("TercerApellido"));
                    persona.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                    persona.setTipoPersona(rs.getString("TipoPersona")); // Agregando tipo de persona

                    // Añadir la persona a la lista
                    personas.add(persona);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener las personas: " + e.getMessage());
            }

            return personas;
        }

        // Método para actualizar una persona
        public boolean actualizarPersona(Persona persona) {
            String sql = "UPDATE Persona SET PrimerNombre = ?, SegundoNombre = ?, TercerNombre = ?, PrimerApellido = ?, " +
                    "SegundoApellido = ?, TercerApellido = ?, FechaNacimiento = ?, TipoPersona = ? WHERE CodigoPersona = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                // Asignar los valores correctos para cada columna
                statement.setString(1, persona.getPrimerNombre());
                statement.setString(2, persona.getSegundoNombre());
                statement.setString(3, persona.getTercerNombre());
                statement.setString(4, persona.getPrimerApellido());
                statement.setString(5, persona.getSegundoApellido());
                statement.setString(6, persona.getTercerApellido());
                statement.setDate(7, java.sql.Date.valueOf(persona.getFechaNacimiento()));
                statement.setString(8, persona.getTipoPersona()); // Tipo de Persona
                statement.setInt(9, persona.getCodigoPersona());

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al actualizar la persona: " + e.getMessage());
                return false;
            }
        }

        // Método para eliminar una persona
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

}