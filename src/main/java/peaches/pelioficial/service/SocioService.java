/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

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
        //Logica para validar datos del socio
        
        socioDAO.save(socio);
    }
}
