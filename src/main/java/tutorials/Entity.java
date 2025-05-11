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

    protected double dx = 0, dy = 0;

    protected Color fillColor = Color.BLUE;
    protected Color color = Color.WHITE;

    protected List<Behavior> behaviors = new ArrayList<>();

    public Entity(String name, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.name = name;
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

    public <T extends Entity> T setSize(double width, double height) {
        setRect(x, y, width, height);
        return (T) this;
    }

    public <T extends Entity> T setActive(boolean active) {
        this.active = active;
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
}
