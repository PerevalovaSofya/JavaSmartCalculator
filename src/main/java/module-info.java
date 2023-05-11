module com.example.calc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.prefs;

    opens com.example.calc to javafx.fxml;
    exports com.example.calc;
    exports com.example.calc.model;
    opens com.example.calc.model to javafx.fxml;
    exports com.example.calc.controller;
    opens com.example.calc.controller to javafx.fxml;
}