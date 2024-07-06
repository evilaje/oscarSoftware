/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;
//imports
import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
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
    //atributos de la tabla
    private int idproducto;
    private String nombre;
    private float precio;
    private int cantidad;
    private int idCategoriaProducto;
    private int idProveedor;
    //atributos que queremos y no son de la tabla como tal
    private String nombreCategoria;
    private String nombreProveedor;

    //constructor vacio
    public Producto() {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idCategoriaProducto = idCategoriaProducto;
        this.idProveedor = idProveedor;
        this.nombreCategoria = nombreCategoria;
        this.nombreProveedor = nombreProveedor;
    }
    
    
    //constructor que recibe los datos
    public Producto(int idproducto, String nombre, float precio, int cantidad, int idCategoriaProducto, int idProveedor, String nombreCategoria, String nombreProveedor) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idCategoriaProducto = idCategoriaProducto;
        this.idProveedor = idProveedor;
        this.nombreCategoria = nombreCategoria;
        this.nombreProveedor = nombreProveedor;

    }
    //zona de getters y setters
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
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
    
    
//funcion de consulta, retorna todos los elementos
    @Override
    public ArrayList<Producto> consulta() {
       ArrayList<Producto> pdtos = new ArrayList<>();//creamos el arraylist que vamos a retornar
       String sql = "SELECT p.idproducto, p.nombre, p.precio, p.cantidad, p.idCategoriaProducto,"
               + " c.nombre_categoria AS nombreCategoria, p.idProveedor, pr.nombre AS nombreprov"
               + " FROM producto p "
               + "JOIN categoria_producto c ON p.idCategoriaProducto = c.idCategoria "
               + "JOIN proveedor pr ON p.idProveedor = pr.idproveedor";
       //el texto sql basicamente pide:
       //del producto como tal, el nombre, precio, cantidad, idCategoria, y el idproveedor de la tabla producto que llamamos "p"
       //luego, annade otras dos tablas, las que llamamos categoria "categoria" y proveedor "pr"
       //de estas tablas nos interesa el nombre de cada una, asi que pedimos y "renombramos" (la parte donde dice
       //... talcosa AS talcosa) la tabla para que sea mas comodo
       //y condicionamos para que nos traiga los nombres de los productos con el mismo id, para que 
       //por cad producto nos traiga el nombre deseado y solo ese
       //"intentamos" con el try toda la conexion
       try (
            Connection con = getCon(); // establecemos la conexion a la base de datos
            Statement stm = con.createStatement(); //creamos una "orden"
            ResultSet rs = stm.executeQuery(sql)) { //en el resulset le cargamos la orden sql que hicimos y la ejecutamos
                while (rs.next()) { //mientras que el resultset siga teniendo datos que entregar, se repite este proceso
                    int cod = rs.getInt("idproducto"); //obtenemos el codigo de la tabla idproducto
                    String nombre = rs.getString("nombre");//obtenemos el nombre de la tabla nombre
                    float precio = rs.getFloat("precio");//lomismo
                    int cantidad = rs.getInt("cantidad");//lomismo
                    int codCat = rs.getInt("idCategoriaProducto");//lomismo
                    int codProv = rs.getInt("idProveedor");//lomismo
                    String nmCategoria = rs.getString("nombreCategoria");//obtenemos el nombre de la categoria de la tabla que renombramos en la orden ahi arriba
                    String nmProveedor = rs.getString("nombreprov");//obtenemos el nombre del proveedor de la tabla que renombramos ahi arriba
                    //creamos un objeto producto con todos los datos que rcolectamos
                    Producto producto = new Producto(cod, nombre, precio, cantidad, codCat, codProv, nmCategoria, nmProveedor);
                    //annadimos al arraylist el objeto que acabamos de crear
                    pdtos.add(producto);    
                }
       } catch (SQLException e) { //en caso de que la conexion falle, nos dara el mensaje pero no se muestra al usuario
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
//finalmente, retornamos el arraylist
       return pdtos;
    }


    @Override
    public boolean insertar() {
        String sql = "insert into producto values (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = getCon();
            PreparedStatement stm;
            stm = con.prepareStatement(sql);
            stm.setInt(1, this.idproducto);
            stm.setFloat(2, this.precio);
            stm.setString(3, this.nombre);
            stm.setInt(4, this.cantidad);
            stm.setInt(5, this.idProveedor);
            stm.setInt(6, this.idCategoriaProducto);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        
    }

    @Override
    public boolean borrar() {
        String sql = "Delete from producto where idproducto = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idproducto);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex){
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean modificiar() {
        String sql = "update producto set nombre = ?, cantidad = ?, precio = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.nombre);
            stm.setInt(2, this.cantidad);
            stm.setFloat(3, precio);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
    }
    
    
}
