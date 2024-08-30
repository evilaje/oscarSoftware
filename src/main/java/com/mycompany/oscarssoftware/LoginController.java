/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.modelos.Empleado;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Orne
 */
public class LoginController implements Initializable {
    private Empleado e = new Empleado();
  
    @FXML
    private Button btnIngresar;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContra;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

@FXML
private void ingresar(ActionEvent event) {
    String nombreUsuario = txtUsuario.getText();
    String cedula = txtContra.getText();

    if (nombreUsuario.isEmpty() || cedula.isEmpty()) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe completar todos los campos.");
        return;
    }

    if (e.ingresar(nombreUsuario, Integer.parseInt(cedula)) != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();

            // Obtener la escena y el escenario actuales
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root); // Crear una nueva escena

            // Ajustar tamaño del stage
            stage.setScene(newScene);
            stage.sizeToScene();  // Reajustar el tamaño de la ventana
            stage.centerOnScreen(); // Centrar la ventana en la pantalla

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Usuario o Contraseña Incorrectas");
    }
}


    private void registro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("empleado.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      public void mostrarAlerta (Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }


}
