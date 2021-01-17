/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Drecepcionmaterial;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Nrecepcionmaterial {
    Drecepcionmaterial Datorecepcion;
    
    public Nrecepcionmaterial(){
        Datorecepcion = new Drecepcionmaterial();
    }
    
    public DefaultTableModel mostrar(String buscar){
        return Datorecepcion.mostrar(buscar);
    }
    
    public void Guardar(String material, int nro_muestra, String descripcion, String fechallegada, String fechasalida, int idUsuario, int idSolicitud,String ciCliente){
        Datorecepcion.setMaterial(material);
        Datorecepcion.setNro_muestra(nro_muestra);
        Datorecepcion.setDescripcion(descripcion);
        Datorecepcion.setFecha_llegada(fechallegada);
        Datorecepcion.setFecha_salida(fechasalida);
        Datorecepcion.setIdUsuario(idUsuario);
        Datorecepcion.setIdSolicitud(idSolicitud);
        Datorecepcion.setCiCliente(ciCliente);
        Datorecepcion.Guardar();    
    }   
    
    
}
