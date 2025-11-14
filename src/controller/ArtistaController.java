package controller;

import model.Artista;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar operaciones CRUD de Artistas
 * Utiliza únicamente procedimientos almacenados
 */
public class ArtistaController {
    
    /**
     * Insertar un nuevo artista usando procedimiento almacenado
     */
    public boolean insertarArtista(Artista artista) {
        Connection conn = null;
        CallableStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Llamar al procedimiento almacenado sp_insertar_artista
            String sql = "{call sp_insertar_artista(?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            
            // Parámetros de entrada
            stmt.setString(1, artista.getNombre());
            stmt.setString(2, artista.getGeneroMusical());
            stmt.setString(3, artista.getPaisOrigen());
            
            // Parámetro de salida
            stmt.registerOutParameter(4, Types.NUMERIC);
            
            stmt.execute();
            
            // Obtener el ID generado
            int idGenerado = stmt.getInt(4);
            artista.setIdArtista(idGenerado);
            
            System.out.println("Artista insertado exitosamente con ID: " + idGenerado);
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar artista: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Actualizar un artista usando procedimiento almacenado
     */
    public boolean actualizarArtista(Artista artista) {
        Connection conn = null;
        CallableStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Llamar al procedimiento almacenado sp_actualizar_artista
            String sql = "{call sp_actualizar_artista(?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setInt(1, artista.getIdArtista());
            stmt.setString(2, artista.getNombre());
            stmt.setString(3, artista.getGeneroMusical());
            stmt.setString(4, artista.getPaisOrigen());
            
            stmt.execute();
            
            System.out.println("Artista actualizado exitosamente");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar artista: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Eliminar un artista usando procedimiento almacenado
     */
    public boolean eliminarArtista(int idArtista) {
        Connection conn = null;
        CallableStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Llamar al procedimiento almacenado sp_eliminar_artista
            String sql = "{call sp_eliminar_artista(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setInt(1, idArtista);
            stmt.execute();
            
            System.out.println("Artista eliminado exitosamente");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar artista: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Listar todos los artistas usando cursor
     */
    public List<Artista> listarArtistas() {
        List<Artista> artistas = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Llamar al procedimiento con cursor sp_listar_artistas
            String sql = "{call sp_listar_artistas(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            
            rs = (ResultSet) stmt.getObject(1);
            
            while (rs.next()) {
                Artista artista = new Artista();
                artista.setIdArtista(rs.getInt("id_artista"));
                artista.setNombre(rs.getString("nombre"));
                artista.setGeneroMusical(rs.getString("genero_musical"));
                artista.setPaisOrigen(rs.getString("pais_origen"));
                artista.setFechaCreacion(rs.getDate("fecha_creacion"));
                artista.setCreadoPor(rs.getString("creado_por"));
                
                artistas.add(artista);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar artistas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return artistas;
    }
    
    /**
     * Obtener el nombre de un artista usando función
     */
    public String obtenerNombreArtista(int idArtista) {
        Connection conn = null;
        CallableStatement stmt = null;
        String nombre = "";
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Llamar a la función fn_obtener_nombre_artista
            String sql = "{? = call fn_obtener_nombre_artista(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.setInt(2, idArtista);
            
            stmt.execute();
            nombre = stmt.getString(1);
            
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre del artista: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return nombre;
    }
}
