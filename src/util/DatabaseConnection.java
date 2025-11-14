package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos Oracle
 */
public class DatabaseConnection {
    
    // Configuración de conexión - AJUSTAR SEGÚN SU ENTORNO
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "HR"; // Cambiar según su usuario
    private static final String PASSWORD = "caca123X"; // Cambiar según su contraseña
    
    private static Connection connection = null;
    
    /**
     * Obtiene una conexión a la base de datos
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Registrar el driver de Oracle
                Class.forName("oracle.jdbc.driver.OracleDriver");
                
                // Establecer la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida exitosamente con Oracle");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de Oracle JDBC");
            System.err.println("Asegúrese de tener ojdbc8.jar en el classpath");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            System.err.println("URL: " + URL);
            System.err.println("Usuario: " + USER);
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada exitosamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }
    
    /**
     * Verifica si la conexión está activa
     */
    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Prueba la conexión a la base de datos
     */
    public static boolean testConnection() {
        Connection conn = getConnection();
        return conn != null && isConnected();
    }
}
