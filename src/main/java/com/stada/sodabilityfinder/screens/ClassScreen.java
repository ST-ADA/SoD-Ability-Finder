package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * The ClassScreen class is responsible for displaying the classes for the selected faction.
 */
public class ClassScreen {

    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Starts the ClassScreen.
     *
     * @param stage   The stage on which the ClassScreen is displayed.
     * @param faction The faction selected by the user.
     * @throws IOException If the FXML file for the ClassScreen cannot be found.
     */
    public void start(Stage stage, String faction) throws IOException {
        // Create a new instance of the TopBar class
        TopBar topBar = new TopBar();

        // Call the createTopBar method to create the top bar and store it in the 'top' variable
        HBox top = topBar.createTopBar();

        // Create and configure the center VBox
        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);

        // Create and configure the class label
        Label classLabel = new Label("Choose your Class:");
        classLabel.setId("classLabel");

        // Create the class grid
        GridPane classGrid = createClassGrid(stage, faction);

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

        // Add elements to the center VBox
        centerVBox.getChildren().addAll(classLabel, classGrid);

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
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/class_screen_style.css")
                        .toExternalForm());

        // Set the scene on the stage and show the stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a grid of classes for the selected faction.
     *
     * @param stage   The stage on which the ClassScreen is displayed.
     * @param faction The faction selected by the user.
     * @return The grid of classes for the selected faction.
     */
    private GridPane createClassGrid(Stage stage, String faction) {
        // Create a new GridPane and set its properties
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Create a new instance of the MySQLConnectionManager class
        MySQLConnectionManager connectionManager = new MySQLConnectionManager();
        try {
            // Establish a connection to the MySQL database
            connectionManager.establishConnection();

            // Get the classes for the selected faction
            List<String> classes = connectionManager.getClassesForFaction(faction);

            // Loop through each class and create a hyperlink for it
            for (int i = 0; i < classes.size(); i++) {
                String className = classes.get(i);
                // Check if the class name is not empty
                if (!className.isEmpty()) {
                    // Create an image for the class
                    Image classImage = new Image(
                            Application.class.getResource(
                                            "images/classes/" + className + ".png")
                                    .toString());
                    // Create an ImageView to display the image
                    ImageView classImageView = new ImageView(classImage);
                    classImageView.setFitHeight(100);
                    classImageView.setFitWidth(100);
                    // Create a Hyperlink and set the graphic to the ImageView
                    Hyperlink classHyperlink = new Hyperlink();
                    classHyperlink.setGraphic(classImageView);

                    // Set an action for the hyperlink
                    classHyperlink.setOnAction(e -> {
                        AbilityScreen abilityScreen = new AbilityScreen();
                        try {
                            abilityScreen.start(stage, faction, className);
                            stage.setScene(abilityScreen.getScene());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });

                    // Add the hyperlink to the grid
                    grid.add(classHyperlink, i % 4, i / 4);
                }
            }
        } catch (SQLException ex) {
            // Print the stack trace if a SQLException is thrown
            ex.printStackTrace();
        } finally {
            try {
                // Close the database connection after the classes have been retrieved
                connectionManager.closeConnection();
            } catch (SQLException ex) {
                // Print the stack trace if a SQLException is thrown
                ex.printStackTrace();
            }
        }
        // Return the created grid
        return grid;
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