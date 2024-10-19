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
        String sql = "INSERT INTO FichaMascota (NumeroFicha, codigoEspecie, CodigoRaza, Nombre, FechaNacimiento, Talla, Genero, codigoCliente) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, mascota.getNumeroFicha());
            statement.setString(2, mascota.getCodigoEspecie());
            statement.setString(3, mascota.getCodigoRaza());
            statement.setString(4, mascota.getNombre());
            statement.setDate(5, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
            statement.setString(6, mascota.getTalla());
            statement.setString(7, mascota.getGenero());
            statement.setInt(8, mascota.getCodigoCliente()); // Incluir el código del cliente

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error al crear la mascota: " + e.getMessage());
            return false;
        }
    }

    public List<FichaMascota> obtenerMascotas() {
        List<FichaMascota> mascotas = new ArrayList<>();
        String sql = "SELECT fm.NumeroFicha, fm.codigoEspecie, fm.CodigoRaza, fm.Nombre, fm.FechaNacimiento, " +
                "fm.Talla, fm.Genero, CONCAT(p.primerNombre, ' ', p.primerApellido) AS NombreDueño " +
                "FROM FichaMascota fm " +
                "LEFT JOIN Cliente c ON c.CodigoCliente = fm.codigoCliente " +
                "LEFT JOIN Persona p ON p.CodigoPersona = c.CodigoPersona";

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
                mascota.setNombreDuenio(rs.getString("NombreDueño"));

                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las mascotas: " + e.getMessage());
        }

        return mascotas;
    }


    // Método para actualizar una mascota usando el NumeroFicha
    public boolean actualizarMascota(int numeroFicha, FichaMascota mascotaActualizada) {
        String sql = "UPDATE FichaMascota SET CodigoEspecie = ?, CodigoRaza = ?, Nombre = ?, FechaNacimiento = ?, " +
                "Talla = ?, Genero = ?, codigoCliente = ? WHERE NumeroFicha = ?";
        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, mascotaActualizada.getCodigoEspecie());
            statement.setString(2, mascotaActualizada.getCodigoRaza());
            statement.setString(3, mascotaActualizada.getNombre());
            statement.setDate(4, java.sql.Date.valueOf(mascotaActualizada.getFechaNacimiento()));
            statement.setString(5, mascotaActualizada.getTalla());
            statement.setString(6, mascotaActualizada.getGenero());
            statement.setInt(7, mascotaActualizada.getCodigoCliente());
            statement.setInt(8, numeroFicha);

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

    // Método para obtener las especies
    public List<String[]> obtenerEspecies() {
        List<String[]> especies = new ArrayList<>();
        String sql = "SELECT CodigoEspecie, Descripcion FROM Especie";

        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String[] especie = new String[2];
                especie[0] = rs.getString("CodigoEspecie");
                especie[1] = rs.getString("Descripcion");
                especies.add(especie);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las especies: " + e.getMessage());
        }

        return especies;
    }

    // Método para obtener las razas
    public List<String[]> obtenerRazas() {
        List<String[]> razas = new ArrayList<>();
        String sql = "SELECT codigoRaza, descripcion FROM Raza";

        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String[] raza = new String[2];
                raza[0] = rs.getString("codigoRaza");
                raza[1] = rs.getString("descripcion");
                razas.add(raza);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las razas: " + e.getMessage());
        }

        return razas;
    }
    // Método para obtener los clientes
    public List<String[]> obtenerClientes() {
        List<String[]> clientes = new ArrayList<>();
        String sql = "SELECT C.CodigoCliente, CONCAT(P.primerNombre, ' ', P.primerApellido) AS NombreCliente " +
                "FROM Cliente AS C " +
                "LEFT JOIN Persona AS P ON C.CodigoPersona = P.CodigoPersona";

        try (Connection conexion = obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String[] cliente = new String[2];
                cliente[0] = rs.getString("CodigoCliente");
                cliente[1] = rs.getString("NombreCliente");
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los clientes: " + e.getMessage());
        }

        return clientes;
    }
}
