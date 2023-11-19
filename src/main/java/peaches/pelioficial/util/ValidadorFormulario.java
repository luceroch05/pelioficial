/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

import javax.swing.JOptionPane;

/**
 *
 * @author q-ql
 */
public class ValidadorFormulario {
    
    public static boolean validarRegistroSocio(String nombre, String direccion, String telefono, String placeholderNombre, String placeholderDireccion, String placeholderTelefono){
        // Verifica si el nombre está vacío o si es igual al placeholder
        if(nombre.trim().isEmpty() || nombre.trim().equals(placeholderNombre)){
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Verifica que el nombre tenga solo letras y un máximo de dos espacios
        else if (!nombre.trim().matches("([a-zA-Z]+\\s{0,1}){1,3}")) {
            JOptionPane.showMessageDialog(null, "El nombre no es valido. Debe contener solo letras y no mas de dos espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(direccion.trim().isEmpty() || direccion.equals(placeholderDireccion)){
            JOptionPane.showMessageDialog(null, "La direccion no puede estar vacia.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(!telefono.matches("\\d{9}") || telefono.isEmpty() || telefono.equals(placeholderTelefono)){
            JOptionPane.showMessageDialog(null, "El telefono debe contener exactamente 9 digitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
}
