/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.alquilerpelas.paneles;

/**
 *
 * @author q-ql
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CarteleraPanel extends JPanel {
    JLabel lblPeliculas, lblSeries;
    JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    LoginPanel lp;
ActionEvent e;
    public CarteleraPanel(LoginPanel lp) {
        this.lp = lp;
        setLayout(null); // Utilizar un LayoutManager apropiado

        this.setBackground(lp.getColor());

        lblPeliculas = new JLabel("Películas");
        lblPeliculas.setFont(lp.getFont());
        lblPeliculas.setForeground(Color.white);
        lblPeliculas.setBounds(10,10,100,20);
        add(lblPeliculas);

        lblSeries = new JLabel("Series");
        lblSeries.setForeground(Color.white);
        lblSeries.setFont(lp.getFont());
     lblSeries.setBounds(150,10,100,20);

        add(lblSeries);

        btn1 = createButton("imgPelis\\df.jpg", 0,0,100, 120);
        add(btn1);

        btn2 = createButton("imgPelis\\interstellar.jpg",110,0,100, 120);
        add(btn2);
        btn2.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              // Aquí colocas la lógica que quieres que se ejecute cuando se presiona el botón
          }
      });

        btn3 = createButton("imgPelis\\susi.jpg", 110*2,0,100, 120);
        add(btn3);

        btn4 = createButton("imgPelis\\fnaf.jpg",110*3,0, 100*2, 120*2);
        add(btn4);

        // Puedes seguir agregando más botones si es necesario
    }

    private JButton createButton(String rutaimg,int x, int y ,int imgWidth, int imgHeight) {
        JButton button = new JButton();
        button.setBounds(10 + x, 30 + y, imgWidth, imgHeight );
        ImageIcon icon = new ImageIcon(rutaimg);
        Image image = icon.getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        button.setIcon(resizedIcon);
        return button;
    }
}
