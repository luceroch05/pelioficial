/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import peaches.pelioficial.model.Cinta;
import peaches.pelioficial.model.Prestamo;
import peaches.pelioficial.model.Socio;

/**
 *
 * @author q-ql
 */
public class PrestamoDAO implements Dao<Prestamo>{
    private Connection connection;
    
    public PrestamoDAO(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public Optional<Prestamo> get(long id){
        Prestamo prestamo = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Prestamos WHERE prestamo_id = ?")){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));
//                prestamo.setSocioId(resultSet.getInt("socio_id"));
//                prestamo.setCintaId(resultSet.getInt("cinta_id"));
                prestamo.setFechaPrestamo(resultSet.getDate("fecha_prestamo").toLocalDate());
                if(resultSet.getDate("fecha_devolucion") != null){
                    prestamo.setFechaDevolucion(resultSet.getDate("fecha_devolucion").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(prestamo);
    }
    
    @Override
    public List<Prestamo> getAll(){
        List<Prestamo> prestamos = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Prestamos");
            while(resultSet.next()){
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));
//                prestamo.setSocioId(resultSet.getInt("socio_id"));
//                prestamo.setCintaId(resultSet.getInt("cinta_id"));
                prestamo.setFechaPrestamo(resultSet.getDate("fecha_prestamo").toLocalDate());
                if(resultSet.getDate("fecha_devolucion") != null){
                    prestamo.setFechaDevolucion(resultSet.getDate("fecha_devolucion").toLocalDate());
                }
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    
    @Override
    public int save(Prestamo prestamo) {
        String sql = "INSERT INTO Prestamos (socio_id, cinta_id, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        int generatedId = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setInt(1, prestamo.getSocioId()); 
//            statement.setInt(2, prestamo.getCintaId()); 
            statement.setDate(3, Date.valueOf(prestamo.getFechaPrestamo())); 
            if (prestamo.getFechaDevolucion() != null) {
                statement.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                statement.setNull(4, Types.DATE);
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
    public void update(Prestamo prestamo, String[] params){
        String sql = "UPDATE Prestamos SET socio_id = ?, cinta_id = ?, fecha_prestamo = ?, fecha_devolucion = ? WHERE prestamo_id = ?";
        try (PreparedStatement statement =  connection.prepareStatement(sql)){
//            statement.setInt(1, prestamo.getSocioId());
//            statement.setInt(2, prestamo.getCintaId());
            statement.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            if(prestamo.getFechaDevolucion() != null){
                statement.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            }else{
                statement.setNull(4, Types.DATE);
            }
            statement.setInt(5, prestamo.getPrestamoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void delete(Prestamo prestamo){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Prestamos WHERE prestamo_id = ?")){
            statement.setInt(1, prestamo.getPrestamoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Prestamo> buscarPrestamosPorNombreSocio(String nombreSocio) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, s.nombre AS nombreSocio, c.titulo AS tituloPelicula " +
                     "FROM prestamos p " +
                     "INNER JOIN socios s ON p.socio_id = s.socio_id " +
                     "INNER JOIN cintas ci ON p.cinta_id = ci.cinta_id " +
                     "INNER JOIN peliculas c ON ci.pelicula_id = c.pelicula_id " +
                     "WHERE s.nombre LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nombreSocio + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));

                Socio socio = new Socio();
                socio.setSocioId(resultSet.getInt("socio_id"));
                socio.setNombre(resultSet.getString("nombreSocio")); // Asegúrate de que tu objeto Socio tenga un atributo nombre y su setter

                Cinta cinta = new Cinta();
                cinta.setCintaId(resultSet.getInt("cinta_id"));
                cinta.setTituloPelicula(resultSet.getString("tituloPelicula")); // Asegúrate de que tu objeto Cinta tenga un atributo titulo y su setter

                prestamo.setSocio(socio);
                prestamo.setCinta(cinta);

                prestamo.setFechaPrestamo(resultSet.getDate("fecha_prestamo").toLocalDate());
                if (resultSet.getDate("fecha_devolucion") != null) {
                    prestamo.setFechaDevolucion(resultSet.getDate("fecha_devolucion").toLocalDate());
                }

                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public List<Prestamo> obtenerTodosLosPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.prestamo_id, s.nombre AS nombre_socio, pe.titulo AS titulo_pelicula, p.fecha_prestamo, p.fecha_devolucion, c.estado " + 
                     "FROM prestamos p " +
                     "JOIN socios s ON p.socio_id = s.socio_id " +
                     "JOIN cintas c ON p.cinta_id = c.cinta_id " +
                     "JOIN peliculas pe ON c.pelicula_id = pe.pelicula_id";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));
                prestamo.setFechaPrestamo(resultSet.getDate("fecha_prestamo").toLocalDate());
                Date fechaDevolucion = resultSet.getDate("fecha_devolucion");
                prestamo.setFechaDevolucion(fechaDevolucion != null ? fechaDevolucion.toLocalDate() : null);
                prestamo.setNombreSocio(resultSet.getString("nombre_socio"));
                prestamo.setTituloPelicula(resultSet.getString("titulo_pelicula"));
                prestamo.setEstadoCinta(resultSet.getString("estado"));
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            // Log and handle exceptions
            e.printStackTrace();
        }

        return prestamos;
    }

    public boolean insertarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (socio_id, cinta_id, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, prestamo.getSocio().getSocioId()); // Nota que aquí usamos getSocio().getSocioId()
            statement.setInt(2, prestamo.getCinta().getCintaId()); // y getCinta().getCintaId()
            statement.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            if (prestamo.getFechaDevolucion() != null) {
                statement.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
