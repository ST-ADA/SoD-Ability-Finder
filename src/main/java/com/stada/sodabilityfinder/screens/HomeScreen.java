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
        // Create top HBox
        HBox top = new HBox();

        // Create and configure logo
        ImageView logo = new ImageView(
                new Image(Application.class.getResource("images/Logo.png").toString())
        );
        logo.setFitHeight(50);
        logo.setFitWidth(100);

        // Create and configure top label
        Label topLabel = new Label("Ability Finder");
        topLabel.setId("topLabel");

        // Create an image using the admin_image.png file
        Image adminImage = new Image(Application.class.getResource("images/admin_image.png").toString());

        // Create an ImageView to display the image
        ImageView adminImageView = new ImageView(adminImage);
        adminImageView.setFitHeight(50);
        adminImageView.setFitWidth(62);

        // Create a Hyperlink and set the graphic to the ImageView
        Hyperlink adminLink = new Hyperlink();
        adminLink.setGraphic(adminImageView);

        // Set an action for the hyperlink
        adminLink.setOnAction(e -> {
            // Add your action here
        });

        // Create regions for spacing
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        // Add elements to top HBox
        top.getChildren().addAll(logo, region1, topLabel, region2, adminLink);

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
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create and configure stack pane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(mediaView, top, centerVBox);
        stackPane.setAlignment(top, Pos.TOP_CENTER);

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