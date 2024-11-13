package com.mycompany.oscarssoftware.util;

public class Modulo {
    private String nombreAmigable;
    private String archivoFxml;

    public Modulo(String nombreAmigable, String archivoFxml) {
        this.nombreAmigable = nombreAmigable;
        this.archivoFxml = archivoFxml;
    }

    public String getNombreAmigable() {
        return nombreAmigable;
    }

    public String getArchivoFxml() {
        return archivoFxml;
    }

    @Override
    public String toString() {
        return nombreAmigable; // Para que el autocompletado muestre solo el nombre amigable
    }
}
