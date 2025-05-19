package tutorials.weather;

import java.awt.*;

public interface WeatherEffect {

    void draw(Graphics2D g);

    void update(double elapsed);

    boolean isActive();
}
