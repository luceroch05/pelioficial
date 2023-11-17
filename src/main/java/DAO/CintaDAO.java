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
import modelo.Cinta;

/**
 *
 * @author q-ql
 */
public class CintaDAO implements Dao<Cinta>{
    private Connection connection;
    
    public CintaDAO(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public Optional<Cinta> get(long id){
        Cinta cinta = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Cintas WHERE cinta_id = ?")){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                cinta = new Cinta();
                cinta.setCintaId(resultSet.getInt("cinta_id"));
                cinta.setPeliculaId(resultSet.getInt("pelicula_id"));
                cinta.setEstado(resultSet.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(cinta);
    }
    
    @Override
    public List<Cinta> getAll(){
        List<Cinta> cintas = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Cintas");
            while(resultSet.next()){
                Cinta cinta = new Cinta();
                cinta.setCintaId(resultSet.getInt("cinta_id"));
                cinta.setPeliculaId(resultSet.getInt("pelicula_id"));
                cinta.setEstado(resultSet.getString("estado"));
                cintas.add(cinta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cintas;
    }
    
    @Override
    public int save(Cinta cinta) {
        String sql = "INSERT INTO Cintas (pelicula_id, estado) VALUES (?, ?)";
        int generatedId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, cinta.getPeliculaId());
            statement.setString(2, cinta.getEstado());
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
    public void update(Cinta cinta, String[] params){
        String sql = "UPDATE Cintas SET pelicula_id = ?, estado = ? WHERE cinta_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, cinta.getPeliculaId());
            statement.setString(2, cinta.getEstado());
            statement.setInt(3, cinta.getCintaId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(Cinta cinta){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Cintas WHERE cinta_id = ?")){
            statement.setInt(1, cinta.getCintaId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
