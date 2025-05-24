package tutorials;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

import static tutorials.Log.error;
import static tutorials.Log.info;

public class Configuration {
    Properties config = new Properties();
    private String configFilePath = "/config.properties";


    void processConfiguration(String[] args) {
        parseArguments(args);
        info(App.class, "Configuration file: %s", configFilePath);
        loadConfigurationFile();
        info(App.class, "Configuration loaded.");
        parseArguments(args);
    }

    private void parseArguments(String[] args) {
        for (String s : args) {
            if (s.startsWith("-") && s.contains("=")) {
                String[] parts = s.substring(1).split("=");
                info(App.class, "Argument %s=%s", parts[0], parts[1]);
                config.setProperty(parts[0], parts[1]);
            }
        }
    }

    private void loadConfigurationFile() {
        try {
            config.load(App.class.getResourceAsStream(configFilePath));
        } catch (IOException e) {
            error(App.class, "Unable to read Configuration file %s: %s", configFilePath, e.getMessage());
        }
    }

    private void extractConfigValue(String key, String value) {

        switch (key) {
            case "debug", "d" -> {
                App.setDebug(Integer.parseInt(value));
                info(App.class, "Debug level set to %d", App.getDebug());
            }
            case "configFile", "cf" -> {
                configFilePath = value;
                info(App.class, "Configuration file set to %s", configFilePath);
            }
            case "app.window.size", "window", "w" -> {
                App.setWindowSize(getDimensionFromString(value, "640x400"));

                info(App.class, "Window size set to %s", App.getWindowSize());
            }
        }
    }

    public <T> T getConfigValue(String key, String defaultValue) {
        switch (key) {
            case "app.window.size", "window" -> {
                return (T) getDimensionFromString((String) config.getProperty(key), defaultValue);
            }
            case "app.debug.level", "debug", "dl" -> {
                return (T) Integer.valueOf(config.getProperty(key, defaultValue));
            }
            case "app.physic.world.size", "app.window.buffer.resolution" -> {
                return (T) getDimensionFromString(config.getProperty(key), defaultValue);
            }
            default -> {
                error(Configuration.class, "Unknown configuration key %s", key);
                return null;
            }
        }
    }

    private Dimension getDimensionFromString(String value, String defaultValue) {
        if (!value.contains("x")) {
            info(App.class, "%s is not a valid dimension, using default value %s", value, defaultValue);
            return getDimensionFromString(defaultValue, null);
        }
        String[] parts = value.split("x");
        if (parts.length == 2) {
            try {
                return new Dimension(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                error(App.class, "Unable to parse dimension %s: %s", value, e.getMessage());
            }
        }
        return null;
    }


    public String[] getStrings(String s, String separator, String s1) {
        return config.getOrDefault(s, s1).toString().split(separator);
    }

    public String getString(String key, String defaultValue) {
        return config.getOrDefault(key, defaultValue).toString();
    }
}
