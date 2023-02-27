module com.mycompany.ecomm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.awt;
    requires java.desktop;

    opens com.mycompany.ecomm to javafx.fxml;
    exports com.mycompany.ecomm;
}
