package com.HipervetCRUDSQL.Hipervet.Conexion;

import java.sql.*;


public class Conexion {

        // Cambiar estos valores según tu configuración de la base de datos SQL Server
        private static final String URL = "jdbc:sqlserver://localhost:50013;databaseName=HVV1";
        private static final String USUARIO = "sa";
        private static final String CONTRASENA = "123456789";

        // Obtener la conexión a la base de datos
        public static Connection obtenerConexion() {
            Connection conexion = null;
            try {
                // Cargar el controlador JDBC de SQL Server
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Establecer la conexión a la base de datos
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            } catch (ClassNotFoundException e) {
                System.err.println("Error al cargar el controlador JDBC de SQL Server: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
            return conexion;
        }

        // Cerrar la conexión a la base de datos
        public static void cerrarConexion(Connection conexion) {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }

        // Verificar las credenciales del usuario
        public static boolean verificarCredenciales(String nombreUsuario, String password) {
            String sql = "SELECT COUNT(*) FROM Usuario WHERE nombreUsuario = ? AND password = ?";
            try (Connection conexion = obtenerConexion();
                 PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

                preparedStatement.setString(1, nombreUsuario);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Retorna true si se encontró al menos un registro
                }
            } catch (SQLException e) {
                System.err.println("Error en la verificación de credenciales: " + e.getMessage());
            }
            return false;
        }
    }
