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
       ArrayList<Cliente> clien = new ArrayList<>();//creamos el arraylist que vamos a retornar
       String sql = "SELECT * from cliente ";     
       try (
            Connection con = getCon(); // establecemos la conexion a la base de datos
            Statement stm = con.createStatement(); //creamos una "orden"
            ResultSet rs = stm.executeQuery(sql)) { //en el resulset le cargamos la orden sql que hicimos y la ejecutamos
                while (rs.next()) { //mientras que el resultset siga teniendo datos que entregar, se repite este proceso
                    String nombre = rs.getString("nombre");//obtenemos el nombre de la tabla nombre
                    String telefono = rs.getString("telefono");//lomismo
                    int ruc = rs.getInt("ruc"); //obtenemos el codigo de la tabla idproducto
                    String direccion = rs.getString("direccion");//lomismo
                    //creamos un objeto producto con todos los datos que rcolectamos
                    Cliente cliente = new Cliente(nombre, telefono, ruc, direccion);
                    //annadimos al arraylist el objeto que acabamos de crear
                    clien.add(cliente);    
                }
       } catch (SQLException c) { //en caso de que la conexion falle, nos dara el mensaje pero no se muestra al usuario
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, c);
        }
//finalmente, retornamos el arraylist
       return clien;
    }

    @Override
    public boolean insertar() {
       
        String sql = "insert into cliente values (?, ?, ?, ?)"; 
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
            PreparedStatement stm = con.prepareStatement(sql); //preparamos la orden
            stm.setInt(1, this.ruc); //asignamos el id del producto que queremos borrar
            stm.executeUpdate();//ejecutamos la orden
            return true; //devolvemos Verdadero si la orden se ejecuto con exito
        } catch (SQLException ex){//atrapamos cualquier error que pueda haber
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;//devolvemos falso si la orden fallo y para saber que no se ejecuto
        }
    }

    @Override
    public boolean modificar() {
         //preparamos el texto que servira de orden sql
        String sql = "update cliente set nombre = ?, telefono = ?, direccion = ? where ruc = ?" ;
        //abrimos el try para los errores que puedan haber
        try {
            Connection con = getCon();//preparamos el camino
            PreparedStatement stm = con.prepareStatement(sql);//preparamos la orden
            //seteamos los datos en cada ? segun correspondan
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
