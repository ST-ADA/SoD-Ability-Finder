package com.stada.sodabilityfinder.objects;

public class Ability {
    private String name;
    private byte[] image;
    private String description;
    private String location;
    private int classId;

    public Ability(String name, byte[] image, String description, String location, int classId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.location = location;
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getClassId() {
        return classId;
    }
}