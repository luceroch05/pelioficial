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
}
