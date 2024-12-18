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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anibal
 */
public class Producto extends conexion implements sentencias {

    //atributos de la tabla
    private int idproducto;
    private String nombre;
    private double precio;
    private int cantidad;
    private int idCategoriaProducto;
    private int idProveedor;
    //atributos que queremos y no son de la tabla como tal
    private String nombreCategoria;
    private String nombreProveedor;
    private double costo;

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
        this.costo = costo;

    }

    public Producto(int idproducto, String nombre, int cantidad, int idCategoriaProducto, int idProveedor, double precio) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.idCategoriaProducto = idCategoriaProducto;
        this.idProveedor = idProveedor;
        this.precio = precio;
    }

    //constructor que recibe los datos
    public Producto(int idproducto, String nombre, double precio, int cantidad, int idCategoriaProducto, int idProveedor, String nombreCategoria, String nombreProveedor, double costo) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idCategoriaProducto = idCategoriaProducto;
        this.idProveedor = idProveedor;
        this.nombreCategoria = nombreCategoria;
        this.nombreProveedor = nombreProveedor;
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
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

    public double getPrecio() {
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

    //sentencias
//funcion de consulta, retorna todos los elementos
    @Override
    public ArrayList<Producto> consulta() {
        ArrayList<Producto> pdtos = new ArrayList<>();//creamos el arraylist que vamos a retornar
        String sql = "SELECT p.idproducto, p.nombre, p.precio, p.cantidad, p.idCategoriaProducto,"
                + " c.nombre_categoria AS nombreCategoria, p.idProveedor, pr.nombre AS nombreprov, p.costo"
                + " FROM producto p "
                + "JOIN categoria_producto c ON p.idCategoriaProducto = c.idCategoria "
                + "JOIN proveedor pr ON p.idProveedor = pr.idproveedor "
                + "ORDER BY p.idproducto";
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
                String nmProveedor = rs.getString("nombreprov");
                double costo = rs.getDouble("costo");

                //obtenemos el nombre del proveedor de la tabla que renombramos ahi arriba
                //creamos un objeto producto con todos los datos que rcolectamos
                Producto producto = new Producto(cod, nombre, precio, cantidad, codCat, codProv, nmCategoria, nmProveedor, costo);
                //annadimos al arraylist el objeto que acabamos de crear
                pdtos.add(producto);
            }
        } catch (SQLException e) { //en caso de que la conexion falle, nos dara el mensaje pero no se muestra al usuario
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
//finalmente, retornamos el arraylist
        return pdtos;
    }

    //funcion de insertar
    @Override
    public boolean insertar() {
        //creamos el texto que luego usaremos como orden sql
        String sql = "insert into producto (precio, nombre, cantidad, idProveedor, idCategoriaProducto, costo) values (?, ?, ?, ?, ?, ?)"; //en los ??? luego iran los valores correspondientes
        //luego abrimos un try para las conexiones y prever posibles errores
        try {
            Connection con = getCon();//creamos la conexion
            PreparedStatement stm = con.prepareStatement(sql);;//creamos un objeto que sirve como orden
            //a la orden le asignamos el texto sql...

            //por cada ? obtenemos el objeto determinado y le ponemos en el lugar que corresponda
            stm.setDouble(1, this.precio);//
            stm.setString(2, this.nombre);
            stm.setInt(3, this.cantidad);
            stm.setInt(4, this.idProveedor);
            stm.setInt(5, this.idCategoriaProducto);
            stm.setDouble(6, this.costo);
            //una vez obtenidos todos los valores ejecutamos la orden
            stm.executeUpdate();
            // devolvemos verdadero para que sepamos que la insercion se realizo con exito
            return true;
        } catch (SQLException ex) { //en caso de que ocurra un error lo atrapamos y lo mostramos unicamente para los programadores
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            //devolvemos falso para saber que ocurrio un error y que no se inserto nada
            return false;
        }
    }

    //funcion para borrar
    @Override
    public boolean borrar() {
        //preparamos la orden para borrar todos los datos del producto, o sea
        //el producto en si.
        String sql = "Delete from producto where idproducto = ?";
        //el try para la conexion
        try {
            Connection con = getCon(); //preparamos el camino
            PreparedStatement stm = con.prepareStatement(sql); //preparamos la orden
            stm.setInt(1, this.idproducto); //asignamos el id del producto que queremos borrar
            stm.executeUpdate();//ejecutamos la orden
            return true; //devolvemos Verdadero si la orden se ejecuto con exito
        } catch (SQLException ex) {//atrapamos cualquier error que pueda haber
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;//devolvemos falso si la orden fallo y para saber que no se ejecuto
        }
    }

    //funcion para modificar determinado producto
    @Override
    public boolean modificar() {
        //preparamos el texto que servira de orden sql
        String sql = "update producto set nombre = ?, cantidad = ?, precio = ?, idProveedor = ?, idCategoriaProducto = ?, costo = ? where idProducto = ?";
        //abrimos el try para los errores que puedan haber
        try {
            Connection con = getCon();//preparamos el camino
            PreparedStatement stm = con.prepareStatement(sql);//preparamos la orden
            //seteamos los datos en cada ? segun correspondan
            stm.setString(1, this.nombre);
            stm.setInt(2, this.cantidad);
            stm.setDouble(3, this.precio);
            stm.setInt(4, this.idProveedor);
            stm.setInt(5, this.idCategoriaProducto);
            stm.setInt(7, this.idproducto);
            stm.setDouble(6, this.costo);
            //ejecutamos la orden
            stm.executeUpdate();
            return true;// devolvemos verdadero en caso de exito
        } catch (SQLException ex) { //en caso de error lo atrapamos y lo identificamos
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            //devolvemos falso para saber que no se pudo realizar nada
            return false;
        }

    }

    public Producto buscarPorId(int id) {
        String sql = "SELECT * FROM producto WHERE idproducto = ?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int idProducto = rs.getInt("idproducto");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int cantidad = rs.getInt("cantidad");
                    int idCategoriaProducto = rs.getInt("idCategoriaProducto");
                    int idProveedor = rs.getInt("idProveedor");

                    // Assuming you have a way to fetch category and provider names
                    return new Producto(idProducto, nombre, cantidad, idCategoriaProducto, idProveedor, precio);
                }
            }
        } catch (SQLException e) {
            // Consider rethrowing or handling the exception based on your application's needs
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public boolean restarStock(int cantidad) {
        // Primero, verifica si hay suficiente stock antes de intentar restar
        if (this.cantidad < cantidad) {
            // Si no hay suficiente stock, regresa false inmediatamente
            return false;
        }

        String sql = "UPDATE producto SET cantidad = cantidad - ? WHERE idproducto = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, cantidad);
            stm.setInt(2, this.idproducto);

            int rowsAffected = stm.executeUpdate();

            // Verifica si la actualización afectó alguna fila
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int cantidadPorProducto(int idproducto) {
        String sql = "SELECT cantidad from producto where idproducto = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idproducto);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("cantidad");
            }

        } catch (SQLException e) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public ArrayList<Producto> consultaProductosDisponibles() {
        ArrayList<Producto> pdtos = new ArrayList<>();//creamos el arraylist que vamos a retornar
        String sql = "SELECT p.idproducto, p.nombre, p.precio, p.cantidad, p.idCategoriaProducto,"
                + " c.nombre_categoria AS nombreCategoria, p.idProveedor, pr.nombre AS nombreprov, p.costo as costo "
                + " FROM producto p "
                + "JOIN categoria_producto c ON p.idCategoriaProducto = c.idCategoria "
                + "JOIN proveedor pr ON p.idProveedor = pr.idproveedor "
                + "WHERE cantidad > 0 "
                + "ORDER BY p.idproducto";

        try (
                Connection con = getCon(); 
                 Statement stm = con.createStatement(); 
                 ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) { 
                int cod = rs.getInt("idproducto"); 
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                int cantidad = rs.getInt("cantidad");
                int codCat = rs.getInt("idCategoriaProducto");
                int codProv = rs.getInt("idProveedor");
                String nmCategoria = rs.getString("nombreCategoria");
                String nmProveedor = rs.getString("nombreprov");
                double costo = rs.getDouble("costo");
                
                //creamos un objeto producto con todos los datos que rcolectamos
                Producto producto = new Producto(cod, nombre, precio, cantidad, codCat, codProv, nmCategoria, nmProveedor, costo);
                //annadimos al arraylist el objeto que acabamos de crear
                pdtos.add(producto);
            }
        } catch (SQLException e) { //en caso de que la conexion falle, nos dara el mensaje pero no se muestra al usuario
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, e);
        }
//finalmente, retornamos el arraylist
        return pdtos;
    }
    
    public boolean existeProveedor (int idProveedor) {
            String sql = "SELECT  * from producto where idProveedor = ? limit 1";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idProveedor);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    
    

}
