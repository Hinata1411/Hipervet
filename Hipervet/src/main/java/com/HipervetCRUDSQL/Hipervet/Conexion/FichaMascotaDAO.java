package com.HipervetCRUDSQL.Hipervet.Conexion;

import com.HipervetCRUDSQL.Hipervet.Entidades.FichaMascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FichaMascotaDAO extends Conexion {

        // Método para crear una mascota
        public boolean crearMascota(FichaMascota mascota) {
            String sql = "INSERT INTO FichaMascota (NumeroFicha, codigoEspecie, CodigoRaza, Nombre, FechaNacimiento, Talla, Genero) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                if (mascota.getNumeroFicha() <= 0) {
                    throw new IllegalArgumentException("El número de ficha no puede ser negativo o cero.");
                }
                statement.setInt(1, mascota.getNumeroFicha());
                statement.setString(2, mascota.getCodigoEspecie());
                statement.setString(3, mascota.getCodigoRaza());
                statement.setString(4, mascota.getNombre());
                statement.setDate(5, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
                statement.setString(6, mascota.getTalla());
                statement.setString(7, mascota.getGenero());

                int affectedRows = statement.executeUpdate();
                return affectedRows > 0;

            } catch (SQLException e) {
                System.err.println("Error al crear la mascota: " + e.getMessage());
                return false;
            }
        }

        // Método para obtener todas las mascotas
        public List<FichaMascota> obtenerMascotas() {
            List<FichaMascota> mascotas = new ArrayList<>();
            String sql = "SELECT NumeroFicha, codigoEspecie, CodigoRaza, Nombre, FechaNacimiento, Talla, Genero FROM FichaMascota";

            try (Connection conn = obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    FichaMascota mascota = new FichaMascota();
                    mascota.setNumeroFicha(rs.getInt("NumeroFicha"));
                    mascota.setCodigoEspecie(rs.getString("codigoEspecie"));
                    mascota.setCodigoRaza(rs.getString("CodigoRaza"));
                    mascota.setNombre(rs.getString("Nombre"));
                    mascota.setFechaNacimiento(rs.getDate("FechaNacimiento").toLocalDate());
                    mascota.setTalla(rs.getString("Talla"));
                    mascota.setGenero(rs.getString("Genero"));

                    mascotas.add(mascota);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener las mascotas: " + e.getMessage());
            }

            return mascotas;
        }

        // Método para actualizar una mascota usando el NumeroFicha
        public boolean actualizarMascota(int numeroFicha, FichaMascota mascotaActualizada) {
            String sql = "UPDATE FichaMascota SET Codigoespecie = ?, CodigoRaza = ?, Nombre = ?, FechaNacimiento = ?, " +
                    "Talla = ?, Genero = ? WHERE NumeroFicha = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setString(1, mascotaActualizada.getCodigoEspecie());
                statement.setString(2, mascotaActualizada.getCodigoRaza());
                statement.setString(3, mascotaActualizada.getNombre());
                statement.setDate(4, java.sql.Date.valueOf(mascotaActualizada.getFechaNacimiento()));
                statement.setString(5, mascotaActualizada.getTalla());
                statement.setString(6, mascotaActualizada.getGenero());
                statement.setInt(7, numeroFicha);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al actualizar la mascota: " + e.getMessage());
                return false;
            }
        }

        // Método para eliminar una mascota usando el NumeroFicha
        public boolean eliminarMascota(int numeroFicha) {
            String sql = "DELETE FROM FichaMascota WHERE NumeroFicha = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql)) {

                statement.setInt(1, numeroFicha);
                return statement.executeUpdate() > 0;

            } catch (SQLException e) {
                System.err.println("Error al eliminar la mascota: " + e.getMessage());
                return false;
            }
        }

        // Método para obtener el siguiente NumeroFicha disponible
        public int obtenerSiguienteNumeroFicha() {
            String sql = "SELECT ISNULL(MAX(NumeroFicha), 0) + 1 AS SiguienteNumeroFicha FROM FichaMascota";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("SiguienteNumeroFicha");
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener el siguiente número de ficha: " + e.getMessage());
            }
            return 1; // Retornar 1 si no hay fichas en la tabla
        }
}
