
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
            if (nuevoPedido.insertar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Pedido registrado con exito");
                
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "El pedido no pudo ser registrado");
            }
                        
        }
        cancelar(event);
        mostrarDatos();
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


    
}
