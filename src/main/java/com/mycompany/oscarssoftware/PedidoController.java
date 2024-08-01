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
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class PedidoController implements Initializable {
    
    private Cliente c = new Cliente();
    private Empleado e = new Empleado();
    private Producto pr = new Producto();
    private Pedido p = new Pedido();
    private DetallePedido d = new DetallePedido();

    @FXML
    private ComboBox<String> comboCliente;
    @FXML
    private ComboBox<String> comboEmpleado;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnNuevoEmpleado;
    @FXML
    private Button btnFechaHoy;
    @FXML
    private Button btnNuevoPedido;
    @FXML
    private Button btnEliminarPedido;
    @FXML
    private Button btnModificarPedido;
    @FXML
    private Button btnCancelarPedido;
    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private TableColumn<Pedido, Integer> colNroPedido;
    @FXML
    private TableColumn<Pedido, String> colClientes;
    @FXML
    private TableColumn<Pedido, Date> colFechas;
    @FXML
    private TableView<DetallePedido> tablaDetalles;
    @FXML
    private TableColumn<DetallePedido, Integer> colIdPedido;
    @FXML
    private TableColumn<DetallePedido, String> colProducto;
    @FXML
    private TableColumn<DetallePedido, Integer> colCantidad;
    @FXML
    private TableColumn<DetallePedido, Double> colPrecioUnit;
    @FXML
    private TableColumn<DetallePedido, Double> colTotal;
    @FXML
    private ComboBox<String> comboProductos;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnCancelarDetalle;
    @FXML
    private Button btnEliminarDetalle;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnEditarCantidad;
    @FXML
    private Label labNroSeleccionado;
    @FXML
    private Button btnGuardarPedido;
    @FXML
    private Button btnGuardarDetalle;
    @FXML
    private Button btnNuevoDetalle;
    ArrayList<DetallePedido> detalles = new ArrayList<>();
    //listas
    private ObservableList<Cliente> listaClientes;
    private ObservableList<Pedido> listaPedidos;
    private ObservableList<Empleado> listaEmpleados;
    private ObservableList<Producto> listaProductos;
    private ObservableList<DetallePedido> listaDetalles = FXCollections.observableArrayList();
       
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboClientes();
        cargarComboEmpleados();
        cargarComboProductos();
        mostrarDatos();
    }    



    @FXML
    private void nuevoPedido(ActionEvent event) {
        comboCliente.setDisable(false);
        dateFecha.setDisable(false);
        comboEmpleado.setDisable(false);
        btnNuevoDetalle.setDisable(false);
        btnFechaHoy.setDisable(false);
        btnNuevoCliente.setDisable(false);
        btnNuevoEmpleado.setDisable(false);
        btnGuardarDetalle.setDisable(false);
    }

    @FXML
    private void nuevoDetallePedido(ActionEvent event) {
        txtCantidad.setDisable(false);
        comboProductos.setDisable(false);
        btnGuardarDetalle.setDisable(false);
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }
    
    @FXML
    private void cancelarDetalle(ActionEvent event) {
    }

    @FXML
    private void eliminarDetalle(ActionEvent event) {
    }

    @FXML
    private void editarDetalle(ActionEvent event) {
    }

    
    @FXML
    private void abrirVentanaCliente(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaEmpleado(ActionEvent event) {
    }
    
    @FXML
    private void fechaHoy(ActionEvent event) {
    }


    @FXML
    private void mostrarDetalleSeleccionado(MouseEvent event) {
    }    

    @FXML
    private void mostrarFila(MouseEvent event) {
        mostrarDetalles();
    }
    
    private int obtenerCliente(String nombreCliente) {
            ArrayList<Cliente> listaClientes = c.consulta();
            for (Cliente cliente : listaClientes) {
                if (cliente.getNombre().equals(nombreCliente)) return cliente.getRuc();
            }
            return 0;
        }

    private int obtenerEmpleado(String nombreEmpleado) {
        ArrayList<Empleado> listaEmpleados = e.consulta();
        for (Empleado emp : listaEmpleados) {
            if (emp.getNombre().equals(nombreEmpleado)) return emp.getIdempleado();
        }
        return 0;
    }

        //metodos para cargar los combos
    private void cargarComboClientes(){
        listaClientes = FXCollections.observableArrayList(c.consulta());
        for (Cliente client : listaClientes) {
            comboCliente.getItems().add(client.getNombre());
        }
        comboCliente.setPromptText("Buscar cliente");

    }

    private void cargarComboEmpleados(){
        comboEmpleado.getItems().clear();
        comboEmpleado.setValue(null);
        ArrayList<Empleado> listaEmpleados = e.consulta();
        if (listaEmpleados != null && !listaEmpleados.isEmpty()) {
            for (Empleado emp : listaEmpleados) {
                comboEmpleado.getItems().add(emp.getNombre());
            }
        }
        comboEmpleado.setPromptText("Buscar empleado");
    }

        //metodo para las alertas comnunes
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }
    
    private void cargarComboProductos() {
        comboProductos.getItems().clear();
        comboProductos.setValue(null);
        ArrayList<Producto> listaProductos = pr.consulta();
        if (listaProductos != null && !listaProductos.isEmpty()) {
            for (Producto pro : listaProductos) {
                comboProductos.getItems().add(pro.getNombre());
            }
        }
        comboEmpleado.setPromptText("Buscar producto");
    }

    private int obtenerProducto(String nombreProducto) {
        ArrayList<Producto> listaProductos = pr.consulta();
        String input = comboProductos.getEditor().getText();
        for (Producto pro : listaProductos) {
            if (pro.getNombre().equalsIgnoreCase(nombreProducto) || pro.getNombre().equalsIgnoreCase(input)) {
                return pro.getIdproducto();
            }
        }
        return 0; // Devuelve 0 si no se encuentra el producto
    }

    @FXML
    private void guardarDetalle(ActionEvent event) {
        DetallePedido nuevoDetalle = new DetallePedido();
        nuevoDetalle.setIdProducto(obtenerProducto(comboProductos.getSelectionModel().getSelectedItem()));
        nuevoDetalle.setCantidad(Integer.parseInt(txtCantidad.getText()));
        detalles.add(nuevoDetalle);
        btnGuardarPedido.setDisable(false);
        comboProductos.getSelectionModel().clearSelection();
        txtCantidad.clear();
        mostrarDetallesAgregados();
        
    }

    @FXML
    private void guardar(ActionEvent event) {
        Pedido pedido = new Pedido();
        pedido.setCliente_ruc(obtenerCliente(comboCliente.getSelectionModel().getSelectedItem()));
        pedido.setIdEmpleado(obtenerEmpleado(comboEmpleado.getSelectionModel().getSelectedItem()));
        java.sql.Date fecha = java.sql.Date.valueOf(dateFecha.getValue());
        pedido.setFecha_pedido(fecha);
        boolean resultado = true;

        if (pedido.insertar()) {
            int idPedidoInsertado = pedido.obtenerID();// Asegúrate de que el id se actualice correctamente en el objeto Pedido
            for (DetallePedido d : detalles) {
                System.out.println("id del pedido nuevo: " + idPedidoInsertado);
                d.setIdPedido(idPedidoInsertado);
                System.out.println("idproducto" + d.getIdProducto());

                if (!d.insertar()) {
                    System.out.println("error");
                    resultado = false;
                    System.out.println("detalle: " + d.getIdProducto());
                    break;
                }
            }
            if (resultado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Pedido y detalles guardados correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido");
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar el pedido");
        }
        btnGuardarPedido.setDisable(true);
        mostrarDatos();
        
    }
    
    private void mostrarDatos() {
        listaPedidos = FXCollections.observableArrayList(p.consulta());
        colNroPedido.setCellValueFactory(new PropertyValueFactory<>("idpedido"));
        colClientes.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colFechas.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
        tablaPedidos.setItems(listaPedidos);
    }
    
    private void mostrarDetalles(){
        d.setIdPedido(tablaPedidos.getSelectionModel().getSelectedItem().getIdpedido());
        listaDetalles = FXCollections.observableArrayList(d.consulta());
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaDetalles.setItems(listaDetalles);
    }
    
    private void mostrarDetallesAgregados(){
        ObservableList<DetallePedido> detallesAgregados = FXCollections.observableArrayList(detalles);
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaDetalles.setItems(detallesAgregados);
        
    }
            


}
