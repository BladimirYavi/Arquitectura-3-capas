/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author blyq_
 */
public class conexion {
    // creando el constructor de la clase conexion
    public conexion(){
    }
    
    // Creando el método para conectar a la base de datos sitecal
    public Connection conectar(){
        Connection link = null;
        try{
            Class.forName("com.mysql.jdbc.Driver"); // cargamos el driver de la conexion
            link = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sitecal","root",""); // creamos un enlace a la BD
            
        }catch (ClassNotFoundException | SQLException e){
            JOptionPane.showConfirmDialog(null, e);
        }
        return link;
    }
}
