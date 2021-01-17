/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.Dcuota;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Ncuota {
    private Dcuota Datocuota;
    
    public Ncuota(){
        Datocuota = new Dcuota();
    }
    
    
    public DefaultTableModel Mostrar(){
        return Datocuota.Mostrar();
    }
    
    public void Guardar(Double monto, String fechaCuota,int idPago){
        Datocuota.setMonto(monto);
        Datocuota.setFechaCuota(fechaCuota);
        Datocuota.setIdPago(idPago);
        Datocuota.Guardar();
    }
}
