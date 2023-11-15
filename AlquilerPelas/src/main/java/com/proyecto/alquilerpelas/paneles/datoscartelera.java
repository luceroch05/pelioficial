/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.alquilerpelas.paneles;

/**
 *
 * @author ALEX PUA
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class datoscartelera extends JPanel {
JLabel lblcinta,lblnombrepeli,lblgen,lblduracion,lblidioma,lblpais,lblicono,lblcartelera,lblsinopsis;
JButton  btnalquilar, btncancelar;
JTextArea txtcinta,txtnombrepeli,txtduracion,txtidioma,txtpais,txtsinopsis;
JComboBox cbogenero;
    Font f1 = new Font("Corbel", Font.BOLD,20);
    Color c1 = new Color(0,38,85);

public datoscartelera (CarteleraPanel cp){
    //SEAN ORDENADOS XFAVOR
    setLayout(null);
    this.setBackground(new Color(0,38,85));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        lblcinta=new JLabel("Nº Cinta:");
        lblcinta.setBounds(50,200,100,20);
        lblcinta.setFont(f1);
        lblcinta.setForeground(Color.white);
        add(lblcinta);
        txtcinta=new JTextArea();
        txtcinta.setBounds(140,200,100,25);
        txtcinta.setFont(f1);
        add(txtcinta);
        
        lblnombrepeli=new JLabel("Nombre Pelicula:");
        lblnombrepeli.setBounds(50,240,150,20);
        lblnombrepeli.setFont(f1);
        lblnombrepeli.setForeground(Color.white);
        add(lblnombrepeli);
        txtnombrepeli=new JTextArea();
        txtnombrepeli.setBounds(205,240,100,25);
        txtnombrepeli.setFont(f1);
        add(txtnombrepeli);
       
        lblgen=new JLabel("Genero:");
        lblgen.setBounds(50,280,100,20);
        lblgen.setFont(f1);
        lblgen.setForeground(Color.white);
        add(lblgen);
                cbogenero=new JComboBox();
		cbogenero.setBounds(200,280,90,25);
		cbogenero.addItem("Accion");
		cbogenero.addItem("Terror");
		cbogenero.addItem("Suspenso");
		cbogenero.addItem("Drama");
                add(cbogenero);
       
        lblduracion=new JLabel("Duracion:");
        lblduracion.setBounds(50,320,100,20);
        lblduracion.setFont(f1);
        lblduracion.setForeground(Color.white);
        add(lblduracion);
        txtduracion=new JTextArea();
        txtduracion.setBounds(140,320,100,25);
        txtduracion.setFont(f1);
        add(txtduracion);
     
        lblsinopsis=new JLabel("Sinopsis");
        lblsinopsis.setBounds(50,340,100,20);
        lblsinopsis.setFont(f1);
        lblsinopsis.setBackground(Color.white);
        add(lblsinopsis);
        txtsinopsis=new JTextArea();
        txtsinopsis.setBounds(140,340,180,25);
        txtsinopsis.setFont(f1);
        add(txtsinopsis);
 
        lblidioma=new JLabel("Idioma:");
        lblidioma.setBounds(50,400,100,20);
        lblidioma.setFont(f1);
        lblidioma.setForeground(Color.white);
        add(lblidioma);
        txtidioma=new JTextArea();
        txtidioma.setBounds(140,400,100,25);
        txtidioma.setFont(f1);
        add(txtidioma);
        
        lblpais=new JLabel("Pais:");
        lblpais.setBounds(50,440,100,20);
        lblpais.setFont(f1);
        lblpais.setForeground(Color.white);
        add(lblpais);
        txtpais=new JTextArea();
        txtpais.setBounds(140,440,100,25);
        txtpais.setFont(f1);
        add(txtpais);
 
        btnalquilar=new JButton("Alquilar");
        btnalquilar.setBounds(500,600,130,30);
        btnalquilar.setFont(f1);
        add(btnalquilar);

        btncancelar=new JButton("Cancelar");
        btncancelar.setBounds(700,600,130,30);
        btncancelar.setFont(f1);
        add(btncancelar);

        //ICONO ROLLOPELI
        lblicono = new JLabel();
        ImageIcon icon = new ImageIcon("src\\main\\java\\img\\rollopeli.png");
        int labelWidth = 80;
        int labelHeight = 80;
        Image image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        lblicono.setIcon(resizedIcon);
        lblicono.setBounds(50 , 50,100,100);
        add(lblicono);
        
        lblcartelera=new JLabel("Cartelera");
        lblcartelera.setBounds(130,100,100,20);
        lblcartelera.setFont(f1);
        lblcartelera.setForeground(Color.white);
        add(lblcartelera);
        
} 
}
