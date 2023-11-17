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
import modelo.Actor;

/**
 *
 * @author q-ql
 */
public class ActorDAO implements Dao<Actor> {

    private Connection connection;

    public ActorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Actor> get(long id) {
        Actor actor = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Actores WHERE actor_id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = new Actor();
                actor.setActorId(resultSet.getInt("actor_id"));
                actor.setNombre(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(actor);
    }

    @Override
    public List<Actor> getAll() {
        List<Actor> actores = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Actores");
            while (resultSet.next()) {
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

    @Override
    public int save(Actor actor) {
        String sql = "INSERT INTO Actores (nombre) VALUES (?)";
        int generatedId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, actor.getNombre());
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
    public void update(Actor actor, String[] params) {
        String sql = "UPDATE Actores SET nombre = ? WHERE actor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, actor.getNombre());
            statement.setInt(2, actor.getActorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Actor actor) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Actores WHERE actor_id = ?")) {
            statement.setInt(1, actor.getActorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

