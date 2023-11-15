/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.alquilerpelas.paneles;

/**
 *
 * @author q-ql
 */
import com.proyecto.alquilerpelas.panelBuscaSocio;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel{
    JTextField txtusuario, txtcontraseña;
    JLabel lbl,lblusuario,lblpass;
    JButton btnPanel2;
    Font f1 = new Font("Corbel", Font.BOLD,20);
    Color c1 = new Color(0,38,85);

    public  Font getFont(){  
        return f1;
    }

    public Color getColor(){
        return c1;
    }


    //PANEL USUARIO
    public LoginPanel(){
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = (int) screenSize.getWidth();
        int alto = (int) screenSize.getHeight();
        this.setBackground(new Color(0,38,85));
        txtusuario = new JTextField();
        txtusuario.setFont(f1);
        txtusuario.setBounds(ancho/2-150, alto/2 -150,200,40);
        add(txtusuario);
        txtcontraseña = new JTextField();
        txtcontraseña.setBounds(ancho/2-150, alto/2 -70,200,40);
        add(txtcontraseña);

        lblusuario = new JLabel("Usuario");
        lblusuario.setBounds(600,200,100,20);
        lblusuario.setFont(f1);
        add(lblusuario);

        lblpass=new JLabel("Password");
        lblpass.setBounds(590 ,290,100,20);
        lblpass.setFont(f1);
        add(lblpass);

        //CAMBIO DE PANEL A PANEL 2
        btnPanel2=new JButton("Ingresar");
        btnPanel2.setBounds(ancho / 2 - 75, alto / 2 + 50, 150, 40);
        add(btnPanel2);
        btnPanel2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        
        //panelBuscaSocio pbs = new panelBuscaSocio();       
        
              JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPanel.this);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new PanelBuscarSocio());
                
              frame.revalidate();
                frame.repaint();
                
        System.out.print("Presione");
        
        }

       
 
        });

        //IMAGEN LOG IN
        lbl = new JLabel();
        ImageIcon icon = new ImageIcon("src\\imagen\\pngusuario.png");
        int labelWidth = 100;
        int labelHeight = 100;
   
        Image image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);

        lbl.setIcon(resizedIcon);

        lbl.setBounds(ancho/2-100 , alto/2 -300 ,100,100);
        add(lbl);
    }
}
