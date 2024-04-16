package com.library;

import com.library.config.ConfigManager;
import com.library.database.DatabaseConnection;
import com.library.exceptions.DatabaseConnectionException;
import com.library.exceptions.InvalidConfigException;
import com.library.ui.MainMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            // Load the application configuration
            ConfigManager.loadConfig();

            // Establish the database connection
            DatabaseConnection.getConnection();

            // Start the main menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.displayMenu();
        } catch (InvalidConfigException e) {
            logger.error("Error loading configuration: {}", e.getMessage());
        } catch (DatabaseConnectionException e) {
            logger.error("Error connecting to the database: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        } finally {
            // Close the database connection
            DatabaseConnection.closeConnection();
        }
    }
}