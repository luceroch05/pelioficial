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
    private Socio socio;
    private Cinta cinta;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String nombreSocio;
    private String tituloPelicula;
    private String estadoCinta;

    public Prestamo() {
        this.socio = new Socio();
        this.cinta = new Cinta();
    }

    public Prestamo(int prestamoId, Socio socio, Cinta cinta, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.prestamoId = prestamoId;
        this.socio = socio;
        this.cinta = cinta;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Cinta getCinta() {
        return cinta;
    }

    public void setCinta(Cinta cinta) {
        this.cinta = cinta;
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
        return socio.getNombre();
    }

    public String getTituloPelicula() {
        return cinta.getTituloPelicula();
    }

    public String getEstadoCinta() {
        return cinta.getEstado();
    }
    
    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public void setEstadoCinta(String estadoCinta) {
        this.estadoCinta = estadoCinta;
    }
}
