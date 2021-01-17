/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Dcuota {
    private int idCuota;
    private double monto;
    private String fechaCuota;
    private int idPago;
    
    //INSTANCIMOS LOS OBJETOS DE CONEXION
    private conexion cnx = new conexion(); // instanciamos un objeto de la clase conexion
    private Connection cn = cnx.conectar(); // nos conectamos a la base de datos sitecal
    PreparedStatement st; // declaramos una instancia de PrepareStatement para preparar la consulta y ejecutarla
    private String sSQL = ""; // creamos una variable para almacenar las consultas para las base datos

    public Dcuota() {
    }

    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        this.idCuota = idCuota;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaCuota() {
        return fechaCuota;
    }

    public void setFechaCuota(String fechaCuota) {
        this.fechaCuota = fechaCuota;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    
    public DefaultTableModel Mostrar() {
        DefaultTableModel modelo; // creamos un objeto DefaultTableModel
        String[] titulos = {"idCuota", "Monto", "Fecha", "Pago"};
        String[] registros = new String[4]; // Creamos un vector de String para almacenar los registros de la tabla Usuario
        modelo = new DefaultTableModel(null, titulos); // insertamos los titulos a la tabla del formualario
//       sSQL = "select u.idUsuario,u.nombre,u.email,u.telefono,u.tipoUsuario,a.nombre,a.idArea from usuario as u,area as a where u.nombre like'%"+buscar+
//               "%' and u.idArea = a.idArea order by idUsuario desc";

        sSQL = "select idCuota, monto, fechaCuota, idPago from cuota order by idCuota desc";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) { // recorremos el resultset
                registros[0] = rs.getString("idCuota");
                registros[1] = rs.getString("monto");
                registros[2] = rs.getString("fechaCuota");
                registros[3] = rs.getString("idPago");
                modelo.addRow(registros);
            }
            csta.close();
            rs.close();
            return modelo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public void Guardar(){
        sSQL = "insert into cuota (monto, fechaCuota, idPago)"+"values(?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setDouble(1, this.getMonto());
            st.setString(2, this.getFechaCuota());
            st.setInt(3, this.getIdPago());
            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                System.out.println("Se ha registrado la Cuota ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
}
