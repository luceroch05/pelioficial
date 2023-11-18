/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import peaches.pelioficial.dao.SocioDAO;
import peaches.pelioficial.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import peaches.pelioficial.model.Socio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author q-ql
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SocioDAOTest {
    
    private Connection connection;
    private SocioDAO socioDAO;
    private Socio testSocio;
    
    public SocioDAOTest() {
    }
    
    @BeforeAll
    public void setUpClass() {
        connection = DatabaseConnector.conectar();
        socioDAO = new SocioDAO(connection);
    }
    
    @AfterAll
    public void tearDownClass() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    @BeforeEach
    public void setUp() {
        // Inicializar tu objeto testSocio aquí y guardarlo en la base de datos
        testSocio = new Socio();
        testSocio.setNombre("Test Name");
        testSocio.setDireccion("Test Address");
        testSocio.setTelefono("555-1234");
        testSocio.setDirectoresFavoritos("Test Director");
        testSocio.setActoresFavoritos("Test Actor");
        testSocio.setGenerosPreferidos("Test Genre");
        int id = socioDAO.save(testSocio);
        testSocio.setSocioId(id);
    }
    
    @AfterEach
    public void tearDown() throws SQLException {
        if (testSocio != null && testSocio.getSocioId() != 0) {
            socioDAO.delete(testSocio);
            testSocio = null;
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSaveSocio() {
        Socio newSocio = new Socio();
        // Configura las propiedades de newSocio aquí
        newSocio.setNombre("New Name");
        // ...
        int newSocioId = socioDAO.save(newSocio);
        assertTrue(newSocioId > 0, "El socio no fue guardado correctamente.");
    }

    @Test
    public void testGetSocio() {
        assertNotNull(socioDAO.get(testSocio.getSocioId()), "El socio no fue encontrado.");
    }

    @Test
    public void testUpdateSocio() {
        testSocio.setNombre("Updated Name");
        socioDAO.update(testSocio, null);
        Socio updatedSocio = socioDAO.get(testSocio.getSocioId()).orElse(null);
        assertEquals("Updated Name", updatedSocio.getNombre(), "El socio no fue actualizado correctamente.");
    }

    @Test
    public void testDeleteSocio() {
        socioDAO.delete(testSocio);
        assertFalse(socioDAO.get(testSocio.getSocioId()).isPresent(), "El socio no fue eliminado correctamente.");
    }
}
