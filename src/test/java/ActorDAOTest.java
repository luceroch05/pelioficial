import peaches.pelioficial.dao.ActorDAO;
import peaches.pelioficial.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import peaches.pelioficial.model.Actor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActorDAOTest {
    private static Connection connection;
    private ActorDAO actorDAO;
    private int testActorId;
    private Actor testActor;

    @BeforeAll
    public static void setUpClass() {
        connection = DatabaseConnector.conectar();
    }

    @BeforeEach
    public void setUp() {
        actorDAO = new ActorDAO(connection);
        testActor = new Actor();
        testActor.setNombre("Actor de Prueba");
        testActorId = actorDAO.save(testActor); // Guardar el ID generado
        System.out.println("Inserted actor with ID: " + testActorId);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (testActorId > 0) {
            System.out.println("Deleting actor with ID: " + testActorId);
            actorDAO.delete(testActor); // Usa el objeto testActor directamente
            testActorId = 0;
        }
    }

    @AfterAll
    public static void tearDownClass() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testSaveActor() {
        Actor newActor = new Actor();
        newActor.setNombre("Nombre de Prueba");
        actorDAO.save(newActor);

        // Verificar que el actor ha sido guardado
        List<Actor> actores = actorDAO.getAll();
        boolean actorEncontrado = actores.stream().anyMatch(actor -> "Nombre de Prueba".equals(actor.getNombre()));
        assertTrue(actorEncontrado, "El actor no fue guardado correctamente");
    }

    @Test
    public void testGetActor() {
        long actorId = 2;
        Optional<Actor> result = actorDAO.get(actorId);
        assertTrue(result.isPresent(), "El actor no fue encontrado");
    }

    @Test
    public void testGetAllActors() {
        List<Actor> result = actorDAO.getAll();
        assertNotNull(result, "La lista de actores no debe ser nula");
        assertFalse(result.isEmpty(), "La lista de actores no debe estar vacía");
    }

    @Test
    public void testUpdateActor() {
        // Asegúrate de que haya un actor para actualizar
        Actor actorToUpdate = new Actor();
        actorToUpdate.setActorId(1); // 
        actorToUpdate.setNombre("Nombre Actualizado");
        actorDAO.update(actorToUpdate, new String[]{"Nombre Actualizado"});

        // Verificar que la actualización fue exitosa
        Optional<Actor> updatedActor = actorDAO.get(1);
        assertTrue(updatedActor.isPresent() && "Nombre Actualizado".equals(updatedActor.get().getNombre()), "El actor no se actualizó correctamente");
    }

    @Test
    public void testDeleteActor() {
        Actor actorToDelete = new Actor();
        actorToDelete.setActorId(testActorId);
        Optional<Actor> actorBeforeDelete = actorDAO.get(testActorId);
        assertTrue(actorBeforeDelete.isPresent(), "Actor to be deleted does not exist.");

        actorDAO.delete(actorToDelete);

        Optional<Actor> deletedActor = actorDAO.get(testActorId);
        assertFalse(deletedActor.isPresent(), "Actor was not deleted correctly.");
    }
}
