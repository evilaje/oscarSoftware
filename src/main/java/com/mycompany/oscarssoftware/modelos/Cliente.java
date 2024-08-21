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

/**
 *
 * @author Anibal
 */
public class Cliente extends conexion implements sentencias{
    private int ruc;
    private String nombre;
    private String telefono;
    private String direccion;

    public Cliente(String nombre, String telefono, int ruc, String direccion) {
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Cliente() {
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getRuc() {
        return ruc;
    }

    public void setRuc(int ruc) {
        this.ruc = ruc;
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
    @Override
    public ArrayList<Cliente> consulta() {
       ArrayList<Cliente> clien = new ArrayList<>();
       String sql = "SELECT * from cliente ";     
       try (
            Connection con = getCon(); 
            Statement stm = con.createStatement(); 
            ResultSet rs = stm.executeQuery(sql)) { 
                while (rs.next()) { 
                    String nombre = rs.getString("nombre");
                    String telefono = rs.getString("telefono");
                    int ruc = rs.getInt("ruc"); 
                    String direccion = rs.getString("direccion");
                    Cliente cliente = new Cliente(nombre, telefono, ruc, direccion);
                    clien.add(cliente);    
                }
       } catch (SQLException c) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, c);
        }
       return clien;
    }

    @Override
    public boolean insertar() {
       
        String sql = "insert into cliente (ruc, nombre, telefono, direccion ) values (?, ?, ?, ?)"; 
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setInt(1, this.ruc);
            stm.setString(2, this.nombre);
            stm.setString(3, this.telefono);
            stm.setString(4, this.direccion);
           
            stm.executeUpdate();
            
            return true;
        } catch (SQLException ex) { 
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
           
            return false;
        }
    }

    @Override
    public boolean borrar() {
        String sql = "Delete from cliente where ruc  = ?";
        try {
            Connection con = getCon(); 
            PreparedStatement stm = con.prepareStatement(sql); 
            stm.setInt(1, this.ruc); 
            stm.executeUpdate();
            return true;
        } catch (SQLException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean modificar() {
         //preparamos el texto que servira de orden sql
        String sql = "update cliente set nombre = ?, telefono = ?, direccion = ? where ruc = ?" ;
        //abrimos el try para los errores que puedan haber
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.nombre);
            stm.setString(2, this.telefono);
            stm.setString(3, this.direccion);
            stm.setInt(4, this.ruc);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }  
    
}
