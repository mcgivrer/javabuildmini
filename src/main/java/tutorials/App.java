package tutorials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.Map;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;
import static tutorials.Log.*;

public class App implements KeyListener {
    public static ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
    public Configuration config = new Configuration();
    private static int debug = 0;
    private static boolean exit = false;
    private static final int FPS = 60;

    private boolean[] keys = new boolean[1024];

    private JFrame window;
    private static Dimension windowSize = new Dimension(640, 400);
    private Renderer renderer;

    public App() {
        info(App.class, "Start the application %s", messages.getString("app.title"));
    }


    public void run(String[] args) {
        info(App.class, "Running...");
        init(args);
        process();
        dispose();
        info(App.class, "End of Running.");

    }

    private void init(String[] args) {
        info(App.class, "Initializing...");
        config.processConfiguration(args);
        renderer = new Renderer(this);
        createWindow(messages.getString("app.window.title"), windowSize);
    }

    public void createWindow(String title, Dimension size) {
        window = new JFrame(title);
        window.setSize(size);
        window.setPreferredSize(size);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(this);
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
        window.createBufferStrategy(3);

        debug(App.class, 1, "Window created.");
    }

    private void process() {
        info(App.class, "Processing...");
        long startTime = 0, endTime = 0, elapsed = 0;
        do {
            startTime = endTime;
            update();
            render();
            endTime = System.currentTimeMillis();
            elapsed = endTime - startTime;
            if (elapsed < (1000 / FPS)) {
                try {
                    sleep((1000 / FPS) - elapsed);
                } catch (InterruptedException e) {
                    error(App.class, "Unable to sleep for %d ms", (1000 / FPS) - elapsed);
                }
            }
        } while (!exit);
        info(App.class, "End of Processing.");
    }

    public void update() {


    }

    public void render() {
        BufferStrategy bs = window.getBufferStrategy();
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        renderer.draw(g);

        g.dispose();
        bs.show();
    }


    private void dispose() {
        info(App.class, "Disposing...");
        if (window != null && window.isActive()) {
            window.dispose();
        }
        info(App.class, "End of Disposing.");
    }

    public static void main(String[] args) {
        App app = new App();
        app.run(args);
    }

    public static boolean isDebugGreaterThan(int level) {
        return debug > level;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> {
                info(App.class, "Exiting...");
                exit = true;
            }
            case KeyEvent.VK_D -> {
                debug(App.class, 0, "Debug level set to %d", debug);
                debug = debug + 1 > 6 ? 0 : debug + 1;
            }
        }
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

    public JFrame getWindow() {
        return window;
    }
}
