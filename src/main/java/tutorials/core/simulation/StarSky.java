package tutorials.core.simulation;

import tutorials.core.entity.Entity;
import tutorials.core.entity.World;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarSky extends Entity {

    private World world;
    private int nbStars = 100;
    private Rectangle2D viewport;
    List<Star> stars = new ArrayList<>();

    public StarSky(String name, int nbStars) {
        super(name);
        this.nbStars = nbStars;
        setStickToViewport(true);

    }

    public StarSky setWorld(World w) {
        this.world = w;
        return this;
    }

    public StarSky setViewport(Rectangle2D viewport) {
        this.viewport = viewport;
        initStars(nbStars);
        return this;
    }

    private void initStars(int nbMaxStars) {
        Random rand = new Random(1234);
        int rayonMax = (int) (viewport.getWidth()); // rayon maximal autour du zénith
        for (int i = 0; i < nbMaxStars; i++) {
            double rayon = 30 + rand.nextDouble() * (rayonMax - 30);
            double angle = rand.nextDouble() * 2 * Math.PI;
            int taille = 1 + rand.nextInt(2);
            Color couleur = new Color(255, 255, 255, 60 + rand.nextInt(185));
            stars.add(new Star(rayon, angle, taille, couleur));
        }
    }

    @Override
    public void update(long deltaTime) {

    }

    @Override
    public void draw(Graphics2D g2) {
        float heure = world.getDayTime();
        // t va de 0 (pas d’étoiles) à 1 (ciel entièrement étoilé)
        float t = 0f;
        if (heure >= 20.5f) t = Math.min(1f, (heure - 20f) / 2f); // 20h à 22h
        else if (heure < 6f) t = 1f;
        else if (heure < 8f) t = 1f - Math.min(1f, (heure - 6f) / 2f); // 6h à 8h disparition progressive

        int starsToDraw = (int) (stars.size() * t);
        Random rand = new Random(1234); // Pour un ciel constant

        int cx = (int) (viewport.getWidth() * 0.28); // centre du zénith
        int cy = (int) (viewport.getHeight() * 0.35); // zénith (haut du ciel)

        double temps = System.currentTimeMillis() / 10000.0; // secondes

        for (int i = 0; i < starsToDraw; i++) {
            Star e = stars.get(i);
            // vitesse de rotation, ajustable (ici, 0.02 tours/sec)
            double angle = e.angle + 0.02 * 2 * Math.PI * temps;
            int x = (int) (cx + e.rayon * Math.cos(angle));
            int y = (int) (cy + e.rayon * Math.sin(angle));
            g2.setColor(e.couleur);
            g2.fillOval(x, y, e.taille, e.taille);
        }
    }

}
