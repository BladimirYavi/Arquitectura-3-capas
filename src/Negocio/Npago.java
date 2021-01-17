/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Dcuota;
import Datos.Dpago;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Npago {
    private Dpago Datopago;
    
    public Npago(){
        Datopago = new Dpago();
    }
    
    public DefaultTableModel Mostrar(){
        return Datopago.Mostrar();
    }
    
    
     public void Guardar(int idPago,String fecha, int idSolicitud, String ciCliente, Double saldo){
         Datopago.setIdPago(idPago);
        Datopago.setFecha(fecha);
        Datopago.setIdSolicitud(idSolicitud);
        Datopago.setCiCliente(ciCliente);
        Datopago.setSaldo(saldo);
        //Datopago.setSaldo(Saldo);
        Datopago.Guardar();
        
        //Datopago.setSaldo(saldo);
        //Datopago.setSaldo(total);
//        Datopago.setIdSolicitud(idSolicitud);
          //Datopago.updateSaldo();
        //Datopago.
    }
     
    public Double obtenerSaldoPago(int idPago){
        Datopago.setIdPago(idPago);
        return Datopago.obtenerSaldoPago();
    }
    
    public DefaultComboBoxModel obtenerPago(){
        return Datopago.obtenerPago();
    }
}
