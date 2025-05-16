package tutorials;

import javax.swing.*;
import java.awt.*;

public class OldTerminalVFX implements VFXDraw {
    private final float lineWeight;
    float factor = 0.3f;

    public OldTerminalVFX(float f, float lineWeight) {
        this.factor = f;
        this.lineWeight = lineWeight;
    }

    public void update(Graphics2D g, JFrame window) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, factor));
        g.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
        Stroke bck = g.getStroke();
        g.setStroke(new BasicStroke(lineWeight));
        for (int y = 0; y < window.getHeight(); y += ((int) (lineWeight * 2))) {

            g.drawLine(0, y, window.getWidth(), y);
        }
        g.setStroke(bck);

    }
}
