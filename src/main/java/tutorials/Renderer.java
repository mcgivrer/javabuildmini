package tutorials;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Renderer {

    private final App app;
    private List<VFXDraw> postProcessingVFXs = new ArrayList<>();
    private boolean vfxActive = true;

    Renderer(App app) {
        this.app = app;
    }

    public void draw(Graphics2D g, Scene scene) {
        g.setRenderingHints(Map.of(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        ));
        JFrame window = app.getWindow();
        // clear drawing.
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());
        // move camera.
        if (Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
            g.translate(
                    -scene.getActiveCamera().getPosition().getX(),
                    -scene.getActiveCamera().getPosition().getY());
        }
        // draw entities in scene
        drawEntities(g, scene, false);
        // move back camera
        if (Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
            Camera cam = scene.getActiveCamera();
            g.draw(cam.getViewport());
            g.translate(
                    cam.getPosition().getX(),
                    cam.getPosition().getY());
        }
        // draw camera fixed entities
        drawEntities(g, scene, true);
        // draw postprocessed visual effects.
        if (vfxActive) {
            postProcessing(g);
        }
    }

    private void postProcessing(Graphics2D g) {
        for (VFXDraw vfx : postProcessingVFXs) {
            vfx.update(g, app.getWindow());
        }

    }

    private void drawEntities(Graphics2D g, Scene scene, boolean stickToViewport) {
        scene.getEntities().stream()
                .filter(Entity::isActive)
                .filter(e -> e.isStickToViewport() == stickToViewport)
                .filter(entity -> !(entity instanceof Camera))
                .sorted(Comparator.comparingInt(Entity::getPriority))
                .forEach(e -> {
                    if (e instanceof TextEntity te) {
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, te.getAlpha()));
                        drawText(g, te.getText(),
                                (int) te.getX() + 2, (int) te.getY() + 2,
                                te.getShadowColor(),
                                te.getFont(),
                                te.getTextAlign());
                        drawText(g, te.getText(),
                                (int) te.getX(), (int) te.getY(),
                                te.getTextColor(),
                                te.getFont(),
                                te.getTextAlign());
                        te.setSize(app.getGraphics().getFontMetrics().stringWidth(te.getText()), app.getGraphics().getFontMetrics().getHeight());
                    } else if ("World Sun Sky StarSky".contains(e.getClass().getSimpleName())) {
                        e.draw(g);
                    } else {
                        drawEntity(g, e);
                    }
                });
    }

    private void drawEntity(Graphics2D g, Entity e) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, e.getAlpha()));
        if (e.getFillColor() != null) {
            g.setColor(e.getFillColor());
            g.fill(e.getShape());
        }
        if (e.getColor() != null) {
            g.setColor(e.getColor());
            g.draw(e.getShape());
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

    public void addVFX(VFXDraw vfx) {
        if (!this.postProcessingVFXs.contains(vfx)) {
            this.postProcessingVFXs.add(vfx);
        }
    }

    public void setVFX(boolean active) {
        this.vfxActive = active;
    }

    public boolean getVFX() {
        return this.vfxActive;
    }
}
