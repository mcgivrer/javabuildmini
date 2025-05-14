package tutorials.scenes;

import tutorials.Scene;
import tutorials.AbstractScene;
import tutorials.Entity;
import tutorials.Log;
import tutorials.Behavior;
import tutorials.App;
import tutorials.Material;
import tutorials.Camera;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static tutorials.InputHandler.isKeyPressed;

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
                24)
                .setMass(80.0)
                .setMaterial(Material.PLASTIC)
                .add(new Behavior<Entity>() {
                    @Override
                    public void update(App app, Entity e, double deltaTime) {
                        double step = 10.5;
                        if (isKeyPressed(KeyEvent.VK_UP)) {
                            e.addVelocity(0, -step * 5);
                        }
                        if (isKeyPressed(KeyEvent.VK_DOWN)) {
                            e.addVelocity(0, step);
                        }
                        if (isKeyPressed(KeyEvent.VK_LEFT)) {
                            e.addVelocity(-step, 0);
                        }
                        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
                            e.addVelocity(step, 0);
                        }
                        if (isKeyPressed(KeyEvent.VK_SPACE)) {
                            e.dy *= 0.9;
                            e.dx *= 0.9;
                        }
                    }
                });
        add(player);

        generateEntity("enemy_%d", 100, Color.RED, Color.RED.darker());

        // define the new active camera for the scene.
        add(new Camera("cam01")
                .setOffset(40, 40)
                .setViewport(app.getWindow().getWidth(), app.getWindow().getHeight())
                .setTarget(player)
                .setTweenFactor(0.002));
        Log.info(this.getClass(), "End of Initialization.");
    }

    private void generateEntity(String rootName, int nb, Color color, Color fillColor) {
        for (int i = 0; i < nb; i++) {
            Entity enemy = new Entity(String.format(rootName, i),
                    Math.random() * world.getWidth(), Math.random() * world.getHeight(), 8, 8)
                    .setColor(color)
                    .setFillColor(fillColor)
                    .setMaterial(Material.RUBBER)
                    .setMass(10.0);
            add(enemy);
        }
    }
}
