package tutorials.weather;

import tutorials.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowWeatherEffect extends Entity implements WeatherEffect {
    public List<SnowFlake> snowflakes = new ArrayList<>();

    private BufferedImage bufferedImage;

    public SnowWeatherEffect(String name, BufferedImage bufferedImage, int nb) {
        super(name);
        this.bufferedImage = bufferedImage;
        for (int i = 0; i < nb; i++)
            snowflakes.add(new SnowFlake((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight(), new Random()));
    }

    @Override
    public void update(long elapsed) {
        for (SnowFlake f : snowflakes) f.update((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight(), elapsed);
    }

    @Override
    public void draw(Graphics2D g) {
        for (SnowFlake f : snowflakes) {
            g.setColor(new Color(255, 255, 255, 200));
            g.fillOval((int) f.x, (int) f.y, 4, 4);
        }
    }

    public boolean isActive() {
        return !snowflakes.isEmpty();
    }

}
