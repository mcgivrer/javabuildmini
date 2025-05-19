package tutorials.weather;

import java.util.Random;

public class Drop {
    double x, y, vitesseY, vitesseX;

    public Drop(int w, int h, Random rand) {
        x = rand.nextInt(w);
        y = rand.nextInt(h);
        vitesseY = 4 + rand.nextDouble() * 4;
        vitesseX = 1 + rand.nextDouble() * 2;
    }

    public void update(int w, int h, double elapsed) {
        y += vitesseY * elapsed;
        x += vitesseX * elapsed;
        if (y > h || x > w) {
            y = 0;
            x = Math.random() * w;
        }
    }
}

