package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The AdminScreen class is a screen
 * that allows the user to add or update abilities.
 */
public class AdminScreen {

    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Starts the AdminScreen.
     *
     * @param stage The stage to display the screen on.
     * @throws IOException If the screen cannot be displayed.
     */
    public void start(Stage stage) throws IOException {
        // Create a new instance of the TopBar class
        TopBar topBar = new TopBar();

        // Call the createTopBar method to create the top bar and store it in the 'top' variable
        HBox top = topBar.createTopBar();

        // Create and configure center VBox
        HBox centerVBox = new HBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setSpacing(30);

        // Create a new Button for adding abilities
        Button addAbilityButton = new Button("Add Ability");
        // Set the ID of the button, which can be used for CSS styling
        addAbilityButton.setId("addAbilityButton");
        // Set the action to be performed when the button is clicked
        addAbilityButton.setOnAction(e -> {
            // Create a new AddAbilityScreen instance
            AddAbilityScreen addAbilityScreen = new AddAbilityScreen();
            try {
                // Start the AddAbilityScreen
                addAbilityScreen.start(stage);
                // Set the scene of the stage to the AddAbilityScreen scene
                stage.setScene(addAbilityScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
                ioException.printStackTrace();
            }
        });

        // Create a new Button for updating abilities
        Button updateAbilityButton = new Button("Update Ability");
        // Set the ID of the button, which can be used for CSS styling
        updateAbilityButton.setId("updateAbilityButton");
        // Set the action to be performed when the button is clicked
        updateAbilityButton.setOnAction(e -> {
            // Create a new UpdateAbilityScreen instance
            UpdateAbilityScreen updateAbilityScreen = new UpdateAbilityScreen();
            try {
                // Start the UpdateAbilityScreen
                updateAbilityScreen.start(stage);
                // Set the scene of the stage to the UpdateAbilityScreen scene
                stage.setScene(updateAbilityScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
                ioException.printStackTrace();
            }
        });

        // Add the addAbilityButton and updateAbilityButton to the center VBox
        centerVBox.getChildren().addAll(addAbilityButton, updateAbilityButton);

        // Create a new Button for navigating back to the home screen
        Button backButton = new Button("Back to Homescreen");
// Set the ID of the button, which can be used for CSS styling
        backButton.setId("backButton");
// Set the action to be performed when the button is clicked
        backButton.setOnAction(e -> {
            // Create a new HomeScreen instance
            HomeScreen homeScreen = new HomeScreen();
            try {
                // Start the home screen
                homeScreen.start(stage);
                // Set the scene of the stage to the home screen scene
                stage.setScene(homeScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
                ioException.printStackTrace();
            }
        });

        // Create and configure the bottom HBox
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.getChildren().add(backButton);

        // Load image
        Image image = new Image(Application.class.getResource("images/backgrounds/background.png").toExternalForm());

        // Create ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        // Add the media view to the content
        content.getChildren().add(imageView);

        // Set the top HBox as the top of the content
        content.setTop(top);

        // Set the stack pane as the center of the content
        content.setCenter(centerVBox);

        // Set the bottom HBox as the bottom of the content
        content.setBottom(bottomHBox);

        // Create and configure the scene
        Scene scene = new Scene(content, 800, 600);
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/global_style.css")
                        .toExternalForm());

        // Set the scene on the stage and show the stage
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
