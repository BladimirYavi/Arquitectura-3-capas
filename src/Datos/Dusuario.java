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
public class Dusuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String telefono;
    private String tipoUsuario;
    private String idArea;
    
    //INSTANCIMOS LOS OBJETOS DE CONEXION
    private conexion cnx = new conexion(); // instanciamos un objeto de la clase conexion
    private Connection cn = cnx.conectar(); // nos conectamos a la base de datos sitecal
    PreparedStatement st; // declaramos una instancia de PrepareStatement para preparar la consulta y ejecutarla
    private String sSQL = ""; // creamos una variable para almacenar las consultas para las base datos
    
    

    // Creando los Métodos Constructores por defecto
    public Dusuario(){
        
    }
    
    //Constructor parametrizado
    public Dusuario(int idUsuario, String nombre, String email, String telefono, String tipoUsuario){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;               
    }
    
    //Creamos los Getters y Setters

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
       String [] titulos = {"idUsuario", "Nombre", "Email", "Telefono", "TipoUsuario","Area","idArea"};
       String [] registros = new String[7]; // Creamos un vector de String para almacenar los registros de la tabla Usuario
       modelo = new DefaultTableModel(null, titulos); // insertamos los titulos a la tabla del formualario
       sSQL = "select u.idUsuario,u.nombre,u.email,u.telefono,u.tipoUsuario,a.nombre,a.idArea from usuario as u,area as a where u.idArea = a.idArea order by idUsuario desc";
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
               registros[0] = rs.getString("idUsuario");
               registros[1] = rs.getString("nombre");
               registros[2] = rs.getString("email");
               registros[3] = rs.getString("telefono");
               registros[4] = rs.getString("tipoUsuario");
               registros[5] = rs.getString("a.nombre");
               registros[6] = rs.getString("a.idArea");
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
        sSQL = "insert into usuario(idUsuario,nombre,email,telefono,tipoUsuario,idArea)"+"values(?,?,?,?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setInt(1, this.getIdUsuario());
            st.setString(2, this.getNombre());
            st.setString(3, this.getEmail());
            st.setString(4, this.getTelefono());
            st.setString(5, this.getTipoUsuario());
            st.setString(6, this.getIdArea());
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
        sSQL = "update usuario set nombre=?, email=?, telefono=?, tipoUsuario=?, idArea=? where idUsuario=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getNombre());
            st.setString(2, this.getEmail());
            st.setString(3, this.getTelefono());
            st.setString(4, this.getTipoUsuario());
            st.setString(5, this.getIdArea());
            st.setInt(6, this.getIdUsuario());
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
        sSQL = "delete from usuario where idUsuario=?";
        try {
            st = cn.prepareStatement(sSQL);
            st.setInt(1, this.getIdUsuario());
            
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

    public DefaultComboBoxModel obtenerUsuario() {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        sSQL = "select * from usuario";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            combo.addElement("seleccione un Usuario");
            while(rs.next()){
                combo.addElement(rs.getString("nombre"));
            }
            csta.close();
            rs.close();
            return combo;
        } catch (Exception e) {
            System.out.println("no se pudo CARGAR LOS DATOS");
        }
        return combo;
    }
    
    public int getPrimaryUsuario() {
        
        sSQL = "select idUsuario from usuario where nombre='"+nombre+"';";
        try {
            Statement csta = cn.createStatement();
            ResultSet rs = csta.executeQuery(sSQL);
            while (rs.next()) {
                idUsuario = Integer.parseInt(rs.getObject(1).toString());
            }
        } catch (Exception e) {
            System.out.println("no encontro la primary Key");
        }
        return idUsuario;
    }
}
