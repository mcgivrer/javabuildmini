package tutorials.core.simulation;

import java.awt.*;

public class Star {
    double rayon;
    double angle; // angle initial en radians
    int taille;
    Color couleur;

    public Star(double rayon, double angle, int taille, Color couleur) {
        this.rayon = rayon;
        this.angle = angle;
        this.taille = taille;
        this.couleur = couleur;
    }
}