module com.stada.sodabilityfinder {
    requires javafx.controls;
    requires javafx.media;

    requires org.kordamp.bootstrapfx.core;

    opens com.stada.sodabilityfinder to javafx.fxml;
    exports com.stada.sodabilityfinder;
}