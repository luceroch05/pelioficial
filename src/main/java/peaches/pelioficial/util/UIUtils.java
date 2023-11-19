/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author q-ql
 */
public class UIUtils {
    
    public static void mostrarMensajeExitoYLimpiarCampos(JTextField[] camposTexto, JComboBox[] camposCombo, String mensaje, String titulo){
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
        for(JTextField campoTexto : camposTexto){
            campoTexto.setText("");
        }
        
        for(JComboBox campoCombo : camposCombo){
            campoCombo.setSelectedIndex(0);
        }
    }
}
