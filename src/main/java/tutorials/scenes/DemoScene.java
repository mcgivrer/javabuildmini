package tutorials.scenes;

import tutorials.*;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static tutorials.App.isKeyPressed;

public class DemoScene extends AbstractScene implements Scene {
    public DemoScene(App app, String name) {
        super(app, name);
    }

    @Override
    public void init() {
        JFrame window = app.getWindow();
        world.setRect(0, 0, window.getWidth(), window.getHeight());

        Log.info(this.getClass(), "Initializing...");
        Entity player = new Entity("player",
                (window.getWidth() - 16) * 0.5,
                (window.getHeight() - 24) * 0.5,
                16,
                24).add(new Behavior<Entity>() {
            @Override
            public void update(App app, Entity e, double deltaTime) {
                if (isKeyPressed(KeyEvent.VK_UP)) {
                    e.addVelocity(0, -3.0);
                }
                if (isKeyPressed(KeyEvent.VK_DOWN)) {
                    e.addVelocity(0, 0.010);
                }
                if (isKeyPressed(KeyEvent.VK_LEFT)) {
                    e.addVelocity(-0.010, 0);
                }
                if (isKeyPressed(KeyEvent.VK_RIGHT)) {
                    e.addVelocity(0.010, 0);
                }
                if (isKeyPressed(KeyEvent.VK_SPACE)) {
                    e.dy *= 0.9;
                    e.dx *= 0.9;
                }
            }
        });
        add(player);
        Log.info(this.getClass(), "End of Initialization.");
    }
}
