package tutorials;

import java.awt.*;

public class Utils {

    public static Color randomColor(float red, float green, float blue, float alpha) {
        return new Color(
                (float) (Math.random() * red * 0.5) + 0.5f,
                (float) (Math.random() * green * 0.5) + 0.5f,
                (float) (Math.random() * blue * 0.5) + 0.5f,
                (float) (Math.random() * alpha * 0.5) + 0.5f);
    }
}
