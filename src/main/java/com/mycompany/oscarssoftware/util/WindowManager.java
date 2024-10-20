package com.mycompany.oscarssoftware.util;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public class WindowManager {
    private static final Map<String, Stage> ventanasAbiertas = new HashMap<>();
    
    public static void registrarVentana(String modulo, Stage stage) {
        ventanasAbiertas.put(modulo, stage);
    }

    public static Stage obtenerVentana(String modulo) {
        return ventanasAbiertas.get(modulo);
    }

    public static void cerrarVentana(String modulo) {
        Stage stage = ventanasAbiertas.remove(modulo);
        if (stage != null) {
            stage.close();
        }
    }

    public static boolean estaVentanaAbierta(String modulo) {
        return ventanasAbiertas.containsKey(modulo);
    }
}
