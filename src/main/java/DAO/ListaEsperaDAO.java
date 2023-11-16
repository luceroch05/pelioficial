/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.ListaDeEspera;

/**
 *
 * @author q-ql
 */
public class ListaEsperaDAO implements Dao<ListaDeEspera>{
    private Connection connection;
    
    public ListaEsperaDAO(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public Optional<ListaDeEspera> get(long id){
        ListaDeEspera listaDeEspera = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ListaEspera WHERE lista_espera_id = ?")){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                listaDeEspera = new ListaDeEspera();
                listaDeEspera.setListaEsperaId(resultSet.getInt("lista_espera_id"));
                listaDeEspera.setPeliculaId(resultSet.getInt("pelicula_id"));
                listaDeEspera.setSocioId(resultSet.getInt("socio_id"));
                listaDeEspera.setFechaSolicitud(resultSet.getDate("fecha_solicitud").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(listaDeEspera);
    }
    
    @Override
    public List<ListaDeEspera> getAll(){
        List<ListaDeEspera> listaDeEsperas = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ListaEspera");
            while(resultSet.next()){
                ListaDeEspera listaDeEspera = new ListaDeEspera();
                listaDeEspera.setListaEsperaId(resultSet.getInt("lista_espera_id"));
                listaDeEspera.setPeliculaId(resultSet.getInt("pelicula_id"));
                listaDeEspera.setSocioId(resultSet.getInt("socio_id"));
                listaDeEspera.setFechaSolicitud(resultSet.getDate("fecha_solicitud").toLocalDate());
                listaDeEsperas.add(listaDeEspera);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeEsperas;
    }
    
    @Override
    public void save(ListaDeEspera listaDeEspera){
        String sql = "INSERT INTO ListaEspera (pelicula_id, socio_id, fecha_solicitud) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, listaDeEspera.getPeliculaId());
            statement.setInt(2, listaDeEspera.getSocioId());
            statement.setDate(3, Date.valueOf(listaDeEspera.getFechaSolicitud()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(ListaDeEspera listaDeEspera, String[] params) {
        String sql = "UPDATE ListaEspera SET pelicula_id = ?, socio_id = ?, fecha_solicitud = ? WHERE lista_espera_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, listaDeEspera.getPeliculaId());
            statement.setInt(2, listaDeEspera.getSocioId());
            statement.setDate(3, Date.valueOf(listaDeEspera.getFechaSolicitud()));
            statement.setInt(4, listaDeEspera.getListaEsperaId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(ListaDeEspera listaDeEspera) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM ListaEspera WHERE lista_espera_id = ?")) {
            statement.setInt(1, listaDeEspera.getListaEsperaId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
