/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import peaches.pelioficial.dao.PeliculaDAO;
import peaches.pelioficial.model.Genero;
import peaches.pelioficial.model.Pelicula;

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
    
    
    
    public int agregarPeliculaConGeneros(Pelicula pelicula, List<Genero> generosSeleccionados){
        int peliculaId = peliculaDAO.save(pelicula);
        
        if(peliculaId > 0){
            for(Genero genero : generosSeleccionados){
                peliculaDAO.agregarGeneroAPelicula(peliculaId, genero.getGeneroId());
            }
        }
        return peliculaId;
    }
    
    public List<Genero> obtenerGenerosPorPelicula(int peliculaId){
        return peliculaDAO.obtenerGenerosPorPelicula(peliculaId);
    }
    
    public void actualizarGenerosDePelicula(Pelicula pelicula, List<Genero> generosSeleccionados){
        List<Genero> generosActuales = obtenerGenerosPorPelicula(pelicula.getPeliculaId());
        for(Genero genero : generosActuales){
            peliculaDAO.removerGeneroDePelicula(pelicula.getPeliculaId(), genero.getGeneroId());
        }
        
        for(Genero genero : generosSeleccionados){
            peliculaDAO.agregarGeneroAPelicula(pelicula.getPeliculaId(), genero.getGeneroId());
        }
    }
    
    public List<Pelicula> obtenerTodasLasPeliculas(){
        return peliculaDAO.getAll();
    }
    
    public int agregarPelicula(Pelicula pelicula){
        return peliculaDAO.save(pelicula);
    }
    
    public void agregarGeneroAPelicula(Pelicula pelicula, Genero genero){
        peliculaDAO.agregarGeneroAPelicula(pelicula.getPeliculaId(), genero.getGeneroId());
    }
    
    public void eliminarPelicula(int peliculaId){
        peliculaDAO.removerGenerosDePelicula(peliculaId);
        
        Pelicula pelicula = new Pelicula();
        pelicula.setPeliculaId(peliculaId);
        peliculaDAO.delete(pelicula);
    }
    
    public boolean editarPeliculaConGeneros(Pelicula pelicula, List<Genero> generosSeleccionados){
        peliculaDAO.update(pelicula, null);
        
        peliculaDAO.removerGenerosDePelicula(pelicula.getPeliculaId());
        for(Genero genero : generosSeleccionados){
            peliculaDAO.agregarGeneroAPelicula(pelicula.getPeliculaId(), genero.getGeneroId());
        }
        
        return true;
    }
    
    public Pelicula obtenerPeliculaPorId(int peliculaId) {
        if (peliculaId == 0) {
            // ID no válido o no seleccionado aún, devuelve null o maneja de acuerdo a tu lógica de negocio
            return null;
        }
        Optional<Pelicula> pelicula = peliculaDAO.get(peliculaId);
        return pelicula.orElse(null); // Devuelve null si no se encuentra la película
    }
    
    public List<Pelicula> buscarPeliculasPorTitulo(String titulo){
        List<Pelicula> peliculas = realizarBusquedaDePeliculas(titulo);
        
        if(peliculas.isEmpty()){
            return peliculas;
        }
        
        List<Integer> peliculaIds = peliculas.stream()
                                            .map(Pelicula::getPeliculaId)
                                            .collect(Collectors.toList());
        
        Map<Integer, List<Genero>> generosPorPelicula = peliculaDAO.obtenerGenerosDePeliculas(peliculaIds);
        
        for(Pelicula pelicula : peliculas){
            List<Genero> generos = generosPorPelicula.getOrDefault(pelicula.getPeliculaId(), Collections.emptyList());
            pelicula.setGeneros(generos);
        }
        return peliculas;
    }
    
    private List<Pelicula> realizarBusquedaDePeliculas(String titulo){
        return peliculaDAO.buscarPorTitulo(titulo);
    }
    
    private Map<Integer, List<Genero>> obtenerGenerosDePeliculas(List<Integer> peliculaIds){
        return peliculaDAO.obtenerGenerosDePeliculas(peliculaIds);
    }
    
}
