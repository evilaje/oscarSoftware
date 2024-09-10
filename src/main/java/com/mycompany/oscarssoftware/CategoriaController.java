package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.CategoriaProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class CategoriaController implements Initializable {

    private CategoriaProducto categoria = new CategoriaProducto();
    @FXML
    private TextField txtId;
    @FXML
    private TableView<CategoriaProducto> tablaCat;
    @FXML
    private TableColumn<CategoriaProducto, String> cat;
    @FXML
    private TextField txtNombreCat;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    private ObservableList<CategoriaProducto> listaCategorias;
    @FXML
    private Button btnNuevo;
    boolean modificar = false;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la lista de categorías
        mostrar();
        // Deshabilitar el campo ID, ya que normalmente es autogenerado
        txtId.setDisable(true);
    }

    private void mostrar() {
        listaCategorias = FXCollections.observableArrayList(categoria.consulta());

        // Configurar la columna de la tabla
        cat.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));

        // Vincular la tabla con la lista observable
        tablaCat.setItems(listaCategorias);

    }

    @FXML
    private void select(MouseEvent event) {
        // Obtener la categoría seleccionada en la tabla
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(true);
        btnNuevo.setDisable(true);
        CategoriaProducto selectedCat = tablaCat.getSelectionModel().getSelectedItem();
        if (selectedCat != null) {
            txtId.setText(String.valueOf(selectedCat.getIdCategoria()));
            txtNombreCat.setText(selectedCat.getNombreCategoria());
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        // Verificar que el nombre no esté vacío
        if (txtNombreCat.getText().isEmpty()) {
            mostrarAlerta("Error", "El nombre de la categoría no puede estar vacío.");
            return;
        }

        // Crear un nuevo objeto CategoriaProducto
        CategoriaProducto nuevaCategoria = new CategoriaProducto();
        nuevaCategoria.setNombreCategoria(txtNombreCat.getText());
        if (modificar) {
            nuevaCategoria.setIdCategoria(Integer.parseInt(txtId.getText()));
            if (nuevaCategoria.modificar()) {
                mostrarConfirmacion("El sistema comunica", "Categoria modificada con exito");
                cancelar(event);
            } else {
                mostrarAlerta("El sistema comunica", "La categoria no pudo ser modificada");
            }
            modificar = false;
        } else {
            if (nuevaCategoria.insertar()) {
                mostrarConfirmacion("El sistema comunica", "Categoria insertada con exito");
                cancelar(event);
            } else {
                mostrarAlerta("El sistema comunica", "La categoria no pudo ser insertada");
            }
        }
        mostrar();
        // Limpiar los campos
        limpiarCampos();
    }

    @FXML
    private void modificar(ActionEvent event) {
        modificar = true;
        txtNombreCat.setDisable(false);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
        // Verificar que una categoría esté seleccionada
        CategoriaProducto selectedCat = tablaCat.getSelectionModel().getSelectedItem();
        if (selectedCat == null) {
            mostrarAlerta("Error", "No hay ninguna categoría seleccionada.");
            return;
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        // Verificar que una categoría esté seleccionada
        CategoriaProducto selectedCat = tablaCat.getSelectionModel().getSelectedItem();
        if (selectedCat == null) {
            mostrarAlerta("Error", "No hay ninguna categoría seleccionada.");
            return;
        }

        // Confirmar la eliminación
        Optional<ButtonType> result = mostrarConfirmacion("Confirmación", "¿Estás seguro de que quieres eliminar esta categoría?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Eliminar la categoría de la lista
            if (selectedCat.borrar()) {
                mostrarConfirmacion("El sistema comunica", "Categoria borrada con exito");
                limpiarCampos();
            } else {
                mostrarAlerta("El sistema comunica", "La categoria no pudo ser borrada");
            }
            // Limpiar los campos

        }
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombreCat.clear();
        tablaCat.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle(titulo);
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(mensaje);
        return confirmacion.showAndWait();
    }

    @FXML
    private void nuevo(ActionEvent event) {
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        txtNombreCat.setDisable(false);
        btnGuardar.setDisable(false);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        btnNuevo.setDisable(false);
        btnCancelar.setDisable(true);
        btnGuardar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        txtNombreCat.setDisable(true);
        txtNombreCat.clear();
        txtId.clear();
    }
}
