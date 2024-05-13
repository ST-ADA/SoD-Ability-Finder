package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import com.stada.sodabilityfinder.objects.Ability;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

/**
 * This class creates the UpdateAbilityScreen.
 */
public class UpdateAbilityScreen {

    // Create a new BorderPane for the content
    BorderPane content = new BorderPane();

    // Create a new File for the image
    File file;

    /**
     * This method creates the UpdateAbilityScreen.
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

        // Try to establish a connection to the database
        try {
            // Create a new instance of MySQLConnectionManager
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();

            // Establish a connection to the database
            connectionManager.establishConnection();

            // Retrieve a list of all factions from the database
            List<String> factions = connectionManager.getAllFactions();

            // Add the retrieved factions to the ComboBox
            factionComboBox.getItems().addAll(factions);

            // Close the database connection
            connectionManager.closeConnection();
        } catch (SQLException ex) {
            // Print the stack trace if a SQLException is thrown
            ex.printStackTrace();
        }
        // Set the prompt text of the ComboBox
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
                // Create a new instance of MySQLConnectionManager
                MySQLConnectionManager connectionManager = new MySQLConnectionManager();

                // Establish a connection to the database
                connectionManager.establishConnection();

                // Retrieve a list of all classes for the selected faction from the database
                List<String> classes = connectionManager.getClassesForFaction(newValue);

                // Add the retrieved classes to the ComboBox
                classComboBox.getItems().addAll(classes);

                // Close the database connection
                connectionManager.closeConnection();
            } catch (SQLException ex) {
                // Print the stack trace if a SQLException is thrown
                ex.printStackTrace();
            }
        });
        // Set the prompt text of the ComboBox
        classComboBox.setPromptText("Select a Class");

        // Create a ComboBox for abilities
        ComboBox<String> abilityComboBox = new ComboBox<>();
        // Initially disable the abilityComboBox
        abilityComboBox.setDisable(true);

        // Add a listener to the classComboBox
        classComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            // Enable the abilityComboBox when a class is selected
            abilityComboBox.setDisable(false);

            // Clear the previous items in the abilityComboBox
            abilityComboBox.getItems().clear();

            // Get the abilities for the selected class
            try {
                // Create a new instance of MySQLConnectionManager
                MySQLConnectionManager connectionManager = new MySQLConnectionManager();

                // Establish a connection to the database
                connectionManager.establishConnection();

                // Retrieve a list of all abilities for the selected class and faction from the database
                List<String> abilities = connectionManager.getAbilitiesForClass(newValue, factionComboBox.getValue());

                // Add the retrieved abilities to the ComboBox
                abilityComboBox.getItems().addAll(abilities);

                // Close the database connection
                connectionManager.closeConnection();
            } catch (SQLException ex) {
                // Print the stack trace if a SQLException is thrown
                ex.printStackTrace();
            }
        });
        // Set the prompt text of the ComboBox
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
            // Get the selected ability
            String selectedAbility = abilityComboBox.getValue();
            if (selectedAbility != null) {
                // Delete the selected ability
                try {
                    MySQLConnectionManager connectionManager = new MySQLConnectionManager();
                    connectionManager.establishConnection();
                    connectionManager.deleteAbility(selectedAbility);
                    connectionManager.closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Create a new Button for updating abilities
        Button updateButton = new Button("Update Ability");
        updateButton.setId("updateButton");

        // Set the action to be performed when the button is clicked
        updateButton.setOnAction(e -> {
            try {
                // Call the updateAbilityPopUp method with the selected faction, class, and ability
                // This method is used to display a popup for updating the selected ability
                updateAbilityPopUp(stage, factionComboBox.getValue(), classComboBox.getValue(), abilityComboBox.getValue());
            } catch (IOException ex) {
                // If an IOException occurs, wrap it in a RuntimeException and throw it
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
                                "/com/stada/sodabilityfinder/stylesheets/update_ability_style.css")
                        .toExternalForm());

        // Set the scene on the stage and show the stage
        stage.setScene(scene);
        stage.show();
    }

    // Create a new Ability for the existing ability
    Ability existingAbility;

    /**
     * This method creates a popup for updating an ability.
     *
     * @param stage The stage to display the popup on.
     * @param factionName The name of the faction.
     * @param className The name of the class.
     * @param abilityName The name of the ability.
     * @throws IOException If an I/O error occurs.
     */
    public void updateAbilityPopUp(Stage stage, String factionName, String className, String abilityName) throws IOException {
        try {
            // Create a new instance of the MySQLConnectionManager class
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();

            // Establish a connection to the MySQL database
            connectionManager.establishConnection();

            // Get the classId for the selected class and faction
            int classId = connectionManager.getClassId(className, factionName);

            // Get the existing ability data
            existingAbility = connectionManager.getAbility(abilityName, classId);

            // Close the database connection
            connectionManager.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Create a new pane for the popup
        BorderPane content = new BorderPane();

        // Create a new Stage for the popup
        Stage popupStage = new Stage();

        // Set the owner of the popupStage to the main stage
        popupStage.initOwner(stage);

        // Create and configure VBox for adding an ability
        VBox addAbilityVBox = new VBox();
        addAbilityVBox.setAlignment(Pos.CENTER);
        addAbilityVBox.setSpacing(10);

        // Create a TextField for the ability name
        TextField abilityNameField = new TextField();
        abilityNameField.setId("abilityNameField");

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

        // Pre-fill the fields with the existing data
        abilityNameField.setText(existingAbility.getName());
        abilityDescriptionArea.setText(existingAbility.getDescription());
        locationArea.setText(existingAbility.getLocation());

        // Create a Button for uploading an image
        Button uploadButton = new Button("Upload Image");
        uploadButton.setId("uploadButton");

        // Set an action for the uploadButton
        uploadButton.setOnAction(e -> {
            // Show the FileChooser dialog and get the selected file
            FileChooser fileChooser = new FileChooser();

            // Set the title of the FileChooser
            fileChooser.setTitle("Open Image File");

            // Add Extension Filters
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            file = fileChooser.showOpenDialog(stage.getScene().getWindow());
        });

        // Create a Button for adding the ability to the database
        Button updateAbilityButton = new Button("Update Ability");
        updateAbilityButton.setId("addAbilityButton");

        updateAbilityButton.setOnAction(e -> {
            // Get the selected faction and class
            String selectedFaction = factionName;
            String selectedClass = className;

            // Get the ability name, description, and location from the input fields
            String abilityName2 = abilityNameField.getText();
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
            } else {
                // If no image was uploaded, use the existing image
                image = existingAbility.getImage();
            }

            // Create a new instance of the MySQLConnectionManager class
            MySQLConnectionManager connectionManager = new MySQLConnectionManager();
            try {
                // Establish a connection to the MySQL database
                connectionManager.establishConnection();

                // Get the classId for the selected class and faction
                int classId = connectionManager.getClassId(selectedClass, selectedFaction);

                // Update the ability in the database
                connectionManager.updateAbility(abilityName2, image, abilityDescription, location, classId);

                // Close the database connection after the ability has been updated
                connectionManager.closeConnection();

                // Show a success popup
                showPopup(popupStage, "Update was successful!", true);
            } catch (SQLException ex) {
                // Print the stack trace if a SQLException is thrown
                ex.printStackTrace();

                // Show a failure popup
                showPopup(popupStage, "Update failed!", false);
            }
        });

        // Add the nodes to the VBox
        addAbilityVBox.getChildren().addAll(abilityNameField, uploadButton, abilityDescriptionArea,
                locationArea, updateAbilityButton);

        // Load image
        Image image = new Image(Application.class.getResource("images/backgrounds/background.png").toExternalForm());

        // Create ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        // Add the media view to the content
        content.getChildren().add(imageView);

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

        // Create ImageView for the popup for the background
        ImageView imageView = new ImageView(new Image(Application.class.getResourceAsStream("images/backgrounds/popup_background.png")));
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        // Create a BorderPane for the popup
        BorderPane content = new BorderPane();
        content.getChildren().add(imageView);

        // Create a Label for the message
        Label label = new Label(message);

        // Add the label to the center of the BorderPane
        content.setCenter(label);

        // Create a Scene with the label
        Scene scene = new Scene(content, 200, 150);
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/global_style.css")
                        .toExternalForm());

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
