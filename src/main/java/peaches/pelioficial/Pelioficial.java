/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package peaches.pelioficial;

import peaches.pelioficial.view.framePrincipal;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import static peaches.pelioficial.util.DatabaseConnector.conectar;
import javax.swing.UIManager;

/**
 *
 * @author Lucero
 */
public class Pelioficial {

    public static void main(String[] args) {
        
        conectar();



        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new framePrincipal().setVisible(true);
            }
        });
    }
}
