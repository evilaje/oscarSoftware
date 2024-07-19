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

public class DetallePedido extends conexion implements sentencias {
    private int idPedido;
    private int idProducto;
    private int cantidad;
    private String nombreProducto;
    private double precioUnit;
    private double precioTotal;

    public DetallePedido(int idPedido, int idProducto, int cantidad, String nombreProducto, double precioUnit, double precioTotal) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.precioUnit = precioUnit;
        this.precioTotal = precioTotal;
    }



    public DetallePedido() {
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public ArrayList consulta() {
        ArrayList<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT d.idPedido, d.idProducto, p.nombre AS nombreproducto, d.cantidad, d.cantidad * p.precio as total,"
                + "precio "
                + "FROM detalle_pedido d "
                + "JOIN producto p ON d.idProducto = p.idproducto "
                + "WHERE d.idPedido = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idPedido);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdPedido(rs.getInt("idPedido"));
                detalle.setIdProducto(rs.getInt("idProducto"));
                detalle.setNombreProducto(rs.getString("nombreproducto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioTotal(rs.getDouble("total"));
                detalle.setPrecioUnit(rs.getDouble("precio"));
                detalles.add(detalle);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(DetallePedido.class.getName()).log(Level.SEVERE, null, e);
        }
        return detalles;
                
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT into detalle_pedido values (?, ?, ?)";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idPedido);
            stm.setInt(2, this.idProducto);
            stm.setInt(3, this.cantidad);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetallePedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean borrar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}
