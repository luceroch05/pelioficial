/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import peaches.pelioficial.model.Director;
import peaches.pelioficial.model.Genero;
import peaches.pelioficial.model.Pelicula;

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
    public Optional<Pelicula> get(long id) {
        Pelicula pelicula = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Peliculas LEFT JOIN Directores ON Peliculas.director_id = Directores.director_id WHERE pelicula_id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pelicula = new Pelicula();
                pelicula.setPeliculaId(resultSet.getInt("pelicula_id"));
                pelicula.setTitulo(resultSet.getString("titulo"));
                
                Director director = new Director();
                director.setDirectorId(resultSet.getInt("director_id"));
                director.setNombre(resultSet.getString("nombre"));
                pelicula.setDirector(director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(pelicula);
    }
    
    @Override
    public List<Pelicula> getAll() {
        List<Pelicula> peliculas = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Peliculas LEFT JOIN Directores ON Peliculas.director_id = Directores.director_id");
            while (resultSet.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setPeliculaId(resultSet.getInt("pelicula_id"));
                pelicula.setTitulo(resultSet.getString("titulo"));
                
                Director director = new Director();
                director.setDirectorId(resultSet.getInt("director_id"));
                director.setNombre(resultSet.getString("nombre"));
                pelicula.setDirector(director);
                
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }
    
    @Override
    public int save(Pelicula pelicula) {
        String sql = "INSERT INTO Peliculas (titulo, director_id) VALUES (?, ?)";
        int generatedId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pelicula.getTitulo());
            if(pelicula.getDirector() != null && pelicula.getDirector().getDirectorId() > 0){
                statement.setInt(2, pelicula.getDirector().getDirectorId());
            }else{
                statement.setNull(2, Types.INTEGER);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void update(Pelicula pelicula, String[] params){
        String sql = "UPDATE Peliculas SET titulo = ?, director_id = ? WHERE pelicula_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, pelicula.getTitulo());
            if(pelicula.getDirector() != null && pelicula.getDirector().getDirectorId() > 0){
                statement.setInt(2, pelicula.getDirector().getDirectorId());
            }else{
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, pelicula.getPeliculaId());
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
    
    public void agregarGeneroAPelicula(int peliculaId, int generoId){
        String sql = "INSERT INTO pelicula_genero (pelicula_id, genero_id) VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, peliculaId);
            statement.setInt(2, generoId);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Genero> obtenerGenerosPorPelicula(int peliculaId){
        List<Genero> generos = new ArrayList<>();
        String sql = "SELECT g.genero_id, g.nombre FROM generos g " +
                     "JOIN pelicula_genero pg ON g.genero_id = pg.genero_id " +
                     "WHERE pg.pelicula_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, peliculaId);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    Genero genero = new Genero();
                    genero.setGeneroId(resultSet.getInt("genero_id"));
                    genero.setNombre(resultSet.getString("nombre"));
                    generos.add(genero);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return generos;
    }
    
    public void removerGeneroDePelicula(int peliculaId, int generoId){
        String sql = "DELETE FROM pelicula_genero WHERE pelicula_id = ? AND genero_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, peliculaId);
            statement.setInt(2, generoId);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Genero> obtenerTodosLosGeneros(){
        List<Genero> generos = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM generos");
            while(resultSet.next()){
                Genero genero = new Genero();
                genero.setGeneroId(resultSet.getInt("genero_id"));
                genero.setNombre(resultSet.getString("nombre"));
                generos.add(genero);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return generos;
    }
}
