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
public class Drecepcionmaterial {
    private int idRecepcion;
    private String material;
    private int nro_muestra;
    private String descripcion;
    private String fecha_llegada;
    private String fecha_salida;
    //llaves foraneas
    private int idUsuario;
    private int idSolicitud;
    private String ciCliente;
     //INSTANCIMOS LOS OBJETOS DE CONEXION
    private conexion cnx = new conexion(); // instanciamos un objeto de la clase conexion
    private Connection cn = cnx.conectar(); // nos conectamos a la base de datos sitecal
    PreparedStatement st; // declaramos una instancia de PrepareStatement para preparar la consulta y ejecutarla
    private String sSQL = ""; // creamos una variable para almacenar las consultas para las base datos
    
    
    public Drecepcionmaterial(){
    }

    public Drecepcionmaterial(int idRecepcion, String material, int nro_muestra, String descripcion, String fecha_llegada, String fecha_salida, int idUsuario, int idSolicitud) {
        this.idRecepcion = idRecepcion;
        this.material = material;
        this.nro_muestra = nro_muestra;
        this.descripcion = descripcion;
        this.fecha_llegada = fecha_llegada;
        this.fecha_salida = fecha_salida;
        this.idUsuario = idUsuario;
        this.idSolicitud = idSolicitud;
    }

    public int getIdRecepcion() {
        return idRecepcion;
    }

    public void setIdRecepcion(int idRecepcion) {
        this.idRecepcion = idRecepcion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getNro_muestra() {
        return nro_muestra;
    }

    public void setNro_muestra(int nro_muestra) {
        this.nro_muestra = nro_muestra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(String fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
    
    
    
    public DefaultTableModel mostrar(String buscar){
       DefaultTableModel modelo; // creamos un objeto DefaultTableModel
       String [] titulos = {"idRecepcion", "Material", "Nro_Muestra", "Descripcion", "Fecha_Llegada","Fecha_Salida","Usuario","idSolicitud","Cliente"};
       String [] registros = new String[9]; // Creamos un vector de String para almacenar los registros de la tabla Usuario
       modelo = new DefaultTableModel(null, titulos); // insertamos los titulos a la tabla del formualario
       sSQL = "select rm.idRecepcion, rm.material, rm.nro_muestra, rm.descripcion, rm.fechallegada, rm.fechaSalidad, us.nombre,rm.idSolicitud,cli.nombre from recepcionmaterial as rm,usuario as us,cliente as cli where us.nombre like'%"+buscar+
               "%' and rm.idUsuario = us.idUsuario and rm.ciCliente=cli.ciCliente order by idRecepcion desc";
       try {
           Statement csta = cn.createStatement();
           ResultSet rs = csta.executeQuery(sSQL);
           while(rs.next()){ // recorremos el resultset
               registros[0] = rs.getString("rm.idRecepcion");
               registros[1] = rs.getString("rm.material");
               registros[2] = rs.getString("rm.nro_muestra");
               registros[3] = rs.getString("rm.descripcion");
               registros[4] = rs.getString("rm.fechallegada");
               registros[5] = rs.getString("rm.fechaSalidad");
               registros[6] = rs.getString("us.nombre");
               registros[7] = rs.getString("rm.idSolicitud");
               registros[8] = rs.getString("cli.nombre");
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
        sSQL = "insert into recepcionmaterial(material,nro_muestra,descripcion,fechallegada,fechaSalidad,idUsuario,idSolicitud,ciCliente)"+"values(?,?,?,?,?,?,?,?)";
        try {
            st = cn.prepareStatement(sSQL);
            st.setString(1, this.getMaterial());
            st.setInt(2, this.getNro_muestra());
            st.setString(3, this.getDescripcion());
            st.setString(4, this.getFecha_llegada());
            st.setString(5, this.getFecha_salida());
            st.setInt(6, this.getIdUsuario());
            st.setInt(7, this.getIdSolicitud());
            st.setString(8, this.getCiCliente());
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
          
}
