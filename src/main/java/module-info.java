module com.stada.sodabilityfinder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.stada.sodabilityfinder to javafx.fxml;
    exports com.stada.sodabilityfinder;
}