/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.sql.Connection;
import java.util.List;
import peaches.pelioficial.dao.PeliculaDAO;
import peaches.pelioficial.model.Genero;

/**
 *
 * @author mtx0v
 */
public class PeliculaService {
    private PeliculaDAO peliculaDAO;
    
    public PeliculaService(Connection connection){
        this.peliculaDAO = new PeliculaDAO(connection);
    }
    
    public List<Genero> obtenerTodosLosGeneros(){
        return peliculaDAO.obtenerTodosLosGeneros();
    }
}
