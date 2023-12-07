/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author mtx0v
 */
public class SeleccionElementosDialog<T> extends JDialog {
    private JList<T> listaElementos;
    private List<T> elementosDisponibles;
    private Set<T> elementosSeleccionados;
    private Function<T, String> representacionString;
    private Consumer<Set<T>> onConfirmacion;
    
    public SeleccionElementosDialog(Frame owner, boolean modal, List<T> elementosDisponibles, Set<T> elementosSeleccionadosActuales, Function<T, String> representacionString, Consumer<Set<T>> onConfirmacion){
        super(owner, modal);
        this.elementosDisponibles = elementosDisponibles;
        this.elementosSeleccionados = new HashSet<>(elementosSeleccionadosActuales);
        this.representacionString = representacionString;
        this.onConfirmacion = onConfirmacion;
        
        initUI();
    }
    
    private void initUI(){
        setLayout(new BorderLayout());
        
        DefaultListModel<T> modeloLista = new DefaultListModel<>();
        elementosDisponibles.forEach(modeloLista::addElement);
        listaElementos = new JList<>(modeloLista);
        listaElementos.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(representacionString.apply((T)value));
                return this;
            }
        });
        
        listaElementos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaElementos.setSelectedIndices(getIndicesSeleccionados());
        
        JScrollPane scrollPane = new JScrollPane(listaElementos);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> confirmarSeleccion());
        add(btnConfirmar, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(getOwner());
    }
    
    private int[] getIndicesSeleccionados(){
        return elementosDisponibles.stream()
                                    .filter(elementosSeleccionados::contains)
                                    .mapToInt(elementosDisponibles::indexOf)
                                    .toArray();
    }
    
    private void confirmarSeleccion(){
        elementosSeleccionados.clear();
        List<T> seleccionados = listaElementos.getSelectedValuesList();
        elementosSeleccionados.addAll(seleccionados);
        
        onConfirmacion.accept(elementosSeleccionados);
        
        dispose();
    }
}
