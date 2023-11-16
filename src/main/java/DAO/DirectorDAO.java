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
import modelo.Director;

/**
 *
 * @author q-ql
 */
public class DirectorDAO implements Dao<Director> {

    private Connection connection;

    public DirectorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Director> get(long id) {
        Director director = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Directores WHERE director_id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = new Director();
                director.setDirectorId(resultSet.getInt("director_id"));
                director.setNombre(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(director);
    }

    @Override
    public List<Director> getAll() {
        List<Director> directores = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Directores");
            while (resultSet.next()) {
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

    @Override
    public void save(Director director) {
        String sql = "INSERT INTO Directores (nombre) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, director.getNombre());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Director director, String[] params) {
        String sql = "UPDATE Directores SET nombre = ? WHERE director_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, director.getNombre());
            statement.setInt(2, director.getDirectorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Director director) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Directores WHERE director_id = ?")) {
            statement.setInt(1, director.getDirectorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

