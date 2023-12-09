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
public class Prestamo {
    private int prestamoId;
    private int socioId; // ID del socio
    private int cintaId; // ID de la cinta
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String nombreSocio; // Nombre del socio
    private String tituloPelicula; // Título de la película
    private String estadoCinta; // Estado de la cinta


    public Prestamo() {
    }
    
    public Prestamo(int prestamoId, int socioId, int cintaId, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String nombreSocio, String tituloPelicula, String estadoCinta) {
        this.prestamoId = prestamoId;
        this.socioId = socioId;
        this.cintaId = cintaId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.nombreSocio = nombreSocio;
        this.tituloPelicula = tituloPelicula;
        this.estadoCinta = estadoCinta;
    }
    
    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public int getSocioId() {
        return socioId;
    }

    public void setSocioId(int socioId) {
        this.socioId = socioId;
    }

    public int getCintaId() {
        return cintaId;
    }

    public void setCintaId(int cintaId) {
        this.cintaId = cintaId;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public String getEstadoCinta() {
        return estadoCinta;
    }

    public void setEstadoCinta(String estadoCinta) {
        this.estadoCinta = estadoCinta;
    }
}
