package controller;

import model.Locacion;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocacionController {

    public List<Locacion> listarLocaciones() {
        List<Locacion> locaciones = new ArrayList<>();

        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = DatabaseConnection.getConnection();

            String sql = "{call sp_listar_locaciones(?)}";
            stmt = conn.prepareCall(sql);

            stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {

                Locacion loc = new Locacion();

                loc.setIdLocacion(rs.getInt("id_locacion"));
                loc.setNombre(rs.getString("nombre"));
                loc.setDireccion(rs.getString("direccion"));
                loc.setCapacidad(rs.getInt("capacidad"));

                locaciones.add(loc);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar locaciones: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception ignore) {}
        }

        return locaciones;
    }
}