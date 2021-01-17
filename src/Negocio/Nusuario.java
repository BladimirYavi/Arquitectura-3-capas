/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Dusuario;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Nusuario {
    // Declaramos una instancia de Dusuario
    private Dusuario Datousuario;
    
    // creamos el método constructor por default
    public Nusuario(){
        Datousuario = new Dusuario();
    }
    
    // CREAMOS LOS METODOS CRUD PARA USUARIO
    //Método para Mostrar todos las areas
    public DefaultTableModel Mostrar(){
        return Datousuario.Mostrar();
    }
     // Método para guardar el area
    public void Guardar(int idUsuario, String nombre, String email, String telefono, String tipoUsuario, String idArea){
        Datousuario.setIdUsuario(idUsuario);
        Datousuario.setNombre(nombre);
        Datousuario.setEmail(email);
        Datousuario.setTelefono(telefono);
        Datousuario.setTipoUsuario(tipoUsuario);
        Datousuario.setIdArea(idArea);
        Datousuario.Guardar();
    }
    
     // Método para Modificar una Area
    public void Modificar(int idUsuario, String nombre, String email, String telefono, String tipoUsuario, String idArea){
        Datousuario.setIdUsuario(idUsuario);
        Datousuario.setNombre(nombre);
        Datousuario.setEmail(email);
        Datousuario.setTelefono(telefono);
        Datousuario.setTipoUsuario(tipoUsuario);
        Datousuario.setIdArea(idArea);
        Datousuario.Modificar();
    }
    
    public void Eliminar(int idUsuario){
        Datousuario.setIdUsuario(idUsuario);
        Datousuario.Eliminar();
    }
    
    public DefaultComboBoxModel obtenerUsuario(){
        return Datousuario.obtenerUsuario();
    }
    
    public int getPrimaryUsuario(String nombre){
        Datousuario.setNombre(nombre);
        return Datousuario.getPrimaryUsuario();
    }
    
}
