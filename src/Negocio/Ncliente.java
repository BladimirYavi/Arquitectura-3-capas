/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Dcliente;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Ncliente {
    
    private final Dcliente Datocliente;
    
    public Ncliente(){
        Datocliente = new Dcliente();
    }
    
    //Método para Mostrar todos las areas
    public DefaultTableModel Mostrar(){
        return Datocliente.Mostrar();
    }
    
    
     // Método para guardar el area
    public void Guardar(String ciCliente, String nombre, String direccion, String email, String telefono){
        Datocliente.setCiCliente(ciCliente);
        Datocliente.setNombre(nombre);
        Datocliente.setDireccion(direccion);
        Datocliente.setEmail(email);
        Datocliente.setTelefono(telefono);
        Datocliente.Guardar();
    }
    
     // Método para Modificar una Area
    public void Modificar(String ciCliente, String nombre, String direccion, String email, String telefono){
        Datocliente.setCiCliente(ciCliente);
        Datocliente.setNombre(nombre);
        Datocliente.setDireccion(direccion);
        Datocliente.setEmail(email);
        Datocliente.setTelefono(telefono);
        Datocliente.Modificar();
    }
    
    public void Eliminar(String ciCliente){
        Datocliente.setCiCliente(ciCliente);
        Datocliente.Eliminar();
    }
    
    // Méetodo para Obtener un Area
    public DefaultComboBoxModel obtenerCliente(){
        return Datocliente.obtenerCliente();
    }
    
    public String getPrimaryCliente(String nombre){
        Datocliente.setNombre(nombre);
        return Datocliente.getPrimaryCliente();
    }
}
