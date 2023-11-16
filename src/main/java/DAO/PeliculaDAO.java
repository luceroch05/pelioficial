/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.Pelicula;

/**
 *
 * @author q-ql
 */
public class PeliculaDAO implements Dao<Pelicula>{
    private Connection connection;
    
    public PeliculaDAO(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public Optional<Pelicula> get(long id){
        Pelicula pelicula = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Peliculas WHERE pelicula_id = ?")){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                pelicula = new Pelicula();
                pelicula.setPeliculaId(resultSet.getInt("pelicula_id"));
                pelicula.setTitulo(resultSet.getString("titulo"));
                pelicula.setGenero(resultSet.getString("genero"));
                pelicula.setDirectorId(resultSet.getInt("director_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  Optional.ofNullable(pelicula);
    }
    
    @Override
    public List<Pelicula> getAll(){
        List<Pelicula> peliculas = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Peliculas");
            while(resultSet.next()){
                Pelicula pelicula = new Pelicula();
                pelicula.setPeliculaId(resultSet.getInt("pelicula_id"));
                pelicula.setTitulo(resultSet.getString("titulo"));
                pelicula.setGenero(resultSet.getString("genero"));
                pelicula.setDirectorId(resultSet.getInt("director_id"));
                //
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }
    
    @Override
    public void save(Pelicula pelicula){
        String sql = "INSERT INTO Peliculas (titulo, genero, director_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, pelicula.getTitulo());
            statement.setString(2, pelicula.getGenero());
            statement.setInt(3, pelicula.getDirectorId());
            //
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Pelicula pelicula, String[] params){
        String sql = "UPDATE Peliculas SET titulo = ?, genero = ?, director_id = ? WHERE pelicula_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, pelicula.getTitulo());
            statement.setString(2, pelicula.getGenero());
            statement.setInt(3, pelicula.getDirectorId());
            statement.setInt(4, pelicula.getPeliculaId());
            //
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(Pelicula pelicula){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Peliculas WHERE pelicula_id = ?")){
            statement.setInt(1, pelicula.getPeliculaId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
