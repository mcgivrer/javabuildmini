package tutorials.weather;

import tutorials.Entity;
import tutorials.InputHandler;
import tutorials.Season;
import tutorials.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherEffects extends Entity {

    private final World world;
    private List<Entity> effets = new ArrayList<>();
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
                if (rand.nextBoolean()) effets.add(new CloudyWeatherEffect("clouds", world));
                if (rand.nextBoolean()) effets.add(new SnowWeatherEffect("snow", world, 80));
                //if (rand.nextBoolean()) effets.add(new WindWeatherEffect("wind",world));
                break;
            case SUMMER:
                //if (rand.nextInt(4) == 0) effets.add(new StormWeatherEffect("storm",world)); // orage rare
                //else if (rand.nextBoolean()) effets.add(new HotWaveWeatherEffect("hotWave",world));
                //if (rand.nextBoolean()) effets.add(new WindWeatherEffect("wind",world));
                break;
            case MID_SEASON:
                if (rand.nextBoolean()) effets.add(new CloudyWeatherEffect("clouds", world));
                if (rand.nextBoolean()) effets.add(new RainWeatherEffect("rain", world, 80));
                //if (rand.nextBoolean()) effets.add(new WindWeatherEffect("wind",world));
                //if (season == Saison.AUTOMNE && rand.nextBoolean()) effets.add(new AutumnFallEffect("autumnFall",world));
                break;
        }
    }

    @Override
    public void update(long elapsed) {
        for (WeatherEffect effet : effets) effet.update(elapsed);
        effets.removeIf(e -> !e.isActive());
        if (InputHandler.isKeyPressed(KeyEvent.VK_1)) {
            effets.add(new CloudyWeatherEffect("clouds", world));
        }
        if (InputHandler.isKeyPressed(KeyEvent.VK_2)) {
            effets.add(new RainWeatherEffect(world, 80));
        }
        if (InputHandler.isKeyPressed(KeyEvent.VK_3)) {
            effets.add(new SnowWeatherEffect(world, 80));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for (WeatherEffect effet : effets) effet.draw(g);
    }
}
