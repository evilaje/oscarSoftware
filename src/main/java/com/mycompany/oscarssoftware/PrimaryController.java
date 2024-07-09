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
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Producto, Double> columnPrecio;
    @FXML
    private TableColumn<Producto, Integer> columnStock;
    @FXML
    private TableColumn<Producto, String> columnCategoria;
    

    
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
    //variables
    private Producto p = new Producto();
    private CategoriaProducto catprod = new CategoriaProducto();
    private Proveedor prov = new Proveedor();
    //combos y textfields
    @FXML
    private ComboBox<String> comboProveedores;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<String> filtroCategoria;
    @FXML
    private ComboBox<String> filtroProveedor;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    private boolean modificar = false;
    @FXML
    private Button btnNuevo;
    
    ObservableList<CategoriaProducto> listaCategorias;
    ObservableList<Proveedor> listaProveedores;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnReestablecer;
    

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
    try {
        int idProducto = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double precio = Double.parseDouble(txtPrecio.getText());
        String categoriaSeleccionada = comboCategoria.getValue();
        String proveedorSeleccionado = comboProveedores.getValue();
        int idCat = obtenerCategoria(categoriaSeleccionada);
        int idProv = obtenerProveedor(proveedorSeleccionado);
        if (cantidad < 1 || precio < 1) {
            throw new IllegalArgumentException("Los valores numéricos no pueden ser negativos");
        }
        p.setIdproducto(idProducto);
        p.setNombre(nombre);
        p.setCantidad(cantidad);
        p.setPrecio((float) precio);
        p.setIdCategoriaProducto(idCat);
        p.setIdProveedor(idProv);
        
        
        
        if (modificar) {
            if (p.modificiar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Producto modificado con exito");
                modificar = false;
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "EL sistema comunica", "Error modificando el producto");
            }
            modificar = false;
        } else {
            if (p.insertar()) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Producto ingresado correctamente");
                txtNombre.clear();
                txtCantidad.clear();
                txtPrecio.clear();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "El producto no pudo ser ingresado.");
            }
        }
    } catch (NumberFormatException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingresa valores numéricos válidos.");
    } catch (IllegalArgumentException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error de valor", e.getMessage());
    }

    mostrarDatos();
    vaciar();
}

    
    //esta funcion carga los combobox de categorias
    public void cargarCategorias() {
        comboCategoria.setPromptText("Seleccione categoria");
        filtroCategoria.setPromptText("Filtrar por categoria");
        listaCategorias = FXCollections.observableArrayList(catprod.consulta());

        filtroCategoria.getItems().clear();
        filtroCategoria.getItems().add(null); // Opción para no filtrar por categoría
        comboCategoria.getItems().clear();
        comboCategoria.getItems().add(null); // Opción para no seleccionar ninguna categoría

        if (listaCategorias.isEmpty()) {
            comboCategoria.getItems().add("No se tienen categorias.");
            filtroCategoria.getItems().add("No se tienen categorias");
        } else {
            for (CategoriaProducto categoria : listaCategorias) {
                comboCategoria.getItems().add(categoria.getNombreCategoria());
                filtroCategoria.getItems().add(categoria.getNombreCategoria());
            }
        }
    }

    public void cargarProveedores() {
        comboProveedores.setPromptText("Seleccione un proveedor.");
        filtroProveedor.setPromptText("Filtrar por proveedor");
        listaProveedores = FXCollections.observableArrayList(prov.consulta());

        filtroProveedor.getItems().clear();
        filtroProveedor.getItems().add(null); // Opción para no filtrar por proveedor
        comboProveedores.getItems().clear();
        comboProveedores.getItems().add(null); // Opción para no seleccionar ningún proveedor

        if (listaProveedores.isEmpty()) {
            comboProveedores.getItems().add("No se tienen proveedores.");
            filtroProveedor.getItems().add("No se tienen proveedores.");
        } else {
            for (Proveedor p : listaProveedores) {
                comboProveedores.getItems().add(p.getNombre());
                filtroProveedor.getItems().add(p.getNombre());
            }
        }
    }

    //esta funcion recibe un nombre (texto)de categoria y devuelve la categoria (objeto) en cuestion
    //si existe, y si no, retorna null (vacio)
    public int obtenerCategoria(String nombreCategoria) {
        ArrayList<CategoriaProducto> buscarCategorias = catprod.consulta();
        for (CategoriaProducto categoria : buscarCategorias) {
            if (categoria.getNombreCategoria().equals(nombreCategoria)) {
                return categoria.getIdCategoria();
            }
        }
        return 0; // Retorna 0 si no se encuentra la categoría
    }
    
    //esta funcion recibe un nombre (texto) de proveedor y devuelve el proveedor (objeto) en cuestion
    //si existe, y si no, devuelve null (vacio)
    //misma logica que arriba
    public int obtenerProveedor(String nombreProveedor) {
        ArrayList<Proveedor> listaBuscarProv = prov.consulta();
        for (Proveedor p : listaBuscarProv) {
            if (p.getNombre().equals(nombreProveedor)) {
                return p.getIdproveedor();
            }
        }
        return 0; // Retorna 0 si no se encuentra el proveedor
    }

    //esta funcion se encarga de dejar cambos y combos a su estado inicial
    @FXML
    public void vaciar() {
        txtId.clear();
        txtNombre.setDisable(true);
        txtPrecio.setDisable(true);
        txtCantidad.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(true);
        btnNuevo.setDisable(false);
        //limpiar los campos de texto
        txtNombre.clear();
        txtCantidad.clear();
        txtPrecio.clear();
        //limpiar los combos de seleccion y reestablecer el texto
        comboCategoria.setValue(null);
        comboProveedores.setValue(null);
        comboCategoria.setDisable(true);
        comboProveedores.setDisable(true);
    }

    //esta funcion se encarga de mostrar alertas
    public void mostrarAlerta (Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }

    @FXML
    private void eliminarProveedor(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("El Sistema comunica:");
        a.setHeaderText(null);
        a.setContentText("Desea Eliminar este producto");
        Optional<ButtonType> option = a.showAndWait();
        if(option.get() == ButtonType.OK){
            int codigo = Integer.parseInt(txtId.getText());
            p.setIdproducto(codigo);
            if(p.borrar()){
                mostrarAlerta(Alert.AlertType.INFORMATION, "El Sistema comunica", "Producto eliminado correctamente");
            }else{
                mostrarAlerta(Alert.AlertType.ERROR, "El SIstema comunica", "ERROR!! El producto no se pudo eliminar");
            }
        }
        mostrarDatos();
        vaciar();
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        btnCancelar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnNuevo.setDisable(true);

        Producto pr = tablaProductos.getSelectionModel().getSelectedItem();
        if (pr != null) {
            comboCategoria.setValue(pr.getNombreCategoria());
            comboProveedores.setValue(pr.getNombreProveedor());
            txtNombre.setText(pr.getNombre());
            txtCantidad.setText(String.valueOf(pr.getCantidad()));
            txtPrecio.setText(String.valueOf(pr.getPrecio()));
            txtId.setText(String.valueOf(p.getIdproducto()));
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        modificar = true;
        comboCategoria.setDisable(false);
        comboProveedores.setDisable(false);
        txtCantidad.setDisable(false);
        txtNombre.setDisable(false);
        txtPrecio.setDisable(false);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        

    }

    @FXML
    private void nuevo(ActionEvent event) {
        btnCancelar.setDisable(false);
        comboCategoria.setDisable(false);
        comboProveedores.setDisable(false);
        cargarCategorias();
        cargarProveedores();
        txtCantidad.setDisable(false);
        txtNombre.setDisable(false);
        txtPrecio.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        
    }        

    @FXML
    private void search(ActionEvent event) {
        btnReestablecer.setDisable(false);
        String buscar = txtBuscar.getText().toLowerCase();
        String filtroCategoriaSeleccionada = filtroCategoria.getValue();
        String filtroProveedorSeleccionado = filtroProveedor.getValue();

        ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList();

        for (Producto producto : Productos) {
            boolean matchesNombre = buscar.isEmpty() || producto.getNombre().toLowerCase().contains(buscar);
            boolean matchesCategoria = filtroCategoriaSeleccionada == null || filtroCategoriaSeleccionada.equals(producto.getNombreCategoria());
            boolean matchesProveedor = filtroProveedorSeleccionado == null || filtroProveedorSeleccionado.equals(producto.getNombreProveedor());

            if (matchesNombre && matchesCategoria && matchesProveedor) {
                productosFiltrados.add(producto);
            }
        }

        tablaProductos.setItems(productosFiltrados);

    }

    @FXML
    private void reestablecer(ActionEvent event) {
        txtBuscar.clear();
        filtroCategoria.getSelectionModel().clearSelection();
        filtroProveedor.getSelectionModel().clearSelection();
        mostrarDatos();
        btnReestablecer.setDisable(true);
    }

}
