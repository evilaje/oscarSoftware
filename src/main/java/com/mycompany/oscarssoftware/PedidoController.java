
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Producto;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PedidoController implements Initializable {

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private Button btnNuevoPedido;
    @FXML
    private Button btnGuardarPedido;
    @FXML
    private Button btnEliminarPedido;
    @FXML
    private Button btnModificarPedido;
    @FXML
    private ComboBox<String> comboCliente;
    @FXML
    private ComboBox<String> comboEmpleado;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private TableColumn<Pedido, String> colClientes;
    @FXML
    private TableColumn<Pedido, Date> colFechas;
    private Pedido pedido = new Pedido();
    ObservableList<Pedido> ListaPedidos;
    private Cliente c = new Cliente();
    private Empleado e = new Empleado();
    @FXML
    private Button btnCancelarPedido;
    private boolean modificar = false;
    @FXML
    private TextField txtId;
    ObservableList<Cliente> listaCliente;
    @FXML
    private Button btnNuevoEmpleado;
    @FXML
    private Button btnNuevoProdroducto;
    @FXML
    private ComboBox<String> comboProductos;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnCargarProducto;
    @FXML
    private TableView<DetallePedido> tablaDetalles;
    private Producto p = new Producto();
    private DetallePedido detalle = new DetallePedido();
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
    ObservableList<DetallePedido> listaDetalles;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }    
    
    public void mostrarDatos(){
        ListaPedidos = FXCollections.observableArrayList(pedido.consulta());
        colClientes.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colFechas.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
        tablaPedidos.setItems(ListaPedidos);
    }

    @FXML
    private void nuevoPedido(ActionEvent event) {
        // Activar combos y fechas

        
        // Limpiar comboboxes y datepicker
        comboCliente.getItems().clear();
        comboCliente.setValue(null);
        comboEmpleado.getItems().clear();
        comboEmpleado.setValue(null);
        dateFecha.setValue(null);
        comboCliente.setDisable(false);
        comboEmpleado.setDisable(false);
        dateFecha.setDisable(false);
        // Desactivar y activar botones
        btnNuevoPedido.setDisable(true);
        btnGuardarPedido.setDisable(false);
        btnCancelarPedido.setDisable(false);
        btnNuevoEmpleado.setDisable(false);
        btnNuevoCliente.setDisable(false);
        
        cargarComboClientes();
        cargarComboEmpleados();
        dateFecha.setPromptText("Seleccione la fecha");
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Limpiar selección de la tabla y deshabilitar controles
        tablaPedidos.getSelectionModel().clearSelection();
        comboCliente.setDisable(true);
        comboEmpleado.setDisable(true);
        dateFecha.setDisable(true);

        // Limpiar comboboxes y datepicker
        comboCliente.getSelectionModel().clearSelection();
        comboCliente.setValue(null);
        comboCliente.setPromptText("Cliente");
        comboEmpleado.setValue(null);
        comboEmpleado.getSelectionModel().clearSelection();
        comboEmpleado.setPromptText("Empleado");
        dateFecha.setValue(null);
        
        // Desactivar y activar botones
        btnModificarPedido.setDisable(true);
        btnNuevoPedido.setDisable(false);
        btnGuardarPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);
        btnEliminarPedido.setDisable(true);
        btnNuevoEmpleado.setDisable(true);
        btnNuevoCliente.setDisable(true);
    }


    @FXML
    private void guardar(ActionEvent event) {
        int idcliente = obtenerCliente(comboCliente.getSelectionModel().getSelectedItem());
        int idempleado = obtenerEmpleado(comboEmpleado.getSelectionModel().getSelectedItem());
        System.out.println(idempleado);
        java.sql.Date fecha = java.sql.Date.valueOf(dateFecha.getValue());
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setCliente_ruc(idcliente);
        nuevoPedido.setIdEmpleado(idempleado);
        nuevoPedido.setFecha_pedido(fecha);
        nuevoPedido.setNombreCliente(comboCliente.getSelectionModel().getSelectedItem());
        nuevoPedido.setNombreEmpleado(comboEmpleado.getSelectionModel().getSelectedItem());

        
        
        boolean resultado = false;
        if (modificar) {
            int idpedido = Integer.parseInt(txtId.getText());
            nuevoPedido.setIdpedido(idpedido);
            if (nuevoPedido.modificar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Pedido modificado con exito");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "El pedido no pudo ser modificado");
            }
            modificar = false;
        } else {
            resultado = nuevoPedido.insertar();
            if (resultado) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Pedido registrado con exito");
                
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "El pedido no pudo ser registrado");
            }
                        
        }
        if (resultado) {
            seleccionarYEnfocarPedido(nuevoPedido);
            mostrarDatos();
            btnNuevoProdroducto.setDisable(false);
            mostrarDetalles();
            
        }
        cancelar(event);

    }
        @FXML
    private void mostrarFila() {
        //desactivar y activar botones
        btnNuevoPedido.setDisable(true);
        btnModificarPedido.setDisable(false);
        btnCancelarPedido.setDisable(false);
        btnEliminarPedido.setDisable(false);
        comboCliente.setDisable(true);
        dateFecha.setDisable(true);
        comboEmpleado.setDisable(true);
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {
            txtId.setText(String.valueOf(pedidoSeleccionado.getIdpedido()));
            comboCliente.setValue(pedidoSeleccionado.getNombreCliente());
            comboEmpleado.setValue(pedidoSeleccionado.getNombreEmpleado());
            LocalDate fecha = pedidoSeleccionado.getFecha_pedido().toLocalDate();
            dateFecha.setValue(fecha);
            mostrarDetalles();
            btnNuevoProdroducto.setDisable(false);
        }

        
    }
    
    @FXML
    private void modificar(ActionEvent event) {
        comboCliente.setDisable(false);
        comboEmpleado.setDisable(false);
        dateFecha.setDisable(false);
        btnCancelarPedido.setDisable(true);
        btnEliminarPedido.setDisable(true);
        btnModificarPedido.setDisable(true);
        btnGuardarPedido.setDisable(false);
        modificar = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Aviso de eliminacion");
        a.setHeaderText(null);
        a.setContentText("Desea eliminar el pedido?");
        Optional<ButtonType> opcion = a.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            int id = Integer.parseInt(txtId.getText());
            pedido.setIdpedido(id);
            if (pedido.borrar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Pedido eliminado con exito");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "Error eliminando pedido");
            }
        }
        mostrarDatos();
        cancelar(event);
    }
    
    
    //metodos de ayuda
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
        listaCliente = FXCollections.observableArrayList(c.consulta());
        for (Cliente client : listaCliente) {
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

    @FXML
    private void abrirVentanaCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hacer la ventana modal
            stage.showAndWait(); // Esperar hasta que se cierre la ventana

            // Después de que se cierre la ventana, actualizar el combo box
            cargarComboClientes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirVentanaEmpleado(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("empleado.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hacer la ventana modal
            stage.showAndWait(); // Esperar hasta que se cierre la ventana

            // Después de que se cierre la ventana, actualizar el combo box
            cargarComboEmpleados();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void seleccionarYEnfocarPedido(Pedido pedido) {
        // Obtener el índice del pedido en la lista
        int index = tablaPedidos.getItems().indexOf(pedido);

        if (index >= 0) {
            // Seleccionar el pedido
            tablaPedidos.getSelectionModel().select(index);

            // Asegurar que la fila esté visible
            tablaPedidos.scrollTo(index);

            // Enfocar el TableView para asegurar que la selección sea evidente
            tablaPedidos.requestFocus();
            btnNuevoProdroducto.setDisable(false);
            
           
        }
    }
    
    /*
    //DETALLE PEDIDO CONTROLLER!!!!
    */

    @FXML
    private void nuevoDetallePedido(ActionEvent event) {
        comboProductos.setDisable(false);
        txtCantidad.setDisable(false);
        btnCargarProducto.setDisable(false);
        btnNuevoProdroducto.setDisable(false);
        tablaDetalles.setDisable(false);
        cargarComboProductos();
    }

    @FXML
    private void guardarDetalle(ActionEvent event) {
        int idProducto = obtenerProducto(comboProductos.getSelectionModel().getSelectedItem());
        int idPedido = tablaPedidos.getSelectionModel().getSelectedItem().getIdpedido();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        DetallePedido nuevoDetalle = new DetallePedido();
        nuevoDetalle.setIdPedido(idPedido);
        nuevoDetalle.setIdProducto(idProducto);
        nuevoDetalle.setCantidad(cantidad);
        if (nuevoDetalle.insertar()) {
            mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Producto añadido con exito!");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "EL producto no pudo ser añadido");
        }
        
    }
    
    private void cargarComboProductos() {
        comboProductos.getItems().clear();
        comboProductos.setValue(null);
        ArrayList<Producto> listaProductos = p.consulta();
        if (listaProductos != null && !listaProductos.isEmpty()) {
            for (Producto pro : listaProductos) {
                comboProductos.getItems().add(pro.getNombre());
            }
        }
        comboEmpleado.setPromptText("Buscar producto");
    }
    
        private int obtenerProducto(String nombreProducto) {
        ArrayList<Producto> listaProductos = p.consulta();
        for (Producto pro : listaProductos) {
            if (pro.getNombre().equals(nombreProducto)) return pro.getIdproducto();
        }
        return 0;
    }
        
        private void mostrarDetalles(){
            detalle.setIdPedido(tablaPedidos.getSelectionModel().getSelectedItem().getIdpedido());
            listaDetalles = FXCollections.observableArrayList(detalle.consulta());
            colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
            colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
            colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
            tablaDetalles.setItems(listaDetalles);
            
            
        }
        




    
}
