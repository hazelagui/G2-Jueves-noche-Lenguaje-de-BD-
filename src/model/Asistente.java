package model;

/**
 * Clase modelo para representar un Asistente
 */
public class Asistente {
    private int idAsistente;
    private String nombre;
    private String correo;
    private String telefono;
    
    // Constructor vacío
    public Asistente() {
    }
    
    // Constructor completo
    public Asistente(int idAsistente, String nombre, String correo, String telefono) {
        this.idAsistente = idAsistente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    // Getters y Setters
    public int getIdAsistente() {
        return idAsistente;
    }
    
    public void setIdAsistente(int idAsistente) {
        this.idAsistente = idAsistente;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + correo + ")";
    }
}
