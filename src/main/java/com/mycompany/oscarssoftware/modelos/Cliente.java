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
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String ruc;

    public Cliente(int id, String nombre, String telefono, String direccion, String ruc) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ruc = ruc;
    }

    public Cliente() {
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String telefono = rs.getString("telefono");
                    String ruc = rs.getString("ruc"); 
                    String direccion = rs.getString("direccion");
                    Cliente cliente = new Cliente(id, nombre, telefono, direccion, ruc);
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
            
            stm.setString(1, this.ruc);
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
        String sql = "Delete from cliente where id = ?";
        try {
            Connection con = getCon(); 
            PreparedStatement stm = con.prepareStatement(sql); 
            stm.setInt(1, this.id); 
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
        String sql = "update cliente set ruc = ?, nombre = ?, telefono = ?, direccion = ? where (id = ?)" ;
        //abrimos el try para los errores que puedan haber
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.ruc);
            stm.setString(2, this.nombre);
            stm.setString(3, this.telefono);
            stm.setString(4, this.direccion);
            stm.setInt(5, this.id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }  
    
}
