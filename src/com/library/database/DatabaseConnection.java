package com.library.database;

import com.library.config.ConfigManager;
import com.library.exceptions.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    private static Connection connection;

    private DatabaseConnection() {
        // Private constructor to prevent direct instantiation
    }

    public static Connection getConnection() throws DatabaseConnectionException {
        if (connection == null || connection.isClosed()) {
            try {
                String url = ConfigManager.getDatabaseUrl();
                String username = ConfigManager.getDatabaseUsername();
                String password = ConfigManager.getDatabasePassword();

                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                logger.error("Error connecting to the database: {}", e.getMessage());
                throw new DatabaseConnectionException("Error connecting to the database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing the database connection: {}", e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}