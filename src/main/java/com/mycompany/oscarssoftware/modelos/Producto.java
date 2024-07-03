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
public class Producto extends conexion implements sentencias{
    
    private int idproducto;
    private String nombre;
    private float precio;
    private int cantidad;
    private int idCategoriaProducto;
    private int idProveedor;


    public Producto() {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idCategoriaProducto = idCategoriaProducto;
        this.idProveedor = idProveedor;
    }
    
    

    public Producto(int idproducto, String nombre, float precio, int cantidad, int idCategoriaProducto, int idProveedor) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idCategoriaProducto = idCategoriaProducto;
        this.idProveedor = idProveedor;

    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int id_producto) {
        this.idproducto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }
    
    


    @Override
    public ArrayList consulta() {
       ArrayList <Producto> pdtos = new ArrayList<>();
       String sql = "SELECT * FROM producto";
       try (
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql)) {
                while (rs.next()) {
                    int cod = rs.getInt("idproducto");
                    String nombre = rs.getString("nombre");
                    float precio = rs.getFloat("precio");
                    int stock = rs.getInt("cantidad");
                    int codCat = rs.getInt("idCategoriaProducto");
                    int codProv = rs.getInt("idProveedor");
                    

                    
                    Producto producto = new Producto(cod, nombre, precio, stock, codCat, codProv);
                    pdtos.add(producto);    
                }
       }catch (SQLException e){
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
       
       return pdtos;
    }
    
    
}
