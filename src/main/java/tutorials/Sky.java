package tutorials;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Sky extends Entity {
    private World world;
    private Rectangle2D viewport;

    public Sky(String name) {
        super(name);
    }

    public Sky setWorld(World w) {
        this.world = w;
        return this;
    }

    public Sky setViewport(Rectangle2D viewport) {
        this.viewport = viewport;
        return this;
    }

    public void draw(Graphics2D g) {
        int w = (int) viewport.getWidth();
        int h = (int) viewport.getHeight();
        // Calcul des couleurs en fonction du temps
        Color[] couleursCiel = calculerPalette(world.getDayTime());
        float[] positions = {0f, 0.4f, 0.7f, 1f};

        LinearGradientPaint gradient = new LinearGradientPaint(
                new Point(0, 0),
                new Point(0, h),
                positions,
                couleursCiel
        );

        g.setPaint(gradient);
        g.fillRect(0, 0, w, h);

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


    private Color[] interpolatePalette(Color[] palette1, Color[] palette2, float t) {
        Color[] result = new Color[palette1.length];
        for (int i = 0; i < palette1.length; i++) {
            result[i] = Utils.interpolateColor(palette1[i], palette2[i], t);
        }
        return result;
    }

}
