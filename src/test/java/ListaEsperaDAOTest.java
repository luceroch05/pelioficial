/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import peaches.pelioficial.dao.ListaEsperaDAO;
import peaches.pelioficial.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import peaches.pelioficial.model.ListaDeEspera;
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
public class ListaEsperaDAOTest {
    
    private static Connection connection;
    private ListaEsperaDAO listaEsperaDAO;
    private ListaDeEspera testListaDeEspera;
    
    public ListaEsperaDAOTest() {
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
        listaEsperaDAO = new ListaEsperaDAO(connection);
        testListaDeEspera = new ListaDeEspera();
        testListaDeEspera.setPeliculaId(1); 
        testListaDeEspera.setSocioId(1); 
        testListaDeEspera.setFechaSolicitud(LocalDate.now());
        int id = listaEsperaDAO.save(testListaDeEspera);
        testListaDeEspera.setListaEsperaId(id);
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        if (testListaDeEspera != null && testListaDeEspera.getListaEsperaId() != 0) {
            listaEsperaDAO.delete(testListaDeEspera);
        }
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    void testSaveListaDeEspera() {
        ListaDeEspera newListaDeEspera = new ListaDeEspera();
        newListaDeEspera.setPeliculaId(2); 
        newListaDeEspera.setSocioId(2); 
        newListaDeEspera.setFechaSolicitud(LocalDate.now());
        int newListaDeEsperaId = listaEsperaDAO.save(newListaDeEspera);
        assertTrue(newListaDeEsperaId > 0, "La entrada en la lista de espera no fue guardada correctamente.");
    }

    @Test
    void testGetListaDeEspera() {
        Optional<ListaDeEspera> foundListaDeEspera = listaEsperaDAO.get(testListaDeEspera.getListaEsperaId());
        assertTrue(foundListaDeEspera.isPresent(), "La entrada en la lista de espera no fue encontrada.");
    }

    @Test
    void testUpdateListaDeEspera() {
        Optional<ListaDeEspera> listaDeEsperaToUpdateOpt = listaEsperaDAO.get(testListaDeEspera.getListaEsperaId());
        assertTrue(listaDeEsperaToUpdateOpt.isPresent(), "La entrada en la lista de espera para actualizar no fue encontrada.");
        ListaDeEspera listaDeEsperaToUpdate = listaDeEsperaToUpdateOpt.get();
        listaDeEsperaToUpdate.setPeliculaId(3); // 
        listaDeEsperaToUpdate.setSocioId(3); // 
        listaEsperaDAO.update(listaDeEsperaToUpdate, new String[]{
            String.valueOf(listaDeEsperaToUpdate.getPeliculaId()),
            String.valueOf(listaDeEsperaToUpdate.getSocioId()),
            listaDeEsperaToUpdate.getFechaSolicitud().toString()
        });
        Optional<ListaDeEspera> updatedListaDeEsperaOpt = listaEsperaDAO.get(testListaDeEspera.getListaEsperaId());
        assertTrue(updatedListaDeEsperaOpt.isPresent(), "La entrada en la lista de espera actualizada no fue encontrada.");
        ListaDeEspera updatedListaDeEspera = updatedListaDeEsperaOpt.get();
        assertEquals(3, updatedListaDeEspera.getPeliculaId(), "La película de la lista de espera no fue actualizada correctamente.");
        assertEquals(3, updatedListaDeEspera.getSocioId(), "El socio de la lista de espera no fue actualizado correctamente.");
    }

    @Test
    void testDeleteListaDeEspera() {
        listaEsperaDAO.delete(testListaDeEspera);
        Optional<ListaDeEspera> deletedListaDeEspera = listaEsperaDAO.get(testListaDeEspera.getListaEsperaId());
        assertFalse(deletedListaDeEspera.isPresent(), "La entrada en la lista de espera no fue borrada correctamente.");
    }
}
