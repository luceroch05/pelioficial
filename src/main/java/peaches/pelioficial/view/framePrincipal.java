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

/**
 *
 * @author Lucero
 */
public class framePrincipal extends javax.swing.JFrame {


    public framePrincipal() {
        initComponents();
    }

 int xMouse, yMouse;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtuser = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtpassword = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setForeground(new java.awt.Color(255, 153, 153));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs Login/Banner1.jpg"))); // NOI18N
        Fondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 440, 520));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setText("¡Bienvenido!");
        Fondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 250, 40));

        txtuser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtuser.setForeground(new java.awt.Color(204, 204, 204));
        txtuser.setText("usuario");
        txtuser.setBorder(null);
        txtuser.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtuserMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtuserMousePressed(evt);
            }
        });
        txtuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtuserActionPerformed(evt);
            }
        });
        Fondo.add(txtuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 250, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        Fondo.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 250, 20));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        Fondo.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 323, 250, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Iniciar Sesión");
        Fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 170, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
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
        Fondo.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 250, 30));

        txtpassword.setForeground(new java.awt.Color(204, 204, 204));
        txtpassword.setText("jPasswordField1");
        txtpassword.setBorder(null);
        txtpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpasswordMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtpasswordMousePressed(evt);
            }
        });
        Fondo.add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 240, -1));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(920, 18));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs Login/closeButton.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 900, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        Fondo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 524, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtuserActionPerformed


    }//GEN-LAST:event_txtuserActionPerformed

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
xMouse = evt.getX();
yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
     int x = evt.getXOnScreen();
     int y  = evt.getYOnScreen();
        this.setLocation(x -xMouse, y- yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void txtuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtuserMouseClicked
  
        
    }//GEN-LAST:event_txtuserMouseClicked

    private void txtpasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpasswordMouseClicked
       

    }//GEN-LAST:event_txtpasswordMouseClicked

    private void txtuserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtuserMousePressed
       txtuser.setText("");
        txtuser.setForeground(Color.black);
     

    }//GEN-LAST:event_txtuserMousePressed

    private void txtpasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpasswordMousePressed
        txtpassword.setText("");
        txtpassword.setForeground(Color.black);
         
        
    }//GEN-LAST:event_txtpasswordMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           
//            panelMenu pm = new panelMenu();
            panelCartelera pm = new panelCartelera();

            // Cambia el layout del contenedor principal a BorderLayout
            this.setLayout(new BorderLayout());

            // Remueve todos los componentes del contenedor principal
            this.getContentPane().removeAll();

            // Agrega el panelMenu al centro del contenedor principal
            this.add(pm, BorderLayout.CENTER);

            // Revalida y repinta el contenedor principal
            this.revalidate();
            this.repaint();
                    // TODO add your handling code here:
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtuser;
    // End of variables declaration//GEN-END:variables
}
