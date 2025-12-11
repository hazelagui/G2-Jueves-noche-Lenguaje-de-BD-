package controller;

import model.Auditoria;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar operaciones de Auditoría
 */
public class AuditoriaController {
    
    /**
     * Listar todas las auditorías usando cursor
     */
    public List<Auditoria> listarAuditorias() {
        List<Auditoria> auditorias = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_listar_auditorias(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            
            rs = (ResultSet) stmt.getObject(1);
            
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();
                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setTablaAfectada(rs.getString("tabla_afectada"));
                auditoria.setOperacion(rs.getString("operacion"));
                auditoria.setIdRegistro(rs.getInt("id_registro"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setFechaOperacion(rs.getDate("fecha_operacion"));
                auditoria.setValoresAnteriores(rs.getString("valores_anteriores"));
                auditoria.setValoresNuevos(rs.getString("valores_nuevos"));
                auditoria.setDescripcion(rs.getString("descripcion"));
                
                auditorias.add(auditoria);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar auditorías: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return auditorias;
    }
    
    
    public String obtenerTablaMasActiva() {
    Connection conn = null;
    CallableStatement stmt = null;
    String resultado = "";

    try {
        conn = DatabaseConnection.getConnection();

        String sql = "{? = call fn_tabla_mas_activa()}";
        stmt = conn.prepareCall(sql);

        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();

        resultado = stmt.getString(1);

    } catch (SQLException e) {
        e.printStackTrace();
        resultado = "N/A";
    } finally {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return resultado;
}
    
    /**
     * Listar auditorías por tabla usando cursor
     */
    public List<Auditoria> listarAuditoriasPorTabla(String nombreTabla) {
        List<Auditoria> auditorias = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_listar_auditoria_por_tabla(?, ?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setString(1, nombreTabla);
            stmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            
            rs = (ResultSet) stmt.getObject(2);
            
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();
                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setTablaAfectada(rs.getString("tabla_afectada"));
                auditoria.setOperacion(rs.getString("operacion"));
                auditoria.setIdRegistro(rs.getInt("id_registro"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setFechaOperacion(rs.getDate("fecha_operacion"));
                auditoria.setDescripcion(rs.getString("descripcion"));
                
                auditorias.add(auditoria);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar auditorías por tabla: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return auditorias;
    }
    
    /**
     * Listar auditorías por rango de fechas usando cursor
     */
    public List<Auditoria> listarAuditoriasPorFecha(Date fechaInicio, Date fechaFin) {
        List<Auditoria> auditorias = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{call sp_listar_auditoria_por_fecha(?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            stmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            
            rs = (ResultSet) stmt.getObject(3);
            
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();
                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setTablaAfectada(rs.getString("tabla_afectada"));
                auditoria.setOperacion(rs.getString("operacion"));
                auditoria.setIdRegistro(rs.getInt("id_registro"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setFechaOperacion(rs.getDate("fecha_operacion"));
                auditoria.setDescripcion(rs.getString("descripcion"));
                
                auditorias.add(auditoria);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar auditorías por fecha: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return auditorias;
    }
    
    /**
     * Contar auditorías de una tabla usando función
     */
    public int contarAuditoriasPorTabla(String nombreTabla) {
        Connection conn = null;
        CallableStatement stmt = null;
        int contador = 0;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{? = call fn_contar_auditorias_tabla(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, Types.NUMERIC);
            stmt.setString(2, nombreTabla);
            
            stmt.execute();
            contador = stmt.getInt(1);
            
        } catch (SQLException e) {
            System.err.println("Error al contar auditorías: " + e.getMessage());
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
    
    /**
     * Obtener última operación de una tabla usando función
     */
    public String obtenerUltimaOperacion(String nombreTabla) {
        Connection conn = null;
        CallableStatement stmt = null;
        String operacion = "";
        
        try {
            conn = DatabaseConnection.getConnection();
            
            String sql = "{? = call fn_ultima_operacion_tabla(?)}";
            stmt = conn.prepareCall(sql);
            
            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.setString(2, nombreTabla);
            
            stmt.execute();
            operacion = stmt.getString(1);
            
        } catch (SQLException e) {
            System.err.println("Error al obtener última operación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return operacion;
    }
}
