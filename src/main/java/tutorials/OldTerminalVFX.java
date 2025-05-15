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
        for (int y = 0; y < window.getHeight(); y += 2) {

            g.drawLine(0, y, window.getWidth(), y);
        }

    }
}
