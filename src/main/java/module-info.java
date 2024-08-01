module com.mycompany.oscarssoftware {
    requires javafx.controls;
    requires javafx.fxml;
    
    //para la base de datos
    requires java.base;
    requires java.sql;
    requires jasperreports;

    opens com.mycompany.oscarssoftware to javafx.fxml;
    exports com.mycompany.oscarssoftware;
    exports com.mycompany.oscarssoftware.modelos;
}
