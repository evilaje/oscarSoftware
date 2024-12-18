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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anibal
 */
public class Empleado extends conexion implements sentencias {

    private int idempleado;
    private String nombre;
    private String telefono;
    private String direccion;
    private String cedula;
    private String password;

    public Empleado(int idempleado, String nombre, String telefono, String direccion, String cedula, String password) {
        this.idempleado = idempleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula = cedula;
        this.password = password;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Empleado() {
        this.idempleado = idempleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Empleado(int idempleado, String nombre, String telefono, String direccion) {
        this.idempleado = idempleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
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

    //funcion de consulta, retorna todos los elementos
    @Override
    public ArrayList<Empleado> consulta() {
        ArrayList<Empleado> emple = new ArrayList<>();//creamos el arraylist que vamos a retornar
        String sql = "SELECT * from empleado ";
        try (
                Connection con = getCon(); // establecemos la conexion a la base de datos
                 Statement stm = con.createStatement(); //creamos una "orden"
                 ResultSet rs = stm.executeQuery(sql)) { //en el resulset le cargamos la orden sql que hicimos y la ejecutamos
            while (rs.next()) { //mientras que el resultset siga teniendo datos que entregar, se repite este proceso
                int id = rs.getInt("idEmpleado"); //obtenemos el codigo de la tabla idproducto
                String nombre = rs.getString("nombre");//obtenemos el nombre de la tabla nombre
                String telefono = rs.getString("telefono");//lomismo
                String direccion = rs.getString("direccion");//lomismo
                String ced = rs.getString("cedula");
                //creamos un objeto producto con todos los datos que rcolectamos
                Empleado empleado = new Empleado(id, nombre, telefono, direccion, ced, ced);
                //annadimos al arraylist el objeto que acabamos de crear
                emple.add(empleado);
            }
        } catch (SQLException e) { //en caso de que la conexion falle, nos dara el mensaje pero no se muestra al usuario
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, e);
        }
//finalmente, retornamos el arraylist
        return emple;
    }

    @Override
    public boolean insertar() {
        //creamos el texto que luego usaremos como orden sql
        String sql = "insert into empleado (nombre, telefono, direccion, cedula, password) values (?, ?, ?, ?, ?)";
        try {
            Connection con = getCon();//creamos la conexion
            PreparedStatement stm = con.prepareStatement(sql);//creamos un objeto que sirve como orden
            //a la orden le asignamos el texto sql...

            //por cada ? obtenemos el objeto determinado y le ponemos en el lugar que corresponda
            stm.setString(1, this.nombre);
            stm.setString(2, this.telefono);//
            stm.setString(3, this.direccion);
            stm.setString(4, this.cedula);
            stm.setString(5, this.password);
            //una vez obtenidos todos los valores ejecutamos la orden
            stm.executeUpdate();
            // devolvemos verdadero para que sepamos que la insercion se realizo con exito
            return true;
        } catch (SQLException ex) { //en caso de que ocurra un error lo atrapamos y lo mostramos unicamente para los programadores
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            //devolvemos falso para saber que ocurrio un error y que no se inserto nada
            return false;
        }
    }

    @Override
    public boolean borrar() {
        //preparamos la orden para borrar todos los datos del producto, o sea
        //el producto en si.
        String sql = "Delete from empleado where idempleado  = ?";
        //el try para la conexion
        try {
            Connection con = getCon(); //preparamos el camino
            PreparedStatement stm = con.prepareStatement(sql); //preparamos la orden
            stm.setInt(1, this.idempleado); //asignamos el id del producto que queremos borrar
            stm.executeUpdate();//ejecutamos la orden
            return true; //devolvemos Verdadero si la orden se ejecuto con exito
        } catch (SQLException ex) {//atrapamos cualquier error que pueda haber
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;//devolvemos falso si la orden fallo y para saber que no se ejecuto
        }
    }

    @Override
    public boolean modificar() {
        //preparamos el texto que servira de orden sql
        String sql = "update empleado set nombre = ?, telefono = ?, direccion = ?, cedula = ?, password = ? where idempleado = ?";
        //abrimos el try para los errores que puedan haber
        try {
            Connection con = getCon();//preparamos el camino
            PreparedStatement stm = con.prepareStatement(sql);//preparamos la orden
            //seteamos los datos en cada ? segun correspondan
            stm.setString(1, this.nombre);
            stm.setString(2, this.telefono);
            stm.setString(3, this.direccion);
            stm.setString(4, this.cedula);
            stm.setString(5, this.cedula);
            stm.setInt(6, this.idempleado);
            //ejecutamos la orden
            stm.executeUpdate();
            return true;// devolvemos verdadero en caso de exito
        } catch (SQLException ex) { //en caso de error lo atrapamos y lo identificamos
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            //devolvemos falso para saber que no se pudo realizar nada
            return false;
        }
    }

    public Empleado ingresar(String nombre, String pass) {
        String sql = "Select * from empleado where nombre = ? and cedula = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, pass);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idEmpleado"); //obtenemos el codigo de la tabla idproducto
                String nm = rs.getString("nombre");//obtenemos el nombre de la tabla nombre
                String telefono = rs.getString("telefono");//lomismo
                String direccion = rs.getString("direccion");//lomismo

                Empleado empleado = new Empleado(id, nombre, telefono, direccion);
                return empleado;

            }
        } catch (SQLException e) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return null;
    }

    public List<String> obtenerNombres() {
        String sql = "SELECT nombre from empleado";
        List<String> nombres = new ArrayList<>();
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }
            return nombres;
        } catch  (SQLException e) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        

    }

}
