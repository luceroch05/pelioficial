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
import modelo.Socio;

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
                socio.setDirectoresFavoritos(resultSet.getString("directores_favoritos"));
                socio.setActoresFavoritos(resultSet.getString("actores_favoritos"));
                socio.setGenerosPreferidos(resultSet.getString("generos_preferidos"));
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
                socio.setDirectoresFavoritos(resultSet.getString("directores_favoritos"));
                socio.setActoresFavoritos(resultSet.getString("actores_favoritos"));
                socio.setGenerosPreferidos(resultSet.getString("generos_preferidos"));
                socios.add(socio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return socios;
    }
    
    @Override
    public int save(Socio socio) {
        String sql = "INSERT INTO Socios (nombre, direccion, telefono, directores_favoritos, actores_favoritos, generos_preferidos) VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getDireccion());
            statement.setString(3, socio.getTelefono());
            statement.setString(4, socio.getDirectoresFavoritos());
            statement.setString(5, socio.getActoresFavoritos());
            statement.setString(6, socio.getGenerosPreferidos());
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
    public void update(Socio socio, String[] params){
        String sql = "UPDATE Socios SET nombre = ?, direccion = ?, telefono = ?, directores_favoritos = ?, actores_favoritos = ?, generos_preferidos = ? WHERE socio_id = ?";
        try (PreparedStatement statement =  connection.prepareStatement(sql)){
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getDireccion());
            statement.setString(3, socio.getTelefono());
            statement.setString(4, socio.getDirectoresFavoritos());
            statement.setString(5, socio.getActoresFavoritos());
            statement.setString(6, socio.getGenerosPreferidos());
            statement.setInt(7, socio.getSocioId());            
        } catch (Exception e) {
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
}
