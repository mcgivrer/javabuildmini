package tutorials;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarSky extends Entity {

    private final World world;
    List<Star> stars = new ArrayList<>();

    public StarSky(String name, World world, int nbStars) {
        super(name);
        this.world = world;
        initStars(nbStars);
    }


    private void initStars(int nbMaxStars) {
        Random rand = new Random(1234);
        int rayonMax = (int) (world.getWidth() * 0.9); // rayon maximal autour du zénith
        for (int i = 0; i < nbMaxStars; i++) {
            double rayon = 30 + rand.nextDouble() * (rayonMax - 30);
            double angle = rand.nextDouble() * 2 * Math.PI;
            int taille = 1 + rand.nextInt(2);
            Color couleur = new Color(255, 255, 255, 180 + rand.nextInt(75));
            stars.add(new Star(rayon, angle, taille, couleur));
        }
    }

    public void update(double deltaTime) {

    }

    public void draw(Graphics2D g2) {
        float heure = world.getDayTime();
        // t va de 0 (pas d’étoiles) à 1 (ciel entièrement étoilé)
        float t = 0f;
        if (heure >= 21f) t = Math.min(1f, (heure - 20f) / 2f); // 20h à 22h
        else if (heure < 6f) t = 1f;
        else if (heure < 8f) t = 1f - Math.min(1f, (heure - 6f) / 2f); // 6h à 8h disparition progressive

        int starsToDraw = (int) (stars.size() * t);
        Random rand = new Random(1234); // Pour un ciel constant

        int cx = (int) getWidth() / 2; // centre du zénith
        int cy = (int) (getHeight() * 0.35); // zénith (haut du ciel)

        double temps = System.currentTimeMillis() / 5000.0; // secondes

        for (int i = 0; i < starsToDraw; i++) {
            Star e = stars.get(i);
            // vitesse de rotation, ajustable (ici, 0.02 tours/sec)
            double angle = e.angle0 + 0.02 * 2 * Math.PI * temps;
            int x = (int) (cx + e.rayon * Math.cos(angle));
            int y = (int) (cy + e.rayon * Math.sin(angle));
            g2.setColor(e.couleur);
            g2.fillOval(x, y, e.taille, e.taille);
        }
    }

}
