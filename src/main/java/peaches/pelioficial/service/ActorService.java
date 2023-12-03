/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.sql.Connection;
import java.util.List;
import peaches.pelioficial.dao.ActorDAO;
import peaches.pelioficial.model.Actor;

/**
 *
 * @author q-ql
 */
public class ActorService {
    private ActorDAO actorDAO;
    
    public ActorService(Connection connection){
        this.actorDAO = new ActorDAO(connection);
    }
    
    public List<Actor> obtenerTodosLosActores(){
        return actorDAO.getAll();
    }
    
    public void agregarActor(String nombre){
        Actor actor = new Actor();
        actor.setNombre(nombre);
        actorDAO.save(actor);
    }
    
    public void editarActor(int id, String nuevoNombre){
        Actor actor = actorDAO.get(id).orElseThrow(() -> new IllegalArgumentException("Actor not found."));
        actor.setNombre(nuevoNombre);
        actorDAO.update(actor, new String[]{nuevoNombre});
    }
    
    public void eliminarActor(int id){
        Actor actor = actorDAO.get(id).orElseThrow(() -> new IllegalArgumentException("Actor not found."));
        actorDAO.delete(actor);
    }
}
