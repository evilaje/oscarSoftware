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
    private int stock;
    private CategoriaProducto categoriaProducto;


    public Producto() {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoriaProducto = categoriaProducto;
    }
    
    

    public Producto(int idproducto, String nombre, float precio, int stock, CategoriaProducto categoriaProducto) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoriaProducto = categoriaProducto;

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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
    
    


    @Override
    public ArrayList consulta() {
       ArrayList <Producto> pdtos = new ArrayList<>();
       String sql = "SELECT p.idproducto, p.nombre, p.precio, p.stock, c.idCategoria, c.nombreCategoria " +
                "FROM producto p, categoriaproducto c where 1";
       try (
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql)) {
                while (rs.next()) {
                    int cod = rs.getInt("idproducto");
                    String nombre = rs.getString("nombre");
                    float precio = rs.getFloat("precio");
                    int stock = rs.getInt("stock");
                    int codCat = rs.getInt("idcategoria");
                    String nomCat = rs.getString("nombrecategoria");
                    

                    CategoriaProducto catProd = new CategoriaProducto(codCat, nomCat);
                    Producto producto = new Producto(cod, nombre, precio, stock, catProd);
                    pdtos.add(producto);
                }
       }catch (SQLException e){
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
       
       return pdtos;
    }
    
    
}
