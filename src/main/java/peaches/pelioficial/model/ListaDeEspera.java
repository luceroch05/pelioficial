/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.model;

import java.time.LocalDate;

/**
 *
 * @author q-ql
 */
public class ListaDeEspera {
    private int listaEsperaId;
    private int peliculaId;
    private int socioId;
    private LocalDate fechaSolicitud;

    public ListaDeEspera() {
    }

    public ListaDeEspera(int listaEsperaId, int peliculaId, int socioId, LocalDate fechaSolicitud) {
        this.listaEsperaId = listaEsperaId;
        this.peliculaId = peliculaId;
        this.socioId = socioId;
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public int getListaEsperaId() {
        return listaEsperaId;
    }

    public void setListaEsperaId(int listaEsperaId) {
        this.listaEsperaId = listaEsperaId;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

    public int getSocioId() {
        return socioId;
    }

    public void setSocioId(int socioId) {
        this.socioId = socioId;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
