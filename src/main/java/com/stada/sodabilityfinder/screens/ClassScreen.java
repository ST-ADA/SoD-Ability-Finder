package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassScreen {

    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Starts the ClassScreen.
     *
     * @param stage The stage on which the ClassScreen is displayed.
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

        // Create the back button
        Button backButton = new Button("Back to Homescreen");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen();
            try {
                homeScreen.start(stage);
                stage.setScene(homeScreen.getScene());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Create and configure the bottom HBox
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.getChildren().add(backButton);

        // Add elements to the center VBox
        centerVBox.getChildren().addAll(classLabel, classGrid);

        // Create and configure the media player
        Media media = new Media(
                Application.class.getResource("images/backgrounds/background.mp4")
                        .toString()
        );
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Add the media view to the content
        content.getChildren().add(mediaView);

        // Create and configure the stack pane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(top, centerVBox);
        stackPane.setAlignment(top, Pos.TOP_CENTER);

        // Set the stack pane as the center of the content
        content.setCenter(stackPane);

        // Set the bottom HBox as the bottom of the content
        content.setBottom(bottomHBox);

        // Play the media
        mediaPlayer.play();

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
     * Creates and configures the class grid.
     *
     * @param faction The faction selected by the user.
     * @return The created class grid.
     */
    private GridPane createClassGrid(Stage stage, String faction) {
        // Create a new GridPane and set its properties
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Create a new GridPane and set its properties
        String[][] classes = {
                {"druid", "hunter", "mage", "priest"},
                {"rogue", "warlock", "warrior", faction.equals("alliance") ? "paladin" : "shaman"}
        };

        // Loop through each class and create a hyperlink for it
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < classes[i].length; j++) {
                String className = classes[i][j];
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
                    grid.add(classHyperlink, j, i);
                }
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