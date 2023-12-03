/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.sql.Connection;
import java.util.List;
import peaches.pelioficial.dao.DirectorDAO;
import peaches.pelioficial.model.Director;

/**
 *
 * @author q-ql
 */
public class DirectorService {
    private DirectorDAO directorDAO;
    
    public DirectorService(Connection connection){
        this.directorDAO = new DirectorDAO(connection);
    }
    
    public List<Director> obtenerTodosLosDirectores(){
        return directorDAO.getAll();
    }
    
    public void agregarDirector(String nombre){
        Director director = new Director();
        director.setNombre(nombre);
        directorDAO.save(director);
    }
    
    public void editarDirector(int id, String nuevoNombre){
        Director director = directorDAO.get(id).orElseThrow(() -> new IllegalArgumentException("Director not found."));
        director.setNombre(nuevoNombre);
        directorDAO.update(director, new String[]{nuevoNombre});
    }
    
    public void eliminarDirector(int id){
        Director director = directorDAO.get(id).orElseThrow(() -> new IllegalArgumentException("Director not found."));
        directorDAO.delete(director);
    }
}
