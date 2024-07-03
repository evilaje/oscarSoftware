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
public class Pedido {
    private int idpedido;
    private Date fecha_pedido;
    private int idEmpleado;
    private int cliente_ruc;

    public Pedido(int idpedido, Date fecha_pedido, int idEmpleado, int cliente_ruc) {
        this.idpedido = idpedido;
        this.fecha_pedido = fecha_pedido;
        this.idEmpleado = idEmpleado;
        this.cliente_ruc = cliente_ruc;
    }

    public Pedido() {
        this.idpedido = idpedido;
        this.fecha_pedido = fecha_pedido;
        this.idEmpleado = idEmpleado;
        this.cliente_ruc = cliente_ruc;
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

    public int getCliente_ruc() {
        return cliente_ruc;
    }

    public void setCliente_ruc(int cliente_ruc) {
        this.cliente_ruc = cliente_ruc;
    }
    
    
    
    
}
