/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.App;
import com.mycompany.oscarssoftware.modelos.CategoriaProducto;
import com.mycompany.oscarssoftware.modelos.Producto;
import com.mycompany.oscarssoftware.modelos.Proveedor;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class PrimaryController implements Initializable {


    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> columnId;
    @FXML
    private TableColumn<Producto, String> columnNombre;
    @FXML
    private TableColumn<Producto, Float> columnPrecio;
    @FXML
    private TableColumn<Producto, Integer> columnStock;
    @FXML
    private TableColumn<Producto, String> columnCategoria;
    
    private Producto p = new Producto();
    private CategoriaProducto catprod = new CategoriaProducto();
    
    ObservableList<Producto> Productos;
    @FXML
    private ComboBox<String> comboCategoria;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableColumn<Producto, String> columnProveedor;
    
    private Proveedor prov = new Proveedor();
    @FXML
    private ComboBox<String> comboProveedores;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<String> filtroCategoria;
    @FXML
    private ComboBox<String> filtroProveedor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProveedores(); //cargamos los proveedores
        cargarCategorias(); //cargamos las categorias
        mostrarDatos(); //Mostramos la tabla
    }   
    //esta funcion se encarga de mostrar cada dato de Producto en la tabla
    public void mostrarDatos() {
        Productos = FXCollections.observableArrayList(p.consulta()); //aca se crea una lista observable que va a ser cargada a la tabla, la parte de p.consulta() trae todos los elementos de Producto como un ArrayLists
        tablaProductos.getItems().clear(); //vaciamos la lista antigua por si hay datos que en la vista conflictuan
        columnId.setCellValueFactory(new PropertyValueFactory<>("idproducto"));//Carga en la casilla de ID el id
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));//igual
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));//igual
        columnStock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));//igual
        columnCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));//igual
        columnProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor")); //igual
        tablaProductos.setItems(Productos);//en la tabla general carga la lista ya completa
        
    }
    //esta funcion no hace nada vino con el controlador me da paja sacar capaz sirve
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    //Funcion para guardar los datos ingresados
    @FXML
    private void guardar(ActionEvent event) {
        //metemos todo en un try-catch por si algo de eso falla
        //la carga de datos no se hace a medias
        //evitamos problemas de atomicidad
        try {
            int idProducto = Integer.parseInt(txtId.getText());//obtiene el dato del campo de texto
            String nombre = txtNombre.getText();//igual
            int cantidad = Integer.parseInt(txtCantidad.getText());
            float precio = Float.parseFloat(txtPrecio.getText());//igual
            //este if verifica si los valores cumplen en terminos de numeros
            if (idProducto < 1 || cantidad < 1 || precio < 1) {
                throw new IllegalArgumentException("Los valores numéricos no pueden ser negativos");
                //lanzamos un mensaje de error que sera captado por el catch
            }
            CategoriaProducto cat = null;//creamos la variable que usaremos luego, por ahora sin valores
            Proveedor pr = null; //creamos la variable que usaremos luego, por ahora sin valores
            //verificamos que el nombre recibido exista en la tabla de categorias
            if (obtenerCategoria(catprod.consulta(), comboCategoria.getSelectionModel().getSelectedItem()) != null) { //verifica que el nombre seleccionado en el combo exista en el ArrayList de categorias
                //el nombre nos lo da el propio combobox, es el item que este seleccionado
                cat = obtenerCategoria(catprod.consulta(), comboCategoria.getSelectionModel().getSelectedItem()); // Obtiene la categoria que tenga el nombre seleccionado
                System.out.println(cat); //print para ver si anda
            }
            //verificamos que el nombre recibido exista en los proveedores
            if (obtenerProveedor(prov.consulta(), comboProveedores.getSelectionModel().getSelectedItem()) != null) {
                //obtenemo el proveedor con el nombre seleccionado
                //el nombre nos lo da el combobox, es el item que esta seleccionado
                pr = obtenerProveedor(prov.consulta(), comboProveedores.getSelectionModel().getSelectedItem());
            }
            //creamos un objeto Producto con los valores que acabamos de obtener
            Producto producto = new Producto(idProducto, nombre, precio, cantidad, cat.getIdCategoria(), pr.getIdproveedor(), cat.getNombreCategoria(), pr.getNombre());
            if (producto.insertar()) { //verificamos si se pudo insertar el objeto creado
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Producto ingresado correctamente");
                //lanzamos mensaje de exito
                vaciar();
                //vaciamos todo
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El producto no pudo ser ingresado.");
                //si no se pudo cargar, hubo un error en la base de datos, lanzamos
                //respectivo mensaje
            }  
            
        } catch (NumberFormatException e) { //zona de catchs, es decir, donde atrapamos los erroes
            //atrapamos error de formato, quiere decir, el error que se genera si el usuario coloca palabras
            //en los campos donde deben ir numeros
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos.");
            //mostramos mensaje 
        } catch (IllegalArgumentException e) {
            //atrapamos el error que mandamos antes si uno de los valores numericos no cumple con los minimos requeridos
            mostrarAlerta(Alert.AlertType.ERROR,"Error de valor", e.getMessage());
            //lanzamos mensaje
        }
        //mostramos los datos
        mostrarDatos();
    }
    
    //esta funcion carga los combobox de categorias
    public void cargarCategorias() {
        ArrayList<CategoriaProducto> categorias = catprod.consulta();
        if (categorias.isEmpty()) {
            comboCategoria.getItems().add("No se tienen categorias.");
            filtroCategoria.getItems().add("No se tienen categorias");
        } else {
            comboCategoria.getItems().clear();
            for (CategoriaProducto categoria : categorias) {
                comboCategoria.getItems().add(categoria.getNombreCategoria());
                filtroCategoria.getItems().add(categoria.getNombreCategoria());
            }
        }
    }
    //esta funcion carga los combobox de proveedores con los nombres de los proveedores
    public void cargarProveedores() {
        ArrayList<Proveedor> proveedores = prov.consulta();
        if (proveedores.isEmpty()) {
            comboProveedores.getItems().add("No se tienen proveedores.");
            filtroProveedor.getItems().add("No se tienen proveedores.");
        } else {
            comboProveedores.getItems().clear();
            filtroProveedor.getItems().clear();
            for (Proveedor p : proveedores) {
                comboProveedores.getItems().add(p.getNombre());
                filtroProveedor.getItems().add(p.getNombre());
            }
        }
    }
    
    //esta funcion recibe un nombre (texto)de categoria y devuelve la categoria (objeto) en cuestion
    //si existe, y si no, retorna null (vacio)
    public CategoriaProducto obtenerCategoria (ArrayList<CategoriaProducto> categorias, String nombreCategoria) {
        for (CategoriaProducto categoria : categorias) { //for especial que recorre objeto sin indice
            if (categoria.getNombreCategoria().equals(nombreCategoria)) { //vemos si el nombre del objeto actual coincide con el nombre que le entregamos
                return categoria; //si es asi, retornamos el objeto en cuestion ya que es el que queremos
            }
        }
        return null; //si el for termina sin retornar nada, significa que no existe y no retorna
    }
    
    //esta funcion recibe un nombre (texto) de proveedor y devuelve el proveedor (objeto) en cuestion
    //si existe, y si no, devuelve null (vacio)
    //misma logica que arriba
    public Proveedor obtenerProveedor (ArrayList<Proveedor> proveedores, String nombreProv) {
        for (Proveedor p : proveedores) {
            if (p.getNombre().equals(nombreProv))
                return p;
        }
        return null;
    }
    
    //esta funcion se encarga de dejar cambos y combos a su estado inicial
    public void vaciar() {
        //limpiar los campos de texto
        txtNombre.clear();
        txtCantidad.clear();
        txtId.clear();
        txtPrecio.clear();
        //limpiar los combos de seleccion
        comboCategoria.getSelectionModel().clearSelection();
        comboProveedores.getSelectionModel().clearSelection();
        //reestablecer el texto de los combos
        comboCategoria.setPromptText("Seleccione una categoría");
        comboProveedores.setPromptText("Seleccione un proveedor");
        
    }
    //esta funcion se encarga de mostrar alertas
    public void mostrarAlerta (Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }
    
    
        
        
}
