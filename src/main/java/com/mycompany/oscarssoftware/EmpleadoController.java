/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.CategoriaProducto;
import com.mycompany.oscarssoftware.modelos.Empleado;
import com.mycompany.oscarssoftware.modelos.Producto;
import com.mycompany.oscarssoftware.modelos.Proveedor;
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


public class EmpleadoController implements Initializable {
    private Empleado e = new Empleado();
    private boolean modificar = false;
    ObservableList<Empleado> Empleados;
    
    private String[] header={"Id","Nombre","Telefono","Direccion"};
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }  
    
    public void mostrarDatos() {
        Empleados = FXCollections.observableArrayList(e.consulta()); //aca se crea una lista observable que va a ser cargada a la tabla, la parte de p.consulta() trae todos los elementos de Producto como un ArrayLists
        tablaEmpleado.getItems().clear(); //vaciamos la lista antigua por si hay datos que en la vista conflictuan
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idempleado"));//Carga en la casilla de ID el id
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));//igual
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));//igual
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));//igual
        tablaEmpleado.setItems(Empleados);//en la tabla general carga la lista ya completa
    }
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    try {
        int idEmpleado = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        String telefono = txtTel.getText();
        String direccion = txtDire.getText();

        Empleado empleado = new Empleado(idEmpleado, nombre, telefono, direccion );

        if (modificar) {
            if (empleado.modificar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Empleado modificado con exito");
                modificar = false;
                txtId.clear();
                txtNombre.clear();
                txtTel.clear();
                txtDire.clear();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "EL sistema comunica", "Error modificando el empleado");
            }
            modificar = false;
        } else {
            if (empleado.insertar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Empleado registrado correctamente");
                txtId.clear();
                txtNombre.clear();
                txtTel.clear();
                txtDire.clear();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El empleado no pudo ser registrado.");
            }
        }
    } catch (NumberFormatException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos.");
    } catch (IllegalArgumentException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error de valor", e.getMessage());
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
    
    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void vaciar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    }
}