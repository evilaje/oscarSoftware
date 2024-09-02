/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;
import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Proveedor extends conexion implements sentencias{
    private int idproveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    
    public Proveedor(int idproveedor, String nombre, String telefono, String direccion) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    public Proveedor() {
    }
    
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        ArrayList<Proveedor> prove = new ArrayList<>();
        String sql = "SELECT * from proveedor";
        try {
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int idprov = rs.getInt("idproveedor");
                String nombre = rs.getString("nombre");
                String tel = rs.getString("telefono");
                String direcc = rs.getString("direccion");
                
                
                Proveedor proveedor = new Proveedor(idprov, nombre, tel, direcc);
                prove.add(proveedor);
            }
        } catch (SQLException p) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, p);
        } 
        return prove;
    }
    @Override
    public boolean insertar() {
        String sql = "insert into proveedor (nombre, telefono, direccion, email) values (?, ?, ?, ?)"; 
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setString(1, this.nombre);
            stm.setString(2, this.telefono);
            stm.setString(3, this.direccion);
            stm.setString(4, "evilaje@gmail.com");
           
            stm.executeUpdate();
            
            return true;
        } catch (SQLException ex) { 
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
           
            return false;
       
    }}

    @Override
    public boolean borrar() {
        String sql = "Delete from proveedor where idproveedor  = ?";
        try {
            Connection con = getCon(); 
            PreparedStatement stm = con.prepareStatement(sql); 
            stm.setInt(1, this.idproveedor); 
            stm.executeUpdate();
            return true; 
        } catch (SQLException ex){
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean modificar() {
        String sql = "update proveedor set nombre = ?, telefono = ?, direccion = ? where idproveedor = ?" ;
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.nombre);
            stm.setString(2, this.telefono);
            stm.setString(3, this.direccion);
            stm.setInt(4, this.idproveedor);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    } 


    }

  

    
    

