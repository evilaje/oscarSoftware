package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.util.AtajosTecladoUtil;
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

/**
 * FXML Controller class
 *
 * @author Orne
 */
public class ClienteController implements Initializable {

    private Cliente c = new Cliente();
    private boolean modificar = false;
    ObservableList<Cliente> Clientes;
    private Pedido p = new Pedido();

    private String[] header = {"Nombre", "Telefono", "Ruc", "Direccion"};
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtRuc;
    @FXML
    private TextField txtDire;
    @FXML
    private TableView<Cliente> tablaCliente;
    @FXML
    private TableColumn<Cliente, String> columnaNom;
    @FXML
    private TableColumn<Cliente, String> columnaTel;
    @FXML
    private TableColumn<Cliente, Integer> columnaRuc;
    @FXML
    private TableColumn<Cliente, String> columnaDire;
    @FXML
    private Button btnNuevo;
    @FXML
    private Pane side_ankerpane;
    @FXML
    private TextField txtId;
    @FXML
    private AnchorPane root;

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
        tablaCliente.refresh();
        Clientes = FXCollections.observableArrayList(c.consulta()); //aca se crea una lista observable que va a ser cargada a la tabla, la parte de p.consulta() trae todos los elementos de Producto como un ArrayLists
        tablaCliente.getItems().clear(); //vaciamos la lista antigua por si hay datos que en la vista conflictuan
        columnaNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));//Carga en la casilla de ID el id
        columnaTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));//igual
        columnaRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));//igual
        columnaDire.setCellValueFactory(new PropertyValueFactory<>("direccion"));//igual
        tablaCliente.setItems(Clientes);
    }

    private void switchToSecondary() throws IOException {
        App.setRoot("menu", 780, 460);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        //desactivar botonesi

        btnCancelar.setDisable(false);
        btnModificar.setDisable(false);
        txtNombre.setDisable(true);
        btnNuevo.setDisable(true);
        Cliente clien = tablaCliente.getSelectionModel().getSelectedItem();
        if (clien != null) {
            //verificar si el cliente tiene pedidos (si los tiene no se debe eliminar)
            if (p.clienteExiste(clien.getId())) {
                btnEliminar.setDisable(true);
            } else {
                btnEliminar.setDisable(false);
            }
            txtNombre.setText(clien.getNombre());
            txtTel.setText(String.valueOf(clien.getTelefono()));
            txtRuc.setText(clien.getRuc());
            txtId.setText(String.valueOf(clien.getId()));
            txtDire.setText(String.valueOf(clien.getDireccion()));
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            String ruc = txtRuc.getText().trim();
            String nombre = txtNombre.getText().trim();
            String telefono = txtTel.getText().trim();
            String direccion = txtDire.getText().trim();

            if (ruc.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de validación", "Todos los campos son obligatorios.");
                return;
            }

            c.setRuc(ruc);
            c.setNombre(nombre);
            c.setDireccion(direccion);
            c.setTelefono(telefono);

            if (modificar) {
                int id = Integer.parseInt(txtId.getText().trim());
                c.setId(id);

                if (c.modificar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Cliente modificado con éxito");
                    modificar = false;
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "Error modificando el cliente");
                }
            } else {
                if (c.insertar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Cliente registrado correctamente");
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El cliente no pudo ser registrado.");
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos en el ID.");
        }
        cancelar(event);
        mostrarDatos();
    }

    private void limpiarCampos() {
        txtRuc.clear();
        txtNombre.clear();
        txtTel.clear();
        txtDire.clear();
        txtId.clear();
    }

    public void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtRuc.setDisable(true);
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
    private void eliminar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("El Sistema comunica:");
        a.setHeaderText(null);
        a.setContentText("Desea eliminar los datos de este cliente?");
        Optional<ButtonType> option = a.showAndWait();
        if (option.get() == ButtonType.OK) {
            int codigo = Integer.parseInt(txtId.getText());
            c.setId(codigo);
            if (c.borrar()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "El Sistema comunica", "Datos del cliente eliminado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El Sistema comunica", "ERROR!! Los Datos del cliente no se pudieron eliminar");
            }
        }
        mostrarDatos();
        cancelar(event);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtRuc.setDisable(true);
        txtNombre.setDisable(true);
        txtDire.setDisable(true);
        txtTel.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(true);
        btnNuevo.setDisable(false);
        txtNombre.clear();
        txtRuc.clear();
        txtTel.clear();
        txtDire.clear();
        btnCancelar.setDisable(true);
        tablaCliente.getSelectionModel().clearSelection();
    }

    @FXML
    private void nuevo(ActionEvent event) {
        btnCancelar.setDisable(false);
        txtRuc.setDisable(false);
        txtNombre.setDisable(false);
        txtTel.setDisable(false);
        txtDire.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
    }

}
