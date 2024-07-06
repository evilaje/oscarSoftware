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


/**
 * Clase que representa la categoría de un producto.
 */
public class CategoriaProducto extends conexion implements sentencias {
    
    private int idCategoria;
    private String nombreCategoria;

    public CategoriaProducto(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public CategoriaProducto() {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
    
    

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    @Override
    public ArrayList<CategoriaProducto> consulta() {
        ArrayList<CategoriaProducto> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_producto";
        try (
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql)) {
                while (rs.next()) {
                    int idCategoria = rs.getInt("idCategoria");
                    String nombreCategoria = rs.getString("nombre_categoria");
                    CategoriaProducto categoria = new CategoriaProducto(idCategoria, nombreCategoria);
                    categorias.add(categoria);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
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