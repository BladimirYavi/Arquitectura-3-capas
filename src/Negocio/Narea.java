/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Darea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Narea {
    private final Darea Datoarea;
    
    public Narea(){
        Datoarea = new Darea();
    }
    
    //Método para Mostrar todos las areas
    public DefaultTableModel Mostrar(){
        return Datoarea.Mostrar();
    }
    
    // Método para guardar el area
    public void Guardar(String idArea, String nombre, String telefono){
        Datoarea.setIdArea(idArea);
        Datoarea.setNombre(nombre);
        Datoarea.setTelefono(telefono);
        Datoarea.Guardar();
    }
    
     // Método para Modificar una Area
    public void Modificar(String idArea,String nombre, String telefono){
        Datoarea.setNombre(nombre);
        Datoarea.setTelefono(telefono);
        Datoarea.setIdArea(idArea);
        Datoarea.Modificar();
    }
    
    public void Eliminar(String idArea){
        Datoarea.setIdArea(idArea);
        Datoarea.Eliminar();
    }
    
    // Méetodo para Obtener un Area
    public DefaultComboBoxModel obtenerArea(){
        return Datoarea.obtenerArea();
    }
}
