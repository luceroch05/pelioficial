/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.model;

/**
 *
 * @author q-ql
 */
public class Socio {
    private int socioId;
    private String nombre;
    private String direccion;
    private String telefono;
    private String directoresFavoritos;
    private String actoresFavoritos;
    private String generosPreferidos;

    public int getSocioId() {
        return socioId;
    }

    public void setSocioId(int socioId) {
        this.socioId = socioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDirectoresFavoritos() {
        return directoresFavoritos;
    }

    public void setDirectoresFavoritos(String directoresFavoritos) {
        this.directoresFavoritos = directoresFavoritos;
    }

    public String getActoresFavoritos() {
        return actoresFavoritos;
    }

    public void setActoresFavoritos(String actoresFavoritos) {
        this.actoresFavoritos = actoresFavoritos;
    }

    public String getGenerosPreferidos() {
        return generosPreferidos;
    }

    public void setGenerosPreferidos(String generosPreferidos) {
        this.generosPreferidos = generosPreferidos;
    }
}
