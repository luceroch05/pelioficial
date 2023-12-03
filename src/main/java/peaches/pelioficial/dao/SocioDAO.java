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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import peaches.pelioficial.model.Actor;
import peaches.pelioficial.model.Director;
import peaches.pelioficial.model.Genero;
import peaches.pelioficial.model.Socio;

/**
 *
 * @author q-ql
 */
public class SocioDAO implements Dao<Socio>{
    private Connection connection;
    
    public SocioDAO(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public Optional<Socio> get(long id){
        Socio socio = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Socios WHERE socio_id = ?")){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                socio = new Socio();
                socio.setSocioId(resultSet.getInt("socio_id"));
                socio.setNombre(resultSet.getString("nombre"));
                socio.setDireccion(resultSet.getString("direccion"));
                socio.setTelefono(resultSet.getString("telefono"));
                socio.setDirectoresFavoritos(getDirectoresFavoritos(socio.getSocioId()));
                socio.setActoresFavoritos(getActoresFavoritos(socio.getSocioId()));
                socio.setGenerosPreferidos(getGenerosPreferidos(socio.getSocioId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(socio);
    }
    
    @Override
    public List<Socio> getAll(){
        List<Socio> socios = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Socios");
            while(resultSet.next()){
                Socio socio = new Socio();
                socio.setSocioId(resultSet.getInt("socio_id"));
                socio.setNombre(resultSet.getString("nombre"));
                socio.setDireccion(resultSet.getString("direccion"));
                socio.setTelefono(resultSet.getString("telefono"));
                socio.setDirectoresFavoritos(getDirectoresFavoritos(socio.getSocioId()));
                socio.setActoresFavoritos(getActoresFavoritos(socio.getSocioId()));
                socio.setGenerosPreferidos(getGenerosPreferidos(socio.getSocioId()));
                socios.add(socio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return socios;
    }
    
    @Override
    public int save(Socio socio) {
        String sql = "INSERT INTO Socios (nombre, direccion, telefono) VALUES (?, ?, ?)";
        int generatedId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getDireccion());
            statement.setString(3, socio.getTelefono());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        socio.setSocioId(generatedId);
                    }
                }
            }
            insertSocioDirectoresFavoritos(socio);
            insertSocioActoresFavoritos(socio);
            insertSocioGenerosFavoritos(socio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void update(Socio socio, String[] params) {
        String sql = "UPDATE Socios SET nombre = ?, direccion = ?, telefono = ? WHERE socio_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getDireccion());
            statement.setString(3, socio.getTelefono());
            statement.setInt(4, socio.getSocioId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating socio failed, no rows affected.");
            }
            updateSocioDirectoresFavoritos(socio);
            updateSocioActoresFavoritos(socio);
            updateSocioGenerosFavoritos(socio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Socio socio){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Socios WHERE socio_id = ?")){
            statement.setInt(1, socio.getSocioId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para obtener los directores favoritos de un socio
    private List<Director> getDirectoresFavoritos(int socioId) {
        List<Director> directores = new ArrayList<>();
        String sql = "SELECT d.* FROM directores d " +
                     "JOIN socio_director sd ON d.director_id = sd.director_id " +
                     "WHERE sd.socio_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, socioId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Director director = new Director();
                director.setDirectorId(resultSet.getInt("director_id"));
                director.setNombre(resultSet.getString("nombre"));
                directores.add(director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directores;
    }
    
    // Método para obtener los actores favoritos de un socio
    private List<Actor> getActoresFavoritos(int socioId) {
        List<Actor> actores = new ArrayList<>();
        String sql = "SELECT a.* FROM actores a " +
                     "JOIN socio_actor sa ON a.actor_id = sa.actor_id " +
                     "WHERE sa.socio_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, socioId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Actor actor = new Actor();
                actor.setActorId(resultSet.getInt("actor_id"));
                actor.setNombre(resultSet.getString("nombre"));
                actores.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actores;
    }
    
    // Método para obtener los géneros preferidos de un socio
    private List<Genero> getGenerosPreferidos(int socioId) {
        List<Genero> generos = new ArrayList<>();
        String sql = "SELECT g.* FROM generos g " +
                     "JOIN socio_genero sg ON g.genero_id = sg.genero_id " +
                     "WHERE sg.socio_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, socioId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Genero genero = new Genero();
                genero.setGeneroId(resultSet.getInt("genero_id"));
                genero.setNombre(resultSet.getString("nombre"));
                generos.add(genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generos;
    }
    
    private void insertSocioDirectoresFavoritos(Socio socio) throws SQLException{
        String sql = "INSERT INTO socio_director (socio_id, director_id) VALUES (?, ?)";
        for(Director director : socio.getDirectoresFavoritos()){
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, socio.getSocioId());
                statement.setInt(2, director.getDirectorId());
                statement.executeUpdate();
            }
        }
    }
    
    private void insertSocioActoresFavoritos(Socio socio) throws SQLException{
        String sql = "INSERT INTO socio_actor (socio_id, actor_id) VALUES (?, ?)";
        for(Actor actor : socio.getActoresFavoritos()){
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, socio.getSocioId());
                statement.setInt(2, actor.getActorId());
                statement.executeUpdate();
            }
        }
    }
    
    private void insertSocioGenerosFavoritos(Socio socio) throws SQLException{
        String sql = "INSERT INTO socio_genero (socio_id, genero_id) VALUES (?, ?)";
        for(Genero genero : socio.getGenerosPreferidos()){
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, socio.getSocioId());
                statement.setInt(2, genero.getGeneroId());
                statement.executeUpdate();
            }
        }
    }
    
    private void updateSocioDirectoresFavoritos(Socio socio) throws SQLException{
        String sql = "DELETE FROM socio_director WHERE socio_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, socio.getSocioId());
            statement.executeUpdate();
        }
        insertSocioDirectoresFavoritos(socio);
    }
    
    private void updateSocioActoresFavoritos(Socio socio) throws SQLException{
        String sql = "DELETE FROM socio_actor WHERE socio_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, socio.getSocioId());
            statement.executeUpdate();
        }
        insertSocioActoresFavoritos(socio);
    }
    
    private void updateSocioGenerosFavoritos(Socio socio) throws SQLException{
        String sql = "DELETE FROM socio_genero WHERE socio_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, socio.getSocioId());
            statement.executeUpdate();
        }
        insertSocioGenerosFavoritos(socio);
    }
}
