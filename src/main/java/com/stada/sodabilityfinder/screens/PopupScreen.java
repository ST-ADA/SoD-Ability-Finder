package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * PopupScreen class is a custom
 * Stage that displays a popup screen
 * with a message.
 */
public class PopupScreen extends Stage {

    /**
     * Constructor for the PopupScreen class.
     * @param title The title of the popup screen.
     * @param message The message to display on the popup screen.
     */
    public PopupScreen(String title, String message) {
        // Set the title of the popup screen
        this.setTitle(title);

        // Create ImageView for the popup for the background
        ImageView imageView = new ImageView(new Image(Application.class.getResourceAsStream("images/backgrounds/popup_background.png")));
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        // Create a BorderPane for the popup
        BorderPane content = new BorderPane();
        content.getChildren().add(imageView);

        // Create a Label for the message
        Label label = new Label(message);
        label.setId("popupLabel");
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);

        // Add the label to the center of the BorderPane
        content.setCenter(label);

        // Create a new Scene with the VBox
        Scene scene = new Scene(content, 200, 150);
        scene.getStylesheets().add(
                Application.class.getResource(
                                "/com/stada/sodabilityfinder/stylesheets/global_style.css")
                        .toExternalForm());

        // Set the Scene of the Stage
        this.setScene(scene);

        // Set the modality of the Stage
        this.initModality(Modality.APPLICATION_MODAL);
    }
}
