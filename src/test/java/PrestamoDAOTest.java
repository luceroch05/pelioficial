/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DAO.PrestamoDAO;
import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import modelo.Prestamo;
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
public class PrestamoDAOTest {
    
    private static Connection connection;
    private PrestamoDAO prestamoDAO;
    private Prestamo testPrestamo;
    
    public PrestamoDAOTest() {
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
        prestamoDAO = new PrestamoDAO(connection);
        testPrestamo = new Prestamo();
        testPrestamo.setSocioId(1); // 
        testPrestamo.setCintaId(1); //
        testPrestamo.setFechaPrestamo(LocalDate.now());
        // No establecemos fecha de devolución porque es un préstamo nuevo
        int id = prestamoDAO.save(testPrestamo);
        testPrestamo.setPrestamoId(id);
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        if (testPrestamo != null && testPrestamo.getPrestamoId() != 0) {
            prestamoDAO.delete(testPrestamo);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    void testSavePrestamo() {
        Prestamo newPrestamo = new Prestamo();
        newPrestamo.setSocioId(2); //
        newPrestamo.setCintaId(2); // 
        newPrestamo.setFechaPrestamo(LocalDate.now());
        int newPrestamoId = prestamoDAO.save(newPrestamo);
        assertTrue(newPrestamoId > 0, "El préstamo no fue guardado correctamente.");
    }

    @Test
    void testGetPrestamo() {
        Optional<Prestamo> foundPrestamo = prestamoDAO.get(testPrestamo.getPrestamoId());
        assertTrue(foundPrestamo.isPresent(), "El préstamo no fue encontrado.");
    }

    @Test
    void testUpdatePrestamo() {
        Optional<Prestamo> prestamoToUpdateOpt = prestamoDAO.get(testPrestamo.getPrestamoId());
        assertTrue(prestamoToUpdateOpt.isPresent(), "El préstamo para actualizar no fue encontrado.");
        Prestamo prestamoToUpdate = prestamoToUpdateOpt.get();
        prestamoToUpdate.setFechaDevolucion(LocalDate.now().plusDays(5)); // Establecer una fecha de devolución
        prestamoDAO.update(prestamoToUpdate, new String[]{
            String.valueOf(prestamoToUpdate.getSocioId()),
            String.valueOf(prestamoToUpdate.getCintaId()),
            prestamoToUpdate.getFechaPrestamo().toString(),
            prestamoToUpdate.getFechaDevolucion().toString()
        });
        Optional<Prestamo> updatedPrestamoOpt = prestamoDAO.get(testPrestamo.getPrestamoId());
        assertTrue(updatedPrestamoOpt.isPresent(), "El préstamo actualizado no fue encontrado.");
        Prestamo updatedPrestamo = updatedPrestamoOpt.get();
        assertEquals(LocalDate.now().plusDays(5), updatedPrestamo.getFechaDevolucion(), "La fecha de devolución del préstamo no fue actualizada correctamente.");
    }

    @Test
    void testDeletePrestamo() {
        prestamoDAO.delete(testPrestamo);
        Optional<Prestamo> deletedPrestamo = prestamoDAO.get(testPrestamo.getPrestamoId());
        assertFalse(deletedPrestamo.isPresent(), "El préstamo no fue borrado correctamente.");
    }
}
