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
            ResultSet resultSet = statement.executeQuery(
                "SELECT p.prestamo_id, s.nombre AS nombre_socio, pe.titulo AS titulo_pelicula, p.fecha_prestamo, p.fecha_devolucion, c.estado " + 
                "FROM prestamos p " +
                "JOIN socios s ON p.socio_id = s.socio_id " +
                "JOIN cintas c ON p.cinta_id = c.cinta_id " +
                "JOIN peliculas pe ON c.pelicula_id = pe.pelicula_id"
            );
            while(resultSet.next()){
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));
                prestamo.setFechaPrestamo(resultSet.getDate("fecha_prestamo").toLocalDate());
                prestamo.setFechaDevolucion(resultSet.getDate("fecha_devolucion") != null ? resultSet.getDate("fecha_devolucion").toLocalDate() : null);
                prestamo.setNombreSocio(resultSet.getString("nombre_socio"));
                prestamo.setTituloPelicula(resultSet.getString("titulo_pelicula"));
                prestamo.setEstadoCinta(resultSet.getString("estado"));
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
        String sql = "SELECT p.prestamo_id, s.socio_id, pe.pelicula_id, s.nombre AS nombre_socio, pe.titulo AS titulo_pelicula, p.fecha_prestamo, p.fecha_devolucion, ci.estado " +
                     "FROM prestamos p " +
                     "INNER JOIN socios s ON p.socio_id = s.socio_id " +
                     "INNER JOIN cintas ci ON p.cinta_id = ci.cinta_id " +
                     "INNER JOIN peliculas pe ON ci.pelicula_id = pe.pelicula_id " +
                     "WHERE s.nombre LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nombreSocio + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));
                prestamo.setSocioId(resultSet.getInt("socio_id"));
                prestamo.setCintaId(resultSet.getInt("pelicula_id"));  // Asegúrate de que es el ID correcto para cintaId
                prestamo.setNombreSocio(resultSet.getString("nombre_socio"));
                prestamo.setTituloPelicula(resultSet.getString("titulo_pelicula"));
                prestamo.setEstadoCinta(resultSet.getString("estado"));
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
                prestamo.setNombreSocio(resultSet.getString("nombre_socio"));
                prestamo.setTituloPelicula(resultSet.getString("titulo_pelicula"));
                prestamo.setEstadoCinta(resultSet.getString("estado"));
                Date fechaDevolucion = resultSet.getDate("fecha_devolucion");
                prestamo.setFechaDevolucion(fechaDevolucion != null ? fechaDevolucion.toLocalDate() : null);
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamos;
    }


    public boolean insertarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (socio_id, cinta_id, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, prestamo.getSocioId());
            statement.setInt(2, prestamo.getCintaId());
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
    
    public boolean actualizarPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET socio_id = ?, cinta_id = ?, fecha_prestamo = ?, fecha_devolucion = ? WHERE prestamo_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, prestamo.getSocioId());
            statement.setInt(2, prestamo.getCintaId());
            statement.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            if (prestamo.getFechaDevolucion() != null) {
                statement.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.setInt(5, prestamo.getPrestamoId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Prestamo obtenerPorId(int id) {
        // Este método busca en la base de datos el préstamo por su ID y devuelve el objeto Prestamo.
        // A continuación, un ejemplo genérico:

        String sql = "SELECT * FROM prestamos WHERE prestamo_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamoId(resultSet.getInt("prestamo_id"));
                prestamo.setSocioId(resultSet.getInt("socio_id"));
                prestamo.setCintaId(resultSet.getInt("cinta_id"));
                prestamo.setFechaPrestamo(resultSet.getDate("fecha_prestamo").toLocalDate());
                Date fechaDevolucion = resultSet.getDate("fecha_devolucion");
                prestamo.setFechaDevolucion(fechaDevolucion != null ? fechaDevolucion.toLocalDate() : null);
                // Completa con el resto de campos si es necesario.
                return prestamo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean eliminarPrestamo(int prestamoId) {
        String sql = "DELETE FROM prestamos WHERE prestamo_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Establecer los parámetros del comando DELETE SQL
            pstmt.setInt(1, prestamoId);

            // Ejecutar la sentencia DELETE SQL
            int affectedRows = pstmt.executeUpdate();

            // Si se eliminó exactamente un registro, entonces el resultado es true
            return affectedRows == 1;
        } catch (SQLException e) {
            // Log and handle exceptions
            e.printStackTrace();
        }
        return false; // Si llegamos aquí, algo falló
    }
}
