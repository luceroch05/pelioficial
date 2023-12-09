/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package peaches.pelioficial.view;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import peaches.pelioficial.util.Placeholders;

/**
 *
 * @author Lucero
 */
public class framePrincipal extends javax.swing.JFrame {
    private panelMenu panelMenu;
    private panelCartelera panelCartelera;
    
    public framePrincipal() {
        initComponents();
        panelMenu = new panelMenu(this);
        panelCartelera = new panelCartelera(this);
        
        
        add(panelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        add(panelCartelera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        panelCartelera.setVisible(false);
        panelMenu.setVisible(false);
    }
    
    public void mostrarPanelCartelera(){
        panelMenu.setVisible(false);
        panelCartelera.setVisible(true);
    }
    
    public void mostrarPanelMenu(){
        panelCartelera.setVisible(false);
        panelMenu.setVisible(true);
    }

 int xMouse, yMouse;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBarra = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Fondo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBarra.setBackground(new java.awt.Color(0, 0, 0));
        panelBarra.setPreferredSize(new java.awt.Dimension(920, 18));
        panelBarra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelBarraMouseDragged(evt);
            }
        });
        panelBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelBarraMousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/closeButton.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBarraLayout = new javax.swing.GroupLayout(panelBarra);
        panelBarra.setLayout(panelBarraLayout);
        panelBarraLayout.setHorizontalGroup(
            panelBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBarraLayout.createSequentialGroup()
                .addGap(0, 1180, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBarraLayout.setVerticalGroup(
            panelBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        getContentPane().add(panelBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 20));

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setForeground(new java.awt.Color(255, 153, 153));
        Fondo.setPreferredSize(new java.awt.Dimension(1200, 640));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 60)); // NOI18N
        jLabel2.setText("¡Bienvenido!");
        Fondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 470, 100));

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtUsuario.setText("Usuario");
        txtUsuario.setBorder(null);
        txtUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        Fondo.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 250, 30));
        txtUsuario.setForeground(Color.GRAY);
        txtUsuario.addFocusListener(new Placeholders("Usuario", new Color(204, 204, 204), Color.BLACK));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        Fondo.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 250, 20));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        Fondo.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, 250, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel3.setText("Iniciar Sesión");
        Fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 170, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ingresar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Fondo.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, 250, 40));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(204, 204, 204));
        txtPassword.setText("jPasswordField1");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPasswordMousePressed(evt);
            }
        });
        Fondo.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 390, 240, 30));
        txtPassword.setForeground(Color.GRAY);
        txtPassword.addFocusListener(new Placeholders("jPasswordField1", new Color(204, 204, 204), Color.BLACK));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/Banner1.jpg"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(564, 600));
        Fondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 540, 710));

        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1200, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void panelBarraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraMousePressed
xMouse = evt.getX();
yMouse = evt.getY();
    }//GEN-LAST:event_panelBarraMousePressed

    private void panelBarraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraMouseDragged
     int x = evt.getXOnScreen();
     int y  = evt.getYOnScreen();
        this.setLocation(x -xMouse, y- yMouse);
    }//GEN-LAST:event_panelBarraMouseDragged

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void txtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMouseClicked
    }//GEN-LAST:event_txtUsuarioMouseClicked

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void txtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMousePressed
    }//GEN-LAST:event_txtUsuarioMousePressed

    private void txtPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMousePressed
    }//GEN-LAST:event_txtPasswordMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            this.setLayout(new BorderLayout());
            this.getContentPane().remove(Fondo);
            panelMenu.setVisible(true);
            this.revalidate();
            this.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel panelBarra;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
