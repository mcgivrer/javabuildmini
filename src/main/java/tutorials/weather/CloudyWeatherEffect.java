package tutorials.weather;

import tutorials.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloudyWeatherEffect extends Entity implements WeatherEffect {
    private final BufferedImage bufferedImage;
    List<Cloud> clouds = new ArrayList<>();
    Random rand = new Random();

    public CloudyWeatherEffect(String name, BufferedImage bufferedImage) {
        super(name);
        this.bufferedImage = bufferedImage;
        init();
    }

    private void init() {
        double x = rand.nextInt((int) bufferedImage.getWidth());
        double y = rand.nextInt((int) bufferedImage.getHeight() / 2); // nuages en haut du ciel
        double taille = 20 + rand.nextInt(40);
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



