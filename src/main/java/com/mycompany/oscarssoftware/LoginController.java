/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Empleado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Button btnRegistro;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContra;
    @FXML
    private Button btnCancelar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void ingresar(ActionEvent event) {
        
    }

    @FXML
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

    @FXML
    private void cancelar(ActionEvent event) {
    }
    
    
}
