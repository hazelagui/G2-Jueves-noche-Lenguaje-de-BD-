package model;

import java.util.Date;

/**
 * Clase modelo para representar un Artista
 */
public class Artista {
    private int idArtista;
    private String nombre;
    private String generoMusical;
    private String paisOrigen;
    private Date fechaCreacion;
    private String creadoPor;
    
    // Constructor vacío
    public Artista() {
    }
    
    // Constructor completo
    public Artista(int idArtista, String nombre, String generoMusical, String paisOrigen) {
        this.idArtista = idArtista;
        this.nombre = nombre;
        this.generoMusical = generoMusical;
        this.paisOrigen = paisOrigen;
    }
    
    // Getters y Setters
    public int getIdArtista() {
        return idArtista;
    }
    
    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getGeneroMusical() {
        return generoMusical;
    }
    
    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }
    
    public String getPaisOrigen() {
        return paisOrigen;
    }
    
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getCreadoPor() {
        return creadoPor;
    }
    
    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + generoMusical + ")";
    }
}
