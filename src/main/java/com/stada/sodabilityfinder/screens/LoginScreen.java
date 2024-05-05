package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;

public class LoginScreen {
    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Starts the login screen on the provided stage.
     *
     * @param stage The stage on which the login screen is displayed.
     * @throws IOException If there is an error loading the image or video resources.
     */
    public void start(Stage stage) throws IOException {
        // Create main VBox for layout
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        // Create spacing boxes for username and buttons
        VBox spacingBoxUsername = new VBox();
        spacingBoxUsername.setSpacing(10);
        spacingBoxUsername.setAlignment(Pos.CENTER);
        VBox spacingButton = new VBox();
        spacingButton.setSpacing(10);
        spacingButton.setAlignment(Pos.CENTER);

        // Create and configure logo
        ImageView logo = new ImageView(
                new Image(Application.class.getResource("images/Logo.png")
                        .toString()));
        logo.setFitHeight(200);
        logo.setFitWidth(400);

        // Create and configure username label and text field
        Label usernameLabel = new Label("Username");
        usernameLabel.setId("usernameLabel");
        TextField username = new TextField();
        username.setId("username");

        // Create and configure password label and field
        Label passwordLabel = new Label("Password");
        passwordLabel.setId("passwordLabel");
        PasswordField password = new PasswordField();
        password.setId("password");

        // Create and configure login button
        Button login = new Button("Log in");
        login.setId("login");
        login.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen();
            try {
                homeScreen.start(stage);
                stage.setScene(homeScreen.getScene());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Create and configure register hyperlink
        Hyperlink register = new Hyperlink("Register");
        register.setUnderline(true);
        register.setId("hyperlink");
        register.setOnAction(e -> {
            RegisterScreen registerScreen = new RegisterScreen();
            try {
                registerScreen.start(stage);
                stage.setScene(registerScreen.getScene());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Add elements to spacing boxes and main VBox
        spacingBoxUsername.getChildren().addAll(username, passwordLabel);
        spacingButton.getChildren().addAll(password, login, register);
        vbox.getChildren().addAll(logo, usernameLabel, spacingBoxUsername, spacingButton);

        // Create and configure media player
        Media media = new Media(Application.class.getResource("images/backgrounds/background.mp4").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create and configure stack pane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(mediaView, vbox);

        // Set stack pane as center of content
        content.setCenter(stackPane);
        mediaPlayer.play();

        // Create and configure scene
        Scene scene = new Scene(content, 800, 600);
        scene.getStylesheets().add(
                Application.class.getResource(
                        "/com/stada/sodabilityfinder/stylesheets/global_text_styles.css")
                        .toExternalForm());
        scene.getStylesheets().add(
                Application.class.getResource(
                        "/com/stada/sodabilityfinder/stylesheets/login_register_style.css")
                        .toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns the scene of the current content.
     *
     * @return The scene of the current content.
     */
    public Scene getScene() {
        return content.getScene();
    }
}