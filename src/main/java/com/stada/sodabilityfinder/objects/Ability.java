package com.stada.sodabilityfinder.objects;

/**
 * This class represents an ability that a class can have.
 * It contains the name of the ability, an image of the ability,
 */
public class Ability {
    // Name of the ability
    private String name;
    // Image of the ability (stored as a byte array)
    private byte[] image;
    // Description of the ability
    private String description;
    // Location where the ability can be found or used
    private String location;
    // ID of the class that this ability belongs to
    private int classId;

    // Constructor for the Ability class
    public Ability(String name, byte[] image, String description, String location, int classId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.location = location;
        this.classId = classId;
    }

    // Getter for the name of the ability
    public String getName() {
        return name;
    }

    // Getter for the image of the ability
    public byte[] getImage() {
        return image;
    }

    // Getter for the description of the ability
    public String getDescription() {
        return description;
    }

    // Getter for the location of the ability
    public String getLocation() {
        return location;
    }

    // Getter for the class ID of the ability
    public int getClassId() {
        return classId;
    }
}