/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DAO.CintaDAO;
import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import modelo.Cinta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author q-ql
 */
public class CintaDAOTest {
    
    private static Connection connection;
    private CintaDAO cintaDAO;
    private Cinta testCinta;
    
    public CintaDAOTest() {
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
        cintaDAO = new CintaDAO(connection);
        testCinta = new Cinta();
        testCinta.setPeliculaId(1);
        testCinta.setEstado("Disponible");
        int id = cintaDAO.save(testCinta);
        testCinta.setCintaId(id); 
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        if (testCinta != null && testCinta.getCintaId() != 0) {
            cintaDAO.delete(testCinta);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSaveCinta() {
        Cinta newCinta = new Cinta(/* parámetros necesarios para crear una Cinta */);
        newCinta.setPeliculaId(1);
        newCinta.setEstado("Disponible");
        int newCintaId = cintaDAO.save(newCinta);
        assertTrue(newCintaId > 0, "La cinta no fue guardada correctamente.");
    }

    @Test
    public void testGetCinta() {
        Optional<Cinta> foundCintaOpt = cintaDAO.get(testCinta.getCintaId());
        assertTrue(foundCintaOpt.isPresent(), "La cinta no fue encontrada.");
        Cinta foundCinta = foundCintaOpt.get();
        assertNotNull(foundCinta, "La cinta no debe ser nula.");
    }

    @Test
    public void testUpdateCinta() {
        Optional<Cinta> cintaToUpdateOpt = cintaDAO.get(testCinta.getCintaId());
        assertTrue(cintaToUpdateOpt.isPresent(), "La cinta para actualizar no fue encontrada.");
        Cinta cintaToUpdate = cintaToUpdateOpt.get();
        cintaToUpdate.setEstado("Prestado");

        // Suponiendo que el segundo parámetro del método update es un arreglo de Strings con los nuevos valores.
        String[] updateParams = {String.valueOf(cintaToUpdate.getPeliculaId()), cintaToUpdate.getEstado()};
        cintaDAO.update(cintaToUpdate, updateParams);

        Optional<Cinta> updatedCintaOpt = cintaDAO.get(testCinta.getCintaId());
        assertTrue(updatedCintaOpt.isPresent(), "La cinta actualizada no fue encontrada.");
        Cinta updatedCinta = updatedCintaOpt.get();
        assertEquals("Prestado", updatedCinta.getEstado(), "La cinta no fue actualizada correctamente.");
    }


    @Test
    public void testDeleteCinta() {
        Cinta cintaToDelete = cintaDAO.get(testCinta.getCintaId()).orElse(null);
        assertNotNull(cintaToDelete, "La cinta para borrar no fue encontrada.");
        cintaDAO.delete(cintaToDelete);
        Cinta deletedCinta = cintaDAO.get(testCinta.getCintaId()).orElse(null);
        assertNull(deletedCinta, "La cinta no fue borrada correctamente.");
    }
}
