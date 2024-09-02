package com.mycompany.oscarssoftware;


import com.jfoenix.controls.JFXButton;
import com.mycompany.oscarssoftware.clases.Reporte;
import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.DetallePedido;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Producto;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
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
    private JFXButton btnReporte;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresClientes = FXCollections.observableArrayList();
        nombresEmpleados = FXCollections.observableArrayList();
        nombresProductos = FXCollections.observableArrayList();
        cargarComboClientes();
        cargarComboEmpleados();
        cargarComboProductos();
        comboCliente.getEditor().textProperty().addListener((obs, oldValue, newValue) -> filterNames(comboCliente, nombresClientes, newValue));
        comboEmpleado.getEditor().textProperty().addListener((obs, oldValue, newValue) -> filterNames(comboEmpleado, nombresEmpleados, newValue));
        comboProductos.getEditor().textProperty().addListener((obs, oldValue, newValue) -> filterNames(comboProductos, nombresProductos, newValue));

    }
    
    private void filterNames(ComboBox<String> comboBox, ObservableList<String> originalItems, String input) {
        if (isUpdating) return; // Prevent recursive updates
        isUpdating = true;

        // Almacenar el texto actual del editor
        String currentText = comboBox.getEditor().getText();

        ObservableList<String> filteredNames = FXCollections.observableArrayList();

        if (input.equals(null)|| input.isEmpty()) {
            filteredNames.setAll(originalItems);
        } else {
            String lowerCaseInput = input.toLowerCase();
            for (String name : originalItems) {
                if (name.toLowerCase().startsWith(lowerCaseInput)) {
                    filteredNames.add(name);
                }
            }
        }

        comboBox.setItems(filteredNames);
        comboBox.getEditor().setText(currentText);
        comboBox.show(); // Esto fuerza que el comboBox muestre las opciones filtradas

        isUpdating = false;
    }



    @FXML
    private void nuevoPedido(ActionEvent event) {
        detalles.clear();
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
        btnGuardarPedido.setDisable(false);

    }

    @FXML
    private void nuevoDetallePedido(ActionEvent event) {
        txtCantidad.setDisable(false);
        comboProductos.setDisable(false);
        btnGuardarDetalle.setDisable(false);
        btnNuevoDetalle.setDisable(true);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        total = 0;
        labTotal.setText("0 Gs.");
        tablaDetalles.getSelectionModel().clearSelection();
        comboCliente.getSelectionModel().clearSelection();
        comboEmpleado.getSelectionModel().clearSelection();
        comboEmpleado.getEditor().clear();
        comboCliente.getEditor().clear();
        btnNuevoPedido.setDisable(false);
        btnCancelarPedido.setDisable(true);

        dateFecha.setValue(null);
        btnNuevoCliente.setDisable(true);
        btnFechaHoy.setDisable(true);
        btnNuevoEmpleado.setDisable(true);
        comboCliente.setDisable(true);
        comboEmpleado.setDisable(true);
        dateFecha.setDisable(true);
        tablaDetalles.getItems().clear();
        modificar = false;
        btnGuardarPedido.setDisable(true);
        ocultarCombos();
        cancelarDetalle(event);
    }

    @FXML
    private void cancelarDetalle(ActionEvent event) {
        tablaDetalles.getSelectionModel().clearSelection();
        btnGuardarPedido.setDisable(false);
        btnNuevoDetalle.setDisable(false);
        txtCantidad.clear();
        txtCantidad.setDisable(true);
        comboProductos.getSelectionModel().clearSelection();
        comboProductos.setDisable(true);
        btnGuardarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnEditarCantidad.setDisable(true);
        btnGuardarDetalle.setDisable(true);
        comboProductos.hide();


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
        DetallePedido detalleSeleccionado = tablaDetalles.getSelectionModel().getSelectedItem();
        if (detalleSeleccionado != null) {
            comboProductos.getEditor().setText(detalleSeleccionado.getNombreProducto());
            txtCantidad.setText(String.valueOf(detalleSeleccionado.getCantidad()));

        }
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
      mostrarAlerta(Alert.AlertType.INFORMATION, "Detalle Guardado", "El detalle del producto ha sido guardado correctamente.");
      labTotal.setText(String.valueOf(total) + " Gs.");
  }

    @FXML
    private void guardar(ActionEvent event) {
        Pedido pedido = new Pedido();

        try {

            pedido.setIdCliente(obtenerCliente(comboCliente.getSelectionModel().getSelectedItem()));
            pedido.setIdEmpleado(obtenerEmpleado(comboEmpleado.getSelectionModel().getSelectedItem()));
            java.sql.Date fecha = java.sql.Date.valueOf(dateFecha.getValue());
            pedido.setFecha_pedido(fecha);
            boolean resultado = true;
            if (pedido.insertar()) {
                for (DetallePedido d : detalles) {
                    System.out.println("idproducto" + d.getIdProducto());

                    if (!d.insertar()) {
                        System.out.println("error");
                        resultado = false;
                        
                        System.out.println("detalle: " + d.getIdProducto());
                        break;
                    }
                }
                if (resultado) {
                    cancelar(event);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Pedido y detalles guardados correctamente");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar el pedido");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar los detalles del pedido");
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
        comboEmpleado.hide();
        comboProductos.hide();
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        Reporte r = new Reporte();
        String ubi = "/reportes/pedidoF1.jasper";
        String tit = "Informe de pedido";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idpedido", p.obtenerID()); 
        r.generarReporteParametros(ubi, tit, parameters);
    }

}
