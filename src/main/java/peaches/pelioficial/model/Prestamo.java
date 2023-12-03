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
    private int socioId;
    private int cintaId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo() {
    }

    public Prestamo(int prestamoId, int socioId, int cintaId, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.prestamoId = prestamoId;
        this.socioId = socioId;
        this.cintaId = cintaId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
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
}
