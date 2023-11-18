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
}
