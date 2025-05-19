package tutorials;

import tutorials.sfx.VFXDraw;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Renderer {

    private final App app;
    private final JFrame window;
    private List<VFXDraw> postProcessingVFXs = new ArrayList<>();
    private boolean vfxActive = true;

    Renderer(App app, JFrame window) {
        this.app = app;
        this.window = window;
    }

    public void draw(Graphics2D g, Scene scene) {
        g.setRenderingHints(Map.of(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        ));

        // clear drawing.
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());

        // draw entities in scene
        drawEntities(g, scene);

        // draw postprocessed visual effects.
        if (vfxActive) {
            postProcessing(g);
        }
        if (app.getHelpDisplay()) {
            drawHelpText(g, scene);
        }
    }

    private void postProcessing(Graphics2D g) {
        for (VFXDraw vfx : postProcessingVFXs) {
            vfx.update(g, window);
        }

    }

    private void drawEntities(Graphics2D g, Scene scene) {
        scene.getEntities().stream()
                .filter(Entity::isActive)
                .filter(entity -> !(entity instanceof Camera))
                .sorted(Comparator.comparingInt(Entity::getPriority))
                .forEach(e -> {
                    if (!e.isStickToViewport() && Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
                        g.translate(
                                -scene.getActiveCamera().getPosition().getX(),
                                -scene.getActiveCamera().getPosition().getY());
                    }
                    e.draw(g);
                    if (!e.isStickToViewport() && Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
                        Camera cam = scene.getActiveCamera();
                        g.draw(cam.getViewport());
                        g.translate(
                                cam.getPosition().getX(),
                                cam.getPosition().getY());
                    }
                });

    }

    private void drawHelpText(Graphics2D g, Scene scene) {
        if (Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
            g.translate(
                    -scene.getActiveCamera().getPosition().getX(),
                    -scene.getActiveCamera().getPosition().getY());
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.setFont(g.getFont().deriveFont(12.0f));
        String[] help = app.messages.getString("app.display.help").split("\\|");
        int height = window.getHeight() - help.length * g.getFontMetrics().getHeight();
        for (int i = 0; i < help.length; i++) {
            g.setColor(Color.BLACK);
            g.drawString(help[i], 20 + 1, height + (i * g.getFontMetrics().getHeight()) + 1);
            g.setColor(Color.WHITE);
            g.drawString(help[i], 20, height + (i * g.getFontMetrics().getHeight()));
        }
        if (Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
            Camera cam = scene.getActiveCamera();
            g.draw(cam.getViewport());
            g.translate(
                    cam.getPosition().getX(),
                    cam.getPosition().getY());
        }
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
