package tutorials;

import javax.swing.*;
import java.awt.*;

public class OldTerminalVFX implements VFXDraw {
    float factor = 0.3f;

    public OldTerminalVFX(float f) {
        this.factor = f;
    }

    public void update(Graphics2D g, JFrame window) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, factor));
        g.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
        Stroke bck = g.getStroke();
        g.setStroke(new BasicStroke(2.0f));
        for (int y = 0; y < window.getHeight(); y += 4) {

            g.drawLine(0, y, window.getWidth(), y);
        }
        g.setStroke(bck);

    }
}
