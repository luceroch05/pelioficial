/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import peaches.pelioficial.dao.SocioDAO;
import peaches.pelioficial.model.Actor;
import peaches.pelioficial.model.Director;
import peaches.pelioficial.model.Genero;
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
    
    public SocioService(SocioDAO socioDAO){
        this.socioDAO = socioDAO;
    }
    
    public void agregarSocio(Socio socio){        
        socioDAO.save(socio);
    }
    
    public List<Socio> obtenerTodosLosSocios(){
        return socioDAO.getAll();
    }
    
    public void actualizarSocio(Socio socio){
        socioDAO.update(socio, null);
    }
    
    public void eliminarSocio(Socio socio){
        socioDAO.delete(socio);
    }
    
    public Socio obtenerSocioId(int id){
        Optional<Socio> socioOpt = socioDAO.get(id);
        return socioOpt.orElse(null);
    }
    
    public void agregarSocioConFavoritos(Socio socio, List<Director> directoresFavoritos, List<Actor> actoresFavoritos, List<Genero> generosFavoritos) {
        int idSocio = socioDAO.save(socio);
        socioDAO.guardarDirectoresFavoritos(idSocio, directoresFavoritos);
        socioDAO.guardarActoresFavoritos(idSocio, actoresFavoritos);
        socioDAO.guardarGenerosFavoritos(idSocio, generosFavoritos);
    }

    public List<Director> obtenerTodosLosDirectores(){
        return socioDAO.obtenerTodosLosDirectores();
    }
    
    public List<Actor> obtenerTodosLosActores(){
        return socioDAO.obtenerTodosLosActores();
    }
    
    public List<Genero> obtenerTodosLosGeneros(){
        return socioDAO.obtenerTodosLosGeneros();
    }
}
