/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author q-ql
 */
public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/pelioficial";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection conectar(){
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos");
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos" +  e.getMessage());
            return null;
        }
    }
}
