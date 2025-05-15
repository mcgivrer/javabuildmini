package tutorials;

import java.awt.*;

public class Star {
    double rayon;
    double angle0; // angle initial en radians
    int taille;
    Color couleur;

    public Star(double rayon, double angle0, int taille, Color couleur) {
        this.rayon = rayon;
        this.angle0 = angle0;
        this.taille = taille;
        this.couleur = couleur;
    }
}