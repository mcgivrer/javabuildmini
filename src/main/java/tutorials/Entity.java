package tutorials;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
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
    protected Material material = Material.DEFAULT;
    protected double mass = 1.0;

    protected Color fillColor = Color.BLUE;
    protected Color color = Color.WHITE;

    protected List<Behavior> behaviors = new ArrayList<>();
    private int priority = 0;
    private boolean stickToViewport = false;
    private float alpha = 1.0f;

    private Shape shape;

    public Entity() {
        super();
    }

    public Entity(String name) {
        super();
        this.name = name;
    }

    public Entity(String name, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.name = name;
        setType(PhysicType.DYNAMIC);
    }

    public <T extends Entity> T add(Behavior<T> behavior) {
        behaviors.add(behavior);
        return (T) this;
    }

    public void update(long elapsed) {
        // nothing by default.
    }

    public void draw(Graphics2D g) {
        if (getShape() != null) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
            if (getFillColor() != null) {
                g.setColor(getFillColor());
                g.fill(getShape());
            }
            if (getColor() != null) {
                g.setColor(getColor());
                g.draw(getShape());
            }
        }
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

    public <T extends Entity> T setStickToViewport(boolean stick) {
        this.stickToViewport = stick;
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

    public <T extends Entity> T setMaterial(Material m) {
        this.material = m;
        return (T) this;
    }

    public <T extends Entity> T setMass(double m) {
        this.mass = m;
        return (T) this;
    }

    public <T extends Entity> T setPriority(int m) {
        this.priority = m;
        return (T) this;
    }

    public <T extends Entity> T setAlpha(float a) {
        this.alpha = a;
        return (T) this;
    }


    public <T extends Entity> T setShape(Shape s) {
        this.shape = s;
        return (T) this;
    }

    public String getName() {
        return name;
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

    public Material getMaterial() {
        return material;
    }

    public double getMass() {
        return mass;
    }

    public Point2D getVelocity() {
        return new Point2D.Double(dx, dy);
    }

    public <T extends Entity> T setVelocity(Point2D v) {
        this.dx = v.getX();
        this.dy = v.getY();
        return (T) this;
    }

    public Point2D getPosition() {
        return new Point2D.Double(x, y);
    }

    public int getPriority() {
        return priority;
    }

    public boolean isStickToViewport() {
        return stickToViewport;
    }

    public float getAlpha() {
        return alpha;
    }

    public Shape getShape() {
        if (shape instanceof Rectangle2D.Double) {
            return new Rectangle2D.Double(x, y, width, height);
        } else if (shape instanceof Ellipse2D.Double) {
            return new Ellipse2D.Double(x, y, width, height);
        }
        return null;
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
