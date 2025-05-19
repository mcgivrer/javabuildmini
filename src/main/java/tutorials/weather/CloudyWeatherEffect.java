package tutorials.weather;

import tutorials.Entity;
import tutorials.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloudyWeatherEffect extends Entity implements WeatherEffect {
    private final World world;
    List<Cloud> clouds = new ArrayList<>();
    Random rand = new Random();

    public CloudyWeatherEffect(String name, World world) {
        super(name);
        this.world = world;
        init();
    }

    private void init() {
        double x = rand.nextInt((int) world.getWidth());
        double y = rand.nextInt((int) world.getHeight() / 2); // nuages en haut du ciel
        double taille = 60 + rand.nextInt(80);
        float opacite = 0.5f + rand.nextFloat() * 0.4f;
        clouds.add(new Cloud(x, y, taille, opacite));
    }

    @Override
    public void update(long elapsed) {
        for (Cloud c : clouds) c.update(elapsed);
        // Ajoute de nouveaux nuages aléatoirement, supprime ceux devenus invisibles
        if (rand.nextDouble() < 0.01) init();
        clouds.removeIf(n -> n.opacite < 0.01 || n.x > width + 100);
    }

    @Override
    public void draw(Graphics2D g) {
        for (Cloud c : clouds) {
            c.draw(g);
        }
    }
}



