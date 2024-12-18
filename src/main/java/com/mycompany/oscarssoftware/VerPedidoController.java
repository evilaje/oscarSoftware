/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.util.AtajosTecladoUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class VerPedidoController implements Initializable {

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private TableColumn<Pedido, Integer> colId;
    @FXML
    private TableColumn<Pedido, String> colCliente;
    @FXML
    private TableColumn<Pedido, Date> colFecha;
    @FXML
    private TableColumn<Pedido, String> colEmpleado;
    //listas
    ObservableList<Pedido> listaPedidos;
    //variables
    Pedido p = new Pedido();
    private ObservableList<Pedido> pedidosFiltrados;

    @FXML
    private TextField txtSearch;
    @FXML
    private Pane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        tablaPedidos.setRowFactory(tv -> {
            TableRow<Pedido> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Pedido pedidoSeleccionado = row.getItem();
                    cambiarASceneVerDetalles(pedidoSeleccionado);
                }
            });
            return row;
        });
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                Stage ventanaActual = (Stage) root.getScene().getWindow(); // Obtener la ventana actual
                AtajosTecladoUtil.inicializarAtajos(newScene, ventanaActual); // Pasar la ventana actual
            }
        });
    }

    public void mostrarDatos() {
        listaPedidos = FXCollections.observableList(p.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("idpedido"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
        tablaPedidos.setItems(listaPedidos);

    }

    private void cambiarASceneVerDetalles(Pedido p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("verDetallesDelPedido.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena y pasarle el ID del pedido
            VerDetallesDelPedidoController controller = loader.getController();
            controller.inicializarConDatos(p.getIdpedido(), p.getNombreCliente(), p.getNombreEmpleado(), p.getFecha_pedido());

            // Cambiar la escena en la misma ventana
            Stage stage = (Stage) tablaPedidos.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.sizeToScene();

        } catch (IOException ex) {
            Logger.getLogger(VerPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void search(ActionEvent event) {
        pedidosFiltrados = FXCollections.observableArrayList();
        String buscar = txtSearch.getText();

        if (buscar.isEmpty()) {
            mostrarDatos();
        } else {
            pedidosFiltrados.clear();
            for (Pedido pedido : listaPedidos) {
                if (pedido.getNombreCliente().toLowerCase().contains(buscar.toLowerCase())) {
                    pedidosFiltrados.add(pedido);
                }
            }
            tablaPedidos.setItems(pedidosFiltrados);
        }
    }

}
