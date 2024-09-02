module com.mycompany.oscarssoftware {
    requires javafx.fxml;
    
    //para la base de datos
    requires java.base;
    requires java.sql;
    requires jasperreports;
    requires com.jfoenix;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.logging;
    
    opens com.mycompany.oscarssoftware to javafx.fxml;
    exports com.mycompany.oscarssoftware;
    exports com.mycompany.oscarssoftware.modelos;
}
