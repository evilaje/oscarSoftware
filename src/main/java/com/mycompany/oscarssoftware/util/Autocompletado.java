/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oscarssoftware.util;


import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 *
 * @author arace
 */
public class Autocompletado {

    public Autocompletado() {
    }
    
    
    
    public void configurarAutocompletado(TextField textField, List<String> items) {
        ContextMenu contextMenu = new ContextMenu();
        
        // Listener para el cambio de texto
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                contextMenu.hide();
            } else {
                List<String> filteredItems = items.stream()
                        .filter(item -> item.toLowerCase().startsWith(newValue.toLowerCase()))
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

                    // Calcula la posición del TextField en la pantalla y muestra el ContextMenu allí
                    Point2D screenPosition = textField.localToScreen(0, textField.getHeight());
                    if (!contextMenu.isShowing()) {
                        contextMenu.show(textField, screenPosition.getX(), screenPosition.getY());
                    }
                } else {
                    contextMenu.hide();
                }
            }
        });

        // Cerrar el menú contextual si se pierde el foco
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                contextMenu.hide();
            }
        });
    }
}