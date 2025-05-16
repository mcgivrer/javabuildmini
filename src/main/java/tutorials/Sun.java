package tutorials;

import tutorials.Entity;
import tutorials.World;

import java.awt.*;
import java.time.LocalDate;

public class Sun extends Entity {

    private World world;
    private double latitude = 45.0; // Latitude de Paris

    public Sun(String name) {
        super(name);
        setSize(80, 80);
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

    private Point getOldSunPosition(int width, int height, float heure) {
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

    private Point getSunPosition(int width, int height, float heure) {
        LocalDate date = LocalDate.now();
        int jourAnnee = date.getDayOfYear();

        // 1. Calcul déclinaison solaire (formule simplifiée)
        double delta = Math.toRadians(23.45 * Math.sin(Math.toRadians(360 * (284 + jourAnnee) / 365.0)));

        // 2. Calcul durée du jour
        double phi = Math.toRadians(latitude);
        double heuresLumiere = 2 * Math.acos(-Math.tan(phi) * Math.tan(delta)) * 12 / Math.PI;

        // 3. Heures lever/coucher dynamiques
        float lever = (float) (12 - heuresLumiere / 2);
        float coucher = (float) (12 + heuresLumiere / 2);

        // 4. Position horizontale et verticale
        if (heure < lever || heure > coucher+2)
            return new Point((int)world.getWidth() / 2, (int)world.getHeight()+100); // Soleil caché

        float progression = (heure - lever) / (coucher - lever);
        int x = (int)world.getWidth() / 2 + (int) (width * 0.3 * Math.sin(progression * Math.PI - Math.PI / 2));
        int y = (int) ((int)world.getHeight() * 1.0 - world.getHeight() * 0.8 * Math.cos(delta) * Math.sin(progression * Math.PI));

        return new Point(x, y);
    }

    @Override
    public void update(long elapsed) {
        float heure = world.getDayTime();
        Point sunPos = getSunPosition((int) world.getWidth(), (int) world.getHeight(), heure);
        this.x = sunPos.x - width * 0.5;
        this.y = sunPos.y - height * 0.5;
    }

    public void draw(Graphics2D g2) {
        float heure = world.getDayTime();
        float brightness = getSunBrightness(heure);
        Color colorHorizon = new Color(255, 200, 80, 220); // Jaune/orangé
        Color colorZenith = new Color(255, 255, 255, 240);
        // Couleur de base du soleil (jaune vif)
        Color sunColor = Utils.interpolateColor(colorHorizon, colorZenith, brightness);

        g2.setColor(sunColor);
        g2.fillOval((int) x, (int) y, (int) width, (int) height);
    }

}
