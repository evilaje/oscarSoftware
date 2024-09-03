package com.mycompany.oscarssoftware.util;

import com.mycompany.oscarssoftware.modelos.Empleado;

public class EmpleadoSingleton {
    private static EmpleadoSingleton instance;
    private Empleado empleado;

    private EmpleadoSingleton() {
    }

    public static EmpleadoSingleton getInstance() {
        if (instance == null) {
            instance = new EmpleadoSingleton();
        }
        return instance;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
