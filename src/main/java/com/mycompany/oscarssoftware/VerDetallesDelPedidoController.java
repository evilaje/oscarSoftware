/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.clases.Reporte;
import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Pedido;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class VerDetallesDelPedidoController implements Initializable {

    @FXML
    private Label txtIdPedido;
    @FXML
    private Label txtNombreCliente;
    @FXML
    private Label txtNombreEmpleado;
    @FXML
    private Label txtFecha;
    @FXML
    private TableView<DetallePedido> tablaDetalles;
    @FXML
    private TableColumn<DetallePedido, String> colProducto;
    @FXML
    private TableColumn<DetallePedido, Double> colPrecioUnit;
    @FXML
    private TableColumn<DetallePedido, Integer> colCantidad;
    @FXML
    private TableColumn<DetallePedido, Double> colTotal;
    //listas
    ObservableList<DetallePedido> listaDetalles;
    //variables
    Pedido p = new Pedido();
    DetallePedido d = new DetallePedido();
    int idpedido;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnReporte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarConDatos(int idPedido, String cliente, String empleado, Date fecha) {
        d.setIdPedido(idPedido);
        txtIdPedido.setText(String.valueOf(idPedido));
        txtNombreCliente.setText(cliente);
        txtNombreEmpleado.setText(empleado);
        txtFecha.setText(String.valueOf(fecha));
        mostrarDatos();
    }
    
    private void cargarDetallesPedido(int idPedido ) {
        
    }
    
    public void mostrarDatos(){
        listaDetalles = FXCollections.observableList(d.consulta());
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaDetalles.setItems(listaDetalles);
        
    }
    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("verPedido.fxml"));
            Parent root = loader.load();

            // Cambiar la escena en la misma ventana
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.sizeToScene();

        } catch (IOException ex) {
            Logger.getLogger(VerPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        Reporte r = new Reporte();
        String ubi = "/reportes/pedidoF.jasper";
        String tit = "Informe de pedido";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idpedido", d.getIdPedido());    
        r.generarReporteParametros(ubi, tit, parameters);
    }

    
}
