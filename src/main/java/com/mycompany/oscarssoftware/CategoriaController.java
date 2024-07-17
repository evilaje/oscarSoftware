    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
     */
    package com.mycompany.oscarssoftware;

    import com.mycompany.oscarssoftware.modelos.CategoriaProducto;
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

    /**
     * FXML Controller class
     *
     * @author Anibal
     */
    public class CategoriaController implements Initializable {

        private CategoriaProducto categoriaProducto = new CategoriaProducto();
        private boolean modificar;
        @FXML
        private Button btnNuevo;
        @FXML
        private TextField txtNombre;
        @FXML
        private TextField txtBuscar;
        @FXML
        private Button btnGuardar;
        @FXML
        private Button btnModificar;
        @FXML
        private Button btnEliminar;
        @FXML
        private Button btnCancelar;
        @FXML
        private TextField txtId;
        @FXML
        private TableView<CategoriaProducto> tablaCategorias;
        @FXML
        private TableColumn<CategoriaProducto, String> colCategorias;
        ObservableList <CategoriaProducto> categorias;

        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            mostrarDatos();
        }    

        @FXML
        private void search(ActionEvent event) {
            String buscar = txtBuscar.getText().toLowerCase();
            System.out.println(buscar);
            ObservableList<CategoriaProducto> categoriasFiltradas = FXCollections.observableArrayList();
            for (CategoriaProducto categoria : categorias) {
                String nombreCategoria = categoria.getNombreCategoria().toLowerCase();
                if (nombreCategoria.contains(buscar))
                    categoriasFiltradas.add(categoria);
            }
            tablaCategorias.setItems(categoriasFiltradas);
        }

        @FXML
        private void guardar(ActionEvent event) {
            String nombre = txtNombre.getText();
            categoriaProducto.setNombreCategoria(nombre);
            if (modificar) {
                int id = Integer.parseInt(txtId.getText());
                categoriaProducto.setIdCategoria(id);
                if (categoriaProducto.modificar()) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Categoria modificada correctamente");

                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "La categoria no pudo ser modificada");
                }
                modificar = false;
            } else {
                if(categoriaProducto.insertar() && nombre != null && !nombre.isEmpty()){
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, "El sistema comunica", "Categoria insertada correctamente");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica", "La categoria no pudo ser ingresada");
                }
            }
            cancelar(event);
            mostrarDatos();
        }

        @FXML
        private void modificar(ActionEvent event) {
            btnModificar.setDisable(true);
            btnNuevo.setDisable(true);
            btnGuardar.setDisable(false);
            txtNombre.setDisable(false);
            btnEliminar.setDisable(true);
            modificar = true;

        }

        @FXML
        private void eliminar(ActionEvent event) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("El Sistema comunica:");
            a.setHeaderText(null);
            a.setContentText("Desea Eliminar este producto");
            Optional<ButtonType> option = a.showAndWait();
            if(option.get() == ButtonType.OK){
                int codigo = Integer.parseInt(txtId.getText());
                categoriaProducto.setIdCategoria(codigo);
                if(categoriaProducto.borrar()){
                    mostrarAlerta(Alert.AlertType.INFORMATION, "El Sistema comunica", "Producto eliminado correctamente");
                }else{
                    mostrarAlerta(Alert.AlertType.ERROR, "El SIstema comunica", "ERROR!! El producto no se pudo eliminar");
                }
            }
            mostrarDatos();
            cancelar(event);
        }

        @FXML
        private void cancelar(ActionEvent event) {
            btnNuevo.setDisable(false);
            btnGuardar.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnCancelar.setDisable(true);
            txtNombre.clear();
            txtId.clear();
            txtBuscar.clear();
            txtNombre.setDisable(true);
            txtBuscar.setDisable(false);
            mostrarDatos();
        }

        public void mostrarAlerta (Alert.AlertType tipo, String titulo, String mensaje) {
            Alert a = new Alert(tipo);
            a.setTitle(titulo);
            a.setHeaderText(null);
            a.setContentText(mensaje);
            a.show();
        }

        public void mostrarDatos(){
            categorias = FXCollections.observableArrayList(categoriaProducto.consulta());
            tablaCategorias.getSelectionModel().clearSelection();
            colCategorias.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
            tablaCategorias.setItems(categorias);
        }

        @FXML
        private void nuevo(ActionEvent event) {
            txtNombre.setDisable(false);
            txtNombre.clear();
            btnNuevo.setDisable(true);
            btnGuardar.setDisable(false);
            txtBuscar.setDisable(true);
            txtBuscar.clear();
        }

        @FXML
        private void mostrarFila(MouseEvent event) {
            btnModificar.setDisable(false);
            btnCancelar.setDisable(false);
            btnEliminar.setDisable(false);
            CategoriaProducto cat = tablaCategorias.getSelectionModel().getSelectedItem();
            if (cat != null) {
                txtNombre.setText(cat.getNombreCategoria());
                txtId.setText(String.valueOf(cat.getIdCategoria()));
            }
        }

    }
