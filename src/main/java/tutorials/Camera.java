package tutorials;

import javax.swing.*;

public class Camera extends Entity {
    private Entity target;
    private double tween = 0.02;

    public Camera() {
        this("camera");
    }

    public Camera(String name) {
        super(name, 0, 0, 320, 300);
    }

    public void update(App app, double deltaTime) {
        JFrame window = app.getWindow();
        this.setPosition(
                target.getX() - ((window.getWidth() - width - target.width) * 0.5),
                target.getY() - ((window.getHeight() - height - target.height) * 0.5));
    }

    public Camera setTarget(Entity entity) {
        this.target = entity;
        return this;
    }

    public Camera setTweenFactor(double tf) {
        this.tween = tf;
        return this;
    }

    public Camera setViewport(int w, int h) {
        setSize(w, h);
        return this;
    }

}
