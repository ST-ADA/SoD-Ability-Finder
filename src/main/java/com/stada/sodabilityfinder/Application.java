package com.stada.sodabilityfinder;

import com.stada.sodabilityfinder.screens.LoginScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        LoginScreen loginScreen = new LoginScreen();

        loginScreen.start(stage);
        mainStage.setTitle("Season of Discovery Ability Finder");
        mainStage.setScene(loginScreen.getScene());
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}