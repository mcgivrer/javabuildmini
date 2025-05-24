package tutorials.weather;

import tutorials.Entity;
import tutorials.PhysicType;
import tutorials.Season;
import tutorials.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherEffects extends Entity {

    private final BufferedImage world;
    private List<Entity> effets = new ArrayList<>();
    private Season season;
    private Random rand = new Random();

    public WeatherEffects(String name, Season saison, BufferedImage bufferedImage) {
        super(name);
        this.season = saison;
        this.world = bufferedImage;
        generateRandomWeatherEffect();
        setPriority(-9);
        setType(PhysicType.STATIC);
        setStickToViewport(true);
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
        for (Entity effet : effets) effet.update(elapsed);
        effets.removeIf(e -> !e.isActive());
    }

    @Override
    public void draw(Graphics2D g) {
        for (Entity effet : effets) effet.draw(g);
    }

    public List<Entity> getEffects() {
        return effets;
    }
}
