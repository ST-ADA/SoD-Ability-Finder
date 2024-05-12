package com.stada.sodabilityfinder.connector;

import com.stada.sodabilityfinder.objects.Ability;
import com.stada.sodabilityfinder.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A MySQLConnectionManager object is used to manage the connection to the MySQL database
 */
public class MySQLConnectionManager {
    // Database connection parameters
    private static final String DATABASE_URL = "jdbc:mysql://adainforma.tk:3306/bp2_sodaf";
    private static final String DATABASE_USERNAME = "sodaf";
    private static final String DATABASE_PASSWORD = "623v4@gIi";

    // Connection object to manage the database connection
    private Connection connection;

    // Method to establish a connection to the database
    public void establishConnection() throws SQLException {
        // Check if the connection is null or closed
        if (connection == null || connection.isClosed()) {
            try {
                // Attempt to establish a connection to the database
                connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            } catch (SQLException ex) {
                // Throw an exception if there is an error connecting to the database
                throw new SQLException("Error connecting to the database", ex);
            }
        }
    }

    // Method to close the connection to the database
    public void closeConnection() throws SQLException {
        // Check if the connection is not null and is open
        if (connection != null && !connection.isClosed()) {
            try {
                // Attempt to close the connection
                connection.close();
            } catch (SQLException ex) {
                // Throw an exception if there is an error closing the connection
                throw new SQLException("Error closing the database connection", ex);
            }
        }
    }

    // Method to read a user from the database
    public User readUser(String username) throws SQLException {
        // SQL query to select a user by username
        String query = "SELECT * FROM Users WHERE username = ?";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the username parameter in the SQL statement
        preparedStatement.setString(1, username);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If a user is found, create a new User object and return it
        if (resultSet.next()) {
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("pw"),
                    resultSet.getBoolean("isAdmin")
            );
        } else {
            // If no user is found, return null
            return null;
        }
    }

    // Method to create a new user in the database
    public void createUser(String username, String password) throws SQLException {
        // SQL query to insert a new user into the Users table
        String query = "INSERT INTO Users (username, pw, isAdmin) VALUES (?, ?, ?)";
        // Check if the user already exists in the database
        User existingUser = readUser(username);
        if (existingUser != null) {
            // If the user already exists, throw an exception
            throw new SQLException("Username already exists");
        }
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the parameters in the SQL statement
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setBoolean(3, false);
        // Execute the SQL statement
        preparedStatement.executeUpdate();
    }

    // Method to add a new ability to the database
    public void addAbility(String name, byte[] image, String description, String location, int classId) throws SQLException {
        // SQL query to insert a new ability into the Ability table
        String query =
                "INSERT INTO Ability (name, image, description, location, class_id) VALUES (?, ?, ?, ?, ?)"
                ;
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the parameters in the SQL statement
        preparedStatement.setString(1, name);
        preparedStatement.setBytes(2, image);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, location);
        preparedStatement.setInt(5, classId);
        // Execute the SQL statement
        preparedStatement.executeUpdate();
    }

    // Method to get the faction of a class from the database
    public String getFaction(int classId) throws SQLException {
        // SQL query to select the faction of a class from the Faction and Class tables
        String query =
                "SELECT Faction.name FROM Faction JOIN Class ON Faction.faction_id = Class.faction_id " +
                        "WHERE Class.class_id = ?"
                ;
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the classId parameter in the SQL statement
        preparedStatement.setInt(1, classId);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If a faction is found, return its name
        if (resultSet.next()) {
            return resultSet.getString("name");
        } else {
            // If no faction is found, return null
            return null;
        }
    }

    // Method to get all factions from the database
    public List<String> getAllFactions() throws SQLException {
        // SQL query to select all faction names
        String query = "SELECT name FROM Faction";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // List to store the faction names
        List<String> factions = new ArrayList<>();
        // Loop through the result set and add each faction name to the list
        while (resultSet.next()) {
            factions.add(resultSet.getString("name"));
        }
        // Return the list of faction names
        return factions;
    }

    // Method to get the class ID for a given class name and faction name
    public int getClassId(String className, String factionName) throws SQLException {
        // SQL query to select the class ID for a given class name and faction name
        String query =
                "SELECT Class.class_id FROM Class JOIN Faction ON Class.faction_id = Faction.faction_id " +
                        "WHERE Class.name = ? AND Faction.name = ?"
                ;
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the class name and faction name parameters in the SQL statement
        preparedStatement.setString(1, className);
        preparedStatement.setString(2, factionName);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If a class ID is found, return it
        if (resultSet.next()) {
            return resultSet.getInt("class_id");
        } else {
            // If no class ID is found, return -1
            return -1;
        }
    }

    // Method to get all classes for a given faction
    public List<String> getClassesForFaction(String factionName) throws SQLException {
        // SQL query to select all class names for a given faction
        String query =
                "SELECT Class.name FROM Class JOIN Faction ON Class.faction_id = Faction.faction_id WHERE Faction.name = ?"
                ;
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the faction name parameter in the SQL statement
        preparedStatement.setString(1, factionName);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // List to store the class names
        List<String> classes = new ArrayList<>();
        // Loop through the result set and add each class name to the list
        while (resultSet.next()) {
            classes.add(resultSet.getString("name"));
        }
        // Return the list of class names
        return classes;
    }

    // Method to get the names of abilities for a specific class
    public List<String> getAbilitiesForClass(String className, String factionName) throws SQLException {
        // Get the class ID for the given class name and faction name
        int classId = getClassId(className, factionName);
        // SQL query to select the names of abilities for a specific class
        String query = "SELECT name FROM Ability WHERE class_id = ?";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the class ID parameter in the SQL statement
        preparedStatement.setInt(1, classId);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // List to store the ability names
        List<String> abilities = new ArrayList<>();
        // Loop through the result set and add each ability name to the list
        while (resultSet.next()) {
            abilities.add(resultSet.getString("name"));
        }
        // Return the list of ability names
        return abilities;
    }

    // Method to read abilities for a specific class
    public List<Ability> readAbilities(String faction, String className) throws SQLException {
        // Get the class ID for the given class name and faction name
        int classId = getClassId(className, faction);
        // SQL query to select all abilities for a specific class
        String query = "SELECT * FROM Ability WHERE class_id = ?";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the class ID parameter in the SQL statement
        preparedStatement.setInt(1, classId);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // List to store the Ability objects
        List<Ability> abilities = new ArrayList<>();
        // Loop through the result set and create an Ability object for each row
        while (resultSet.next()) {
            abilities.add(new Ability(
                    resultSet.getString("name"),
                    resultSet.getBytes("image"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    resultSet.getInt("class_id")
            ));
        }
        // Return the list of Ability objects
        return abilities;
    }

    // Method to delete an ability from the database
    public void deleteAbility(String name) throws SQLException {
        // SQL query to delete an ability by name
        String query = "DELETE FROM Ability WHERE name = ?";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the name parameter in the SQL statement
        preparedStatement.setString(1, name);
        // Execute the SQL statement
        preparedStatement.executeUpdate();
    }

    // Method to update an ability in the database
    public void updateAbility(
            String name, byte[] image, String description, String location, int classId)
            throws SQLException {
        // SQL query to update an ability in the Ability table
        String query = "UPDATE Ability SET image = ?, description = ?, location = ?, class_id = ? WHERE name = ?";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the parameters in the SQL statement
        preparedStatement.setBytes(1, image);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setInt(4, classId);
        preparedStatement.setString(5, name);
        // Execute the SQL statement
        preparedStatement.executeUpdate();
    }

    // Method to get an ability from the database
    public Ability getAbility(String name, int classId) throws SQLException {
        // SQL query to select an ability from the Ability table
        String query = "SELECT * FROM Ability WHERE name = ? AND class_id = ?";
        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // Set the parameters in the SQL statement
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, classId);
        // Execute the SQL statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If an ability is found, create a new Ability object and return it
        if (resultSet.next()) {
            return new Ability(
                    resultSet.getString("name"),
                    resultSet.getBytes("image"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    resultSet.getInt("class_id")
            );
        } else {
            // If no ability is found, return null
            return null;
        }
    }
}