package com.HipervetCRUDSQL.Hipervet.Conexion;
import java.sql.*;

public class Conexion {
    // Cambiar estos valores según tu configuración de la base de datos SQL Server
    private static final String URL = "jdbc:sqlserver://localhost\\VE_SERVER1:50013;databaseName=HVV1";
    private static final String USUARIO = "sa";
    private static final String CONTRASENA = "123456789";

    public static Connection conexion = null;

    public static Connection obtenerConexion() {

        try {
            // Cargar el controlador JDBC de SQL Server (Asegúrate de tener el archivo .jar en tu proyecto)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Obtener la conexión a la base de datos
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC de SQL Server: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static boolean verificarCredenciales(String nombreUsuario, String password) {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE nombreUsuario = ? AND password = ?";
        try (PreparedStatement preparedStatement = obtenerConexion().prepareStatement(sql)) {
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Devuelve true si se encontró al menos un registro
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
