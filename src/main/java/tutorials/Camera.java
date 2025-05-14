package tutorials;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Camera extends Entity {

    private Entity target;
    private double tweenFactor = 0.02;
    private double offsetX = 0, offsetY = 0;

    public Camera(String name) {
        super(name);
        setColor(Color.DARK_GRAY);
        setFillColor(null);
        setType(PhysicType.STATIC);
    }


    public Camera setTarget(Entity t) {
        this.target = t;
        return this;
    }

    public Camera setTweenFactor(double factor) {
        this.tweenFactor = factor;
        return this;
    }

    public Camera setViewport(double width, double height) {
        this.setFrame(x, y, width, height);
        return this;
    }


    public void update(double deltaTime) {
        if (target != null) {

            double targetX = target.getX() - (getWidth() + target.getWidth()) / 2;
            double targetY = target.getY() - (getHeight() + target.getHeight()) / 2;
            double newX = getX() + (targetX - getX()) * tweenFactor * deltaTime;
            double newY = getY() + (targetY - getY()) * tweenFactor * deltaTime;
            setPosition(newX, newY);

        }
    }

    public Camera setOffset(int offX, int offY) {
        this.offsetX = offX;
        this.offsetY = offY;
        return this;
    }

    public Rectangle2D getViewport() {
        return new Rectangle2D.Double(x - offsetX, y - offsetY, width + offsetX * 2, height + offsetY * 2);
    }
}
