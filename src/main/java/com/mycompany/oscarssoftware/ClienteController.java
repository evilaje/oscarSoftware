/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Cliente;
import com.mycompany.oscarssoftware.modelos.Empleado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Orne
 */
public class ClienteController implements Initializable {
    private Cliente c = new Cliente();
    private boolean modificar = false;
    ObservableList<Cliente> Clientes;
    
    private String[] header={"Nombre","Telefono","Ruc","Direccion"};
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }    
    
     public void mostrarDatos() {
        Clientes = FXCollections.observableArrayList(c.consulta()); //aca se crea una lista observable que va a ser cargada a la tabla, la parte de p.consulta() trae todos los elementos de Producto como un ArrayLists
        tablaCliente.getItems().clear(); //vaciamos la lista antigua por si hay datos que en la vista conflictuan
        columnaNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));//Carga en la casilla de ID el id
        columnaTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));//igual
        columnaRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));//igual
        columnaDire.setCellValueFactory(new PropertyValueFactory<>("direccion"));//igual
        tablaCliente.setItems(Clientes);
    }
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void guardar(ActionEvent event) {
    try {
        int ruc = Integer.parseInt(txtRuc.getText());
        String nombre = txtNombre.getText();
        String telefono = txtTel.getText();
        String direccion = txtDire.getText();

        Cliente cliente = new Cliente(nombre, telefono, ruc, direccion );

        if (modificar) {
            if (cliente.modificar()) {
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
            if (cliente.insertar()) {
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

    mostrarDatos();   
    }
     public void mostrarAlerta (Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }
    
    private void mostrarFila(){
    }

}
