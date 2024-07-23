/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Venta;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class VentaController implements Initializable {

    @FXML
    private Button btnNuevaVenta;
    @FXML
    private ComboBox<String> cboPedidos;
    @FXML
    private TextField txtNroVenta;
    @FXML
    private DatePicker dateVenta;
    @FXML
    private TextField txtPedidoSeleccionado;
    @FXML
    private TableView<DetallePedido> tablaVenta;
    @FXML
    private TableColumn<DetallePedido, Integer> columnId;
    @FXML
    private TableColumn<DetallePedido, String> columnDesc;
    @FXML
    private TableColumn<DetallePedido, Integer> columnCantidad;
    @FXML
    private TableColumn<DetallePedido, Double> columnPrecio;
    @FXML
    private TableColumn<DetallePedido, Double> columnTotal;
    @FXML
    private Button btnCambiarPedido;
    
    private Pedido p = new Pedido();
    private DetallePedido detalle = new DetallePedido();
    private Venta v = new Venta();
    private ObservableList<DetallePedido> listaDetalles;
    private ObservableList<Venta> listaVentas;
    private ObservableList<Pedido> listaPedidos;
    @FXML
    private Button btnGuardarPedido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboPedido();
    }

    private void mostrarDatos(){
        int idPedido = obtenerPedido(cboPedidos.getSelectionModel().getSelectedItem());
        detalle.setIdPedido(idPedido);
        listaDetalles = FXCollections.observableArrayList(detalle.consulta());
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tablaVenta.setItems(listaDetalles);
    }

    @FXML
    private void nuevo(ActionEvent event) {
        btnNuevaVenta.setDisable(true);
        cboPedidos.setDisable(false);
        btnGuardarPedido.setDisable(false);
        
    }

    @FXML
    private void cambiar(ActionEvent event) {
    }
    
    private void cargarComboPedido(){
        cboPedidos.getItems().clear();
        listaPedidos = FXCollections.observableArrayList(p.consulta());
        if (listaPedidos != null && !listaPedidos.isEmpty()) {
            for (Pedido p : listaPedidos) {
                cboPedidos.getItems().add("Pedido de " + p.getNombreCliente());
            }
        }
        cboPedidos.setPromptText("Seleccionar pedido");
    }

    @FXML
    private void guardarPedido(ActionEvent event) {
        cboPedidos.setDisable(true);
        mostrarDatos();
        btnGuardarPedido.setDisable(true);
    }
    
    private int obtenerPedido(String nombreClientePedido) {
        if (nombreClientePedido.startsWith("Pedido de ")) {
            nombreClientePedido = nombreClientePedido.substring(10);
        }
        ArrayList<Pedido> listaPedidos = p.consulta();
        
        for (Pedido pedido : listaPedidos) {
            if (pedido.getNombreCliente().equals(nombreClientePedido)) return pedido.getIdpedido();
        }
        return 0;
    }
    
    
    
}
