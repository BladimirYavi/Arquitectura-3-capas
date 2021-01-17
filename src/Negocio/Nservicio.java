/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Dservicio;
import Datos.Dusuario;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Nservicio {
    // Declaramos una instancia de Dusuario
    private Dservicio Datoservicio;
    
    // creamos el método constructor por default
    public Nservicio(){
        Datoservicio = new Dservicio();
    }
    
    // CREAMOS LOS METODOS CRUD PARA USUARIO
    //Método para Mostrar todos las areas
    public DefaultTableModel Mostrar(){
        return Datoservicio.Mostrar();
    }
     // Método para guardar el area
    public void Guardar(String nombre, Double costoServ, String idArea){
        Datoservicio.setNombre(nombre);
        Datoservicio.setCostoServ(costoServ);
        Datoservicio.setIdArea(idArea);
        Datoservicio.Guardar();
    }
    
     // Método para Modificar una Area
    public void Modificar(int idServicio, String nombre, Double costoServ, String idArea){
        Datoservicio.setIdServicio(idServicio);
        Datoservicio.setNombre(nombre);
        Datoservicio.setCostoServ(costoServ);
        Datoservicio.setIdArea(idArea);
        Datoservicio.Modificar();
    }
    
    public void Eliminar(int idServicio){
        Datoservicio.setIdServicio(idServicio);
        Datoservicio.Eliminar();
    }
    
    public DefaultComboBoxModel obtenerServicio(){
        return Datoservicio.obtenerServicio();
    }
    
    public int getPrimaryServicio(String nombre){
        Datoservicio.setNombre(nombre);
        return Datoservicio.getPrimaryServicio();
    }
    
    public Double getCostoServicio(String nombre){
        return Datoservicio.getCostoServicio(nombre);
    }
}
