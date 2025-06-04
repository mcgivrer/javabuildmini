package tutorials;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class World extends Entity {
    private Point2D gravity;
    private double friction = 0.99;
    private float dayTime = 0;
    private float day = 1;
    private Season season;

    public World(String name, double width, double height) {
        super(name, 0, 0, width, height);
        setGravity(0, 9.81);
        setColor(new Color(0.3f, 0.3f, 0.3f, 0.6f));
        setFillColor(null);
        setType(PhysicType.STATIC);
        setDayTime(ZonedDateTime.now().getHour());
        // generate Season.
        setSeason(Season.getSeason(LocalDate.now()));
    }

    public World() {
        this("world", 640, 480);
    }


    public World setGravity(double x, double y) {
        this.gravity = new Point2D.Double(x, y);
        return this;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) -getWidth(), (int) getHeight(), (int) (3 * getWidth()), 200);
    }

    public Point2D getGravity() {
        return gravity;
    }

    public double getFriction() {
        return friction;
    }


    public void update(long elapsed) {
        dayTime = (dayTime + (float) elapsed * 0.000167f) % 24;
        if (dayTime > 23.98f && dayTime < 24.00) {
            dayTime += 1.0f;
        }
    }

    public World setDayTime(float dayTime) {
        this.dayTime = dayTime;
        return this;
    }

    public World setDay(float day) {
        this.day = day;
        return this;
    }

    public float getDayTime() {
        return dayTime;
    }

    public float getDay() {
        return day;
    }


    public World setSeason(Season season) {
        this.season = season;
        return this;
    }

    public Season getSeason() {
        return season;
    }
}
