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
 * Clase que representa la categoría de un producto.
 */
public class CategoriaProducto extends conexion implements sentencias {
    
    //atributos
    private int idCategoria;
    private String nombreCategoria;
    
    //constructores
    public CategoriaProducto(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public CategoriaProducto() {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
    
    //getters y setters
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
    
    //sentencias
    
    //consulta
    @Override
    public ArrayList<CategoriaProducto> consulta() {
        //creamos el arraylist que servira para para almacenar todo lo que devuelva el select
        ArrayList<CategoriaProducto> categorias = new ArrayList<>();
        //preparamos el texto que servira de orden
        String sql = "SELECT * FROM categoria_producto"; //el texto basicamente dice:
        //devolver TODO(el asterisco significa todo) de la tabla...
        //preparamos el try
        try (
            Connection con = getCon();//establecemos la conexion
            Statement stm = con.createStatement();//preparamos la sentencia
            ResultSet rs = stm.executeQuery(sql)) {//a la sentencia le asignamos el texto
                while (rs.next()) { //mientras el resulset (la variable que trae los resultados) tenga algo que traer...
                    //capturamos los valores que nos trae el resultset
                    int idCategoria = rs.getInt("idCategoria");
                    String nombreCategoria = rs.getString("nombre_categoria");
                    //creamos un objeto categoriaproducto con los valores que nos trajeron
                    CategoriaProducto categoria = new CategoriaProducto(idCategoria, nombreCategoria);
                    //annadimos al arraylist el objeto creado
                    categorias.add(categoria);
                }
        } catch (SQLException e) {
            e.printStackTrace();//atrapamos cualquier error
        }
        return categorias;//devolvemos el arraylist
    }
    //sentencias que todavia no se usan
    @Override
    public boolean insertar() {
        String sql = "insert into categoria_producto (nombre_categoria) values (?)";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.nombreCategoria);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean borrar() {
        String sql = "delete from categoria_producto where idcategoria = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idCategoria);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean modificar() {
        String sql = "update categoria_producto set nombre_categoria = ? where idcategoria = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.nombreCategoria);
            stm.setInt(2, this.idCategoria);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}