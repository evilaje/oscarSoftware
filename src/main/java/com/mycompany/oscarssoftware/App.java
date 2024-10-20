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
        if (conectar != null) {
            conectar.getCon();
            scene = new Scene(loadFXML("login"), 788, 449);
            stage.setScene(scene);
            //    stage.setResizable(false);
            //    
            //    Ajusta el tamaño de la ventana al contenido
            //    stage.sizeToScene(); 

            stage.setOnCloseRequest(event -> {
                event.consume(); // Consume the event to prevent the window from closing immediately
                showExitConfirmation(); // Show the exit confirmation dialog
            });
            stage.show();
        } else {
                    //mensaje de error en caso de que no funcione
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al conectarse a la base de datos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, revise la base de datos!");
            alerta.show();
        }

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
