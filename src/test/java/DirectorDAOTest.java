/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DAO.DirectorDAO;
import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import modelo.Director;
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
public class DirectorDAOTest {
    
    private static Connection connection;
    private DirectorDAO directorDAO;
    private Director testDirector;
    
    public DirectorDAOTest() {
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
        directorDAO = new DirectorDAO(connection);
        testDirector = new Director();
        testDirector.setNombre("Director de Prueba");
        int id = directorDAO.save(testDirector);
        testDirector.setDirectorId(id);
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        if (testDirector != null && testDirector.getDirectorId() != 0) {
            directorDAO.delete(testDirector);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    void testSaveDirector() {
        Director newDirector = new Director();
        newDirector.setNombre("Director Nuevo");
        int newDirectorId = directorDAO.save(newDirector);
        assertTrue(newDirectorId > 0, "El director no fue guardado correctamente.");
    }

    @Test
    void testGetDirector() {
        Optional<Director> foundDirector = directorDAO.get(testDirector.getDirectorId());
        assertTrue(foundDirector.isPresent(), "El director no fue encontrado.");
    }

    @Test
    void testUpdateDirector() {
        Optional<Director> directorToUpdateOpt = directorDAO.get(testDirector.getDirectorId());
        assertTrue(directorToUpdateOpt.isPresent(), "El director para actualizar no fue encontrado.");
        Director directorToUpdate = directorToUpdateOpt.get();
        directorToUpdate.setNombre("Director Actualizado");
        directorDAO.update(directorToUpdate, new String[]{directorToUpdate.getNombre()});
        Optional<Director> updatedDirectorOpt = directorDAO.get(testDirector.getDirectorId());
        assertTrue(updatedDirectorOpt.isPresent(), "El director actualizado no fue encontrado.");
        Director updatedDirector = updatedDirectorOpt.get();
        assertEquals("Director Actualizado", updatedDirector.getNombre(), "El nombre del director no fue actualizado correctamente.");
    }

    @Test
    void testDeleteDirector() {
        directorDAO.delete(testDirector);
        Optional<Director> deletedDirector = directorDAO.get(testDirector.getDirectorId());
        assertFalse(deletedDirector.isPresent(), "El director no fue borrado correctamente.");
    }
}
