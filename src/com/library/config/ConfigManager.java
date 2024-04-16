package com.library.config;

import com.library.exceptions.InvalidConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    private ConfigManager() {
        // Private constructor to prevent direct instantiation
    }

    public static void loadConfig() throws InvalidConfigException {
        properties = new Properties();
        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new InvalidConfigException("Configuration file not found: " + CONFIG_FILE);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error loading configuration: {}", e.getMessage());
            throw new InvalidConfigException("Error loading configuration", e);
        }
    }

    public static String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

    public static String getLogLevel() {
        return properties.getProperty("log.level");
    }
}