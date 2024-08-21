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
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

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
    //bandera
    private boolean modificar;
    private boolean banderaSeleccion;
    private boolean isUpdating = false; // Flag to prevent recursive updates
    //listas nombres
    List<String> nombresProductos = new ArrayList<>();
    List<String> nombresClientes = new ArrayList<>();
    List<String> nombresEmpleados = new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboClientes();
        cargarComboEmpleados();
        cargarComboProductos();
        mostrarDatos();
        comboCliente.getEditor().textProperty().addListener((obs, oldValue, newValue) -> filtrarCombos(newValue, nombresClientes, comboCliente));
    }
    
private void filtrarCombos(String input, List<String> listaNombres, ComboBox<String> comboBox) {
    if (isUpdating) return; // Prevent recursive updates
    isUpdating = true;

    ObservableList<String> filteredNames = FXCollections.observableArrayList();

    if (input == null || input.isEmpty()) {
        filteredNames = FXCollections.observableArrayList(listaNombres);
    } else {
        String lowerCaseInput = input.toLowerCase();
        for (String name : listaNombres) {
            if (name.toLowerCase().contains(lowerCaseInput)) {
                filteredNames.add(name);
            }
        }
    }
    comboBox.setItems(filteredNames);
    comboBox.show(); // Show filtered options

    isUpdating = false;
}




    @FXML
    private void nuevoPedido(ActionEvent event) {
        labNroSeleccionado.setText("Numero de pedido seleccionado: "
                + (p.obtenerID() + 1));
        tablaPedidos.setDisable(true);
        banderaSeleccion = true;
        comboCliente.setDisable(false);
        dateFecha.setDisable(false);
        comboEmpleado.setDisable(false);
        btnNuevoDetalle.setDisable(false);
        btnFechaHoy.setDisable(false);
        btnNuevoCliente.setDisable(false);
        btnNuevoEmpleado.setDisable(false);
        btnGuardarDetalle.setDisable(false);
        btnCancelarPedido.setDisable(false);
        comboEmpleado.setPromptText("Buscar empleado");
        comboCliente.setPromptText("Buscar cliente");
        btnNuevoPedido.setDisable(true);

    }

    @FXML
    private void nuevoDetallePedido(ActionEvent event) {
        txtCantidad.setDisable(false);
        comboProductos.setDisable(false);
        btnGuardarDetalle.setDisable(false);
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
            p.setIdpedido(id);
            if (p.eliminarDetallePedidos()) {
                if (p.borrar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Pedido eliminado con exito");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "Error eliminando pedido");
                }
            }

        }
        mostrarDatos();
        cancelar(event);
    }

    @FXML
    private void modificar(ActionEvent event) {
        btnNuevoDetalle.setDisable(false);
        comboCliente.setDisable(false);
        comboEmpleado.setDisable(false);
        dateFecha.setDisable(false);
        btnGuardarPedido.setDisable(false);
        modificar = true;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        tablaPedidos.setDisable(false);
        tablaPedidos.getSelectionModel().clearSelection();
        tablaDetalles.getSelectionModel().clearSelection();;
        btnNuevoPedido.setDisable(false);
        btnEliminarPedido.setDisable(true);
        btnModificarPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);

        comboCliente.getSelectionModel().clearSelection();

        comboEmpleado.getSelectionModel().clearSelection();
        dateFecha.setValue(null);
        btnNuevoCliente.setDisable(true);
        btnFechaHoy.setDisable(true);
        btnNuevoEmpleado.setDisable(true);
        comboCliente.setDisable(true);
        comboEmpleado.setDisable(true);
        dateFecha.setDisable(true);
        tablaDetalles.getItems().clear();
        btnNuevoDetalle.setDisable(true);
        btnGuardarDetalle.setDisable(true);
        cargarComboClientes();
        cargarComboEmpleados();
        modificar = false;
        btnNuevoPedido.setDisable(false);
    }

    @FXML
    private void cancelarDetalle(ActionEvent event) {
        btnNuevoDetalle.setDisable(false);
        txtCantidad.setDisable(true);
        comboProductos.getSelectionModel().clearSelection();
        comboProductos.setDisable(true);
        btnGuardarDetalle.setDisable(true);

    }

    @FXML
    private void eliminarDetalle(ActionEvent event) {
        d = tablaDetalles.getSelectionModel().getSelectedItem();
        if (banderaSeleccion) {
            detalles.remove(d);
            mostrarDetallesAgregados();
            btnEliminarDetalle.setDisable(true);
        } else {
            if (d.borrar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Producto eliminado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "Error eliminando el producto");
            }
            mostrarDetalles();
        }
    }

    @FXML
    private void editarDetalle(ActionEvent event) {
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

    @FXML
    private void fechaHoy(ActionEvent event) {
        dateFecha.setValue(LocalDate.now());
    }

    @FXML
    private void mostrarDetalleSeleccionado(MouseEvent event) {
        btnNuevoDetalle.setDisable(true);
        btnCancelarDetalle.setDisable(false);
        btnEliminarDetalle.setDisable(false);
        btnEditarCantidad.setDisable(false);
        txtCantidad.setDisable(true);
        DetallePedido detalleSeleccionado = tablaDetalles.getSelectionModel().getSelectedItem();
        if (detalleSeleccionado != null) {
            comboProductos.setValue(detalleSeleccionado.getNombreProducto());
            txtCantidad.setText(String.valueOf(detalleSeleccionado.getCantidad()));

        }
    }

    @FXML
    private void mostrarFila(MouseEvent event) {

        banderaSeleccion = false;
        //desactivar botones
        btnCancelarPedido.setDisable(false);
        btnNuevoPedido.setDisable(true);
        btnModificarPedido.setDisable(false);
        btnEliminarPedido.setDisable(false);
        //desactivar datos del pedido
        comboCliente.setDisable(true);
        dateFecha.setDisable(true);
        comboEmpleado.setDisable(true);
        //rellenar datos del pedido
        p = tablaPedidos.getSelectionModel().getSelectedItem();
        if (p != null) {
            comboCliente.setValue(p.getNombreCliente());
            dateFecha.setValue(p.getFecha_pedido().toLocalDate());
            comboEmpleado.setValue(p.getNombreEmpleado());
            txtId.setText(String.valueOf(p.getIdpedido()));
        }
        mostrarDetalles();
    }

    @FXML
    private void guardarDetalle(ActionEvent event) {
        String selectedProduct = comboProductos.getSelectionModel().getSelectedItem();
        if (selectedProduct == null || txtCantidad.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un producto y especifique una cantidad.");
            return;
        }

        DetallePedido nuevoDetalle = new DetallePedido();
        Producto productoSeleccionado = pr.buscarPorId(obtenerProducto(selectedProduct));

        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Producto no encontrado.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "La cantidad debe ser mayor a cero.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La cantidad debe ser un número válido.");
            return;
        }

        double precioUnitario = productoSeleccionado.getPrecio();
        nuevoDetalle.setIdProducto(productoSeleccionado.getIdproducto());
        nuevoDetalle.setCantidad(cantidad);
        nuevoDetalle.setNombreProducto(productoSeleccionado.getNombre());
        nuevoDetalle.setPrecioUnit(precioUnitario);
        nuevoDetalle.setPrecioTotal(precioUnitario * cantidad); // Calculate total price based on user input

        detalles.add(nuevoDetalle);

        btnGuardarPedido.setDisable(false);
        comboProductos.getSelectionModel().clearSelection();
        txtCantidad.clear();
        mostrarDetallesAgregados();
        cancelarDetalle(event);

    }

    @FXML
    private void guardar(ActionEvent event) {
        Pedido pedido = new Pedido();

        try {

            pedido.setCliente_ruc(obtenerCliente(comboCliente.getSelectionModel().getSelectedItem()));
            pedido.setIdEmpleado(obtenerEmpleado(comboEmpleado.getSelectionModel().getSelectedItem()));
            java.sql.Date fecha = java.sql.Date.valueOf(dateFecha.getValue());
            pedido.setFecha_pedido(fecha);
            if (modificar) {
                int idpedido = Integer.parseInt(txtId.getText());
                pedido.setIdpedido(idpedido);
                if (pedido.modificar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Pedido modificado con exito");
                }

                modificar = false;
            } else {
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
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido");
        }
        tablaPedidos.setDisable(false);
        cancelar(event);
        btnGuardarPedido.setDisable(true);
        mostrarDatos();

    }

    private void mostrarDatos() {
        listaPedidos = (ObservableList<Pedido>) FXCollections.observableArrayList(p.consulta());
        colNroPedido.setCellValueFactory(new PropertyValueFactory<>("idpedido"));
        colClientes.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colFechas.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
        tablaPedidos.setItems(listaPedidos);
    }

    private void mostrarDetalles() {
        d.setIdPedido(tablaPedidos.getSelectionModel().getSelectedItem().getIdpedido());
        listaDetalles = FXCollections.observableArrayList(d.consulta());
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaDetalles.setItems(listaDetalles);
    }

    private void mostrarDetallesAgregados() {
        ObservableList<DetallePedido> detallesAgregados = FXCollections.observableArrayList(detalles);
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaDetalles.setItems(detallesAgregados);

    }

    private int obtenerCliente(String nombreCliente) {
        ArrayList<Cliente> listaClientes = c.consulta();
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equals(nombreCliente)) {
                return cliente.getRuc();
            }
        }
        return 0;
    }

    private int obtenerEmpleado(String nombreEmpleado) {
        comboEmpleado.getItems().clear();
        ArrayList<Empleado> listaEmpleados = e.consulta();
        for (Empleado emp : listaEmpleados) {
            if (emp.getNombre().equals(nombreEmpleado)) {
                return emp.getIdempleado();
            }
        }
        return 0;
    }

    //metodos para cargar los combos
    private void cargarComboClientes() {
        listaClientes = FXCollections.observableList(c.consulta());
        for (Cliente cliente : listaClientes) {
            nombresClientes.add(cliente.getNombre());
        }
        comboCliente.setItems(FXCollections.observableArrayList(nombresClientes));
        
    }

    private void cargarComboEmpleados() {
        listaEmpleados = FXCollections.observableList(e.consulta());
        for (Empleado empleado : listaEmpleados) {
            nombresEmpleados.add(empleado.getNombre());
        }
        comboEmpleado.setItems(FXCollections.observableArrayList(nombresEmpleados));
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
        listaProductos = FXCollections.observableList(pr.consulta());

        for (Producto producto : listaProductos) {
            nombresProductos.add(producto.getNombre());
        }
        comboProductos.setItems(FXCollections.observableArrayList(nombresProductos));
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

}
