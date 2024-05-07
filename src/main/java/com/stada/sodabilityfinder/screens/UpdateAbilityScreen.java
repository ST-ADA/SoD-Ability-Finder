package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UpdateAbilityScreen {

    BorderPane content = new BorderPane();

    public void start(Stage stage) throws IOException {
        // Create a new instance of the TopBar class
        TopBar topBar = new TopBar();

        // Call the createTopBar method to create the top bar and store it in the 'top' variable
        HBox top = topBar.createTopBar();

        // Create and configure center VBox
        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setSpacing(10);

        // Create and configure HBox
        HBox comboHBox = new HBox();
        comboHBox.setAlignment(Pos.CENTER);
        comboHBox.setSpacing(10);

        // Create a ComboBox for factions
        ComboBox<String> factionComboBox = new ComboBox<>();
        factionComboBox.getItems().addAll("Alliance", "Horde");
        factionComboBox.setPromptText("Select a Faction");

        // Create a ComboBox for classes
        ComboBox<String> classComboBox = new ComboBox<>();
        classComboBox.getItems().addAll("druid", "hunter", "mage", "priest",
                "rogue", "warlock", "warrior", "paladin", "shaman");
        classComboBox.setPromptText("Select a Class");

        // Create a ComboBox for abilities
        ComboBox<String> abilityComboBox = new ComboBox<>();
        abilityComboBox.getItems().addAll("Ability 1", "Ability 2", "Ability 3", "Ability 4");
        abilityComboBox.setPromptText("Select an Ability");

        // Add the ComboBoxes to the HBox
        comboHBox.getChildren().addAll(factionComboBox, classComboBox, abilityComboBox);

        // Create and configure VBox for 2 buttons
        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(10);

        // Create and configure the delete button
        Button deleteButton = new Button("Delete Ability");
        deleteButton.setId("deleteButton");
        deleteButton.setOnAction(e -> {
        });

        // Create and configure the update button
        Button updateButton = new Button("Update Ability");
        updateButton.setId("updateButton");
        updateButton.setOnAction(e -> {
            try {
                createAddAbilityPopup(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add the buttons to the VBox
        buttonVBox.getChildren().addAll(deleteButton, updateButton);

        // Add the HBox and VBox to the center VBox
        centerVBox.getChildren().addAll(comboHBox, buttonVBox);

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
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Add the media view to the content
        content.getChildren().add(mediaView);

        // Set the top HBox as the top of the content
        content.setTop(top);

        // Set the stack pane as the center of the content
        content.setCenter(centerVBox);

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
                                "/com/stada/sodabilityfinder/stylesheets/update_ability_style.css")
                        .toExternalForm());

        // Set the scene on the stage and show the stage
        stage.setScene(scene);
        stage.show();
    }

    public void createAddAbilityPopup(Stage stage) throws IOException {
        // Create a new pane for the popup
        BorderPane content = new BorderPane();

        // Create a new Stage for the popup
        Stage popupStage = new Stage();

        // Create and configure VBox for adding an ability
        VBox addAbilityVBox = new VBox();
        addAbilityVBox.setAlignment(Pos.CENTER);
        addAbilityVBox.setSpacing(10);

        // Create a TextField for the ability name
        TextField abilityNameField = new TextField();
        abilityNameField.setId("abilityNameField");

        // Create a TextField for the ability description
        TextField abilityDescriptionField = new TextField();
        abilityDescriptionField.setId("abilityDescriptionField");
        abilityDescriptionField.setAlignment(Pos.TOP_LEFT);

        // Create a TextField for the location
        TextField locationField = new TextField();
        locationField.setId("abilityLocationField");
        locationField.setAlignment(Pos.TOP_LEFT);

        // Create a Button for uploading an image
        Button uploadButton = new Button("Upload Image");
        uploadButton.setId("uploadButton");

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();

        // Set the title of the FileChooser
        fileChooser.setTitle("Open Image File");

        // Add Extension Filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Set an action for the uploadButton
        uploadButton.setOnAction(e -> {
            // Show the FileChooser dialog and get the selected file
            File file = fileChooser.showOpenDialog(stage);

            // Check if a file was selected
            if (file != null) {
                try {
                    // Convert the File to a byte[]
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // Create a Button for adding the ability to the database
        Button updateAbilityButton = new Button("Update Ability");
        updateAbilityButton.setId("addAbilityButton");

        updateAbilityButton.setOnAction(e -> {
            // Add ability to the database
//            boolean updateSuccessful = updateAbility();
            boolean updateSuccessful = true;

            if (updateSuccessful) {
                showPopup(popupStage, "Update was successful!", true);
            } else {
                showPopup(popupStage, "Update failed!", false);
            }
        });

        // Add the nodes to the VBox
        addAbilityVBox.getChildren().addAll(abilityNameField, uploadButton, abilityDescriptionField,
                locationField, updateAbilityButton);

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

        // Set the stack pane as the center of the content
        content.setCenter(addAbilityVBox);

        // Create a Scene with your VBox
        Scene popupScene = new Scene(content, 600, 600);
        popupScene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/global_style.css")
                        .toExternalForm());
        popupScene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/update_ability_style.css")
                        .toExternalForm());
        // Set the scene on the popup stage
        popupStage.setScene(popupScene);

        // Show the popup stage
        popupStage.show();
    }

    public void showPopup(Stage mainPopup, String message, boolean isSuccess) {
        // Create a new Stage for the popup
        Stage popup = new Stage();

        // Create a Label for the message
        Label label = new Label(message);

        // Create a Scene with the label
        Scene scene = new Scene(label);

        // Set the scene on the popup
        popup.setScene(scene);

        // Show the popup
        popup.show();

        // Close the main popup if the update was successful
        if (isSuccess) {
            popup.setOnHidden(e -> mainPopup.close());
        }
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
