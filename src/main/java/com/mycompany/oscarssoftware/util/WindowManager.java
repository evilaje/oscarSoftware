package com.mycompany.oscarssoftware.util;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class WindowManager {
    private static final Map<String, Stage> ventanas = new HashMap<>();

    public static void registrarVentana(String titulo, Stage stage) {
        ventanas.put(titulo, stage);
    }

    public static void cerrarVentana(String titulo) {
        Stage stage = ventanas.remove(titulo);
        if (stage != null) {
            stage.close();
        }
    }

    public static Stage obtenerVentana(String titulo) {
        return ventanas.get(titulo);
    }

    public static boolean estaVentanaEstaAbierta(String titulo) {
        return ventanas.containsKey(titulo);
    }
}
