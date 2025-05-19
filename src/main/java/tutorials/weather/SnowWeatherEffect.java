package tutorials.weather;

import tutorials.Entity;
import tutorials.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowWeatherEffect extends Entity implements WeatherEffect {
    public List<SnowFlake> snowflakes = new ArrayList<>();

    private World world;

    public SnowWeatherEffect(World world, int nb) {
        this.world = world;
        for (int i = 0; i < nb; i++)
            snowflakes.add(new SnowFlake((int) world.getWidth(), (int) world.getHeight(), new Random()));
    }

    @Override
    public void update(long elapsed) {
        for (SnowFlake f : snowflakes) f.update((int) world.getWidth(), (int) world.getHeight(), elapsed);
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
