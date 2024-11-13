package com.mycompany.oscarssoftware.util;

import com.mycompany.oscarssoftware.ViajeRapidoController;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class AtajosTecladoUtil {

    public static void inicializarAtajos(Scene escena, Stage ventanaActual) {
        KeyCombination viajeRapidoShortcut = new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN);

        // Abrir la ventana de Viaje Rápido al presionar Ctrl + K
        escena.getAccelerators().put(viajeRapidoShortcut, () -> {
            ViajeRapidoController.abrirVentanaRapida(ventanaActual);
        });
    }
}
