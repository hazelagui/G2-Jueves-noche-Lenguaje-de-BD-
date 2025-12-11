package model;

public class Locacion {

    private int idLocacion;
    private String nombre;
    private int Capacidad;
    private String Direccion;

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int Capacidad) {
        this.Capacidad = Capacidad;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public Locacion() {
    }

    public Locacion(int idLocacion, String nombre) {
        this.idLocacion = idLocacion;
        this.nombre = nombre;
    }

    public int getIdLocacion() {
        return idLocacion;
    }

    public void setIdLocacion(int idLocacion) {
        this.idLocacion = idLocacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "id: "+ idLocacion + ", nombre=" + nombre;
    }

}