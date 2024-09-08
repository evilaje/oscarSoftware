/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.jfoenix.controls.JFXComboBox;
import com.mycompany.oscarssoftware.clases.Reporte;
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
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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

    private MenuController menuController;
    @FXML
    private JFXComboBox<String> cboPago;

    // Método para pasar la referencia del MenuController
    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarVentas();
        cboPago.getItems().add("Contado");
        cboPago.getItems().add("Credito");  
                tablaVentas.setRowFactory(tv -> {
            TableRow<Venta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    generarReporte(null, row.getItem().getIdventa());
                }
            });
            return row;
        });

    }

    public void mostrarDatos(Pedido ped) {
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
        int t = (int) ped.consultaTotal();
        txtTotal2.setText(String.valueOf(t));
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
        cboPago.setDisable(false);
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
            if (pedido.getNombreCliente().equals(nombreClientePedido)) {
                return pedido.getIdpedido();
            }
        }
        return 0;
    }

    private int obtenerCliente(String nombreCliente) {
        ArrayList<Cliente> clientes = c.consulta();
        for (Cliente cl : clientes) {
            if (cl.getNombre().equals(nombreCliente)) {
                return cl.getId();
            }
        }
        return 0;
    }

    private int obtenerEmpleado(String nombreEmpleado) {
        ArrayList<Empleado> empleados = e.consulta();
        for (Empleado emp : empleados) {
            if (emp.getNombre().equals(nombreEmpleado)) {
                return emp.getIdempleado();
            }
        }

        return 0;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cboPago.setDisable(true);
        //habilitar botones
        btnNuevaVenta.setDisable(false);
        btnCancelarVenta.setDisable(true);
        tablaDetalles.getItems().clear();
        txtTotal.setDisable(true);
        txtTotal2.setDisable(true);
        //limpiar campos
        dateVenta.setValue(null);
        txtNroVenta.clear();
        txtCantidad.clear();
        txtEmpleado.clear();
        txtNroPedido.clear();
        txtNroVenta.clear();
        txtPedidoSeleccionado.clear();
        txtPrecioUnit.clear();
        txtProducto.clear();
        txtTotal.clear();
        txtTotal2.clear();
    }

    @FXML
    private void guardarVenta(ActionEvent event) {
        try {
            // Validar el número de pedido
            String nroPedidoText = txtNroPedido.getText();
            if (nroPedidoText == null || nroPedidoText.trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "El número de pedido es obligatorio.");
                return;
            }
            int idpedido = Integer.parseInt(nroPedidoText);

            // Validar la fecha de venta
            if (dateVenta.getValue() == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "La fecha de venta es obligatoria.");
                return;
            }
            java.sql.Date fecha = java.sql.Date.valueOf(dateVenta.getValue());

            // Validar el total
            String totalText = txtTotal.getText();
            if (totalText == null || totalText.trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "El total es obligatorio.");
                return;
            }
            double total;
            try {
                total = Double.parseDouble(totalText);
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Formato de total inválido: " + e.getMessage());
                return;
            }

            // Validar el cliente
            String clienteText = txtPedidoSeleccionado.getText();
            if (clienteText == null || clienteText.trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "El cliente es obligatorio.");
                return;
            }
            int idcliente = obtenerCliente(clienteText);

            // Validar el empleado
            String empleadoText = txtEmpleado.getText();
            if (empleadoText == null || empleadoText.trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "El empleado es obligatorio.");
                return;
            }
            int idempleado = obtenerEmpleado(empleadoText);

            // Validar el método de pago
            String pago = cboPago.getSelectionModel().getSelectedItem();
            if (pago == null || pago.trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "El método de pago es obligatorio.");
                return;
            }

            // Configurar la venta
            v.setIdPedido(idpedido);
            v.setFecha_venta(fecha);
            v.setIdEmpleado(idempleado);
            v.setClienteRuc(idcliente);
            v.setTotal(total);
            p.setIdpedido(idpedido);
            v.setMetodoPago(pago);
            v.setTotal(total);
            

            // Insertar la venta y procesar detalles
            if (v.insertar()) {
                if (p.modificarEstado()) {
                    procesarDetallesPedido(detalle.consulta());
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "¡Venta registrada con éxito!");
                    cancelar(event);
                    cboPago.getSelectionModel().clearSelection();
                    cboPago.setDisable(true);

                    if (menuController != null) {
                        menuController.actualizarGanancias();
                        generarReporte(event, v.obtenerID());
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "No se pudo modificar el estado del pedido.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "La venta no pudo ser registrada.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Ocurrió un error al registrar la venta: " + e.getMessage());
        }
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

    private void mostrarVentas() {
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

    @FXML
    private void mostrarProducto(MouseEvent event) {
        DetallePedido d = tablaDetalles.getSelectionModel().getSelectedItem();
        if (d != null) {
            txtCantidad.setText(String.valueOf(d.getCantidad()));
            txtPrecioUnit.setText(String.valueOf(d.getPrecioUnit()));
            txtProducto.setText(d.getNombreProducto());
            txtTotal.setText(String.valueOf(d.getPrecioTotal()));
        }
    }

    @FXML
    private void generarReporte(ActionEvent event, int id) {
        Reporte r = new Reporte();
        String ubi = "/reportes/facturaF1.jasper";
        String tit = "Informe de pedido";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idventa", id);

        // Ruta de la imagen
        String rutaImagen = "/images/logo_factura.png"; // Cambia la ruta según sea necesario

        r.generarReporteConImagen(ubi, tit, parameters, rutaImagen, id);

        // Puedes agregar un mensaje o notificación aquí para informar al usuario que el reporte se generó
    }

}
