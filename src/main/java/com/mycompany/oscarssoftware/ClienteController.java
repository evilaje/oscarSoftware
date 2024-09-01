package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
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
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Orne
 */
public class ClienteController implements Initializable {

    private Cliente c = new Cliente();
    private boolean modificar = false;
    ObservableList<Cliente> Clientes;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
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
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        txtNombre.setDisable(true);
        btnNuevo.setDisable(true);
        Cliente clien = tablaCliente.getSelectionModel().getSelectedItem();
        if (clien != null) {
            txtNombre.setText(clien.getNombre());
            txtTel.setText(String.valueOf(clien.getTelefono()));
            txtRuc.setText(clien.getRuc());
            txtId.setText(String.valueOf(clien.getId()));
            txtDire.setText(String.valueOf(clien.getDireccion()));
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
        try {
            String ruc = txtRuc.getText();
            String nombre = txtNombre.getText();
            String telefono = txtTel.getText();
            String direccion = txtDire.getText();
            c.setNombre(nombre);
            c.setDireccion(direccion);
            c.setTelefono(telefono);
            c.setRuc(ruc);

            if (modificar) {
                int id = Integer.parseInt(txtId.getText());
                c.setId(id);
                if (c.modificar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Cliente modificado con exito");
                    modificar = false;
                    txtNombre.clear();
                    txtRuc.clear();
                    txtTel.clear();
                    txtDire.clear();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "EL sistema comunica", "Error modificando el cliente");
                }
                modificar = false;
            } else {
                if (c.insertar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Cliente registrado correctamente");
                    txtNombre.clear();
                    txtRuc.clear();
                    txtTel.clear();
                    txtDire.clear();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El cliente no pudo ser registrado.");
                }
            }
        } catch (NumberFormatException c) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos.");
        } catch (IllegalArgumentException c) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de valor", c.getMessage());
        }
        cancelar(event);
        mostrarDatos();

        mostrarDatos();
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
