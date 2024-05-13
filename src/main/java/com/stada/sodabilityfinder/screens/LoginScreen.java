package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import com.stada.sodabilityfinder.objects.User;
import com.stada.sodabilityfinder.objects.UserSession;
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
 * The login screen of the application.
 */
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
                new Image(Application.class.getResource("images/logo.png")
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

        // Set the action to be performed when the login button is clicked
        login.setOnAction(e -> {
            // Create a new instance of the MySQLConnectionManager class
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();
            try {
                // Establish a connection to the MySQL database
                connectionManager.establishConnection();

                // Attempt to read the user with the entered username from the database
                User user = connectionManager.readUser(username.getText());

                // If the user is null (does not exist) or the entered password does not match the user's password,
                // show an error alert
                if (user == null || !user.getPassword().equals(password.getText())) {
                    PopupScreen popup = new PopupScreen("Error", "Invalid username or password!");
                    popup.showAndWait();
                } else {
                    // Get the current user session
                    UserSession.getInstance().setUser(user);

                    // If the entered username and password are correct, start the home screen
                    HomeScreen homeScreen = new HomeScreen();
                    homeScreen.start(stage);
                    stage.setScene(homeScreen.getScene());
                }

                // Close the database connection after the user has been read
                connectionManager.closeConnection();
            } catch (IOException | SQLException ex) {
                // Print the stack trace if an IOException or SQLException is thrown
                ex.printStackTrace();
            }
        });

        // Create a new Hyperlink for the register option
        Hyperlink register = new Hyperlink("Register");
        // Set the underline property of the hyperlink to true
        register.setUnderline(true);
        // Set the ID of the hyperlink, which can be used for CSS styling
        register.setId("hyperlink");
        // Set the action to be performed when the hyperlink is clicked
        register.setOnAction(e -> {
            // Create a new RegisterScreen instance
            RegisterScreen registerScreen = new RegisterScreen();
            try {
                // Start the RegisterScreen
                registerScreen.start(stage);
                // Set the scene of the stage to the RegisterScreen scene
                stage.setScene(registerScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
                ioException.printStackTrace();
            }
        });

        // Add elements to spacing boxes and main VBox
        spacingBoxUsername.getChildren().addAll(username, passwordLabel);
        spacingButton.getChildren().addAll(password, login, register);
        vbox.getChildren().addAll(logo, usernameLabel, spacingBoxUsername, spacingButton);

        // Load image
        Image image = new Image(Application.class.getResource("images/backgrounds/background.png").toExternalForm());

        // Create ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        // Add the ImageView to the content
        content.getChildren().add(imageView);

        // Add the VBox to the content
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
                // Trigger login action
                login.fire();
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