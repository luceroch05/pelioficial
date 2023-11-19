/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peaches.pelioficial.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author q-ql
 */
public class SocioTableModel extends AbstractTableModel{
    private List<Socio> socios;
    private final String[] columnNames = {"IDSocio", "Nombre", "Direccion", "Telefono", "Director Favorito", "Actor Favorito", "Genero Preferido"};
    
    public SocioTableModel(List<Socio> socios){
        this.socios = socios;
    }
    
    public void setSocios(List<Socio> socios){
        this.socios = socios;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount(){
        return socios.size();
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Socio socio = socios.get(rowIndex);
        switch(columnIndex){
            case 0: return socio.getSocioId();
            case 1: return socio.getNombre();
            case 2: return socio.getDireccion();
            case 3: return socio.getTelefono();
            case 4: return socio.getDirectoresFavoritos();
            case 5: return socio.getActoresFavoritos();
            case 6: return socio.getGenerosPreferidos();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}
