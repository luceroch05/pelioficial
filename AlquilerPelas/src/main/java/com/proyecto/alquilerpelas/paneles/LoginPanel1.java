/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.alquilerpelas.paneles;

/**
 *
 * @author q-ql
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LoginPanel1 extends JFrame{
    public LoginPanel1() {
        setTitle("Login Screen");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Usuario icono
        JLabel userIconLabel = new JLabel(new ImageIcon("src\\main\\java\\img\\pngusuario.png")); // Asumiendo que tienes un icono llamado user-icon.png
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(userIconLabel, gbc);

        // Etiqueta SOCIO
        JLabel socioLabel = new JLabel("SOCIO", SwingConstants.CENTER);
        socioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(socioLabel, gbc);

        // Campo de texto para el nombre de usuario
        JTextField usernameField = new JTextField(15);
        usernameField.setText("User name");
        mainPanel.add(usernameField, gbc);

        // Campo de texto para la contraseña
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setText("Password");
        mainPanel.add(passwordField, gbc);

        // Botón Continuar
        JButton continueButton = new JButton("Continuar");
        gbc.anchor = GridBagConstraints.SOUTH;
        mainPanel.add(continueButton, gbc);

        // Fecha y texto del párrafo
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel dateLabel = new JLabel("Fecha: dd/mm/aa");
        bottomPanel.add(dateLabel);
        JLabel paragraphTextLabel = new JLabel("El texto del párrafo");
        bottomPanel.add(paragraphTextLabel);

        // Ajuste final del layout
        gbc.weighty = 1;
        mainPanel.add(bottomPanel, gbc);

        // Agregar el panel principal al frame
        add(mainPanel);
    }
}
