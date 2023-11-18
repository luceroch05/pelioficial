/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import peaches.pelioficial.dao.PeliculaDAO;
import peaches.pelioficial.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import peaches.pelioficial.model.Pelicula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author q-ql
 */
public class PeliculaDAOTest {
    
    private static Connection connection;
    private PeliculaDAO peliculaDAO;
    private Pelicula testPelicula;
    
    public PeliculaDAOTest() {
    }
    
    @BeforeAll
    static void setUpClass() {
        connection = DatabaseConnector.conectar();
    }
    
    @AfterAll
    static void tearDownClass() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    @BeforeEach
    void setUp() {
        peliculaDAO = new PeliculaDAO(connection);
        testPelicula = new Pelicula();
        testPelicula.setTitulo("Prueba");
        testPelicula.setGenero("Comedia");
        testPelicula.setDirectorId(1); // 
        testPelicula.setActores(Collections.singletonList(1)); // 
        int id = peliculaDAO.save(testPelicula);
        testPelicula.setPeliculaId(id);
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        if (testPelicula != null && testPelicula.getPeliculaId() != 0) {
            peliculaDAO.delete(testPelicula);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    void testSavePelicula() {
        Pelicula newPelicula = new Pelicula();
        newPelicula.setTitulo("Nueva");
        newPelicula.setGenero("Drama");
        newPelicula.setDirectorId(2); // 
        newPelicula.setActores(Collections.singletonList(2)); // 
        int newPeliculaId = peliculaDAO.save(newPelicula);
        assertTrue(newPeliculaId > 0, "La película no fue guardada correctamente.");
    }

    @Test
    void testGetPelicula() {
        Optional<Pelicula> foundPelicula = peliculaDAO.get(testPelicula.getPeliculaId());
        assertTrue(foundPelicula.isPresent(), "La película no fue encontrada.");
    }

    @Test
    void testUpdatePelicula() {
        Optional<Pelicula> peliculaToUpdateOpt = peliculaDAO.get(testPelicula.getPeliculaId());
        assertTrue(peliculaToUpdateOpt.isPresent(), "La película para actualizar no fue encontrada.");
        Pelicula peliculaToUpdate = peliculaToUpdateOpt.get();
        peliculaToUpdate.setTitulo("Actualizada");
        peliculaDAO.update(peliculaToUpdate, new String[]{peliculaToUpdate.getTitulo(), peliculaToUpdate.getGenero(), String.valueOf(peliculaToUpdate.getDirectorId())});
        Optional<Pelicula> updatedPeliculaOpt = peliculaDAO.get(testPelicula.getPeliculaId());
        assertTrue(updatedPeliculaOpt.isPresent(), "La película actualizada no fue encontrada.");
        Pelicula updatedPelicula = updatedPeliculaOpt.get();
        assertEquals("Actualizada", updatedPelicula.getTitulo(), "El título de la película no fue actualizado correctamente.");
    }

    @Test
    void testDeletePelicula() {
        peliculaDAO.delete(testPelicula);
        Optional<Pelicula> deletedPelicula = peliculaDAO.get(testPelicula.getPeliculaId());
        assertFalse(deletedPelicula.isPresent(), "La película no fue borrada correctamente.");
    }
}
