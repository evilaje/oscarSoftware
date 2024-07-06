/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.modelos;

/**
 *
 * @author Anibal
 */
public class Empleado {
    private int idempleado;
    private String nombre;
    private String telefono;
    private String direccion;

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
    
    
    
    
}
