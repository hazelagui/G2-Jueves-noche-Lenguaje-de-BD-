package controller;

import model.Evento;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar operaciones CRUD de Eventos
 */
public class EventoController {
    
    /**
     * Insertar un nuevo evento usando procedimiento almacenado
     */
    public boolean insertarEvento(Evento evento) {
        Connection conn = null;
        CallableStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_insertar_evento(?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setString(1, evento.getNombre());
            stmt.setDate(2, new java.sql.Date(evento.getFechaEvento().getTime()));
            stmt.setInt(3, evento.getIdLocacion());
            stmt.setInt(4, evento.getIdArtista());
            stmt.registerOutParameter(5, Types.NUMERIC);
            
            stmt.execute();
            
            int idGenerado = stmt.getInt(5);
            evento.setIdEvento(idGenerado);
            
            System.out.println("Evento insertado exitosamente con ID: " + idGenerado);
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar evento: " + e.getMessage());
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
     * Actualizar un evento usando procedimiento almacenado
     */
    public boolean actualizarEvento(Evento evento) {
        Connection conn = null;
        CallableStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_actualizar_evento(?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setInt(1, evento.getIdEvento());
            stmt.setString(2, evento.getNombre());
            stmt.setDate(3, new java.sql.Date(evento.getFechaEvento().getTime()));
            stmt.setInt(4, evento.getIdLocacion());
            stmt.setInt(5, evento.getIdArtista());
            
            stmt.execute();
            
            System.out.println("Evento actualizado exitosamente");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar evento: " + e.getMessage());
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
     * Eliminar un evento usando procedimiento almacenado
     */
    public boolean eliminarEvento(int idEvento) {
        Connection conn = null;
        CallableStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_eliminar_evento(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setInt(1, idEvento);
            stmt.execute();
            
            System.out.println("Evento eliminado exitosamente");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar evento: " + e.getMessage());
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
     * Listar todos los eventos usando cursor
     */
    public List<Evento> listarEventos() {
        List<Evento> eventos = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_listar_eventos(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            
            rs = (ResultSet) stmt.getObject(1);
            
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setIdEvento(rs.getInt("id_evento"));
                evento.setNombre(rs.getString("nombre"));
                evento.setFechaEvento(rs.getDate("fecha_evento"));
                evento.setIdLocacion(rs.getInt("id_locacion"));
                evento.setIdArtista(rs.getInt("id_artista"));
                evento.setFechaCreacion(rs.getDate("fecha_creacion"));
                evento.setCreadoPor(rs.getString("creado_por"));
                
                eventos.add(evento);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar eventos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return eventos;
    }
    
    /**
     * Listar eventos próximos usando cursor
     */
    public List<Evento> listarEventosProximos() {
        List<Evento> eventos = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_listar_eventos_proximos(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            
            rs = (ResultSet) stmt.getObject(1);
            
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setIdEvento(rs.getInt("id_evento"));
                evento.setNombre(rs.getString("nombre"));
                evento.setFechaEvento(rs.getDate("fecha_evento"));
                evento.setNombreArtista(rs.getString("artista"));
                evento.setNombreLocacion(rs.getString("locacion"));
                
                eventos.add(evento);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar eventos próximos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return eventos;
    }
    
    /**
     * Calcular total de ventas de un evento usando función
     */
    public double calcularTotalVentas(int idEvento) {
        Connection conn = null;
        CallableStatement stmt = null;
        double total = 0.0;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{? = call fn_total_ventas_evento(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, Types.NUMERIC);
            stmt.setInt(2, idEvento);
            
            stmt.execute();
            total = stmt.getDouble(1);
            
        } catch (SQLException e) {
            System.err.println("Error al calcular ventas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return total;
    }
    
    /**
     * Contar asistentes de un evento usando función
     */
    public int contarAsistentes(int idEvento) {
        Connection conn = null;
        CallableStatement stmt = null;
        int contador = 0;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{? = call fn_contar_asistentes_evento(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, Types.NUMERIC);
            stmt.setInt(2, idEvento);
            
            stmt.execute();
            contador = stmt.getInt(1);
            
        } catch (SQLException e) {
            System.err.println("Error al contar asistentes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return contador;
    }
}
