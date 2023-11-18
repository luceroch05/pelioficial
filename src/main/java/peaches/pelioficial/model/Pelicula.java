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
public class Pelicula {
    private int peliculaId;
    private String titulo;
    private String genero;
    private int directorId;
    private List<Integer> actores;//Lista de Id's de Actores

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public List<Integer> getActores() {
        return actores;
    }

    public void setActores(List<Integer> actores) {
        this.actores = actores;
    }
}
