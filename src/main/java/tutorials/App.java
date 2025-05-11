package tutorials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.ResourceBundle;

public class App {
    public static ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
    public Configuration config = new Configuration();
    private static int debug = 0;
    private static boolean exit = false;

    private JFrame window;
    private static Dimension windowSize = new Dimension(640, 400);

    public App() {
        Log.info(App.class, "Start the application %s", messages.getString("app.title"));
    }

    public static void setDebug(int d) {
        debug = d;
    }

    public static Object getDebug() {
        return debug;
    }

    public static void setWindowSize(Dimension dimensionFromString) {
        windowSize = dimensionFromString;
    }

    public static Dimension getWindowSize() {
        return windowSize;
    }

    public void run(String[] args) {
        Log.info(App.class, "Running...");
        init(args);
        process();
        dispose();
        Log.info(App.class, "Done.");

    }

    private void init(String[] args) {
        Log.info(App.class, "Initializing...");
        config.processConfiguration(args);
        createWindow(messages.getString("app.window.title"), windowSize);
    }

    public void createWindow(String title, Dimension size) {
        window = new JFrame(title);
        window.setSize(size);
        window.setPreferredSize(size);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit = true;
            }
        });
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // to do apply specific operation on resizing.
            }
        });

        window.setVisible(true);


        Log.debug(App.class, 1, "Window created.");
    }

    private void process() {
        Log.info(App.class, "Processing...");
        do {
            // do something or not.
        } while (!exit);
        Log.info(App.class, "done.;");
    }

    private void dispose() {
        Log.info(App.class, "Disposing...");
        window.dispose();
        Log.info(App.class, "done.");
    }

    public static void main(String[] args) {
        App app = new App();
        app.run(args);
    }

    public static boolean isDebugGreaterThan(int level) {
        return debug > level;
    }
}
