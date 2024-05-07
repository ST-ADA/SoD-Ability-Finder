package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class HomeScreen {
    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    /**
     * Creates the home screen.
     *
     * @param stage The stage on which the home screen is displayed.
     * @throws IOException If the FXML file for the home screen cannot be found.
     */
    public void start(Stage stage) throws IOException {
        // Create a new instance of the TopBar class
        TopBar topBar = new TopBar();

        // Call the createTopBar method to create the top bar and store it in the 'top' variable
        HBox top = topBar.createTopBar();

        // Create and configure center HBox
        HBox centerHBox = new HBox();
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.setSpacing(60);

        // Create and configure faction label
        Label factionLabel = new Label("Choose your Faction");
        factionLabel.setId("factionLabel");

        // Create and configure center VBox
        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setSpacing(30);

        // Create and configure alliance and horde hyperlinks
        Hyperlink allianceHyperlink = createFactionHyperlink("alliance", stage);
        allianceHyperlink.setId("allianceHyperlink");
        Hyperlink hordeHyperlink = createFactionHyperlink("horde", stage);
        hordeHyperlink.setId("hordeHyperlink");

        // Add elements to center HBox and VBox
        centerHBox.getChildren().addAll(allianceHyperlink, hordeHyperlink);
        centerVBox.getChildren().addAll(factionLabel, centerHBox);

        // Create and configure media player
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

        // Set stack pane as center of content
        content.setCenter(centerVBox);

        mediaPlayer.play();

        // Create and configure scene
        Scene scene = new Scene(content, 800, 600);
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/global_style.css")
                        .toExternalForm());
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/homescreen_style.css")
                        .toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a hyperlink with an image for a given faction.
     *
     * @param faction The name of the faction for which the hyperlink is created.
     * @return The created hyperlink with the faction image.
     */
    private Hyperlink createFactionHyperlink(String faction, Stage stage) {
        // Create an image using the faction name to locate the image file
        Image factionImage = new Image(
                Application.class.getResource(
                        "images/" + faction + ".png")
                        .toString());

        // Create an ImageView to display the image, and set its size
        ImageView factionImageView = new ImageView(factionImage);
        factionImageView.setFitHeight(150);
        factionImageView.setFitWidth(150);

        // Create a Hyperlink and set the graphic to the ImageView
        Hyperlink factionHyperlink = new Hyperlink();
        factionHyperlink.setGraphic(factionImageView);

        // Set an action for the hyperlink
        factionHyperlink.setOnAction(e -> {
            // go to ClassScreen, passing the faction as a parameter as a string
            ClassScreen classScreen = new ClassScreen();
            try {
                classScreen.start(stage, faction);
                stage.setScene(classScreen.getScene());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Return the created hyperlink
        return factionHyperlink;
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