/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;
import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}