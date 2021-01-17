/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author blyq_
 */
public class Dcliente {
    private String ciCliente;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
     /// Insntaciando objetos de conexion
    private conexion cnx = new conexion();
    private Connection cn = cnx.conectar();
    PreparedStatement st;
    private String sSQL = "";
    
    //Constructor por defecto
    public Dcliente(){
        
    }
    
    //Constructor parametrizado de mi clase Cliente
    public Dcliente(String ciCliente, String nombre, String direccion, String email,String telefono) {
        this.ciCliente = ciCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    //CREANDO LOS METODOS PARA GUARDAR, MODIFICAR Y ELIMINAR UN CLIENTE
    
    public DefaultTableModel Mostrar(){
        DefaultTableModel modelo;
        
        String [] titulos = {"ciCliente", "nombre", "Direccion","Email","Telefono"};
        String [] registros = new  String [5];

        modelo = new DefaultTableModel (null,titulos);
        sSQL="select * from cliente";
        try{
            // los statement se usa para ejecutar sentecias SQL, lleva asociada una conexion que 
            //sirvio como origen para su creacion
            /*
            Los objetos Statement se crean a partir de objetos Connection con el método createStatement. Por ejemplo,
            suponiendo que ya exista un objeto Connection denominado cn,
            la siguiente línea de código crea un objeto Statement para pasar sentencias SQL a la base de datos:
            */
            Statement csta = cn.createStatement();
            
            //Los metodos para ejecutar la consulta que contiene un statemen depende del tipo de consulta
            /* si select -> executeQuery(sSQL),
            si insert, update, delete -> se ejecuta con executeUpdate(sSQL), devuelve un int con
            el numero de filas afectadas*/
            
            ResultSet rs = csta.executeQuery(sSQL);
            
            /* executeQuery(sSQL),una vez ejecutada la consulta devuelve una instancia Resultset 
            LOS RESULTSETS Lleva asociadas las filas y columnas que cumplían con la sentencia SQL*/
            
            while(rs.next()){
                registros[0] = rs.getString("ciCliente");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("direccion");
                registros[3] = rs.getString("email");
                registros[4] = rs.getString("telefono");
                modelo.addRow(registros);
            }
            csta.close();
            rs.close();
            return modelo;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "error al cargar");
            return  null;
        }
    }
    
    
    //Método para Guardar un Area
    public void Guardar(){
        sSQL = "insert into cliente(ciCliente,nombre,direccion,email,telefono)"+"values(?,?,?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getCiCliente());
            st.setString(2, this.getNombre());
            st.setString(3, this.getDireccion());
            st.setString(4, this.getEmail());
            st.setString(5, this.getTelefono());
            
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
    public void Modificar(){
        sSQL = "update cliente set nombre=?, direccion=?, email=?, telefono=? where ciCliente=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getNombre());
            st.setString(2, this.getDireccion());
            st.setString(3, this.getEmail());
            st.setString(4, this.getTelefono());
            st.setString(5, this.getCiCliente());
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
    public void Eliminar(){
        sSQL = "delete from cliente where ciCliente=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getCiCliente());
            
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
    public DefaultComboBoxModel obtenerCliente() {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        sSQL = "select * from cliente";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            combo.addElement("seleccione un cliente");
            while(rs.next()){
                combo.addElement(rs.getString("nombre"));
            }
            csta.close();
            rs.close();
            return combo;
        } catch (Exception e) {
            System.out.println("no se pudo CARGAR LOS DATOS ClIENTES");
        }
        return combo;
    }
    
    
    public String getPrimaryCliente() {
        sSQL = "select ciCliente from cliente where nombre='"+nombre+"'";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                ciCliente = rs.getObject(1).toString();
            }
        } catch (Exception e) {
            System.out.println("no encontro la marca");
        }
        return ciCliente;
    }
}
