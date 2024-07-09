/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;
//imports
import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anibal
 */
//creamos la clase
public class Proveedor extends conexion implements sentencias{
    //atributos
    private int idproveedor;
    private String nombre;
    private String telfono;
    private String direccion;

    //constructores
    public Proveedor(int idproveedor, String nombre, String telfono, String direccion) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.telfono = telfono;
        this.direccion = direccion;
    }
    
    public Proveedor() {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.telfono = telfono;
        this.direccion = direccion;
    }
    
    //getters y setters
    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelfono() {
        return telfono;
    }

    public void setTelfono(String telfono) {
        this.telfono = telfono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    //sentencias

    @Override
    public ArrayList consulta() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();//creamos el arraylist que vamos a retornar
        String sql = "SELECT * from proveedor";//preparamos el texto para la orden
        try {
            Connection con = getCon();//preparamos la conexion
            Statement stm = con.createStatement();//preparamos la orden
            ResultSet rs = stm.executeQuery(sql);//ejecutamos la consulta
            while (rs.next()) {//mientras tengamos datos...
                //capturamos los datos que nos traen
                int idprov = rs.getInt("idproveedor");
                String nombre = rs.getString("nombre");
                String tel = rs.getString("telefono");
                String direcc = rs.getString("telefono");
                
                //creamos objetos con los datos recolectados
                Proveedor prov = new Proveedor(idprov, nombre, tel, direcc);
                proveedores.add(prov);//annadimos al arraylist el objeto q acabamos de crear
            }
        } catch (SQLException ex) {//enc caso de erro atrapamos el error y lo identificamos
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //devolvemos la lista
        return proveedores;
    }

    //sentencias en desuso
    @Override
    public boolean insertar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean borrar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificiar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
}
