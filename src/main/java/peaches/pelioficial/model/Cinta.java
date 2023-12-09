/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.model;

/**
 *
 * @author q-ql
 */
public class Cinta {
    private int cintaId;
    private int peliculaId;
    private String estado; //Disponible, Prestado, Daniada, Perdida
    private String tituloPelicula; // Nuevo atributo para almacenar el título

    public Cinta() {
    }

    public Cinta(int cintaId, int peliculaId, String estado) {
        this.cintaId = cintaId;
        this.peliculaId = peliculaId;
        this.estado = estado;
    }
    
    public int getCintaId() {
        return cintaId;
    }

    public void setCintaId(int cintaId) {
        this.cintaId = cintaId;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getTituloPelicula() {
        return tituloPelicula;
    }
    
    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }
}
