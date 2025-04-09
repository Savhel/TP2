module com.example.poo_tp1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires jbcrypt;

    opens com.example.poo_tp1 to javafx.fxml;
    opens core to javafx.base;
    exports com.example.poo_tp1;
    exports core;

}