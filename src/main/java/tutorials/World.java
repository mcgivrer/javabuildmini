package tutorials;

import java.awt.*;
import java.awt.geom.Point2D;

public class World extends Entity {
    private Point2D gravity;
    private double friction = 0.998;

    public World(String name, double width, double height) {
        super(name, 0, 0, width, height);
    }

    public World() {
        super("world", 0, 0, 640, 480);
        setGravity(0, 9.81);
        setColor(new Color(0.3f, 0.3f, 0.3f, 0.6f));
        setFillColor(null);
        setType(PhysicType.STATIC);
    }

    public World setGravity(double x, double y) {
        this.gravity = new Point2D.Double(x, y);
        return this;
    }

    public Point2D getGravity() {
        return gravity;
    }

    public double getFriction() {
        return friction;
    }
}
