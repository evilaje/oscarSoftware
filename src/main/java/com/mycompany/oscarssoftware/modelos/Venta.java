/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;

import java.sql.Date;

/**
 *
 * @author Anibal
 */
public class Venta {
    private int idventa;
    private Date fecha_venta;
    private double total;
    private int idPedido;
    private int clienteRuc;
    private int idEmpleado;

    public Venta(int idventa, Date fecha_venta, double total, int idPedido, int clienteRuc, int idEmpleado) {
        this.idventa = idventa;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.idPedido = idPedido;
        this.clienteRuc = clienteRuc;
        this.idEmpleado = idEmpleado;
    }

    public Venta() {
        this.idventa = idventa;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.idPedido = idPedido;
        this.clienteRuc = clienteRuc;
        this.idEmpleado = idEmpleado;
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
    
    
    
    
    
    
}
