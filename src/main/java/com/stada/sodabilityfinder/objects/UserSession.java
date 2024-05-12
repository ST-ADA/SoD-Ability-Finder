package com.stada.sodabilityfinder.objects;

/**
 * A UserSession object is used to store the current user session
 */
public class UserSession {
    // Singleton instance of UserSession
    private static UserSession instance;

    // User associated with the current session
    private User user;

    // Private constructor to prevent direct instantiation
    private UserSession() {
    }

    // Method to set the user for the current session
    public void setUser(User user) {
        this.user = user;
    }

    // Method to get the singleton instance of UserSession
    public static UserSession getInstance() {
        // If the instance is null, create a new UserSession
        if (instance == null) {
            instance = new UserSession();
        }
        // Return the singleton instance
        return instance;
    }

    // Method to get the user associated with the current session
    public User getUser() {
        return user;
    }

    // Method to clean the current user session
    public void cleanUserSession() {
        // Set the user and instance to null
        user = null;
        instance = null;
    }
}