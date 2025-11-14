package model;

import java.util.Date;

/**
 * Clase modelo para representar un Evento
 */
public class Evento {
    private int idEvento;
    private String nombre;
    private Date fechaEvento;
    private int idLocacion;
    private int idArtista;
    private Date fechaCreacion;
    private String creadoPor;
    
    // Para mostrar información completa
    private String nombreArtista;
    private String nombreLocacion;
    
    // Constructor vacío
    public Evento() {
    }
    
    // Constructor completo
    public Evento(int idEvento, String nombre, Date fechaEvento, int idLocacion, int idArtista) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechaEvento = fechaEvento;
        this.idLocacion = idLocacion;
        this.idArtista = idArtista;
    }
    
    // Getters y Setters
    public int getIdEvento() {
        return idEvento;
    }
    
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Date getFechaEvento() {
        return fechaEvento;
    }
    
    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
    
    public int getIdLocacion() {
        return idLocacion;
    }
    
    public void setIdLocacion(int idLocacion) {
        this.idLocacion = idLocacion;
    }
    
    public int getIdArtista() {
        return idArtista;
    }
    
    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
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
    
    public String getNombreArtista() {
        return nombreArtista;
    }
    
    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }
    
    public String getNombreLocacion() {
        return nombreLocacion;
    }
    
    public void setNombreLocacion(String nombreLocacion) {
        this.nombreLocacion = nombreLocacion;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
