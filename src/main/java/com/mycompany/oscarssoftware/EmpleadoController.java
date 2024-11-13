/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.CategoriaProducto;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Producto;
import com.mycompany.oscarssoftware.modelos.Proveedor;
import com.mycompany.oscarssoftware.util.AtajosTecladoUtil;
import com.mycompany.oscarssoftware.util.EmpleadoSingleton;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EmpleadoController implements Initializable {

    private Empleado e = new Empleado();
    private Pedido p = new Pedido();
    private boolean modificar = false;
    ObservableList<Empleado> Empleados;

    private String[] header = {"Id", "Nombre", "Telefono", "Direccion"};
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtDire;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableView<Empleado> tablaEmpleado;
    @FXML
    private TableColumn<Empleado, Integer> columnaId;
    @FXML
    private TableColumn<Empleado, String> columnaNombre;
    @FXML
    private TableColumn<Empleado, String> columnaTelefono;
    @FXML
    private TableColumn<Empleado, String> columnaDireccion;
    @FXML
    private Button btnNuevo;
    @FXML
    private AnchorPane root;
    @FXML
    private Pane side_ankerpane;
    @FXML
    private TextField txtCedula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                AtajosTecladoUtil.inicializarAtajos(newScene, (Stage) root.getScene().getWindow());
            }
        });
    }

    public void mostrarDatos() {
        Empleados = FXCollections.observableArrayList(e.consulta()); //aca se crea una lista observable que va a ser cargada a la tabla, la parte de p.consulta() trae todos los elementos de Producto como un ArrayLists
        tablaEmpleado.getItems().clear(); //vaciamos la lista antigua por si hay datos que en la vista conflictuan
        columnaId.setCellValueFactory(new PropertyValueFactory<>("cedula"));//Carga en la casilla de ID el id
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));//igual
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));//igual
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));//igual
        tablaEmpleado.setItems(Empleados);//en la tabla general carga la lista ya completa
    }

    private void switchToSecondary() throws IOException {
        App.setRoot("menu", 780, 460);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        //desactivar botonesi
        btnCancelar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        txtNombre.setDisable(true);
        btnNuevo.setDisable(true);
        txtDire.setDisable(true);
        txtTel.setDisable(true);
        txtId.setDisable(true);
        Empleado emple = tablaEmpleado.getSelectionModel().getSelectedItem();
        if (emple != null) {
            //verificar si el empleado ha registrado algun pedido. Si es asi no se puede eliminar
            if (p.existeEmpleado(EmpleadoSingleton.getInstance().getEmpleado().getIdempleado())) {
                btnEliminar.setDisable(true);
            } else {
                btnEliminar.setDisable(false);

            }
            txtId.setText(String.valueOf(emple.getIdempleado()));
            txtNombre.setText(emple.getNombre());
            txtTel.setText(emple.getTelefono());
            txtDire.setText(emple.getDireccion());
            txtCedula.setText(emple.getCedula());
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtCedula.setDisable(false);
        txtId.setDisable(true);
        txtNombre.setDisable(false);
        txtDire.setDisable(false);
        txtTel.setDisable(false);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(false);
        btnModificar.setDisable(true);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        modificar = true;

    }

    @FXML
    private void cancelar() {
        txtCedula.setDisable(true);
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtDire.setDisable(true);
        txtTel.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(true);
        btnNuevo.setDisable(false);
        txtNombre.clear();
        txtId.clear();
        txtTel.clear();
        txtDire.clear();
        txtCedula.clear();
        btnCancelar.setDisable(true);
        tablaEmpleado.setDisable(false);

    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            int idEmpleado = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String telefono = txtTel.getText().trim();
            String direccion = txtDire.getText().trim();
            String cedula = txtCedula.getText().trim();

            if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || cedula.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de validación", "Todos los campos son obligatorios.");
                return;
            }

            Empleado empleado = new Empleado(idEmpleado, nombre, telefono, direccion, cedula, cedula);

            if (modificar) {
                if (empleado.modificar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Empleado modificado con éxito");
                    modificar = false;
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "Error modificando el empleado");
                }
            } else {
                if (empleado.insertar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Empleado registrado correctamente");
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El empleado no pudo ser registrado.");
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos.");
        }
        cancelar();
        mostrarDatos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtTel.clear();
        txtCedula.clear();
        txtDire.clear();
    }

    public void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }

    @FXML
    private void eliminarEmpleado(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("El Sistema comunica:");
        a.setHeaderText(null);
        a.setContentText("Desea eliminar los datos de este empleado");
        Optional<ButtonType> option = a.showAndWait();
        if (option.get() == ButtonType.OK) {
            int codigo = Integer.parseInt(txtId.getText());
            e.setIdempleado(codigo);
            if (e.borrar()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "El Sistema comunica", "Datos del empleado eliminado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El Sistema comunica", "ERROR!! Los Datos del empleado no se pudieron eliminar");
            }
        }
        mostrarDatos();
        cancelar();
    }

    @FXML
    private void nuevo(ActionEvent event) {
        btnCancelar.setDisable(false);
        txtId.setDisable(false);
        txtNombre.setDisable(false);
        txtTel.setDisable(false);
        txtDire.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        tablaEmpleado.setDisable(true);
        txtCedula.setDisable(false);
    }

}
