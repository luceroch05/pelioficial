/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.sql.Connection;
import java.util.List;
import peaches.pelioficial.dao.PrestamoDAO;
import peaches.pelioficial.model.Prestamo;

/**
 *
 * @author mtx0v
 */
public class PrestamoService {
    private PrestamoDAO prestamoDAO;
    
    public PrestamoService(Connection connection){
        this.prestamoDAO = new PrestamoDAO(connection);
    }
    
    public List<Prestamo> buscarPrestamosPorNombreSocio(String nombreSocio) {
        return prestamoDAO.buscarPrestamosPorNombreSocio(nombreSocio);
    }
    
    public List<Prestamo> obtenerTodosLosPrestamos() {
        return prestamoDAO.obtenerTodosLosPrestamos();
    }
    
    public boolean agregarPrestamo(Prestamo prestamo) {
        return prestamoDAO.insertarPrestamo(prestamo);
    }
}
