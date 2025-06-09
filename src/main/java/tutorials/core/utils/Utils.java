package tutorials.core.utils;

import java.awt.*;

public class Utils {

    public static Color randomColor(float red, float green, float blue, float alpha) {
        return new Color(
                (float) (Math.random() * red * 0.5) + 0.5f,
                (float) (Math.random() * green * 0.5) + 0.5f,
                (float) (Math.random() * blue * 0.5) + 0.5f,
                (float) (Math.random() * alpha * 0.5) + 0.5f);
    }

    public static Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int r = (int) (c1.getRed() * (1 - t) + c2.getRed() * t);
        int g = (int) (c1.getGreen() * (1 - t) + c2.getGreen() * t);
        int b = (int) (c1.getBlue() * (1 - t) + c2.getBlue() * t);
        int a = (int) (c1.getAlpha() * (1 - t) + c2.getAlpha() * t);
        return new Color(r, g, b, a);
    }

}
