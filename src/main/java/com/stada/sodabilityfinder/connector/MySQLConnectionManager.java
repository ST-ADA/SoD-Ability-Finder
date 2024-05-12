package com.stada.sodabilityfinder.connector;

import com.stada.sodabilityfinder.objects.Ability;
import com.stada.sodabilityfinder.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLConnectionManager {
    private static final String DATABASE_URL = "jdbc:mysql://adainforma.tk:3306/bp2_sodaf";
    private static final String DATABASE_USERNAME = "sodaf";
    private static final String DATABASE_PASSWORD = "623v4@gIi";
    private Connection connection;

    public void establishConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            } catch (SQLException ex) {
                throw new SQLException("Error connecting to the database", ex);
            }
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new SQLException("Error closing the database connection", ex);
            }
        }
    }

    public User readUser(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("pw"),
                    resultSet.getBoolean("isAdmin")
            );
        } else {
            return null;
        }
    }

    public void createUser(String username, String password) throws SQLException {
        String query = "INSERT INTO Users (username, pw, isAdmin) VALUES (?, ?, ?)";
        User existingUser = readUser(username);
        if (existingUser != null) {
            throw new SQLException("Username already exists");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setBoolean(3, false);
        preparedStatement.executeUpdate();
    }

    public void addAbility(String name, byte[] image, String description, String location, int classId) throws SQLException {
        String query = "INSERT INTO Ability (name, image, description, location, class_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setBytes(2, image);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, location);
        preparedStatement.setInt(5, classId);
        preparedStatement.executeUpdate();
    }

    public String getFaction(int classId) throws SQLException {
        String query = "SELECT Faction.name FROM Faction JOIN Class ON Faction.faction_id = Class.faction_id WHERE Class.class_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, classId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("name");
        } else {
            return null;
        }
    }

    public List<String> getAllFactions() throws SQLException {
        String query = "SELECT name FROM Faction";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> factions = new ArrayList<>();
        while (resultSet.next()) {
            factions.add(resultSet.getString("name"));
        }
        return factions;
    }

    public int getClassId(String className, String factionName) throws SQLException {
        String query = "SELECT Class.class_id FROM Class JOIN Faction ON Class.faction_id = Faction.faction_id WHERE Class.name = ? AND Faction.name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, className);
        preparedStatement.setString(2, factionName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("class_id");
        } else {
            return -1;
        }
    }

    public List<String> getClassesForFaction(String factionName) throws SQLException {
        String query = "SELECT Class.name FROM Class JOIN Faction ON Class.faction_id = Faction.faction_id WHERE Faction.name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, factionName);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> classes = new ArrayList<>();
        while (resultSet.next()) {
            classes.add(resultSet.getString("name"));
        }
        return classes;
    }

    public List<String> getAbilitiesForClass(String className, String factionName) throws SQLException {
        int classId = getClassId(className, factionName);
        String query = "SELECT name FROM Ability WHERE class_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, classId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> abilities = new ArrayList<>();
        while (resultSet.next()) {
            abilities.add(resultSet.getString("name"));
        }
        return abilities;
    }

    public void deleteAbility(String name) throws SQLException {
        String query = "DELETE FROM Ability WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
    }

    public void updateAbility(String name, byte[] image, String description, String location, int classId) throws SQLException {
        String query = "UPDATE Ability SET image = ?, description = ?, location = ?, class_id = ? WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setBytes(1, image);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setInt(4, classId);
        preparedStatement.setString(5, name);
        preparedStatement.executeUpdate();
    }

    public Ability getAbility(String name, int classId) throws SQLException {
        String query = "SELECT * FROM Ability WHERE name = ? AND class_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, classId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Ability(
                    resultSet.getString("name"),
                    resultSet.getBytes("image"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    resultSet.getInt("class_id")
            );
        } else {
            return null;
        }
    }
}