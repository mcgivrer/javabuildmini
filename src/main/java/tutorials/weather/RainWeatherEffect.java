package tutorials.weather;

import tutorials.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainWeatherEffect implements WeatherEffect {

    private final World world;
    public List<Drop> drops = new ArrayList<>();

    public RainWeatherEffect(World world, int nb) {
        this.world = world;
        for (int i = 0; i < nb; i++) drops.add(new Drop((int) world.getWidth(), (int) world.getHeight(), new Random()));
    }

    public void update(double elapsed) {
        for (Drop g : drops) g.update((int) world.getWidth(), (int) world.getHeight(), elapsed);
    }

    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        for (Drop g : drops) {
            g2.setColor(new Color(100, 100, 255, 180));
            g2.drawLine((int) g.x, (int) g.y, (int) (g.x - g.vitesseX * 2), (int) (g.y - g.vitesseY * 2));
        }
    }

    @Override
    public boolean isActive() {
        return !drops.isEmpty();
    }
}