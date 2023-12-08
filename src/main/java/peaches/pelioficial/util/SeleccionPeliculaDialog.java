/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

/**
 *
 * @author mtx0v
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import peaches.pelioficial.model.Pelicula;

public class SeleccionPeliculaDialog extends JDialog {
    private JTable tablaPeliculas;
    private JButton btnSeleccionar, btnCancelar;
    private Pelicula peliculaSeleccionada;
    private List<Pelicula> listaPeliculas;

    public SeleccionPeliculaDialog(Frame parent, boolean modal, List<Pelicula> peliculas) {
        super(parent, modal);
        this.listaPeliculas = peliculas;
        setTitle("Seleccionar Película");
        setSize(300, 200); // O el tamaño que prefieras
        setLayout(new BorderLayout());
        
        tablaPeliculas = new JTable(); // Deberías configurar el modelo de tabla aquí
        // Llenar la JTable con las películas, por ejemplo, con el título de la película
        
        // Configurar el modelo de tabla aquí
        String[] columnas = {"ID", "Título"}; // Ajusta esto según los datos que quieras mostrar
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Para que no se puedan editar los datos
            }
        };
        tablaPeliculas.setModel(modeloTabla);

        // Llenar la JTable con las películas
        llenarTablaConPeliculas(modeloTabla, peliculas);

        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSeleccionar = new JButton("Seleccionar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnSeleccionar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de los botones
        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaPeliculas.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Asegúrate de obtener el ID o el objeto de película correcto según tu implementación
                    peliculaSeleccionada = listaPeliculas.get(filaSeleccionada);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SeleccionPeliculaDialog.this,
                                                  "Debes seleccionar una película.",
                                                  "Advertencia",
                                                  JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                peliculaSeleccionada = null;
                dispose();
            }
        });

        // Centrar el diálogo en la pantalla o en relación al padre
        setLocationRelativeTo(parent);
    }

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }
    
    private void llenarTablaConPeliculas(DefaultTableModel modeloTabla, List<Pelicula> peliculas) {
        for (Pelicula pelicula : peliculas) {
            Object[] fila = new Object[] {
                pelicula.getPeliculaId(), // Asumiendo que Pelicula tiene un método getId()
                pelicula.getTitulo() // Asumiendo que Pelicula tiene un método getTitulo()
            };
            modeloTabla.addRow(fila);
        }
    }
}

