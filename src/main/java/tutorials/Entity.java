package tutorials;

import java.awt.geom.Rectangle2D;

public class Entity extends Rectangle2D.Double {
    protected static int index = 0;
    protected final int id = index++;
    protected String name = "entity_%d".formatted(id);

    protected boolean active = true;

    public Entity(String name, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.name = name;
    }

    public Entity() {
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


}
