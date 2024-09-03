/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.jfoenix.controls.JFXButton;
import com.mycompany.oscarssoftware.clases.Reporte;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Venta;
import com.mycompany.oscarssoftware.util.EmpleadoSingleton;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    private static Scene scene;
    @FXML
    private HBox root;
    @FXML
    private AnchorPane side_ankerpane;
    @FXML
    private JFXButton btnuno;
    @FXML
    private JFXButton btnuno1;
    @FXML
    private JFXButton btnuno2;
    @FXML
    private JFXButton btnuno3;
    @FXML
    private JFXButton btnuno4;
    @FXML
    private JFXButton btnuno5;
    @FXML
    private Pane pane_12;
    @FXML
    private Label lblGananciasTotales;
    private double gananciasTotales = 0.00;
    private Pedido p = new Pedido();
    private Venta v = new Venta();
    @FXML
    private Pane pane_1211;
    @FXML
    private Label lblPedidos;
    @FXML
    private Pane pane_12111;
    @FXML
    private Pane pane_121;
    @FXML
    private Button btnVerPedido;
    @FXML
    private Label txtEmpleado;
    @FXML
    private Label lblVentas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtEmpleado.setText(EmpleadoSingleton.getInstance().getEmpleado().getNombre());
        actualizarGanancias();

    }

    public void actualizarGanancias() {
        gananciasTotales = p.ingresosTotales();
        lblVentas.setText(String.valueOf(v.ventasRegistradas()));
        lblPedidos.setText(String.valueOf(p.pedidosPendientes()));
        lblGananciasTotales.setText(String.format("%.2f", gananciasTotales));
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

    private void abrirInicio(ActionEvent event) {
        abrirFxml("login.fxml", "login");

    }

    private void abrirVerPedidos(ActionEvent event) {
        abrirFxml("verPedido.fxml", "Lista de pedidos");
    }

    @FXML
    private void pedido(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pedido.fxml"));
            Parent root = loader.load();
            PedidoController pedidoController = loader.getController();
            pedidoController.setMenuController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void venta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("venta.fxml"));
            Parent root = loader.load();

            VentaController ventaController = loader.getController();
            ventaController.setMenuController(this); // Pasar la referencia del MenuController

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void producto(ActionEvent event) {
        abrirFxml("Producto.fxml", "Formulario Producto");
    }

    @FXML
    private void clientes(ActionEvent event) {
        abrirFxml("cliente.fxml", "Formulario Cliente");
    }

    @FXML
    private void proveedor(ActionEvent event) {
        abrirFxml("proveedor.fxml", "Formulario Proveedor");
    }

    @FXML
    private void empleado(ActionEvent event) {
        abrirFxml("empleado.fxml", "Formulario Empleado");
    }

    private void switchToMenu() throws IOException {
        App.setRoot("menu", 757, 513);

    }

    @FXML
    private void verPedidos(ActionEvent event) throws IOException {
        abrirFxml("verPedido.fxml", "Ver pedidos disponibles");
    }
}
