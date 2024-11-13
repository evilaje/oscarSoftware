package com.mycompany.oscarssoftware;

import com.jfoenix.controls.JFXButton;
import com.mycompany.oscarssoftware.clases.Reporte;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Venta;
import com.mycompany.oscarssoftware.util.AtajosTecladoUtil;
import com.mycompany.oscarssoftware.util.EmpleadoSingleton;
import com.mycompany.oscarssoftware.util.WindowManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    private static Scene scene;
    @FXML
    private HBox root;
    @FXML
    private AnchorPane side_ankerpane;
    @FXML
    private JFXButton btnuno;
    @FXML
    private JFXButton btnuno1;
    @FXML
    private JFXButton btnuno2;
    @FXML
    private JFXButton btnuno3;
    @FXML
    private JFXButton btnuno4;
    @FXML
    private JFXButton btnuno5;
    @FXML
    private Pane pane_12;
    @FXML
    private Label lblGananciasTotales;
    private double gananciasTotales = 0.00;
    private Pedido p = new Pedido();
    private Venta v = new Venta();
    @FXML
    private Pane pane_1211;
    @FXML
    private Label lblPedidos;
    @FXML
    private Pane pane_12111;
    @FXML
    private Pane pane_121;
    @FXML
    private Button btnVerPedido;
    @FXML
    private Label txtEmpleado;
    @FXML
    private Label lblVentas;
    @FXML
    private JFXButton btnAyuda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtEmpleado.setText(EmpleadoSingleton.getInstance().getEmpleado().getNombre());
        actualizarGanancias();

        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                Stage ventanaActual = (Stage) root.getScene().getWindow(); // Obtener la ventana actual
                AtajosTecladoUtil.inicializarAtajos(newScene, ventanaActual); // Pasar la ventana actual
            }
        });
    }

    public void actualizarGanancias() {
        gananciasTotales = p.ingresosTotales();
        lblVentas.setText(String.valueOf(v.ventasRegistradas()));
        lblPedidos.setText(String.valueOf(p.pedidosPendientes()));
        lblGananciasTotales.setText(String.format("%.2f", gananciasTotales));
    }

    public void abrirFxml(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
            WindowManager.registrarVentana(titulo, stage);

            stage.setOnCloseRequest(e -> WindowManager.cerrarVentana(titulo));
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirInicio(ActionEvent event) {
        abrirFxml("login.fxml", "login");
    }

    @FXML
    private void abrirVerPedidos(ActionEvent event) {
        abrirFxml("verPedido.fxml", "Lista de pedidos");
    }

    @FXML
    private void pedido(ActionEvent event) {
        abrirFxml("pedido.fxml", "Formulario Pedido");
    }

    @FXML
    private void venta(ActionEvent event) {
        abrirFxml("venta.fxml", "Formulario Venta");
    }

    @FXML
    private void producto(ActionEvent event) {
        abrirFxml("Producto.fxml", "Formulario Producto");
    }

    @FXML
    private void clientes(ActionEvent event) {
        abrirFxml("cliente.fxml", "Formulario Cliente");
    }

    @FXML
    private void proveedor(ActionEvent event) {
        abrirFxml("proveedor.fxml", "Formulario Proveedor");
    }

    @FXML
    private void empleado(ActionEvent event) {
        abrirFxml("empleado.fxml", "Formulario Empleado");
    }

    @FXML
    private void verPedidos(ActionEvent event) {
        abrirFxml("verPedido.fxml", "Ver pedidos disponibles");
    }

    @FXML
    private void ayuda(MouseEvent event) {
        String filePath = getClass().getResource("/ayuda/Ayuda_Oscarsdb.chm").getPath();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("El archivo chm no existe.");
        }
    }
}
