package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

public class AddAbilityScreen {
    // The main content pane for this screen
    private BorderPane content = new BorderPane();

    // The selected file from the FileChooser
    private File file;

    /**
     * Starts the AddAbilityScreen.
     *
     * @param stage The stage to display the screen on.
     * @throws IOException If an I/O error occurs.
     */
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
        try {
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();
            connectionManager.establishConnection();
            List<String> factions = connectionManager.getAllFactions();
            factionComboBox.getItems().addAll(factions);
            connectionManager.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        factionComboBox.setPromptText("Select a Faction");

        // Create a ComboBox for classes
        ComboBox<String> classComboBox = new ComboBox<>();
        // Initially disable the classComboBox
        classComboBox.setDisable(true);

        // Add a listener to the factionComboBox
        factionComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            // Enable the classComboBox when a faction is selected
            classComboBox.setDisable(false);

            // Clear the previous items in the classComboBox
            classComboBox.getItems().clear();

            // Get the classes for the selected faction
            try {
                MySQLConnectionManager connectionManager = new MySQLConnectionManager();
                connectionManager.establishConnection();
                List<String> classes = connectionManager.getClassesForFaction(newValue);
                classComboBox.getItems().addAll(classes);
                connectionManager.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        classComboBox.setPromptText("Select a Class");

        // Add the ComboBoxes to the HBox
        comboHBox.getChildren().addAll(factionComboBox, classComboBox);

        // Create and configure VBox for adding an ability
        VBox addAbilityVBox = new VBox();
        addAbilityVBox.setAlignment(Pos.CENTER);
        addAbilityVBox.setSpacing(10);

        // Create a TextField for the ability name
        TextField abilityNameField = new TextField();
        abilityNameField.setId("abilityNameField");
        abilityNameField.setPromptText("Ability Name");

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
            file = fileChooser.showOpenDialog(stage);
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

        // Create a TextField for the ability description
        TextArea abilityDescriptionArea = new TextArea();
        abilityDescriptionArea.setId("abilityDescriptionArea");
        abilityDescriptionArea.setPromptText("Ability Description");
        abilityDescriptionArea.setWrapText(true);
        abilityDescriptionArea.setEditable(true);

        // Create a TextArea for the location
        TextArea locationArea = new TextArea();
        locationArea.setId("abilityLocationArea");
        locationArea.setPromptText("Location");
        locationArea.setWrapText(true);
        locationArea.setEditable(true);

        // Create a Button for adding the ability to the database
        Button addAbilityButton = new Button("Add Ability");
        addAbilityButton.setId("addAbilityButton");

        addAbilityButton.setOnAction(e -> {
            // Get the selected faction and class
            String selectedFaction = factionComboBox.getValue();
            String selectedClass = classComboBox.getValue();

            // Get the ability name, description, and location from the input fields
            String abilityName = abilityNameField.getText();
            String abilityDescription = abilityDescriptionArea.getText();
            String location = locationArea.getText();

            // Convert the image file to a byte array
            byte[] image = null;
            if (file != null) {
                try {
                    image = Files.readAllBytes(file.toPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            // Create a new instance of the MySQLConnectionManager class
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();
            try {
                // Establish a connection to the MySQL database
                connectionManager.establishConnection();

                // Get the classId for the selected class and faction
                int classId = connectionManager.getClassId(selectedClass, selectedFaction);

                // Add the ability to the database
                connectionManager.addAbility(abilityName, image, abilityDescription, location, classId);

                // Close the database connection after the ability has been added
                connectionManager.closeConnection();
            } catch (SQLException ex) {
                // Print the stack trace if a SQLException is thrown
                ex.printStackTrace();
            }

            // Show a popup screen to inform the user that the ability has been added
            PopupScreen popup = new PopupScreen("Success", "Ability added successfully!");
            popup.showAndWait();
        });

        // Add the nodes to the VBox
        addAbilityVBox.getChildren().addAll(abilityNameField, uploadButton, abilityDescriptionArea,
                locationArea, addAbilityButton);

        // Add the HBox and VBox to the center VBox
        centerVBox.getChildren().addAll(comboHBox, addAbilityVBox);

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
                                "/com/stada/sodabilityfinder/stylesheets/add_ability_style.css")
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
