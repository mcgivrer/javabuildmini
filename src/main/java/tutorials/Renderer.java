package tutorials;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

public class Renderer {

    private final App app;

    Renderer(App app) {
        this.app = app;
    }

    public void draw(Graphics2D g) {
        g.setRenderingHints(Map.of(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        ));
        JFrame window = app.getWindow();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());
        Scene scene = AbstractScene.getCurrentScene();
        if (Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
            g.translate(
                    -scene.getActiveCamera().getPosition().getX(),
                    -scene.getActiveCamera().getPosition().getY());
        }
        scene.getEntities().stream()
                .filter(Entity::isActive)
                .filter(entity -> !(entity instanceof Camera))
                .forEach(e -> {
                    drawEntity(g, e);
                });
        if (Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
            Camera cam = scene.getActiveCamera();
            g.draw(cam.getViewport());
            g.translate(
                    cam.getPosition().getX(),
                    cam.getPosition().getY());
        }
    }

    private void drawEntity(Graphics2D g, Entity e) {
        if (e.getFillColor() != null) {
            g.setColor(e.getFillColor());
            g.fill(e);
        }
        if (e.getColor() != null) {
            g.setColor(e.getColor());
            g.draw(e);
        }
    }


    private static void drawText(Graphics2D g, String message, int x, int y, Color c, Font f, TextAlign ta) {
        if (c != null) g.setColor(c);
        if (f != null) g.setFont(f);
        if (ta != null) {
            switch (ta) {
                case LEFT -> x = x;
                case RIGHT -> x = x - g.getFontMetrics().stringWidth(message);
                case CENTER -> x = x - g.getFontMetrics().stringWidth(message) / 2;
            }
        }
        g.drawString(message, x, y);
    }
}
