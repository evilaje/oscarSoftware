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
public class Pedido extends conexion implements sentencias {
    private int idpedido;
    private Date fecha_pedido;
    private int idEmpleado;
    private int idCliente;
    private String nombreCliente;
    private String nombreEmpleado;

    public Pedido(int idpedido, Date fecha_pedido, int idEmpleado, int idCliente, String nombreCliente, String nombreEmpleado) {
        this.idpedido = idpedido;
        this.fecha_pedido = fecha_pedido;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
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

    public Pedido() {
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setCliente_ruc(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public ArrayList consulta() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "select p.idpedido, p.idcliente, c.nombre AS nombrecliente, p.idempleado, e.nombre AS "
                + "nombreempleado , p.fecha_pedido "
                + "FROM pedido p "
                + "JOIN cliente c ON p.idcliente = c.ruc "
                + "JOIN empleado e ON p.idempleado = e.idempleado";
        try {
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("idpedido");
                int idcliente = rs.getInt("idcliente");
                int idempleado = rs.getInt("idempleado");
                Date fecha = rs.getDate("fecha_pedido");
                String cliente = rs.getString("nombrecliente");
                String empleado = rs.getString("nombreempleado");
                        
                Pedido p = new Pedido(id, fecha, idempleado, idcliente, cliente, empleado);
                pedidos.add(p);
            }
        
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
        }
        return pedidos;
        
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT into pedido (idempleado, idcliente, fecha_pedido) VALUES (?, ?, ?)";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idEmpleado);
            stm.setInt(2, this.idCliente);
            stm.setDate(3, this.fecha_pedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean borrar() {
        String sql = "DELETE from pedido where idpedido = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idpedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE pedido SET idcliente = ?, idempleado = ?, fecha_pedido = ? where idpedido = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idCliente);
            stm.setInt(2, this.idEmpleado);
            stm.setDate(3, this.fecha_pedido);
            stm.setInt(4, this.idpedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
}
