module com.example.jpp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens Controlleur to javafx.fxml;
    exports Controlleur;
    exports Modele;
    opens Modele to javafx.fxml;
}