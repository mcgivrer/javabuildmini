package tutorials;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Entity extends Rectangle2D.Double {
    protected static int index = 0;
    protected final int id = index++;
    protected String name = "entity_%d".formatted(id);

    protected boolean active = true;

    public double dx = 0;
    public double dy = 0;

    public PhysicType type;

    protected Color fillColor = Color.BLUE;
    protected Color color = Color.WHITE;

    protected List<Behavior> behaviors = new ArrayList<>();

    public Entity(String name, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.name = name;
        setType(PhysicType.DYNAMIC);
    }

    public Entity() {
    }

    public <T extends Entity> T add(Behavior<T> behavior) {
        behaviors.add(behavior);
        return (T) this;
    }

    public <T extends Entity> T setPosition(double x, double y) {
        setRect(x, y, width, height);
        return (T) this;
    }

    public <T extends Entity> T setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        return (T) this;
    }

    public <T extends Entity> T addVelocity(double vdx, double vdy) {
        this.dx += vdx;
        this.dy += vdy;
        return (T) this;
    }

    public <T extends Entity> T setSize(double width, double height) {
        setRect(x, y, width, height);
        return (T) this;
    }

    public <T extends Entity> T setActive(boolean active) {
        this.active = active;
        return (T) this;
    }

    public <T extends Entity> T setColor(Color c) {
        this.color = c;
        return (T) this;
    }

    public <T extends Entity> T setFillColor(Color fc) {
        this.fillColor = fc;
        return (T) this;
    }

    public <T extends Entity> T setType(PhysicType type) {
        this.type = type;
        return (T) this;
    }

    public boolean isActive() {
        return active;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getColor() {
        return color;
    }

    public List<Behavior> getBehaviors() {
        return this.behaviors;
    }

    public PhysicType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dy=" + dy +
                ", dx=" + dx +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", active=" + active +
                '}';
    }

}
