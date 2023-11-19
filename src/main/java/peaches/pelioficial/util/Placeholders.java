/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author q-ql
 */
public class Placeholders extends FocusAdapter{
    private final String placeholderText;
    private final Color placeholderColor;
    private final Color txtColor;
    
    public Placeholders(String text, Color placeholder, Color textC){
        this.placeholderText = text;
        this.placeholderColor = placeholder;
        this.txtColor = textC;
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        JTextField source = (JTextField) e.getComponent();
        if (source.getText().equals(placeholderText)) {
            source.setText("");
            source.setForeground(txtColor);
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        JTextField source = (JTextField) e.getComponent();
        if (source.getText().isEmpty()) {
            source.setText(placeholderText);
            source.setForeground(placeholderColor);
        }
    }
}
