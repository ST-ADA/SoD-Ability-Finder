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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterScreen {
        private BorderPane content = new BorderPane();

        public void start(Stage stage) throws IOException {
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);

            VBox spacingBoxUsername = new VBox();
            spacingBoxUsername.setSpacing(10);
            spacingBoxUsername.setAlignment(Pos.CENTER);

            VBox spacingButton = new VBox();
            spacingButton.setSpacing(10);
            spacingButton.setAlignment(Pos.CENTER);

            ImageView logo = new ImageView(new Image(Application.class.getResource("images/Logo.png").toString()));
            logo.setFitHeight(200);
            logo.setFitWidth(400);

            Label usernameLabel = new Label("Username");
            usernameLabel.setId("usernameLabel");

            TextField username = new TextField();
            username.setId("username");

            Label passwordLabel = new Label("Password");
            passwordLabel.setId("passwordLabel");

            PasswordField password = new PasswordField();
            password.setId("password");

            Button register = new Button("Register");
            register.setId("register");
            register.setOnAction(e -> {
            });

            Hyperlink login = new Hyperlink("Back to Log In");
            login.setUnderline(true);
            login.setId("hyperlink");
            login.setOnAction(e -> {
                LoginScreen loginScreen = new LoginScreen();
                try {
                    loginScreen.start(stage);
                    stage.setScene(loginScreen.getScene());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            spacingBoxUsername.getChildren().addAll(username, passwordLabel);
            spacingButton.getChildren().addAll(password,register, login);
            vbox.getChildren().addAll(logo, usernameLabel, spacingBoxUsername, spacingButton);

            Media media = new Media(Application.class.getResource("images/backgrounds/background.mp4").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0);
            MediaView mediaView = new MediaView(mediaPlayer);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(mediaView, vbox);
            StackPane.setAlignment(vbox, Pos.CENTER);

            content.setCenter(stackPane);
            mediaPlayer.play();

            Scene scene = new Scene(content, 800, 600);
            scene.getStylesheets().add(Application.class.getResource(
                            "/com/stada/sodabilityfinder/stylesheets/global_text_styles.css")
                    .toExternalForm()
            );
            scene.getStylesheets().add(Application.class.getResource(
                            "/com/stada/sodabilityfinder/stylesheets/login_register_style.css")
                    .toExternalForm()
            );
            stage.setScene(scene);
            stage.show();
        }

        public Scene getScene() {
            return content.getScene();
        }
}
