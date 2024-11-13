package com.mycompany.oscarssoftware;

import com.mycompany.oscarssoftware.util.Modulo;
import com.mycompany.oscarssoftware.util.WindowManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViajeRapidoController implements Initializable {

    @FXML
    private TextField txtBusqueda; // Campo de texto donde el usuario escribe
    @FXML
    private ListView<Modulo> lstSugerencias; // Lista de sugerencias

    // Lista de módulos disponibles
    private ObservableList<Modulo> modulos = FXCollections.observableArrayList(
            new Modulo("Productos", "Producto.fxml"),
            new Modulo("Nuevo Pedido", "pedido.fxml"),
            new Modulo("Gestión de Pedidos", "verPedido.fxml"),
            new Modulo("Gestión de Clientes", "cliente.fxml"),
            new Modulo("Gestión de Empleados", "empleado.fxml"),
            new Modulo("Nueva venta", "venta.fxml"),
            new Modulo("Gestión de Proveedores", "proveedor.fxml"),
            new Modulo("Gestión de Categorías", "categoria.fxml")
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Listener para filtrar las sugerencias a medida que el usuario escribe
        txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarSugerencias(newValue);
        });

        // Acción para cuando se selecciona una sugerencia de la lista
        lstSugerencias.setOnMouseClicked(event -> {
            Modulo moduloSeleccionado = lstSugerencias.getSelectionModel().getSelectedItem();
            if (moduloSeleccionado != null) {
                manejarAperturaModulo(moduloSeleccionado);
            }
        });
    }

    // Método para filtrar y mostrar las sugerencias
    private void filtrarSugerencias(String filtro) {
        if (filtro.isEmpty()) {
            lstSugerencias.getItems().clear(); // Limpiar la lista si no hay texto
        } else {
            ObservableList<Modulo> filtradas = modulos.filtered(modulo
                    -> modulo.getNombreAmigable().toLowerCase().contains(filtro.toLowerCase())
            );
            lstSugerencias.setItems(filtradas); // Actualizar la lista de sugerencias
        }
    }

    // Método para manejar la apertura o enfoque de un módulo
    private void manejarAperturaModulo(Modulo modulo) {
        if (WindowManager.estaVentanaAbierta(modulo.getNombreAmigable())) {
            // Si la ventana ya está abierta, traerla al frente
            Stage ventana = WindowManager.obtenerVentana(modulo.getNombreAmigable());
            if (ventana != null) {
                ventana.toFront();
            }
        } else {
            // Si la ventana no está abierta, abrir una nueva
            abrirNuevaVentana(modulo);
            // Cerrar la ventana de Viaje Rápido después de abrir el nuevo módulo
            Stage stage = (Stage) lstSugerencias.getScene().getWindow();
            stage.close();
        }
    }

    // Método para abrir una nueva ventana
    private void abrirNuevaVentana(Modulo modulo) {
        try {
            // Cargar el archivo FXML correspondiente al módulo
            FXMLLoader loader = new FXMLLoader(getClass().getResource(modulo.getArchivoFxml()));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(modulo.getNombreAmigable()); // Asigna el nombre del módulo como título de la ventana
            stage.show();

            // Registrar la nueva ventana en el WindowManager
            WindowManager.registrarVentana(modulo.getNombreAmigable(), stage);

            // Eliminar la ventana del registro cuando se cierre
            stage.setOnCloseRequest(e -> WindowManager.cerrarVentana(modulo.getNombreAmigable()));
        } catch (IOException e) {
            System.err.println("Error al abrir el módulo: " + modulo.getNombreAmigable());
            e.printStackTrace();
        }
    }

    public static void abrirVentanaRapida(Stage ventanaActual) {
        try {
            FXMLLoader loader = new FXMLLoader(ViajeRapidoController.class.getResource("viajeRapido.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hace que sea modal
            stage.showAndWait(); // Esperar hasta que se cierre la ventana

            // Cierra la ventana que abrió el Viaje Rápido
            if (ventanaActual != null) {
                System.out.println("Cerrando ventana actual");
                ventanaActual.close();
            } else {
                System.out.println("La ventana actual es null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
