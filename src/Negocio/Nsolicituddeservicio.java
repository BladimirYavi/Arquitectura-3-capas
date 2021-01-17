/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Ddetalleservicio;
import Datos.Dsolicituddeservicio;
import java.sql.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Nsolicituddeservicio {
    private Dsolicituddeservicio Datosolicitud;
    private Ddetalleservicio Datodetalle;

    public Nsolicituddeservicio(){
        Datosolicitud = new Dsolicituddeservicio();
        Datodetalle = new Ddetalleservicio();
    }
    
    public DefaultTableModel Mostrar(){
        return Datosolicitud.Mostrar();
    }
    
    public void Guardar(int idSolicitud, String descripcion, String fecha, Double montoTotal,Double saldo, String ciCliente,String estadoEntrega,String estadoPago, JTable listaDetalle){
        Datosolicitud.setIdSolicitud(idSolicitud);
        Datosolicitud.setDescripcion(descripcion);
        Datosolicitud.setFecha(fecha);
        Datosolicitud.setMontoTotal(montoTotal);
        Datosolicitud.setSaldo(saldo);
        Datosolicitud.setCiCliente(ciCliente);
        Datosolicitud.setEstadoEntrega(estadoEntrega);
        Datosolicitud.setEstadoPago(estadoPago);
        Datosolicitud.Guardar();
        double total = 0;
        for (int i = 0; i < listaDetalle.getRowCount(); i++) {
            double costo = Double.parseDouble(listaDetalle.getValueAt(i, 3).toString());
            int idServicio = Integer.parseInt(listaDetalle.getValueAt(i,2).toString());
            Datodetalle.setIdSolicitud(idSolicitud);            
            Datodetalle.setCosto(costo);
            Datodetalle.setIdServicio(idServicio);
            Datodetalle.Guardar();
            total=total+costo;
        }
        Datosolicitud.setMontoTotal(total);
////        Datosolicitud.setSaldo(total);
        Datosolicitud.updateCosto();
    }
    
    public void Modificar(int idSolicitud,String estadoEntrega){
       Datosolicitud.setEstadoEntrega(estadoEntrega);
       Datosolicitud.setIdSolicitud(idSolicitud);
       Datosolicitud.Modificar();
    }
    
    
    public DefaultComboBoxModel obtenerSolicitud(String ciCliente){
        Datosolicitud.setCiCliente(ciCliente);
        return Datosolicitud.obtenerSolicitud();
    }
    
    public double obtenerMontoSolicitud(int idSolicitud){
        Datosolicitud.setIdSolicitud(idSolicitud);
        return Datosolicitud.obtenerMontoSolicitud();
    }
}
