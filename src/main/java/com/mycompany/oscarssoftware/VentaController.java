/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Venta;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TableView<DetallePedido> tablaDetalles;
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
    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtEmpleado;
    @FXML
    private TextField txtProducto;
    @FXML
    private TextField txtPrecioUnit;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtTotal2;
    @FXML
    private Button btnCancelarVenta;
    @FXML
    private Button btnGuardarVenta;
    private Cliente c = new Cliente();
    private Empleado e = new Empleado();
    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private TableColumn<Venta, Integer> colIdVenta;
    @FXML
    private TableColumn<Venta, String> colCliente;
    @FXML
    private TableColumn<Venta, String> colEmpleado;
    @FXML
    private TableColumn<Venta, Date> colFecha;
    @FXML
    private Button btnEliminarVenta;
    @FXML
    private Button btnModificarVenta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarVentas();
    }

    private void mostrarDatos(){
        int idPedido = obtenerPedido(cboPedidos.getSelectionModel().getSelectedItem());
        detalle.setIdPedido(idPedido);
        p.setIdpedido(idPedido);
        listaDetalles = FXCollections.observableArrayList(detalle.consulta());
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tablaDetalles.setItems(listaDetalles);
        txtTotal.setText(String.valueOf(p.consultaTotal()));
        txtTotal2.setText(String.valueOf(p.consultaTotal()));
        //obtener pedido
        Pedido pedidoSeleccionado = obtenerPedidoSeleccionado(idPedido);
        txtCliente.setText(pedidoSeleccionado.getNombreCliente());
        txtEmpleado.setText(pedidoSeleccionado.getNombreEmpleado());
    }

    // Método auxiliar para obtener el pedido seleccionado basado en el ID del pedido
    private Pedido obtenerPedidoSeleccionado(int idPedido) {
        ArrayList<Pedido> pedidos = p.consulta();
        for (Pedido pedido : pedidos) {
            if (pedido.getIdpedido() == idPedido) {
                return pedido;
            }
        }
        return null; // o manejar el caso cuando no se encuentre el pedido
    }
    @FXML
    private void nuevo(ActionEvent event) {
        btnNuevaVenta.setDisable(true);
        cboPedidos.setDisable(false);
        btnGuardarPedido.setDisable(false);
        btnCancelarVenta.setDisable(false);
        cargarComboPedido();

        
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
        btnCambiarPedido.setDisable(false);
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
    
    private int obtenerCliente (String nombreCliente) {
        ArrayList<Cliente> clientes = c.consulta();
        for (Cliente cl : clientes) {
            if (cl.getNombre().equals(nombreCliente)) return cl.getRuc();
        }
        return 0;
    }
    
    private int obtenerEmpleado (String nombreEmpleado) {
        ArrayList<Empleado> empleados = e.consulta();
        for (Empleado emp : empleados) {
            if (emp.getNombre().equals(nombreEmpleado)) return emp.getIdempleado();
        }
        
        return 0;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        //habilitar botones
        btnNuevaVenta.setDisable(false);
        btnCancelarVenta.setDisable(true);
        btnCambiarPedido.setDisable(true);
        cboPedidos.setDisable(false);
        cboPedidos.getSelectionModel().clearSelection();
        cboPedidos.getItems().clear();
        cboPedidos.setPromptText("Seleccionar pedido");
        cboPedidos.setDisable(true);
        tablaDetalles.getItems().clear();
        txtTotal.setDisable(true);
        txtTotal2.setDisable(true);
        btnGuardarPedido.setDisable(true);
    }

    @FXML
    private void guardarVenta(ActionEvent event) {
        int idpedido = obtenerPedido(cboPedidos.getSelectionModel().getSelectedItem());
        java.sql.Date fecha = java.sql.Date.valueOf(dateVenta.getValue());
        double total = Double.parseDouble(txtTotal.getText());
        int idcliente = obtenerCliente(txtCliente.getText());
        int idempleado = obtenerEmpleado(txtEmpleado.getText());
        v.setIdPedido(idpedido);
        v.setFecha_venta(fecha);
        v.setIdEmpleado(idempleado);
        v.setClienteRuc(idcliente);
        v.setTotal(total);
        if (v.insertar()) {
            mostrarAlerta(Alert.AlertType.CONFIRMATION, "Venta registrada con exito!");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "La venta no pudo ser registrada");
        }
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle("El sistema comunica");
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }
    
    private void mostrarVentas(){
        listaVentas = FXCollections.observableArrayList(v.consulta2());
        colIdVenta.setCellValueFactory(new PropertyValueFactory<>("idventa"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_venta"));
        tablaVentas.setItems(listaVentas);
    }
    
    
    
}
