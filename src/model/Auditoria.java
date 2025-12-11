package model;

import java.util.Date;

/**
 * Clase modelo para representar un registro de Auditoría
 */
public class Auditoria {
    //
    private int idAuditoria;
    private String tablaAfectada;
    private String operacion;
    private int idRegistro;
    private String usuario;
    private Date fechaOperacion;
    private String valoresAnteriores;
    private String valoresNuevos;
    private String descripcion;
    
    // Constructor vacío
    public Auditoria() {
    }
    
    // Constructor completo
    public Auditoria(int idAuditoria, String tablaAfectada, String operacion, 
                     int idRegistro, String usuario, Date fechaOperacion, String descripcion) {
        this.idAuditoria = idAuditoria;
        this.tablaAfectada = tablaAfectada;
        this.operacion = operacion;
        this.idRegistro = idRegistro;
        this.usuario = usuario;
        this.fechaOperacion = fechaOperacion;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public int getIdAuditoria() {
        return idAuditoria;
    }
    
    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }
    
    public String getTablaAfectada() {
        return tablaAfectada;
    }
    
    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }
    
    public String getOperacion() {
        return operacion;
    }
    
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
    
    public int getIdRegistro() {
        return idRegistro;
    }
    
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public Date getFechaOperacion() {
        return fechaOperacion;
    }
    
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }
    
    public String getValoresAnteriores() {
        return valoresAnteriores;
    }
    
    public void setValoresAnteriores(String valoresAnteriores) {
        this.valoresAnteriores = valoresAnteriores;
    }
    
    public String getValoresNuevos() {
        return valoresNuevos;
    }
    
    public void setValoresNuevos(String valoresNuevos) {
        this.valoresNuevos = valoresNuevos;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return operacion + " en " + tablaAfectada + " - " + descripcion;
    }
}
