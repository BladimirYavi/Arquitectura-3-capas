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
public class Dservicio {
    private int idServicio;
    private String nombre;
    private Double costoServ;
    private String idArea;
    
    //INSTANCIMOS LOS OBJETOS DE CONEXION
    private conexion cnx = new conexion(); // instanciamos un objeto de la clase conexion
    private Connection cn = cnx.conectar(); // nos conectamos a la base de datos sitecal
    PreparedStatement st; // declaramos una instancia de PrepareStatement para preparar la consulta y ejecutarla
    private String sSQL = ""; // creamos una variable para almacenar las consultas para las base datos
    
    

    // Creando los Métodos Constructores por defecto
    public Dservicio(){
        
    }
    
    //Constructor parametrizado

    public Dservicio(int idServicio, String nombre, Double costoServ, String idArea) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.costoServ = costoServ;
        this.idArea = idArea;      
    }


    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCostoServ() {
        return costoServ;
    }

    //Creamos los Getters y Setters
    public void setCostoServ(Double costoServ) {
        this.costoServ = costoServ;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }
    
    
    
    // Creamos los CRUD para accedes a la base de datos
    
   public DefaultTableModel Mostrar(){
       DefaultTableModel modelo; // creamos un objeto DefaultTableModel
       String [] titulos = {"idServicio", "Nombre", "Costo","Area","idArea"};
       String [] registros = new String[5]; // Creamos un vector de String para almacenar los registros de la tabla Usuario
       modelo = new DefaultTableModel(null, titulos); // insertamos los titulos a la tabla del formualario
       sSQL = "select s.idServicio,s.nombre,s.costoServ,a.nombre,a.idArea from servicio as s,area as a where s.idArea = a.idArea order by idServicio desc";
       try {
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
           while(rs.next()){ // recorremos el resultset
               registros[0] = rs.getString("idServicio");
               registros[1] = rs.getString("nombre");
               registros[2] = rs.getString("costoServ");
               registros[3] = rs.getString("a.nombre");
               registros[4] = rs.getString("a.idArea");
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
    
    //Método para Guardar un Area
    public void Guardar(){
        sSQL = "insert into servicio(nombre,costoServ,idArea)"+"values(?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getNombre());
            st.setDouble(2, this.getCostoServ());
            st.setString(3, this.getIdArea());
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
        sSQL = "update servicio set nombre=?, costoServ=?, idArea=? where idServicio=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getNombre());
            st.setDouble(2, this.getCostoServ());
            st.setString(3, this.getIdArea());
            st.setInt(4, this.getIdServicio());
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
        sSQL = "delete from servicio where idServicio=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setInt(1, this.getIdServicio());
            
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
    public DefaultComboBoxModel obtenerServicio() {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        sSQL = "select * from servicio";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            combo.addElement("seleccione un servicio");
            while(rs.next()){
                combo.addElement(rs.getString("nombre"));
            }
            csta.close();
            rs.close();
            return combo;
        } catch (Exception e) {
            System.out.println("no se pudo CARGAR LOS DATOS PROVEEDOR...");
        }
        return combo;
    }
    
    public Double getCostoServicio(String nombre) {
        
        sSQL = "select costoServ from servicio where nombre='"+nombre+"';";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                costoServ = Double.parseDouble(rs.getObject(1).toString());
            }
        } catch (Exception e) {
            System.out.println("no encontro el costo");
        }
        return costoServ;
    }
    
    public int getPrimaryServicio() {
        
        sSQL = "select idServicio from servicio where nombre='"+nombre+"';";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                idServicio = Integer.parseInt(rs.getObject(1).toString());
            }
        } catch (Exception e) {
            System.out.println("no encontro la primary Key");
        }
        return idServicio;
    }
    
}
