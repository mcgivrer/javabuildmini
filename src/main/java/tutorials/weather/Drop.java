package tutorials.weather;

import java.util.Random;

public class Drop {
    double x, y, dy, dx;

    public Drop(int w, int h, Random rand) {
        x = rand.nextInt(w);
        y = rand.nextInt(h);
        dy = 8 + rand.nextDouble() * 4;
        dx = 2 + rand.nextDouble() * 4;
    }

    public void update(int w, int h, double elapsed) {
        y += dy * elapsed;
        x += dx * elapsed;
        if (y > h || x > w) {
            y = 0;
            x = Math.random() * w;
        }
    }
}

