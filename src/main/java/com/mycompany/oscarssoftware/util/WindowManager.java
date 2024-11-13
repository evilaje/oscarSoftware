package com.mycompany.oscarssoftware.util;

import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class WindowManager {

    // Mapa para almacenar las ventanas abiertas
    private static final Map<String, Stage> ventanasAbiertas = new HashMap<>();

    // Registrar una ventana nueva
    public static void registrarVentana(String modulo, Stage stage) {
        ventanasAbiertas.put(modulo, stage);
    }

    // Obtener una ventana si está abierta
    public static Stage obtenerVentana(String modulo) {
        return ventanasAbiertas.get(modulo);
    }

    // Cerrar y eliminar una ventana del registro
    public static void cerrarVentana(String modulo) {
        Stage stage = ventanasAbiertas.remove(modulo);
        if (stage != null) {
            stage.close();
        }
    }

    // Verificar si una ventana ya está abierta
    public static boolean estaVentanaAbierta(String modulo) {
        return ventanasAbiertas.containsKey(modulo);
    }
}
