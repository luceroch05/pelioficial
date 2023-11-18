/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author q-ql
 */
public class ImageUtils {
    
    public static void escalarImagen(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(ImageUtils.class.getResource(imagePath));
        Image img = originalIcon.getImage();

        // Obten las dimensiones para mantener la proporcion de la imagen
        int newWidth = button.getWidth();
        int newHeight = (int) (originalIcon.getIconHeight() * ((double)newWidth / originalIcon.getIconWidth()));

        // Si la nueva altura es menor que la altura del boton, ajusta la altura y calcula la nueva anchura
        if (newHeight < button.getHeight()) {
            newHeight = button.getHeight();
            newWidth = (int) (originalIcon.getIconWidth() * ((double)newHeight / originalIcon.getIconHeight()));
        }

        // Escala la imagen al nuevo tamanio
        Image resizedImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Crea un nuevo ImageIcon con la imagen redimensionada
        ImageIcon newIcon = new ImageIcon(resizedImage);

        // Establece la imagen redimensionada como icono del boton
        button.setIcon(newIcon);

        // Actualiza los bordes y márgenes del boton
        button.setMargin(new Insets(0, 0, 0, 0));
//        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
//        button.setBorderPainted(false);
        button.setFocusPainted(false);
//        button.setRolloverEnabled(false);

        // Centrar imagen en el boton si es más grande
        if (newWidth > button.getWidth() || newHeight > button.getHeight()) {
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
        }
    }
    
}
