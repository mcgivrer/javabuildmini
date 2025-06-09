package tutorials.core.entity.weather;

import java.awt.*;

public interface WeatherEffect {

    void draw(Graphics2D g);

    void update(long elapsed);

    boolean isActive();
}
