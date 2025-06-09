package tutorials.core.entity.weather;

import java.util.Random;

public class SnowFlake {
    double x, y, dy, amplitude, phase;

    public SnowFlake(int w, int h, Random rand) {
        x = rand.nextInt(w);
        y = rand.nextInt(h);
        dy = 1 + rand.nextDouble() * 2;
        amplitude = 10 + rand.nextDouble() * 10;
        phase = rand.nextDouble() * 2 * Math.PI;
    }

    public void update(int w, int h, long elapsed) {
        y += dy * elapsed;
        x += elapsed * Math.sin(phase + y / 20) * amplitude / 50.0;
        if (y > h) {
            y = 0;
            x = Math.random() * w;
        }
    }
}