package tutorials;

import tutorials.Entity;
import tutorials.World;

import java.awt.*;

public class Sun extends Entity {

    private World world;

    public Sun(String name) {
        super(name);
    }

    public Sun setWorld(World w) {
        this.world = w;
        return this;
    }

    private float getSunBrightness(float heure) {
        // Le soleil se lève à 6h, culmine à 12h, se couche à 18h
        if (heure < 6f || heure > 18f) return 0f;
        if (heure <= 12f) return (heure - 6f) / 6f;      // 6h -> 12h : 0 -> 1
        else return (18f - heure) / 6f;                  // 12h -> 18h : 1 -> 0
    }

    private Point getSunPosition(int width, int height, float heure) {
        // Paramètres de la "journée"
        float heureLever = 6f;
        float heureCoucher = 21f;
        float dureeJour = heureCoucher - heureLever;

        // Si le soleil est sous l’horizon
        if (heure < heureLever || heure > heureCoucher) {
            // Place le soleil sous l’horizon (ex : en bas du panneau)
            return new Point(width / 2, height + 100); // 100px sous le panneau
        }

        // Progression du soleil dans la journée (0 à 1)
        float t = (heure - heureLever) / dureeJour;

        // Calcul de l’angle (0 = lever, PI = coucher)
        double angle = Math.PI * t;

        // Rayon de la trajectoire (distance verticale maximale)
        int r = (int) (height * 1.0);

        // Position horizontale (centre)
        int x = width / 2;
        // Position verticale : plus bas à l’horizon, plus haut au zénith
        int y = (int) ((height + 100) * 0.95 - r * Math.sin(angle));

        return new Point(x, y);
    }


    public void draw(Graphics2D g2) {
        float heure = world.getDayTime();
        Point sunPos = getSunPosition((int) world.getWidth(), (int) world.getHeight(), heure);
        int radius = 50;
        float brightness = getSunBrightness(heure);
        Color colorHorizon = new Color(255, 200, 80, 220); // Jaune/orangé
        Color colorZenith = new Color(255, 255, 255, 240);
        // Couleur de base du soleil (jaune vif)
        Color sunColor = Utils.interpolateColor(colorHorizon, colorZenith, brightness);

        g2.setColor(sunColor);
        g2.fillOval(sunPos.x - radius, sunPos.y - radius, 2 * radius, 2 * radius);
    }

}
