package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.clases.conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion de cierre");
        alert.setHeaderText("¿Desea cerrar la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            //no hace nada
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        conexion conectar = new conexion();
        conectar.getCon();
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent the window from closing immediately
            showExitConfirmation(); // Show the exit confirmation dialog
        });
        stage.show();

    }



static void setRoot(String fxml, double width, double height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();

}

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class  

.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
