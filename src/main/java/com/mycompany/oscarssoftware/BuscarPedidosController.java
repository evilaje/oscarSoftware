package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.modelos.Pedido;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anibal
 */
public class BuscarPedidosController implements Initializable {

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private TableColumn<Pedido, Integer> colId;
    @FXML
    private TableColumn<Pedido, String> colCliente;
    @FXML
    private TableColumn<Pedido, Date> colFecha;
    @FXML
    private TableColumn<Pedido, String> colEmp;
    ObservableList<Pedido> listaPedidos;
    Pedido p = new Pedido();

    private VentaController ventaController;

    public void setVentaController(VentaController ventaController) {
        this.ventaController = ventaController;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        tablaPedidos.setRowFactory(tv -> {
            TableRow<Pedido> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Pedido pedidoSeleccionado = row.getItem();
                    seleccionarPedido(pedidoSeleccionado);
                }
            });
            return row;
        });
    }    

    public void mostrarDatos(){
        listaPedidos = FXCollections.observableList(p.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("idpedido"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colEmp.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
        tablaPedidos.setItems(listaPedidos);
    }

    public void seleccionarPedido(Pedido pedido) {
        ventaController.mostrarDatos(pedido); // Pasar el pedido seleccionado al controlador de Venta
        Stage stage = (Stage) tablaPedidos.getScene().getWindow();
        stage.close(); // Cerrar la ventana de búsqueda de pedidos
    }
}
