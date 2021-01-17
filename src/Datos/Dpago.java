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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Dpago {

    private int idPago;
    private int idSolicitud;
    private String ciCliente;
    private String fecha;
    private double saldo;

    /// Insntaciando objetos de conexion
    private conexion cnx = new conexion();
    private Connection cn = cnx.conectar();
    PreparedStatement st;
    private String sSQL = "";

    public Dpago() {

    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    
    public DefaultTableModel Mostrar() {
        DefaultTableModel modelo; // creamos un objeto DefaultTableModel
        String[] titulos = {"idPago", "Fecha", "Solicitud", "Cliente", "Saldo"};
        String[] registros = new String[5]; // Creamos un vector de String para almacenar los registros de la tabla Usuario
        modelo = new DefaultTableModel(null, titulos); // insertamos los titulos a la tabla del formualario
//       sSQL = "select u.idUsuario,u.nombre,u.email,u.telefono,u.tipoUsuario,a.nombre,a.idArea from usuario as u,area as a where u.nombre like'%"+buscar+
//               "%' and u.idArea = a.idArea order by idUsuario desc";

        sSQL = "select p.idPago, fecha, idSolicitud, cli.nombre,saldo from pago as p,cliente as cli where p.cicliente = cli.ciCliente and p.saldo!=0 order by p.idPago desc";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) { // recorremos el resultset
                registros[0] = rs.getString("idPago");
                registros[1] = rs.getString("fecha");
                registros[2] = rs.getString("idSolicitud");
                registros[3] = rs.getString("cli.nombre");
                registros[4] = rs.getString("saldo");
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
    
    
     ///// Metodos
    public void Guardar() {
        sSQL = "insert into pago (idPago, fecha, idSolicitud, ciCliente, saldo)" + "values(?,?,?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setInt(1, this.getIdPago());
            st.setString(2, this.getFecha());
            st.setInt(3, this.getIdSolicitud());
            st.setString(4, this.getCiCliente());
            st.setDouble(5, this.getSaldo());
            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                JOptionPane.showMessageDialog(null, "Registrado Correctamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     public DefaultComboBoxModel obtenerPago() {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        sSQL = "select * from pago";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            combo.addElement("seleccione un pago");
            while(rs.next()){
                combo.addElement(rs.getString("idPago"));
            }
            csta.close();
            rs.close();
            return combo;
        } catch (Exception e) {
            System.out.println("no se pudo CARGAR LOS PAGOS...");
        }
        return combo;
    }
    
     public Double obtenerSaldoPago() {
        sSQL = "select saldo from pago where idPago='"+idPago+"';";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                saldo = Double.parseDouble(rs.getObject(1).toString());
            }
        } catch (Exception e) {
            System.out.println("no encontro el costo");
        }
        return saldo;
    }
    
    public void updateSaldo() {
        sSQL = "update pago set saldo=? where idPago=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setDouble(1, this.getSaldo());
            st.setInt(2, this.getIdPago());

            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                System.out.println("Saldo Actualizado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
