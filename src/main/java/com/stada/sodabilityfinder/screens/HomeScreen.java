package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import com.stada.sodabilityfinder.objects.UserSession;
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

/**
 * The HomeScreen class is responsible for creating the home screen of the application.
 * The home screen allows the user to choose a faction, Alliance or Horde, and navigate to the class screen.
 * The home screen also allows the user to log out and navigate back to the login screen.
 */
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

        // Create the back button
        Button logoutButton = new Button("Logout");
        logoutButton.setId("backButton");
        logoutButton.setOnAction(e -> {
            // Clear the UserSession
            UserSession.getInstance().cleanUserSession();

            // Navigate back to the LoginScreen
            LoginScreen loginScreen = new LoginScreen();
            try {
                // Start the login screen
                loginScreen.start(stage);
                // Set the scene of the stage to the login screen scene
                stage.setScene(loginScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
                ioException.printStackTrace();
            }
        });

        // Create and configure the bottom HBox
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.getChildren().add(logoutButton);

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

        // Set stack pane as center of content
        content.setCenter(centerVBox);

        // Set the bottom HBox as the bottom of the content
        content.setBottom(bottomHBox);

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
                // Start the class screen with the selected faction
                classScreen.start(stage, faction);
                // Set the scene of the stage to the class screen scene
                stage.setScene(classScreen.getScene());
            } catch (IOException ioException) {
                // Print the stack trace if an IOException occurs
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