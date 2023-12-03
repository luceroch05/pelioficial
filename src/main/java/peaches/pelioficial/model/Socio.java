/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.model;

import java.util.List;

/**
 *
 * @author q-ql
 */
public class Socio {
    private int socioId;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Director> directoresFavoritos;
    private List<Actor> actoresFavoritos;
    private List<Genero> generosPreferidos;

    public Socio() {
    }

    public Socio(int socioId, String nombre, String direccion, String telefono) {
        this.socioId = socioId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

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

    public List<Director> getDirectoresFavoritos() {
        return directoresFavoritos;
    }

    public void setDirectoresFavoritos(List<Director> directoresFavoritos) {
        this.directoresFavoritos = directoresFavoritos;
    }

    public List<Actor> getActoresFavoritos() {
        return actoresFavoritos;
    }

    public void setActoresFavoritos(List<Actor> actoresFavoritos) {
        this.actoresFavoritos = actoresFavoritos;
    }

    public List<Genero> getGenerosPreferidos() {
        return generosPreferidos;
    }

    public void setGenerosPreferidos(List<Genero> generosPreferidos) {
        this.generosPreferidos = generosPreferidos;
    }
    
}
