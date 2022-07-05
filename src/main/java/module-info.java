module com.example.sampletest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires commons.math3;
    requires java.desktop;

    opens com.example.sampletest to javafx.fxml;
    exports com.example.sampletest;
}