/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;

import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
import java.sql.Connection;
import java.sql.Date;
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
public class Venta extends conexion implements sentencias {
    private int idventa;
    private Date fecha_venta;
    private double total;
    private int idPedido;
    private int clienteRuc;
    private int idEmpleado;
    private String nombreCliente;
    private String nombreEmpleado;

    public Venta(int idventa, Date fecha_venta, double total, int idPedido, int clienteRuc, int idEmpleado, String nombreCliente, String nombreEmpleado) {
        this.idventa = idventa;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.idPedido = idPedido;
        this.clienteRuc = clienteRuc;
        this.idEmpleado = idEmpleado;
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    
    public Venta() {
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public Date getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Date fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getClienteRuc() {
        return clienteRuc;
    }

    public void setClienteRuc(int clienteRuc) {
        this.clienteRuc = clienteRuc;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public ArrayList consulta() {
        ArrayList<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.idventa, v.fecha_venta, v.total, v.idPedido, v.clienteRuc as ruc, "
                + "c.nombre as nombrecliente, v.idEmpleado, e.nombre as nombreempleado "
                + "FROM venta v "
                + "JOIN cliente c ON v.clienteRuc = c.ruc "
                + "JOIN empleado e ON e.idEmpleado = e.idempleado";
        try {
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int idventa = rs.getInt("idventa");
                Date fecha = rs.getDate("fecha_vente");
                double total = rs.getDouble("total");
                int idpedido = rs.getInt("idPedido");
                int idcliente = rs.getInt("ruc");
                String nombreCliente = rs.getString("nombrecliente");
                int idempleado = rs.getInt("idEmpleado");
                String nombreempleado = rs.getString("nombreempleado");
                Venta v = new Venta(idventa, fecha, total, idpedido, idcliente, idempleado, nombreCliente, nombreempleado);
                ventas.add(v);
            }
        } catch (SQLException e) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, e);
        }
        return ventas;
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT INTO venta (fecha_venta, total, idPedido, clienteRuc, idEmpleado) values (?, ?, ?, ?, ?)";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDate(1, this.fecha_venta);
            stm.setDouble(2, this.total);
            stm.setInt(3, this.idPedido);
            stm.setInt(4, this.clienteRuc);
            stm.setInt(5, this.idEmpleado);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean borrar() {
        String sql = "DELETE from venta where idventa = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idventa);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE venta SET fecha_date = ?, total = ?, idPedido = ?, clienteRuc = ?, idEmpleado = ? where idventa = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDate(1, this.fecha_venta);
            stm.setDouble(2, this.total);
            stm.setInt(3, this.idPedido);
            stm.setInt(4, this.clienteRuc);
            stm.setInt(5, this.idEmpleado);
            stm.setInt(6, this.idventa);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    
    
    
    
    
}
