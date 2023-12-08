/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.util.ArrayList;
import java.util.List;
import peaches.pelioficial.dao.CintaDAO;
import peaches.pelioficial.model.Cinta;
import peaches.pelioficial.util.DatabaseConnector;

/**
 *
 * @author mtx0v
 */
public class CintaService {
    private CintaDAO cintaDAO;
    
    public CintaService(){
        this.cintaDAO = new CintaDAO(DatabaseConnector.conectar());
    }
    
    public void agregarCinta(Cinta cinta) {
        cintaDAO.agregarCinta(cinta);
    }
    
    public int obtenerPeliculaIdPorNombre(String nombrePelicula) {
        return cintaDAO.obtenerPeliculaIdPorNombre(nombrePelicula);
    }
    
    public void actualizarCinta(Cinta cinta) {
        cintaDAO.actualizarCinta(cinta);
    }
    
    public Cinta obtenerCintaPorId(int cintaId) {
        return cintaDAO.obtenerCintaPorId(cintaId);
    }
    
    public boolean eliminarCinta(int cintaId) {
        return cintaDAO.eliminarCinta(cintaId);
    }
    
    public List<Cinta> obtenerTodasLasCintas() {
        return cintaDAO.obtenerTodasLasCintas();
    }
    
    public List<Cinta> buscarCintasPorNombrePelicula(String nombrePelicula) {
        return cintaDAO.buscarCintasPorNombrePelicula(nombrePelicula);
    }
    
    public List<Cinta> obtenerCintasDisponibles() {
        List<Cinta> cintasDisponibles = new ArrayList<>();
        String estadoDisponible = "Disponible"; // Asegúrate de usar el estado correcto que usas en tu base de datos

        // Suponiendo que tienes un método en tu DAO que obtiene todas las cintas
        List<Cinta> todasLasCintas = cintaDAO.obtenerTodasLasCintas();

        for (Cinta cinta : todasLasCintas) {
            if (cinta.getEstado().equalsIgnoreCase(estadoDisponible)) {
                cintasDisponibles.add(cinta);
            }
        }

        return cintasDisponibles;
    }
}
