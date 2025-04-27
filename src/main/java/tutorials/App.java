package tutorials;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.ResourceBundle;

public class App {
    public static ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
    Properties config = new Properties();
    private String configFilePath = "/config.properties";
    private int debug = 0;

    public App() {
        info(App.class, "Start the application %s", messages.getString("app.title"));
    }

    public void run(String[] args) {
        info(App.class, "Running...");
        init(args);
        process();
        dispose();
        info(App.class, "Done.");

    }

    private void init(String[] args) {
        info(App.class, "Initializing...");
        parseArguments(args);
        info(App.class, "Configuration file: %s", configFilePath);
        loadConfigurationFile();
        info(App.class, "Configuration loaded.");
        parseArguments(args);
        config.forEach((key, value) -> extractConfigValue((String) key, (String) value));
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

    public void extractConfigValue(String key, String value) {

        switch (key) {
            case "debug", "d" -> {
                debug = Integer.parseInt(value);
                info(App.class, "Debug level set to %d", debug);
            }
            case "configFile", "cf" -> {
                configFilePath = value;
                info(App.class, "Configuration file set to %s", configFilePath);
            }
        }
    }

    private void process() {
        info(App.class, "Processing...");
        info(App.class, "done.");
    }


    private void dispose() {
        info(App.class, "Disposing...");
        info(App.class, "done.");
    }


    public static void main(String[] args) {
        App app = new App();
        app.run(args);
    }

    public static void log(Class<?> cls, String level, String message, Object... args) {
        System.out.printf("%s;%s;%s;%s%n", ZonedDateTime.now(), cls.getCanonicalName(), level, message.formatted(args));
    }

    public static void info(Class<?> cls, String message, Object... args) {
        log(cls, "INFO", message, args);
    }

    private static void error(Class<App> cls, String message, Object... args) {
        log(cls, "ERROR", message, args);
    }

}
