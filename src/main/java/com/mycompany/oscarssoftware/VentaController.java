/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Venta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class VentaController implements Initializable {

    @FXML
    private Button btnNuevaVenta;
    @FXML
    private ComboBox<?> cboPedidos;
    @FXML
    private TextField txtNroVenta;
    @FXML
    private DatePicker dateVenta;
    @FXML
    private TextField txtPedidoSeleccionado;
    @FXML
    private TableView<Venta> tablaVenta;
    @FXML
    private TableColumn<Venta, Integer> columnId;
    @FXML
    private TableColumn<Venta, String> columnDesc;
    @FXML
    private TableColumn<Venta, Integer> columnCantidad;
    @FXML
    private TableColumn<Venta, Double> columnPrecio;
    @FXML
    private TableColumn<Venta, Double> columnTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void nuevo(ActionEvent event) {
    }
    
}
