package tutorials;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Renderer {

    private final JFrame window;
    private List<VFXDraw> postProcessingVFXs = new ArrayList<>();
    private boolean vfxActive = true;

    Renderer(JFrame window) {
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
                    drawEntity(g, e);
                    if (!e.isStickToViewport() && Optional.ofNullable(scene.getActiveCamera()).isPresent()) {
                        Camera cam = scene.getActiveCamera();
                        g.draw(cam.getViewport());
                        g.translate(
                                cam.getPosition().getX(),
                                cam.getPosition().getY());
                    }
                });
    }

    private void drawEntity(Graphics2D g, Entity e) {
        e.draw(g);
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
