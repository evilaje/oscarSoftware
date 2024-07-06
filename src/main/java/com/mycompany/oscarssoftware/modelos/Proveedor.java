/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;

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
public class Proveedor extends conexion implements sentencias{
    private int idproveedor;
    private String nombre;
    private String telfono;
    private String direccion;

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

    @Override
    public ArrayList consulta() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * from proveedor";
        try {
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int idprov = rs.getInt("idproveedor");
                String nombre = rs.getString("nombre");
                String tel = rs.getString("telefono");
                String direcc = rs.getString("telefono");
                
                Proveedor prov = new Proveedor(idprov, nombre, tel, direcc);
                proveedores.add(prov);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return proveedores;
    }

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
