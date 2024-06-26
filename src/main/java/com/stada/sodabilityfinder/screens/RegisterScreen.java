package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The RegisterScreen class is responsible for displaying the register screen of the application.
 */
public class RegisterScreen {
    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Starts the register screen on the provided stage.
     *
     * @param stage The stage on which the register screen is displayed.
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
                new Image(Application.class.getResource(
                        "images/logo.png")
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

        // Create and configure register button
        Button register = new Button("Register");
        register.setId("register");

        // Set the action to be performed when the register button is clicked
        register.setOnAction(e -> {
            // Create a new instance of the MySQLConnectionManager class
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();
            try {
                // Establish a connection to the MySQL database
                connectionManager.establishConnection();

                // Attempt to create a new user with the entered username and password
                connectionManager.createUser(username.getText(), password.getText());

                // Close the database connection after the user has been created
                connectionManager.closeConnection();
            } catch (SQLException ex) {
                // If the username already exists in the database, show a warning alert
                if (ex.getMessage().equals("Username already exists")) {
                    // If the username already exists in the database, show a warning alert
                    PopupScreen popup = new PopupScreen("Warning", "Username already exists!");
                    popup.showAndWait();
                } else {
                    // If any other error occurs during the registration process, show an error alert
                    PopupScreen popup = new PopupScreen("Error", "An error occurred!");
                    popup.showAndWait();
                }
            }
            // If the user was created successfully, show a success alert
            PopupScreen popup = new PopupScreen("Success", "User created successfully!");
            popup.showAndWait();
        });

        // Create a new Hyperlink for the login option
        Hyperlink login = new Hyperlink("Back to Log In");
        // Set the underline property of the hyperlink to true
        login.setUnderline(true);
        // Set the ID of the hyperlink, which can be used for CSS styling
        login.setId("hyperlink");
        // Set the action to be performed when the hyperlink is clicked
        login.setOnAction(e -> {
            // Create a new LoginScreen instance
            LoginScreen loginScreen = new LoginScreen();
            try {
                // Start the LoginScreen
                loginScreen.start(stage);
                // Set the scene of the stage to the LoginScreen scene
                stage.setScene(loginScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
                ioException.printStackTrace();
            }
        });

        // Add elements to spacing boxes and main VBox
        spacingBoxUsername.getChildren().addAll(username, passwordLabel);
        spacingButton.getChildren().addAll(password, register, login);
        vbox.getChildren().addAll(logo, usernameLabel, spacingBoxUsername, spacingButton);

        // Load image
        Image image = new Image(Application.class.getResource("images/backgrounds/background.png").toExternalForm());

        // Create ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        // Add the media view to the content
        content.getChildren().add(imageView);

        // Set stack pane as center of content
        content.setCenter(vbox);

        // Create and configure scene
        Scene scene = new Scene(content, 800, 600);
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/global_style.css")
                        .toExternalForm());
        scene.getStylesheets().add(
                Application.class.getResource(
                        "/com/stada/sodabilityfinder/stylesheets/login_register_style.css")
                        .toExternalForm());

        // Add event filter for Enter key press
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                register.fire();
            }
        });
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