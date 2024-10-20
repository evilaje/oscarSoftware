/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.Producto;
import com.mycompany.oscarssoftware.modelos.Proveedor;
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
//orne riego esta hermosa como siempre
import javafx.scene.layout.Pane;

public class ProveedorController implements Initializable {

    private Proveedor p = new Proveedor();
    private boolean modificar = false;
    ObservableList<Proveedor> Proveedores;
    private Producto pr = new Producto();

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDire;
    @FXML
    private TableView<Proveedor> tablaProveedor;
    private TableColumn<Proveedor, Integer> columnaId;
    @FXML
    private TableColumn<Proveedor, String> columnaNombre;
    @FXML
    private TableColumn<Proveedor, String> columnaTel;
    @FXML
    private TableColumn<Proveedor, String> columnaDire;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Pane side_ankerpane;
    @FXML
    private TextField txtEmail;
    @FXML
    private TableColumn<Proveedor, String> columnaEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mostrarDatos();
    }

    public void mostrarDatos() {
        Proveedores = FXCollections.observableArrayList(p.consulta());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnaDire.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tablaProveedor.setItems(Proveedores);
    }

    @FXML
    private void nuevo(ActionEvent event) {

        btnCancelar.setDisable(false);
        txtNombre.setDisable(false);
        txtTel.setDisable(false);
        txtDire.setDisable(false);
        txtEmail.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTel.getText().trim();
            String direccion = txtDire.getText().trim();
            String email = txtEmail.getText().trim();

            if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de validación", "Todos los campos son obligatorios.");
                return;
            }

            p.setNombre(nombre);
            p.setTelefono(telefono);
            p.setDireccion(direccion);
            p.setEmail(email);

            if (modificar) {
                int id = Integer.parseInt(txtId.getText().trim());
                p.setIdproveedor(id);

                if (p.modificar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Proveedor modificado con éxito");
                    modificar = false;
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "Error modificando el proveedor");
                }
            } else {
                if (p.insertar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Proveedor registrado correctamente");
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El proveedor no pudo ser registrado.");
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos.");
        }
        cancelar(event);
        mostrarDatos();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtTel.clear();
        txtDire.clear();
        txtEmail.clear();
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
    private void eliminar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("El Sistema comunica:");
        a.setHeaderText(null);
        a.setContentText("Desea eliminar los datos de este proveedor");
        Optional<ButtonType> option = a.showAndWait();
        if (option.get() == ButtonType.OK) {
            int codigo = Integer.parseInt(txtId.getText());
            p.setIdproveedor(codigo);
            if (p.borrar()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "El Sistema comunica", "Datos del proveedor eliminados correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "El Sistema comunica", "ERROR!! Los Datos del proveedor no se pudieron eliminar");
            }
        }
        mostrarDatos();
        cancelar(event);
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtId.setDisable(true);
        txtNombre.setDisable(false);
        txtDire.setDisable(false);
        txtTel.setDisable(false);
        txtEmail.setDisable(false);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(false);
        btnModificar.setDisable(true);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        modificar = true;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtDire.setDisable(true);
        txtTel.setDisable(true);
        txtEmail.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(true);
        btnNuevo.setDisable(false);
        txtNombre.clear();
        txtId.clear();
        txtTel.clear();
        txtDire.clear();
        txtEmail.clear();
        btnCancelar.setDisable(true);
        tablaProveedor.getSelectionModel().clearSelection();
    }

    @FXML
    private void mostrarfila(MouseEvent event) {
        btnCancelar.setDisable(false);
        
        btnModificar.setDisable(false);
        btnNuevo.setDisable(true);
        txtNombre.setDisable(true);
        txtDire.setDisable(true);
        txtEmail.setDisable(true);
        txtTel.setDisable(true);
        Proveedor provee = (Proveedor) tablaProveedor.getSelectionModel().getSelectedItem();
        if (provee != null) {
            
            if (pr.existeProveedor(provee.getIdproveedor())) {
                btnEliminar.setDisable(true);
            } else {
                btnEliminar.setDisable(false);
            }
            txtNombre.setText(provee.getNombre());
            txtTel.setText(String.valueOf(provee.getTelefono()));
            txtId.setText(String.valueOf(provee.getIdproveedor()));
            txtDire.setText(String.valueOf(provee.getDireccion()));
            txtEmail.setText(provee.getEmail());

        }

    }

}
