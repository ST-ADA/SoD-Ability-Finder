module com.stada.sodabilityfinder {
    requires javafx.controls;
    requires javafx.media;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.stada.sodabilityfinder to javafx.fxml;
    exports com.stada.sodabilityfinder;
    exports com.stada.sodabilityfinder.objects;
    exports com.stada.sodabilityfinder.connector;
    exports com.stada.sodabilityfinder.screens;
}