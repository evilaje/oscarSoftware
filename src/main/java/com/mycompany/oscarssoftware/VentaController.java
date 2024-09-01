/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Producto;
import com.mycompany.oscarssoftware.modelos.Venta;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class VentaController implements Initializable {

    @FXML
    private Button btnNuevaVenta;
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
    
    private Pedido p = new Pedido();
    private DetallePedido detalle = new DetallePedido();
    private Venta v = new Venta();
    private ObservableList<DetallePedido> listaDetalles;
    private ObservableList<Venta> listaVentas;
    private ObservableList<Pedido> listaPedidos;
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
    
    private Producto pr = new Producto();
    @FXML
    private TextField txtNroPedido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarVentas();
    }

    public void mostrarDatos(Pedido ped){
        detalle.setIdPedido(ped.getIdpedido());
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
        txtNroPedido.setText(String.valueOf(ped.getIdpedido()));
        txtPedidoSeleccionado.setText(ped.getNombreCliente());
        txtEmpleado.setText(ped.getNombreEmpleado());
        tablaDetalles.refresh();
        dateVenta.setValue(LocalDate.now());
        txtNroVenta.setText(String.valueOf(v.obtenerID() + 1));
    }

    // Método auxiliar para obtener el pedido seleccionado basado en el ID del pedido
    private Pedido obtenerPedidoSeleccionado(int idPedido) {
        ArrayList<Pedido> pedidos = (ArrayList<Pedido>) p.consulta();
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
        btnCancelarVenta.setDisable(false);
        buscarpedidos();

        
    }


    private void guardarPedido(ActionEvent event) {
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
        tablaDetalles.getItems().clear();
        txtTotal.setDisable(true);
        txtTotal2.setDisable(true);
    }

@FXML
private void guardarVenta(ActionEvent event) {
    try {
        // Validar y obtener los valores
        int idpedido = Integer.parseInt(txtNroPedido.getText());
        java.sql.Date fecha = java.sql.Date.valueOf(dateVenta.getValue());
        double total = Double.parseDouble(txtTotal.getText());
        int idcliente = obtenerCliente(txtPedidoSeleccionado.getText());
        int idempleado = obtenerEmpleado(txtEmpleado.getText());
        
        // Configurar la venta
        v.setIdPedido(idpedido);
        v.setFecha_venta(fecha);
        v.setIdEmpleado(idempleado);
        v.setClienteRuc(idcliente);
        v.setTotal(total);
        p.setIdpedido(idpedido);
        // Insertar la venta y procesar detalles
        if (v.insertar()) {
            if (p.modificarEstado()) {
                procesarDetallesPedido(detalle.consulta());
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "Venta registrada con éxito!");
                MenuController menuController = (MenuController) getMenuController();
                if (menuController != null) {
                    menuController.actualizarGanancias();
                }
            }

        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "La venta no pudo ser registrada.");
        }
        
    } catch (NumberFormatException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Formato de número inválido: " + e.getMessage());
    } catch (NullPointerException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Campo requerido vacío: " + e.getMessage());
    } catch (Exception e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Ocurrió un error al registrar la venta: " + e.getMessage());
    }
}

private Object getMenuController() {
    return null;
}
private void procesarDetallesPedido(ArrayList<DetallePedido> dp) throws Exception {
    for (DetallePedido d : dp) {
        Producto producto = pr.buscarPorId(d.getIdProducto());
        if (!producto.restarStock(d.getCantidad())) {
            mostrarAlerta(Alert.AlertType.ERROR, "Stock insuficiente para el producto: " + producto.getNombre());
            throw new Exception("Stock insuficiente.");
        }
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
    public void buscarpedidos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("buscarPedidos.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de búsqueda
            BuscarPedidosController buscarPedidosController = loader.getController();
            buscarPedidosController.setVentaController(this); // Pasar la referencia de VentaController

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hacer la ventana modal
            stage.showAndWait(); // Esperar hasta que se cierre la ventana

            // Después de que se cierre la ventana, actualizar el combo box
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    
}
