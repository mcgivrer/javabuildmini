package tutorials;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Renderer {

    private final App app;

    Renderer(App app) {
        this.app = app;
    }

    public void draw(Graphics2D g) {
        g.setRenderingHints(Map.of(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        ));
        JFrame window = app.getWindow();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,window.getWidth(), window.getHeight());

        drawText(g, "Hello World !",
                (int) (window.getWidth() * 0.5),
                (int) (window.getHeight() * 0.5),
                Color.WHITE,
                g.getFont().deriveFont(18.0f),
                TextAlign.CENTER);
    }


    private static void drawText(Graphics2D g, String message, int x, int y, Color c, Font f, TextAlign ta) {
        if (c != null) g.setColor(c);
        if (f != null) g.setFont(f);
        if (ta != null) {
            switch (ta) {
                case LEFT -> x = x;
                case RIGHT -> x = x - g.getFontMetrics().stringWidth(message);
                case CENTER -> x = x - g.getFontMetrics().stringWidth(message) / 2;
            }
        }
        g.drawString(message, x, y);
    }
}
