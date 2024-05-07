package com.stada.sodabilityfinder.connector;

import com.stada.sodabilityfinder.objects.User;

import java.sql.*;


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
}