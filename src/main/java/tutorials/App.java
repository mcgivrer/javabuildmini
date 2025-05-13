package tutorials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;
import static tutorials.Log.*;

public class App extends JPanel {
    public static ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
    private static double maxSpeed = 4.0;
    public Configuration config = new Configuration();
    private static int debug = 0;
    private static boolean exit = false;
    private static final int FPS = 60;


    private JFrame window;
    private static Dimension windowSize = new Dimension(640, 400);
    private Renderer renderer;
    private InputHandler inputHandler;

    public App() {
        super();
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
        AbstractScene.loadScenes(this, config);
        renderer = new Renderer(this);
        inputHandler = new InputHandler(this);
        createWindow(messages.getString("app.window.title"), windowSize);
        createScene();
    }

    private void createScene() {
        AbstractScene.initScene();
    }

    public void createWindow(String title, Dimension size) {
        window = new JFrame(title);
        setSize(size);
        setPreferredSize(size);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(inputHandler);
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
        window.setContentPane(this);
        window.setFocusTraversalKeysEnabled(true);
        window.pack();
        window.setVisible(true);
        window.createBufferStrategy(3);
        window.setIgnoreRepaint(true);
        window.requestFocus();

        debug(App.class, 1, "Window created.");
    }

    private void process() {
        info(App.class, "Processing...");
        long startTime = 0, endTime = 0, elapsed = 0;
        do {
            startTime = endTime;
            update(elapsed > (1000 / FPS) ? 1 : (1000 / FPS) - elapsed);
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

    public void update(long elapsed) {
        Scene scene = AbstractScene.getCurrentScene();
        World world = scene.getWorld();
        scene.getEntities().stream().filter(e -> e.getType().equals(PhysicType.DYNAMIC)).forEach(e -> {
            for (Behavior b : e.getBehaviors()) {
                b.update(this, e, elapsed);
            }
            e.setPosition(
                    e.x + ((e.dx + world.getGravity().getX()) * elapsed / e.getMass()),
                    e.y + ((e.dy + world.getGravity().getY()) * elapsed / e.getMass()));

            constrainsEntityToWorld(scene, e, elapsed);

            // friction in World
            e.setVelocity(e.dx * (world.getFriction()),
                    e.dy * (world.getFriction()));
            // maximize speed
            e.setVelocity(maximize(e.getVelocity(), maxSpeed, maxSpeed));
        });
    }

    private Point2D maximize(Point2D velocity, double mxVx, double mxVy) {
        return new Point2D.Double(
                Math.signum(velocity.getX()) * Math.min(Math.abs(velocity.getX()), mxVx),
                Math.signum(velocity.getY()) * Math.min(Math.abs(velocity.getY()), mxVy));
    }

    private void constrainsEntityToWorld(Scene scene, Entity e, double elapsed) {
        if (e.x < scene.getWorld().getX()) {
            e.x = scene.getWorld().getX();
            e.dx = -e.dx * e.getMaterial().elasticity() * elapsed;
        }
        if (e.x > scene.getWorld().width - e.width) {
            e.x = scene.getWorld().width - e.width;
            e.dx = -e.dx * e.getMaterial().elasticity() * elapsed;
        }
        if (e.y < scene.getWorld().getY()) {
            e.y = scene.getWorld().getY();
            e.dy = -e.dy * e.getMaterial().elasticity() * elapsed;
        }
        if (e.y > scene.getWorld().height - e.height) {
            e.y = scene.getWorld().height - e.height;
            e.dy = -e.dy * e.getMaterial().elasticity() * elapsed;
        }
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


    public static void setDebug(int d) {
        debug = d;
    }

    public static int getDebug() {
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

    public static void setExit(boolean ex) {
        exit = ex;
    }

    public static void setMaxSpeed(double ms) {
        maxSpeed = ms;
    }


}
