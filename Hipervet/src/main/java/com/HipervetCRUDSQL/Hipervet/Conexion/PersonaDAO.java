package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO extends Conexion {

    // Crear
    public boolean crearPersona(Persona persona) {
        String sql = "INSERT INTO Persona (CodigoPersona, PrimerNombre, SegundoNombre, TercerNombre, PrimerApellido, SegundoApellido, TercerApellido, TipoPersona, RazonSocial, FechaNacimiento, Genero) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1,persona.getCodigoPersona());
            statement.setString(2, persona.getPrimerNombre());
            statement.setString(3, persona.getSegundoNombre());
            statement.setString(4, persona.getTercerNombre());
            statement.setString(5, persona.getPrimerApellido());
            statement.setString(6, persona.getSegundoApellido());
            statement.setString(7, persona.getTercerApellido());
            statement.setString(8, persona.getTipoPersona());
            statement.setString(9, persona.getRazonSocial());
            statement.setDate(10, new java.sql.Date(persona.getFechaNacimiento().getTime()));
            statement.setString(11, persona.getGenero());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la persona: " + e.getMessage());
            return false;
        }
    }
    // Leer (obtener todas las personas)
    public List<Persona> obtenerPersonas() {
        List<Persona> personas = new ArrayList<>();
        Connection conn = Conexion.obtenerConexion();

        String sql = "SELECT CodigoPersona, PrimerNombre, SegundoNombre, TercerNombre, PrimerApellido, SegundoApellido, TercerApellido, TipoPersona, RazonSocial, FechaNacimiento, Genero FROM Persona";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
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
                persona.setTipoPersona(rs.getString("TipoPersona"));
                persona.setRazonSocial(rs.getString("RazonSocial"));
                persona.setFechaNacimiento(rs.getDate("FechaNacimiento"));
                persona.setGenero(rs.getString("Genero"));

                personas.add(persona);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las personas: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion();
        }

        return personas;
    }
    //Obtener por ID
    public Persona obtenerPersonaPorId(int codigoPersona) {
        Persona persona = null;
        String sql = "SELECT CodigoPersona, PrimerNombre, SegundoNombre, TercerNombre, PrimerApellido, SegundoApellido, TercerApellido, TipoPersona, RazonSocial, FechaNacimiento, Genero FROM Persona WHERE CodigoPersona = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoPersona);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    persona = new Persona();
                    persona.setCodigoPersona(rs.getInt("CodigoPersona"));
                    persona.setPrimerNombre(rs.getString("PrimerNombre"));
                    persona.setSegundoNombre(rs.getString("SegundoNombre"));
                    persona.setTercerNombre(rs.getString("TercerNombre"));
                    persona.setPrimerApellido(rs.getString("PrimerApellido"));
                    persona.setSegundoApellido(rs.getString("SegundoApellido"));
                    persona.setTercerApellido(rs.getString("TercerApellido"));
                    persona.setTipoPersona(rs.getString("TipoPersona"));
                    persona.setRazonSocial(rs.getString("RazonSocial"));
                    persona.setFechaNacimiento(rs.getDate("FechaNacimiento"));
                    persona.setGenero(rs.getString("Genero"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la persona: " + e.getMessage());
        }
        return persona;
    }

    // Actualizar
    public boolean actualizarPersona(Persona persona) {
        String sql = "UPDATE Persona SET PrimerNombre = ?, SegundoNombre = ?, TercerNombre = ?, PrimerApellido = ?, SegundoApellido = ?, TercerApellido = ?, TipoPersona = ?, RazonSocial = ?, FechaNacimiento = ?, Genero = ? WHERE CodigoPersona = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, persona.getPrimerNombre());
            statement.setString(2, persona.getSegundoNombre());
            statement.setString(3, persona.getTercerNombre());
            statement.setString(4, persona.getPrimerApellido());
            statement.setString(5, persona.getSegundoApellido());
            statement.setString(6, persona.getTercerApellido());
            statement.setString(7, persona.getTipoPersona());
            statement.setString(8, persona.getRazonSocial());
            statement.setDate(9, new java.sql.Date(persona.getFechaNacimiento().getTime()));
            statement.setString(10, persona.getGenero());
            statement.setInt(11, persona.getCodigoPersona());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la persona: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarPersona(int codigoPersona) {
        String sql = "DELETE FROM Persona WHERE CodigoPersona = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, codigoPersona);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la persona: " + e.getMessage());
            return false;
        }
    }
}