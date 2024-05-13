package com.stada.sodabilityfinder.objects;

/**
 * Class representing a User in the system
 */
public class User {
    // Username of the user
    private String username;
    // Password of the user
    private String password;
    // Boolean flag indicating if the user is an admin
    private boolean isAdmin;

    // Constructor for the User class
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getter for the username of the user
    public String getUsername() {
        return username;
    }

    // Getter for the password of the user
    public String getPassword() {
        return password;
    }

    // Method to check if the user is an admin
    public boolean isAdmin() {
        return isAdmin;
    }
}
