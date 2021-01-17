/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.awt.Component;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Darea {

    private String idArea;
    private String nombre;
    private String telefono;

    /// Insntaciando objetos de conexion
    private conexion cnx = new conexion();
    private Connection cn = cnx.conectar();
    PreparedStatement st;
    private String sSQL = "";

    public Darea() {
    }

    public Darea(String idArea, String nombre, String telefono) {
        this.idArea = idArea;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
 
    ///// Creando los metodos para acceder ////////
    //Metodo para mostrar todos los datos
    public DefaultTableModel Mostrar() {
        DefaultTableModel modelo;

        String[] titulos = {"idArea", "Area", "Telefono"};
        String[] registros = new String[3];

        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select * from area order by idArea desc";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                registros[0] = rs.getString("idArea");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("telefono");
                modelo.addRow(registros);
            }
            csta.close();
            rs.close();
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al cargar");
            return null;
        }
    }

    //Método para Guardar un Area
    public void Guardar() {
        sSQL = "insert into area(idArea,nombre,telefono)" + "values(?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getIdArea());
            st.setString(2, this.getNombre());
            st.setString(3, this.getTelefono());

            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                JOptionPane.showMessageDialog(null, "Registrado Correctamente");
                //JOptionPane.showMessageDialog(, "Registrado Correctamente");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Método para Modificar un area
    public void Modificar() {
        sSQL = "update area set nombre=?,telefono=? where idArea=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getNombre());
            st.setString(2, this.getTelefono());
            st.setString(3, this.getIdArea());

            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                //JOptionPane.showMessageDialog(, "Registrado Correctamente");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método para eliminar un area
    public void Eliminar() {
        sSQL = "delete from area where idArea=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getIdArea());

            int n = st.executeUpdate();
            st.close();
            if (n != 0) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                //JOptionPane.showMessageDialog(, "Registrado Correctamente");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Metodo para obtener las areas en un combo
    public DefaultComboBoxModel obtenerArea() {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        sSQL = "select * from area";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            combo.addElement("seleccione un area");
            while(rs.next()){
                combo.addElement(rs.getString("idArea")+" "+rs.getString("nombre"));
            }
            csta.close();
            rs.close();
            return combo;
        } catch (Exception e) {
            System.out.println("no se pudo CARGAR LOS DATOS PROVEEDOR...");
        }
        return combo;
    }
}
