package tutorials.core.entity.weather;

import tutorials.core.entity.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cloud extends Entity {
    double x, y; // position centrale
    double vx, vy; // vitesse (pour déplacement/formation)
    double taille;
    float opacite;
    List<ParticleCloud> particules;

    public Cloud(double x, double y, double taille, float opacite) {
        this.x = x;
        this.y = y;
        this.taille = taille;
        this.opacite = opacite;
        this.vx = Math.random() * 0.5 - 0.25;
        this.vy = Math.random() * 0.1 - 0.05;
        particules = new ArrayList<>();
        for (int i = 0; i < 8 + (int) (Math.random() * 6); i++) {
            double px = x + (Math.random() - 0.5) * taille;
            double py = y + (Math.random() - 0.5) * taille / 2;
            double r = taille / 3 + Math.random() * taille / 3;
            particules.add(new ParticleCloud(px, py, r));
        }
    }

    @Override
    public void update(long elapsed) {
        x += vx * elapsed;
        y += vy * elapsed;
        for (ParticleCloud p : particules) {
            p.x += vx* elapsed;
            p.y += vy* elapsed;
        }
        // Optionnel : dissipation progressive
        opacite = Math.max(0, opacite - 0.0002f);
    }

    @Override
    public void draw(Graphics2D g2) {
        for (ParticleCloud p : particules) {
            g2.setColor(new Color(255, 255, 255, (int) (opacite * 255)));
            g2.fillOval((int) (p.x - p.r), (int) (p.y - p.r), (int) (2 * p.r), (int) (2 * p.r));
        }
    }
}

class ParticleCloud {
    double x, y, r;

    public ParticleCloud(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
