/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.util.List;
import javax.swing.table.DefaultTableModel;
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
        // Implementación para obtener el ID de la película por nombre
        // Esto es solo un esbozo, necesitas el código real que consulte tu base de datos
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
}
