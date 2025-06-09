package tutorials.core.entity.weather;

import tutorials.core.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainWeatherEffect extends Entity implements WeatherEffect {

    private final BufferedImage bufferedImage;
    public List<Drop> drops = new ArrayList<>();

    public RainWeatherEffect(String name, BufferedImage bufferedImage, int nb) {
        super(name);
        this.bufferedImage = bufferedImage;
        for (int i = 0; i < nb; i++) drops.add(new Drop((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight(), new Random()));
    }

    @Override
    public void update(long elapsed) {
        for (Drop g : drops) g.update((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight(), elapsed);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        for (Drop g : drops) {
            g2.setColor(new Color(100, 100, 255, 180));
            g2.drawLine((int) g.x, (int) g.y, (int) (g.x - g.dx * 2), (int) (g.y - g.dy * 2));
        }
    }

    @Override
    public boolean isActive() {
        return !drops.isEmpty();
    }
}