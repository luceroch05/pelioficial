/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto.alquilerpelas;

/**
 *
 * @author q-ql
 */
import com.proyecto.alquilerpelas.paneles.LoginPanel;
import com.proyecto.alquilerpelas.paneles.LoginPanel1;
import com.proyecto.alquilerpelas.paneles.pane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;

public class AlquilerPelas{ 
    public static JFrame ventana;
   public static void main(String[] args) {

   /*SwingUtilities.invokeLater(new Runnable() {
        public void run(){
            LoginPanel1 login = new LoginPanel1();
            login.setVisible(true);
        }
    });*/

    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(pane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
      /*  SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            
              new pane().setVisible(true);
            }
        });*/
        
    
   
    ventana = new JFrame("Alquiler de pela");
    ventana.add(new LoginPanel());
    ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventana.setBackground(new Color(0,38,85));

    ventana.setVisible(true);
    
     
    }

}
