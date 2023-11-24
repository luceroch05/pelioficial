/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import peaches.pelioficial.dao.SocioDAO;
import peaches.pelioficial.model.Socio;
import peaches.pelioficial.util.DatabaseConnector;

/**
 *
 * @author q-ql
 */
public class SocioService {
    private SocioDAO socioDAO;
    
    public SocioService(){
        this.socioDAO = new SocioDAO(DatabaseConnector.conectar());
    }
    
    public void registrarSocio(Socio socio){        
        socioDAO.save(socio);
    }
    
    public List<Socio> obtenerTodosLosSocios(){
        return socioDAO.getAll();
    }
    
    public void eliminarSocio(int idsocio){
        Optional<Socio> socio = socioDAO.get(idsocio);
        if(socio.isPresent()){
            socioDAO.delete(socio.get());
            JOptionPane.showMessageDialog(null, "Socio eliminado con exito.");
        }
    }
}
