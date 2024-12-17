package com.mycompany.oscarssoftware;

import com.jfoenix.controls.JFXButton;
import com.mycompany.oscarssoftware.clases.Reporte;
import com.mycompany.oscarssoftware.modelos.Pedido;
import com.mycompany.oscarssoftware.modelos.Venta;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
    }

    public void actualizarGanancias() {
        gananciasTotales = p.ingresosTotales();
        lblVentas.setText(String.valueOf(v.ventasRegistradas()));
        lblPedidos.setText(String.valueOf(p.pedidosPendientes()));
        lblGananciasTotales.setText(String.format("%.2f", gananciasTotales));
    }

    public void abrirFxml(String fxml, String titulo, boolean esModal) {
        try {
            // Verifica si la ventana ya está abierta
            Stage ventanaExistente = WindowManager.obtenerVentana(titulo);
            if (ventanaExistente != null) {
                ventanaExistente.toFront();
                ventanaExistente.requestFocus();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Crea un nuevo `Stage` para la ventana
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

            if (esModal) {
                stage.initOwner(root.getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            stage.show();
            WindowManager.registrarVentana(titulo, stage);

            stage.setOnCloseRequest(e -> WindowManager.cerrarVentana(titulo));

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo cargar la ventana: " + fxml);
        }
    }

    public void cambiarAScene(String fxml, Object controlador, String titulo, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setController(controlador); // Asigna el controlador si es necesario
            Parent root = loader.load();

            // Obtener el Stage a partir del evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.sizeToScene();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirInicio(ActionEvent event) {
        abrirFxml("login.fxml", "login", false);
    }

    @FXML
    private void abrirVerPedidos(ActionEvent event) {
        abrirFxml("verPedido.fxml", "Lista de pedidos", false);
    }

    @FXML
    private void pedido(ActionEvent event) {
        abrirFxml("pedido.fxml", "Formulario Pedido", false);
    }

    @FXML
    private void venta(ActionEvent event) {
        abrirFxml("venta.fxml", "Formulario Venta", false);
    }

    @FXML
    private void producto(ActionEvent event) {
        abrirFxml("Producto.fxml", "Gestion de Productos", false);
    }

    @FXML
    private void clientes(ActionEvent event) {
        abrirFxml("cliente.fxml", "Gestion de clientes", false);
    }

    @FXML
    private void proveedor(ActionEvent event) {
        abrirFxml("proveedor.fxml", "Formulario Proveedor", false);
    }

    @FXML
    private void empleado(ActionEvent event) {
        abrirFxml("empleado.fxml", "Formulario Empleado", false);
    }

    @FXML
    private void verPedidos(ActionEvent event) {
        abrirFxml("verPedido.fxml", "Ver pedidos disponibles", true);
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
