package tutorials;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World extends Entity {
    private Point2D gravity;
    private double friction = 0.99;
    private float dayTime = 0;

    private Color skyColorUpperLeft, skyColorUpperRight, skyColorLowerLeft, skyColorLowerRight;

    List<Star> stars = new ArrayList<>();

    public World(String name, double width, double height) {
        super(name, 0, 0, width, height);
        setGravity(0, 9.81);
        setColor(new Color(0.3f, 0.3f, 0.3f, 0.6f));
        setFillColor(null);
        setType(PhysicType.STATIC);
        setDayTime(ZonedDateTime.now().getHour());
        initStars(100);
    }

    public World() {
        this("world", 640, 480);
    }

    private void initStars(int nbMaxStars) {
        Random rand = new Random(1234);
        int nbEtoiles = 80;
        int rayonMax = (int) (getWidth() * 0.8); // rayon maximal autour du zénith
        for (int i = 0; i < nbEtoiles; i++) {
            double rayon = 30 + rand.nextDouble() * (rayonMax - 30);
            double angle = rand.nextDouble() * 2 * Math.PI;
            int taille = 1 + rand.nextInt(2);
            Color couleur = new Color(255, 255, 255, 180 + rand.nextInt(75));
            stars.add(new Star(rayon, angle, taille, couleur));
        }
    }


    public World setSkyColors(Color cUL, Color cUR, Color cLL, Color cLR) {
        skyColorUpperLeft = cUL;
        skyColorUpperRight = cUR;
        skyColorLowerLeft = cLL;
        skyColorLowerRight = cLR;
        return this;
    }

    public World setGravity(double x, double y) {
        this.gravity = new Point2D.Double(x, y);
        return this;
    }

    public Point2D getGravity() {
        return gravity;
    }

    public double getFriction() {
        return friction;
    }


    public void update(long elapsed) {
        dayTime = (dayTime + elapsed * 0.00167f) % 24;
    }

    public void drawOld2(Graphics2D g2) {
        int w = (int) getWidth();
        int h = (int) getHeight();
// Définir les points de début et de fin du gradient
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, h);

        // Positions des couleurs (fractions) entre 0.0 et 1.0
        float[] fractions = {0.0f, 0.4f, 0.7f, 1.0f};
        Color[] colors = {
                new Color(44, 62, 80),    // Bleu foncé (haut)
                new Color(255, 94, 77),   // Rouge/rose (milieu haut)
                new Color(255, 195, 113), // Orange (milieu bas)
                new Color(255, 255, 153)  // Jaune clair (bas)
        };

        LinearGradientPaint gradient = new LinearGradientPaint(start, end, fractions, colors);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, w, h);
    }

    public void draw(Graphics2D g) {
        int w = (int) getWidth();
        int h = (int) getHeight();
        // Calcul des couleurs en fonction du temps
        Color[] couleursCiel = calculerPalette(dayTime);
        float[] positions = {0f, 0.4f, 0.7f, 1f};

        LinearGradientPaint gradient = new LinearGradientPaint(
                new Point(0, 0),
                new Point(0, h),
                positions,
                couleursCiel
        );

        g.setPaint(gradient);
        g.fillRect(0, 0, w, h);
        drawStars(g, (int) getWidth(), (int) getHeight(), dayTime);
        drawSun(g, dayTime);
    }

    private Color[] calculerPalette(float heure) {
        if (heure < 6f) return paletteNuit();
        else if (heure < 8f) {
            float t = (heure - 6f) / 2f;
            return interpolatePalette(paletteNuit(), paletteAube(heure), t);
        } else if (heure < 18f) {
            float t = (heure - 8f) / 10f;
            return interpolatePalette(paletteAube(heure), paletteJour(), t);
        } else if (heure < 20f) {
            float t = (heure - 18f) / 2f;
            return interpolatePalette(paletteJour(), paletteCoucher(heure), t);
        } else {
            float t = (heure - 20f) / 4f;
            return interpolatePalette(paletteCoucher(heure), paletteNuit(), t);
        }
    }


    private Color hsb(float h, float s, float b) {
        return Color.getHSBColor(h, s, b); // h, s, b entre 0.0 et 1.0
    }

    // Palette de nuit (avant l'aube et après le crépuscule)
    private Color[] paletteNuit() {
        return new Color[]{
                hsb(0.65f, 0.7f, 0.2f),  // Bleu nuit profond
                hsb(0.65f, 0.6f, 0.15f),
                hsb(0.65f, 0.5f, 0.1f),
                hsb(0.65f, 0.4f, 0.08f)
        };
    }

    // Palette de lever du soleil (aube)
    private Color[] paletteAube(float heure) {
        float t = Math.min(1f, Math.max(0f, (heure - 6f) / 2f)); // 6h à 8h
        return new Color[]{
                hsb(0.08f, 0.8f, 0.95f * t + 0.2f * (1 - t)), // Jaune/orangé à bleu nuit
                hsb(0.04f, 0.6f, 0.8f * t + 0.15f * (1 - t)),
                hsb(0.02f, 0.5f, 0.6f * t + 0.1f * (1 - t)),
                hsb(0.65f, 0.4f, 0.3f * t + 0.08f * (1 - t))
        };
    }

    // Palette de plein jour
    private Color[] paletteJour() {
        return new Color[]{
                hsb(0.60f, 0.2f, 1.0f),   // Bleu ciel vif
                hsb(0.58f, 0.2f, 0.85f),
                hsb(0.56f, 0.2f, 0.7f),
                hsb(0.54f, 0.2f, 0.5f)
        };
    }

    // Palette de coucher de soleil
    private Color[] paletteCoucher(float heure) {
        float t = Math.min(1f, Math.max(0f, (heure - 18f) / 2f)); // 18h à 20h
        return new Color[]{
                hsb(0.08f, 0.8f, 0.95f * t + 0.6f * (1 - t)), // Jaune/orangé à bleu ciel
                hsb(0.02f, 0.7f, 0.8f * t + 0.7f * (1 - t)),
                hsb(0.98f, 0.6f, 0.6f * t + 0.8f * (1 - t)),
                hsb(0.65f, 0.4f, 0.3f * t + 0.5f * (1 - t))
        };
    }


    private Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int r = (int) (c1.getRed() * (1 - t) + c2.getRed() * t);
        int g = (int) (c1.getGreen() * (1 - t) + c2.getGreen() * t);
        int b = (int) (c1.getBlue() * (1 - t) + c2.getBlue() * t);
        int a = (int) (c1.getAlpha() * (1 - t) + c2.getAlpha() * t);
        return new Color(r, g, b, a);
    }

    private Color[] interpolatePalette(Color[] palette1, Color[] palette2, float t) {
        Color[] result = new Color[palette1.length];
        for (int i = 0; i < palette1.length; i++) {
            result[i] = interpolateColor(palette1[i], palette2[i], t);
        }
        return result;
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


    private void drawSun(Graphics2D g2, float heure) {
        Point sunPos = getSunPosition((int) getWidth(), (int) getHeight(), heure);
        int radius = 50;
        float brightness = getSunBrightness(heure);
        Color colorHorizon = new Color(255, 200, 80, 220); // Jaune/orangé
        Color colorZenith = new Color(255, 255, 255, 240);
        // Couleur de base du soleil (jaune vif)
        Color sunColor = interpolateColor(colorHorizon, colorZenith, brightness);

        g2.setColor(sunColor);
        g2.fillOval(sunPos.x - radius, sunPos.y - radius, 2 * radius, 2 * radius);
    }

    private void drawStars(Graphics2D g2, int width, int height, float heure) {
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

    private void drawStarsOld(Graphics2D g2, int width, int height, float heure) {
        int nbStars = 100; // nombre total d’étoiles
        // t va de 0 (pas d’étoiles) à 1 (ciel entièrement étoilé)
        float t = 0f;
        if (heure >= 21f) t = Math.min(1f, (heure - 20f) / 2f); // 20h à 22h
        else if (heure < 6f) t = 1f;
        else if (heure < 8f) t = 1f - Math.min(1f, (heure - 6f) / 2f); // 6h à 8h disparition progressive

        int starsToDraw = (int) (nbStars * t);
        Random rand = new Random(1234); // Pour un ciel constant

        for (int i = 0; i < starsToDraw; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt((int) (height * 0.9)); // étoiles sur le haut du ciel
            int r = 1 + rand.nextInt(2); // rayon 1 à 2 px
            Color c = new Color(255, 255, 255, 180 + rand.nextInt(75)); // blanc plus ou moins lumineux
            g2.setColor(c);
            g2.fillOval(x, y, r, r);
        }
    }

    public World setDayTime(float dayTime) {
        this.dayTime = dayTime;
        return this;
    }

    public float getDayTime() {
        return dayTime;
    }
}
