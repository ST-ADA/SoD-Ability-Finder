package com.stada.sodabilityfinder;

import com.stada.sodabilityfinder.screens.LoginScreen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage mainStage;

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

    public static Stage getStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }
}