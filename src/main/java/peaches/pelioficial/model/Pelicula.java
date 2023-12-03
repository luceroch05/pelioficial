/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author q-ql
 */
public class Pelicula {
    private int peliculaId;
    private String titulo;
    private Director director;
    
    public Pelicula(){
    }

    public Pelicula(int peliculaId, String titulo, Director director) {
        this.peliculaId = peliculaId;
        this.titulo = titulo;
        this.director = director;
    }

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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
