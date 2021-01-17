/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author blyq_
 */
public class Ddetalleservicio {
    private int nro;
    private double costo;
    private int idSolicitud;
    private int idServicio;
    
    //INSTANCIMOS LOS OBJETOS DE CONEXION
    private conexion cnx = new conexion(); // instanciamos un objeto de la clase conexion
    private Connection cn = cnx.conectar(); // nos conectamos a la base de datos sitecal
    PreparedStatement st; // declaramos una instancia de PrepareStatement para preparar la consulta y ejecutarla
    private String sSQL = ""; // creamos una variable para almacenar las consultas para las base datos
    
    public Ddetalleservicio(){
    }

    public Ddetalleservicio(int nro, int idSolicitud, int idServicio) {
        this.nro = nro;
        this.idSolicitud = idSolicitud;
        this.idServicio = idServicio;
    }

   

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
    ///// Metodos para CRUD solicitud
    public void Guardar(){
        sSQL = "insert into detalleservicio (idSolicitud, costo, idServicio)"+"values(?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setInt(1, this.getIdSolicitud());
            st.setDouble(2, this.getCosto());
            st.setInt(3, this.getIdServicio());
            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                System.out.println("Se ha registrado el Detalle ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
