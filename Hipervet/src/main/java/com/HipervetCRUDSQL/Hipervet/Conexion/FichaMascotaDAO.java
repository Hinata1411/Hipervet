package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.FichaMascota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FichaMascotaDAO extends Conexion {

    // Crear
    public boolean crearFichaMascota(FichaMascota ficha) {
        String sql = "INSERT INTO FichaMascota (NumeroFicha, CodigoEspecie, CodigoRaza, Nombre, FechaNacimiento, Talla, Genero) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, ficha.getNumeroFicha());
            statement.setInt(2, ficha.getCodigoEspecie());
            statement.setInt(3, ficha.getCodigoRaza());
            statement.setString(4, ficha.getNombre());
            statement.setDate(5, java.sql.Date.valueOf(ficha.getFechaNacimiento()));
            statement.setString(6, ficha.getTalla());
            statement.setString(7, ficha.getGenero());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear la ficha de la mascota: " + e.getMessage());
            return false;
        }
    }

    // Leer (obtener todas las fichas)
    public List<FichaMascota> obtenerFichasMascotas() {
        List<FichaMascota> fichas = new ArrayList<>();
        String sql = "SELECT NumeroFicha, CodigoEspecie, CodigoRaza, Nombre, FechaNacimiento, Talla, Genero FROM FichaMascota";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FichaMascota ficha = new FichaMascota();
                ficha.setNumeroFicha(rs.getInt("NumeroFicha"));
                ficha.setCodigoEspecie(rs.getInt("CodigoEspecie"));
                ficha.setCodigoRaza(rs.getInt("CodigoRaza"));
                ficha.setNombre(rs.getString("Nombre"));
                ficha.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                ficha.setTalla(rs.getString("Talla"));
                ficha.setGenero(rs.getString("Genero"));
                fichas.add(ficha);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las fichas de mascotas: " + e.getMessage());
        }

        return fichas;
    }

    // Obtener por ID
    public FichaMascota obtenerFichaMascotaPorId(int numeroFicha) {
        FichaMascota ficha = null;
        String sql = "SELECT NumeroFicha, CodigoEspecie, CodigoRaza, Nombre, FechaNacimiento, Talla, Genero FROM FichaMascota WHERE NumeroFicha = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroFicha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ficha = new FichaMascota();
                    ficha.setNumeroFicha(rs.getInt("NumeroFicha"));
                    ficha.setCodigoEspecie(rs.getInt("CodigoEspecie"));
                    ficha.setCodigoRaza(rs.getInt("CodigoRaza"));
                    ficha.setNombre(rs.getString("Nombre"));
                    ficha.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                    ficha.setTalla(rs.getString("Talla"));
                    ficha.setGenero(rs.getString("Genero"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la ficha de la mascota: " + e.getMessage());
        }

        return ficha;
    }

    // Actualizar
    public boolean actualizarFichaMascota(FichaMascota ficha) {
        String sql = "UPDATE FichaMascota SET CodigoEspecie = ?, CodigoRaza = ?, Nombre = ?, FechaNacimiento = ?, Talla = ?, Genero = ? WHERE NumeroFicha = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, ficha.getCodigoEspecie());
            statement.setInt(2, ficha.getCodigoRaza());
            statement.setString(3, ficha.getNombre());
            statement.setDate(4, java.sql.Date.valueOf(ficha.getFechaNacimiento()));
            statement.setString(5, ficha.getTalla());
            statement.setString(6, ficha.getGenero());
            statement.setInt(7, ficha.getNumeroFicha());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la ficha de la mascota: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarFichaMascota(int numeroFicha) {
        String sql = "DELETE FROM FichaMascota WHERE NumeroFicha = ?";
        try (Connection conexion = obtenerConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, numeroFicha);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la ficha de la mascota: " + e.getMessage());
            return false;
        }
    }
}
