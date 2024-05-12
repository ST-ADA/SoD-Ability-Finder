package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import com.stada.sodabilityfinder.objects.Ability;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The AbilityScreen class is responsible for displaying the abilities of a given faction and class.
 */
public class AbilityScreen {

    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Starts the ability screen.
     *
     * @param stage     The stage to display the screen on.
     * @param faction   The faction of the abilities to display.
     * @param className The class of the abilities to display.
     * @throws IOException If an I/O exception occurs.
     */
    public void start(Stage stage, String faction, String className) throws IOException {
        // Create a new instance of the TopBar class
        TopBar topBar = new TopBar();

        // Call the createTopBar method to create the top bar and store it in the 'top' variable
        HBox top = topBar.createTopBar();

        // Create the ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background:transparent;-fx-background-color:transparent;");
        scrollPane.setPrefSize(600, 400);
        scrollPane.setFitToWidth(true);

        // Create the FlowPane
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setAlignment(Pos.CENTER);

        // Create a new MySQLConnectionManager
        MySQLConnectionManager connectionManager = new MySQLConnectionManager();
        try {
            // Establish a connection to the database
            connectionManager.establishConnection();
            // Retrieve a list of abilities for the given faction and class
            List<Ability> abilities = connectionManager.readAbilities(faction, className);

            // Loop through each ability in the list
            for (Ability ability : abilities) {
                // Get the image bytes of the ability
                byte[] imageBytes = ability.getImage();
                // Convert the image bytes to an InputStream
                InputStream is = new ByteArrayInputStream(imageBytes);
                // Create an Image from the InputStream
                Image abilityImage = new Image(is);
                // Create a layout for the ability
                VBox abilityVBox = createAbilityLayout(ability.getName(), ability.getDescription(), abilityImage, ability.getLocation());
                // Add the layout to the flow pane
                flowPane.getChildren().add(abilityVBox);
            }
        } catch (Exception e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        } finally {
            try {
                // Close the connection to the database
                connectionManager.closeConnection();
            } catch (Exception e) {
                // Print the stack trace if an exception occurs
                e.printStackTrace();
            }
        }

        // Create a StackPane to center the FlowPane
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(flowPane);

        // Set the StackPane as the content of the ScrollPane
        scrollPane.setContent(vBox);

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

        // Set the top HBox as the top of the content
        content.setTop(top);

        // Set the stack pane as the center of the content
        content.setCenter(scrollPane);

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
                                "/com/stada/sodabilityfinder/stylesheets/ability_screen_style.css")
                        .toExternalForm());

        // Set the scene on the stage and show the stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a layout for an ability.
     *
     * @param abilityName        The name of the ability.
     * @param abilityDescription The description of the ability.
     * @param abilityImage       The image of the ability.
     * @param location           The location of the ability.
     * @return The VBox layout for the ability.
     */
    private VBox createAbilityLayout(String abilityName, String abilityDescription, Image abilityImage, String location) {
        // Create the VBox named abilityVBox
        VBox abilityVBox = new VBox();
        abilityVBox.setPrefSize(600, 200);

        // Create the HBox named topHalfHBox
        HBox topHalfHBox = new HBox();
        topHalfHBox.setPrefSize(600, 100);

        // Create the ImageView named abilityImageView
        ImageView abilityImageView = new ImageView(abilityImage);
        abilityImageView.setFitWidth(100);
        abilityImageView.setFitHeight(100);

        // Create the VBox named abilityNameVBox
        VBox abilityNameVBox = new VBox();
        abilityNameVBox.setPrefSize(500, 100);

        // Create the TextArea named abilityNameText
        TextArea abilityNameText = new TextArea(abilityName);
        abilityNameText.setMaxSize(500, 30);
        abilityNameText.setMinSize(500, 30);
        abilityNameText.setEditable(false);
        abilityNameText.setWrapText(true);

        // Create the TextArea named abilityDescriptionText
        TextArea abilityDescriptionText = new TextArea(abilityDescription);
        abilityDescriptionText.setPrefSize(500, 70);
        abilityDescriptionText.setEditable(false);
        abilityDescriptionText.setWrapText(true);

        // Add the TextAreas to the abilityNameVBox
        abilityNameVBox.getChildren().addAll(abilityNameText, abilityDescriptionText);

        // Add the ImageView and VBox to the topHalfHBox
        topHalfHBox.getChildren().addAll(abilityImageView, abilityNameVBox);

        // Create the TextArea named locationText
        TextArea locationText = new TextArea(location);
        locationText.setPrefSize(600, 100);
        locationText.setEditable(false);
        locationText.setWrapText(true);

        // Add the HBox and TextArea to the abilityVBox
        abilityVBox.getChildren().addAll(topHalfHBox, locationText);

        return abilityVBox;
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
