package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Producto;
import com.mycompany.oscarssoftware.util.Autocompletado;
import com.mycompany.oscarssoftware.util.EmpleadoSingleton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PedidoController implements Initializable {

    private Cliente c = new Cliente();
    private Empleado e = new Empleado();
    private Producto pr = new Producto();
    private Pedido p = new Pedido();
    private DetallePedido d = new DetallePedido();

    @FXML
    private ComboBox<String> comboCliente;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnFechaHoy;
    @FXML
    private Button btnNuevoPedido;
    @FXML
    private Button btnCancelarPedido;
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
    private Button btnGuardarPedido;
    @FXML
    private Button btnGuardarDetalle;
    @FXML
    private Button btnNuevoDetalle;
    //listas
    private ArrayList<DetallePedido> detalles = new ArrayList<>();
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
    private ObservableList<String> nombresProductos;
    private ObservableList<String> nombresClientes;
    private ObservableList<String> nombresEmpleados;
    @FXML
    private Pane paneCabecera;
    @FXML
    private Label labTotal;
    int total = 0;
    @FXML
    private TextField txtEmpleado;

    private MenuController menuController;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresClientes = FXCollections.observableArrayList();
        nombresProductos = FXCollections.observableArrayList();
        cargarComboClientes();
        txtEmpleado.setText(EmpleadoSingleton.getInstance().getEmpleado().getNombre());
        cargarComboProductos();
        Autocompletado a = new Autocompletado();
        a.configurarAutocompletadoComboBox(comboCliente, nombresClientes);
        a.configurarAutocompletadoComboBox(comboProductos, nombresProductos);
        LocalDate today = LocalDate.now();

        // Configurar el DatePicker para deshabilitar las fechas futuras
        dateFecha.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        // Deshabilitar las fechas futuras
                        if (item.isAfter(today)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Color opcional para las fechas deshabilitadas
                        }
                    }
                };
            }
        });

        // Establecer la fecha actual como fecha predeterminada (opcional)
        dateFecha.setValue(today);
    }

    @FXML
    private void nuevoPedido(ActionEvent event) {
        detalles.clear();
        banderaSeleccion = true;
        comboCliente.setDisable(false);
        dateFecha.setDisable(false);
        btnNuevoDetalle.setDisable(false);
        btnFechaHoy.setDisable(false);
        btnNuevoCliente.setDisable(false);
        btnGuardarDetalle.setDisable(true);
        btnCancelarPedido.setDisable(false);
        comboCliente.setPromptText("Buscar cliente");
        btnNuevoPedido.setDisable(true);
        btnGuardarPedido.setDisable(false);
        paneCabecera.setDisable(false);

    }

    @FXML
    private void nuevoDetallePedido(ActionEvent event) {
        txtCantidad.setDisable(false);
        comboProductos.setDisable(false);
        btnGuardarDetalle.setDisable(false);
        btnNuevoDetalle.setDisable(true);
        btnGuardarPedido.setDisable(true);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        total = 0;
        labTotal.setText("0 Gs.");
        limpiarCamposPedido();

        // Limpiar la tabla de detalles y deshabilitar botones
        tablaDetalles.getItems().clear();
        limpiarCamposDetalle();

        // Restablecer el estado de los botones
        btnNuevoPedido.setDisable(false);
        btnGuardarPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);
        comboCliente.hide();
        comboCliente.setDisable(true);
        btnNuevoCliente.setDisable(true);
        btnFechaHoy.setDisable(true);
        dateFecha.setDisable(true);

        btnNuevoDetalle.setDisable(true);
        btnCancelarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnEditarCantidad.setDisable(true);

        // Deseleccionar cualquier elemento en la tabla de detalles
        tablaDetalles.getSelectionModel().clearSelection();
    }

    @FXML
    private void cancelarDetalle(ActionEvent event) {
        limpiarCamposDetalle();
        btnGuardarDetalle.setDisable(true);
        btnGuardarPedido.setDisable(false);

        // Deshabilitar los botones de edición y eliminación
        btnNuevoDetalle.setDisable(false);
        btnCancelarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnEditarCantidad.setDisable(true);

        // Deseleccionar cualquier elemento en la tabla
        tablaDetalles.getSelectionModel().clearSelection();

    }

    private void limpiarCamposPedido() {
        comboCliente.getSelectionModel().clearSelection();

        dateFecha.setValue(null);
    }

    @FXML
    private void eliminarDetalle(ActionEvent event) {
        d = tablaDetalles.getSelectionModel().getSelectedItem();
        if (banderaSeleccion) {
            detalles.remove(d);
            mostrarDetallesAgregados();
            btnEliminarDetalle.setDisable(true);
            total -= d.getPrecioTotal();
            labTotal.setText(total + " Gs.");
            cancelarDetalle(event);
        }

    }

    @FXML
    private void editarDetalle(ActionEvent event) {
        txtCantidad.setDisable(false);
        btnGuardarDetalle.setDisable(false);
        btnEditarCantidad.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnCancelarDetalle.setDisable(false);
        btnGuardarPedido.setDisable(true);
        paneCabecera.setDisable(true);
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
    private void fechaHoy(ActionEvent event) {
        dateFecha.setValue(LocalDate.now());
    }

    @FXML
    private void mostrarDetalleSeleccionado(MouseEvent event) {

        DetallePedido detalleSeleccionado = tablaDetalles.getSelectionModel().getSelectedItem();

        if (detalleSeleccionado != null) {
            // Setear los valores del detalle seleccionado en los controles correspondientes
            comboProductos.getEditor().setText(detalleSeleccionado.getNombreProducto());
            ocultarCombos();
            comboProductos.getSelectionModel().select(detalleSeleccionado.getNombreProducto());
            txtCantidad.setText(String.valueOf(detalleSeleccionado.getCantidad()));
            comboProductos.setDisable(true);
            txtCantidad.setDisable(true);

            // Habilitar los botones de edición y eliminación
            btnNuevoDetalle.setDisable(true);
            btnCancelarDetalle.setDisable(false);
            btnEliminarDetalle.setDisable(false);
            btnEditarCantidad.setDisable(false);
            btnGuardarPedido.setDisable(true);
        } else {
            // Si no hay detalle seleccionado, deshabilitar los botones correspondientes
            limpiarCamposDetalle();
            btnNuevoDetalle.setDisable(false);
            btnCancelarDetalle.setDisable(true);
            btnEliminarDetalle.setDisable(true);
            btnEditarCantidad.setDisable(true);
        }
    }

// Método para limpiar los campos relacionados al detalle
    private void limpiarCamposDetalle() {
        comboProductos.getSelectionModel().clearSelection();
        txtCantidad.clear();
        comboProductos.setDisable(true);
        txtCantidad.setDisable(true);
    }

    @FXML
    private void guardarDetalle(ActionEvent event) {
        String selectedProduct = comboProductos.getSelectionModel().getSelectedItem();
        if (selectedProduct == null || txtCantidad.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un producto y especifique una cantidad.");
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

        int idProducto = obtenerProducto(selectedProduct, cantidad);
        if (idProducto == 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Producto no encontrado.");
            return;
        } else if (idProducto == -1) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "No hay suficiente stock para el producto seleccionado.");
            return;
        }

        Producto productoSeleccionado = pr.buscarPorId(idProducto);
        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Producto no encontrado.");
            return;
        }

        DetallePedido detalleExistente = null;
        for (DetallePedido detalle : detalles) {
            if (detalle.getNombreProducto().equals(selectedProduct)) {
                detalleExistente = detalle;
                break;
            }
        }

        if (detalleExistente != null) {
            detalleExistente.setCantidad(cantidad);
            detalleExistente.setPrecioTotal(productoSeleccionado.getPrecio() * cantidad);
        } else {
            DetallePedido nuevoDetalle = new DetallePedido();
            int idnuevoPedido = p.obtenerID() + 1;
            nuevoDetalle.setIdPedido(idnuevoPedido);
            nuevoDetalle.setIdProducto(productoSeleccionado.getIdproducto());
            nuevoDetalle.setCantidad(cantidad);
            nuevoDetalle.setNombreProducto(productoSeleccionado.getNombre());
            nuevoDetalle.setPrecioUnit(productoSeleccionado.getPrecio());
            nuevoDetalle.setPrecioTotal(productoSeleccionado.getPrecio() * cantidad);
            total += nuevoDetalle.getPrecioTotal();
            detalles.add(nuevoDetalle);
        }

        mostrarDetallesAgregados();
        cancelarDetalle(event);
        paneCabecera.setDisable(false);
        btnGuardarPedido.setDisable(false);
        labTotal.setText(String.valueOf(total) + " Gs.");
        ocultarCombos();
        
    }

    @FXML
    private void guardar(ActionEvent event) {
        Pedido pedido = new Pedido();

        try {
            // Obtener el cliente seleccionado
            String clienteSeleccionado = comboCliente.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado == null) {
                System.out.println("Error: No se ha seleccionado ningún cliente.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha seleccionado ningún cliente.");
                return;
            }

            // Obtener el empleado desde EmpleadoSingleton
            Empleado empleado = EmpleadoSingleton.getInstance().getEmpleado();
            if (empleado == null) {
                System.out.println("Error: No hay empleado en sesión.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay empleado en sesión.");
                return;
            }

            // Validar la fecha seleccionada en el DatePicker
            LocalDate fechaSeleccionada = dateFecha.getValue();
            if (fechaSeleccionada == null) {
                System.out.println("Error: No se ha seleccionado ninguna fecha.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha seleccionado ninguna fecha.");
                return;
            }

            // Validar la lista de detalles
            if (detalles == null || detalles.isEmpty()) {
                System.out.println("Error: No se han agregado detalles al pedido.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se han agregado detalles al pedido.");
                return;
            }

            // Si todos los valores son válidos, procede a configurar el pedido
            pedido.setIdCliente(obtenerCliente(clienteSeleccionado));
            pedido.setIdEmpleado(empleado.getIdempleado());

            // Convertir la fecha seleccionada a java.sql.Date
            java.sql.Date fecha = java.sql.Date.valueOf(fechaSeleccionada);
            pedido.setFecha_pedido(fecha);

            // Intentar insertar el pedido
            if (pedido.insertar()) {
                boolean resultado = true;

                for (DetallePedido d : detalles) {
                    System.out.println("idproducto: " + d.getIdProducto());

                    if (!d.insertar()) {
                        System.out.println("Error al insertar detalle: " + d.getIdProducto());
                        resultado = false;
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido.");
                        break;
                    }
                }

                if (resultado) {
                    cancelar(event);
                    paneCabecera.setDisable(true);
                    if (menuController != null) {
                        menuController.actualizarGanancias();
                    }
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Pedido y detalles guardados correctamente.");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido.");
                    btnNuevoDetalle.setDisable(false);
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar el pedido.");
            }

        } catch (NullPointerException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error inesperado. Revisa todos los campos.");
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido.");
            e.printStackTrace();
        }

        btnNuevoDetalle.setDisable(true);
        btnGuardarPedido.setDisable(true);
    }

    private void mostrarDetallesAgregados() {

        ObservableList<DetallePedido> detallesAgregados = FXCollections.observableArrayList(detalles);
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaDetalles.setItems(detallesAgregados);
        tablaDetalles.refresh();

    }

    private int obtenerCliente(String nombreCliente) {
        ArrayList<Cliente> listaClientes = c.consulta();
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equals(nombreCliente)) {
                return cliente.getId();
            }
        }
        return 0;
    }

    //metodos para cargar los combos
    private void cargarComboClientes() {
        if (nombresClientes != null) {
            nombresClientes.clear();
        }
        listaClientes = FXCollections.observableList(c.consulta());

        for (Cliente cliente : listaClientes) {
            nombresClientes.add(cliente.getNombre());
        }
        comboCliente.setItems(FXCollections.observableArrayList(nombresClientes));

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
        listaProductos = FXCollections.observableList(pr.consultaProductosDisponibles());

        for (Producto producto : listaProductos) {
            nombresProductos.add(producto.getNombre());
        }
        comboProductos.setItems(FXCollections.observableArrayList(nombresProductos));
    }

    private int obtenerProducto(String nombreProducto, int cantidadSolicitada) {
        ArrayList<Producto> listaProductos = pr.consulta();
        for (Producto pro : listaProductos) {
            // Comprobar si el nombre del producto coincide y si hay suficiente stock
            if (pro.getNombre().equalsIgnoreCase(nombreProducto)) {
                if (pro.getCantidad() >= cantidadSolicitada) {
                    return pro.getIdproducto();
                } else {
                    return -1; // Indicar que no hay suficiente stock
                }
            }
        }
        return 0; // Devuelve 0 si no se encuentra el producto
    }

    private void ocultarCombos() {
        comboCliente.hide();
        comboProductos.hide();
    }

}
