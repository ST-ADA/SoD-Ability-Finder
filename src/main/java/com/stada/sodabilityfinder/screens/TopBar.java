package com.stada.sodabilityfinder.screens;

import com.stada.sodabilityfinder.Application;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TopBar {

    public HBox createTopBar() {
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
            // Create a new instance of the AdminScreen class
            AdminScreen adminScreen = new AdminScreen();
            try {
                // Call the start method of the AdminScreen class
                adminScreen.start(Application.getStage());
                // Set the scene of the stage to the scene of the AdminScreen class
                Application.getStage().setScene(adminScreen.getScene());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create regions for spacing
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        // Add elements to the top HBox
        top.getChildren().addAll(logo, region1, topLabel, region2, adminLink);

        return top;
    }
}