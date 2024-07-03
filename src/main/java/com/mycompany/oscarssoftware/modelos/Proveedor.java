/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;

/**
 *
 * @author Anibal
 */
public class Proveedor {
    private int idproveedor;
    private String nombre;
    private String telfono;
    private String direccion;

    public Proveedor(int idproveedor, String nombre, String telfono, String direccion) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.telfono = telfono;
        this.direccion = direccion;
    }
    
    public Proveedor() {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.telfono = telfono;
        this.direccion = direccion;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelfono() {
        return telfono;
    }

    public void setTelfono(String telfono) {
        this.telfono = telfono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
