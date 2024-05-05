package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
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

import java.io.IOException;

public class AbilityScreen {

    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    public void start(Stage stage,String faction, String className) throws IOException {

        content.setId("content");

        // Create the top HBox
        HBox top = new HBox();

        // Create and configure the logo
        ImageView logo = new ImageView(
                new Image(Application.class.getResource("images/logo.png").toString())
        );
        logo.setFitHeight(50);
        logo.setFitWidth(100);

        // Create and configure the top label
        Label topLabel = new Label("Ability Finder");
        topLabel.setId("topLabel");

        // Create an image using the admin_image.png file
        Image adminImage = new Image(
                Application.class.getResource(
                                "images/admin_image.png")
                        .toString());

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

        // Add elements to the top HBox
        top.getChildren().addAll(logo, region1, topLabel, region2, adminLink);

        // Create the ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background:transparent;-fx-background-color:transparent;");
        scrollPane.setPrefSize(600, 400);


        // Create some test data
        String abilityName = "Test Ability";
        String abilityDescription = "This is a test ability.";
        Image abilityImage = new Image(Application.class.getResource("images/alliance.png").toString());
        String location = "Test Location";

        // Create the GridPane with 1 column
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHgrow(gridPane, Priority.ALWAYS);
        GridPane.setVgrow(gridPane, Priority.ALWAYS);

        // Add the ability layout to the grid pane
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 0);
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 1);
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 2);
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 3);
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 4);
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 5);
        gridPane.add(createAbilityLayout(abilityName, abilityDescription, abilityImage, location), 0, 6);


        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);
        stackPane.setAlignment(Pos.CENTER);

        // Set the GridPane as the content of the ScrollPane
        scrollPane.setContent(stackPane);


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


        // Create and configure the media player
        Media media = new Media(
                Application.class.getResource("images/backgrounds/background.mp4")
                        .toString()
        );
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0);
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

        // Create the TextArea named abilityDescriptionText
        TextArea abilityDescriptionText = new TextArea(abilityDescription);
        abilityDescriptionText.setPrefSize(500, 70);
        abilityDescriptionText.setEditable(false);

        // Add the TextAreas to the abilityNameVBox
        abilityNameVBox.getChildren().addAll(abilityNameText, abilityDescriptionText);

        // Add the ImageView and VBox to the topHalfHBox
        topHalfHBox.getChildren().addAll(abilityImageView, abilityNameVBox);

        // Create the TextArea named locationText
        TextArea locationText = new TextArea(location);
        locationText.setPrefSize(600, 100);
        locationText.setEditable(false);

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
