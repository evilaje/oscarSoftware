package com.mycompany.oscarssoftware.util;

import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class Autocompletado {

    public Autocompletado() {
    }

    // Función para TextField
    public void configurarAutocompletado(TextField textField, List<String> items) {
        ContextMenu contextMenu = new ContextMenu();

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.isDisabled()) {  // Verifica si el TextField está deshabilitado
                return;  // No hacer nada si el TextField está deshabilitado
            }

            if (newValue.isEmpty()) {
                contextMenu.hide();
            } else {
                List<String> filteredItems = items.stream()
                        .filter(item -> item != null && item.toLowerCase().startsWith(newValue.toLowerCase()))
                        .collect(Collectors.toList());

                if (!filteredItems.isEmpty()) {
                    contextMenu.getItems().clear();
                    for (String suggestion : filteredItems) {
                        MenuItem item = new MenuItem(suggestion);
                        item.setOnAction(event -> {
                            textField.setText(suggestion);
                            contextMenu.hide();
                        });
                        contextMenu.getItems().add(item);
                    }

                    Point2D screenPosition = textField.localToScreen(0, textField.getHeight());
                    if (!contextMenu.isShowing()) {
                        contextMenu.show(textField, screenPosition.getX(), screenPosition.getY());
                    }
                } else {
                    contextMenu.hide();
                }
            }
        });

        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                contextMenu.hide();
            }
        });
    }

    // Función para ComboBox
    public <T> void configurarAutocompletadoComboBox(ComboBox<T> comboBox, List<T> items) {
        ContextMenu contextMenu = new ContextMenu();
        boolean[] ignoreAutocompletado = {false};  // Variable para controlar cuándo ignorar el autocompletado

        // Listener para cambios de texto en el editor del ComboBox
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (ignoreAutocompletado[0] || comboBox.isDisabled()) {  // Verifica si el ComboBox está deshabilitado
                return;  // No hacer nada si está deshabilitado o si se ignora el autocompletado
            }

            if (newValue.isEmpty()) {
                contextMenu.hide();
            } else {
                List<T> filteredItems = items.stream()
                        .filter(item -> item != null && item.toString().toLowerCase().startsWith(newValue.toLowerCase()))
                        .collect(Collectors.toList());

                if (!filteredItems.isEmpty()) {
                    contextMenu.getItems().clear();
                    for (T suggestion : filteredItems) {
                        MenuItem item = new MenuItem(suggestion.toString());
                        item.setOnAction(event -> {
                            comboBox.getEditor().setText(suggestion.toString());
                            comboBox.getSelectionModel().select(suggestion);
                            contextMenu.hide();
                        });
                        contextMenu.getItems().add(item);
                    }

                    Point2D screenPosition = comboBox.localToScreen(0, comboBox.getHeight());
                    if (!contextMenu.isShowing()) {
                        contextMenu.show(comboBox, screenPosition.getX(), screenPosition.getY());
                    }
                } else {
                    contextMenu.hide();
                }
            }
        });

        // Detectar cuándo se despliega el ComboBox para desactivar el autocompletado
        comboBox.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                ignoreAutocompletado[0] = true;  // Ignorar autocompletado cuando se despliega el ComboBox
            } else {
                ignoreAutocompletado[0] = false;  // Reactivar autocompletado cuando se cierra el ComboBox
            }
        });

        // Manejar la tecla ENTER y TAB para seleccionar el ítem y avanzar al siguiente campo
        comboBox.getEditor().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
                if (comboBox.getSelectionModel().getSelectedItem() != null) {
                    contextMenu.hide();
                    comboBox.getEditor().setText(comboBox.getSelectionModel().getSelectedItem().toString());
                }
            }
        });

        // Cerrar el menú contextual si el ComboBox pierde el foco
        comboBox.getEditor().focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                contextMenu.hide();
            }
        });
    }
}
