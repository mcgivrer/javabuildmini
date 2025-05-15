package tutorials.scenes;

import tutorials.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import static tutorials.InputHandler.isKeyPressed;

public class DemoScene extends AbstractScene implements Scene {
    public DemoScene(App app, String name) {
        super(app, name);
    }

    @Override
    public void init() {
        app.getRenderer().addVFX(new OldTerminalVFX(0.3f));
        JFrame window = app.getWindow();
        world.setRect(0, 0, window.getWidth(), window.getHeight());
        world.setFillColor(new Color(0.0f, 0.4f, 0.8f));
        world.setSkyColors(
                new Color(0.9f, 0.8f, 0.1f),
                new Color(0.9f, 0.8f, 0.1f),
                new Color(0.1f, 0.2f, 0.7f),
                new Color(0.1f, 0.3f, 0.7f)
        );

        Log.info(this.getClass(), "Initializing...");
        Entity player = new Entity("player",
                (window.getWidth() - 16) * 0.5,
                (window.getHeight() - 24) * 0.5,
                32,
                48)
                .setMass(80.0)
                .setMaterial(Material.PLASTIC)
                .setPriority(5)
                .add((app, e, deltaTime) -> {
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
                });
        add(player);

        generateEntity("enemy_%d", 100, Color.RED, Color.RED.darker());

        add(new TextEntity("score")
                .setText("%05d")
                .setValue(0)
                .setTextColor(Color.WHITE)
                .setShadowColor(Color.BLACK)
                .setFont(app.getWindow().getGraphics().getFont().deriveFont(26.0f))
                .setPosition(40, 56)
                .setStickToViewport(true));


        add(new TextEntity("life")
                .setText("%01d")
                .setValue(3)
                .setTextColor(Color.WHITE)
                .setShadowColor(Color.BLACK)
                .setFont(app.getWindow().getGraphics().getFont().deriveFont(26.0f))
                .setPosition(app.getWindow().getWidth() - 50, 56)
                .setStickToViewport(true));
        // define the new active camera for the scene.
        add(new Camera("cam01")
                .setOffset(40, 40)
                .setViewport(app.getWindow().getWidth(), app.getWindow().getHeight())
                .setTarget(player)
                .setTweenFactor(0.002));

        Log.info(this.getClass(), "End of Initialization.");

    }

    public void resize(JFrame window) {
        getEntity("score").setPosition(40, 56);
        getEntity("life").setPosition(app.getWindow().getWidth() - 50, 56);
    }

    private Entity getEntity(String name) {
        return getEntities().stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    private void generateEntity(String rootName, int nb, Color color, Color fillColor) {
        for (int i = 0; i < nb; i++) {
            Color eColor = Utils.randomColor(0.8f, 0.8f, 0.8f, 1.0f);

            Entity enemy = new Entity(rootName.formatted(i),
                    Math.random() * world.getWidth(), Math.random() * world.getHeight(),
                    16, 16)
                    .setColor(eColor)
                    .setFillColor(eColor.darker())
                    .setMaterial(Material.RUBBER)
                    .setMass(10.0)
                    .setAlpha((float) Math.random())
                    .setPriority((int) (Math.random() * 10));
            enemy.setShape(new Ellipse2D.Double(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight()));

            add(enemy);
        }
    }
}
