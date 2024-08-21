/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.clases.Reporte;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController implements Initializable {
    private static Scene scene;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    }

    public void abrirFxml(String fxml, String titulo) {    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirProductos(ActionEvent event) {
        abrirFxml("producto.fxml", "Formulario de productos");
    }

    @FXML
    private void abrirCategorias(ActionEvent event) {
        abrirFxml("categoria.fxml", "Formulario de empleados");
    }

    @FXML
    private void abrirPedidos(ActionEvent event) {
        abrirFxml("pedido.fxml", "Formulario de pedidos");
    }

    @FXML
    private void abrirClientes(ActionEvent event) {
        abrirFxml("cliente.fxml", "Formulario de clientes");
    }

    @FXML
    private void abrirEmpleados(ActionEvent event) {
        abrirFxml("empleado.fxml", "Formulario de empleados");
    }

    @FXML
    private void abrirVentas(ActionEvent event) {
        abrirFxml("venta.fxml", "Formulario venta");
    }

    @FXML
    private void reporteClientes(ActionEvent event) {
        Reporte r = new Reporte();
        String ubi = "/reportes/clienteF1.jasper";
        String tit = "Informe de cliente";
        r.generarReporte(ubi, tit);
    }

    @FXML
    private void abrirInicio(ActionEvent event) {
        abrirFxml("login.fxml", "loging");
    }
    
    
}
