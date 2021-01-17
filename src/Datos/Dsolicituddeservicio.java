/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.sql.Date;
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
public class Dsolicituddeservicio {

    private int idSolicitud;
    private String descripcion;
    private String fecha;
    private Double MontoTotal;
    private String ciCliente;
    private Double saldo;
    private String estadoEntrega;
    private String estadoPago;

    private conexion cnx = new conexion(); // instanciamos un objeto de la clase conexion
    private Connection cn = cnx.conectar(); // nos conectamos a la base de datos sitecal
    PreparedStatement st; // declaramos una instancia de PrepareStatement para preparar la consulta y ejecutarla
    private String sSQL = ""; // creamos una variable para almacenar las consultas para las base datos

    public Dsolicituddeservicio() {

    } 

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMontoTotal() {
        return MontoTotal;
    }

    public void setMontoTotal(Double MontoTotal) {
        this.MontoTotal = MontoTotal;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    
    
    
     public DefaultTableModel Mostrar(){
       DefaultTableModel modelo; // creamos un objeto DefaultTableModel
       String [] titulos = {"idSolicitud", "Descripcion", "Fecha", "MontoTotal","Cliente","EstadoEntrega","EstadoPago","Costo","Servicio"};
       String [] registros = new String[9]; // Creamos un vector de String para almacenar los registros de la tabla Usuario
       modelo = new DefaultTableModel(null, titulos); // insertamos los titulos a la tabla del formualario
//       sSQL = "select u.idUsuario,u.nombre,u.email,u.telefono,u.tipoUsuario,a.nombre,a.idArea from usuario as u,area as a where u.nombre like'%"+buscar+
//               "%' and u.idArea = a.idArea order by idUsuario desc";
       String estadoPago = "debe";
       String estadoEntrega = "En proceso";
//       sSQL = "select sol.idSolicitud, sol.descripcion,sol.fecha, sol.montoTotal, cli.nombre, estadoEntrega,estadoPago from solicitud as sol,cliente as cli where cli.nombre like'%"+buscar+
//               "%' and sol.cicliente = cli.ciCliente order by idSolicitud desc";

    sSQL = "select sol.idSolicitud, sol.descripcion,sol.fecha, sol.montoTotal, cli.nombre, estadoEntrega,estadoPago,ds.costo,ds.idServicio from solicitud as sol,cliente as cli,detalleservicio as ds, servicio as ser where sol.cicliente = cli.ciCliente and ds.idServicio = ser.idServicio and ds.idSolicitud = sol.idSolicitud  order by idSolicitud desc";
       try {//and sol.estadoEntrega='"+estadoEntrega+"' and sol.estadoPago='"+estadoPago+"'
           Statement csta = cn.createStatement();
           ResultSet rs = csta.executeQuery(sSQL);
           while(rs.next()){ // recorremos el resultset
               registros[0] = rs.getString("sol.idSolicitud");
               registros[1] = rs.getString("sol.descripcion");
               registros[2] = rs.getString("sol.fecha");
               registros[3] = rs.getString("sol.montoTotal");
               registros[4] = rs.getString("cli.nombre");
               registros[5] = rs.getString("estadoEntrega");
               registros[6] = rs.getString("estadoPago");
               registros[7] = rs.getString("ds.costo");
               registros[8] = rs.getString("ds.idServicio");
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
        sSQL = "insert into solicitud (idSolicitud, descripcion, fecha, montoTotal,ciCliente,estadoEntrega,estadoPago)" + "values(?,?,?,?,?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setInt(1, this.getIdSolicitud());
            st.setString(2, this.getDescripcion());
            st.setString(3, this.getFecha());
            st.setDouble(4, this.getMontoTotal());
            st.setString(5, this.getCiCliente());
            st.setString(6, this.getEstadoEntrega());
            st.setString(7, this.getEstadoPago());
            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                JOptionPane.showMessageDialog(null, "Registrado Correctamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void Modificar(){
        sSQL = "update solicitud set estadoEntrega=? where idSolicitud=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getEstadoEntrega());
            st.setInt(2, this.getIdSolicitud());
            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public DefaultComboBoxModel obtenerSolicitud() {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        sSQL = "select idSolicitud from solicitud where ciCliente='"+ciCliente+"';";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            //combo.addElement("seleccione la solicitud");
            while(rs.next()){
                combo.addElement(rs.getString("idSolicitud"));
            }
            csta.close();
            rs.close();
            return combo;
        } catch (Exception e) {
            System.out.println("no se pudo CARGAR LOS DATOS");
        }
        return combo;
    }
    
    
    
      public void updateCosto() {
        sSQL = "update solicitud set montoTotal=? where idSolicitud=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setDouble(1, this.getMontoTotal());
            st.setInt(2, this.getIdSolicitud());

            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                System.out.println("Costo Actualizado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
      
      public Double obtenerMontoSolicitud() {
        double montoTotal=0;
        sSQL = "select montoTotal from solicitud where idSolicitud='"+idSolicitud+"';";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                montoTotal = Double.parseDouble(rs.getObject(1).toString());
            }
        } catch (Exception e) {
            System.out.println("no encontro el costo");
        }
        return montoTotal;
    }
}
