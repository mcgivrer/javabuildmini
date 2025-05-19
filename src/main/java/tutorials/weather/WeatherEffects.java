package tutorials.weather;

import tutorials.Entity;
import tutorials.Season;
import tutorials.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherEffects extends Entity {

    private final World world;
    private List<WeatherEffect> effets = new ArrayList<>();
    private Season season;
    private Random rand = new Random();

    public WeatherEffects(String name, Season saison, World world) {
        super(name);
        this.season = saison;
        this.world = world;
        generateRandomWeatherEffect();
        setPriority(-9);
    }

    public void generateRandomWeatherEffect() {
        effets.clear();
        switch (season) {
            case WINTER:
                //if (rand.nextBoolean()) effets.add(new EffetVent());
                if (rand.nextBoolean()) effets.add(new SnowWeatherEffect(world, 80));
                break;
            case SUMMER:
                //if (rand.nextInt(4) == 0) effets.add(new EffetOrage(800, 600)); // orage rare
                //else if (rand.nextBoolean()) effets.add(new EffetChaleur());
                //if (rand.nextBoolean()) effets.add(new EffetVent());
                break;
            case MID_SEASON:
                if (rand.nextBoolean()) effets.add(new RainWeatherEffect(world, 80));
                //if (rand.nextBoolean()) effets.add(new EffetVent());
                //if (season == Saison.AUTOMNE && rand.nextBoolean()) effets.add(new EffetFeuilles());
                break;
        }
    }

    public void update(int width, int height, double elapsed) {
        for (WeatherEffect effet : effets) effet.update(elapsed);
        effets.removeIf(e -> !e.isActive());
    }

    public void draw(Graphics2D g) {
        for (WeatherEffect effet : effets) effet.draw(g);
    }
}
