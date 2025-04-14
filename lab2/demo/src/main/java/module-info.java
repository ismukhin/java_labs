module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires com.google.gson;
    requires javafx.graphics;
    requires java.sql;
    requires java.naming;

    opens com.example to javafx.fxml;
    exports com.example;
}
