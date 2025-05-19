package tutorials;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TextEntity extends Entity {
    private String text;
    private Object value;

    private Color textColor = Color.WHITE;
    private Color shadowColor = Color.BLACK;
    private Font font;
    private TextAlign align = TextAlign.LEFT;

    public TextEntity(String name) {
        super(name);
        setType(PhysicType.STATIC);
        setShape(new Rectangle2D.Double(0, 0, 0, 0));
        setAlpha(1.0f);
    }

    public TextEntity setText(String text) {
        this.text = text;
        return this;
    }

    public TextEntity setValue(Object v) {
        this.value = v;
        return this;
    }

    public TextEntity setTextColor(Color tc) {
        this.textColor = tc;
        return this;
    }

    public TextEntity setShadowColor(Color sc) {
        this.shadowColor = sc;
        return this;
    }

    public TextEntity setFont(Font f) {
        this.font = f;
        return this;
    }

    public String getText() {
        if (value != null && text.contains("%")) {
            return text.formatted(value);
        }
        return text;
    }


    public Color getTextColor() {
        return this.textColor;
    }

    public Color getShadowColor() {
        return this.shadowColor;
    }

    public Font getFont() {
        return font;
    }

    public TextAlign getTextAlign() {
        return align;
    }

    public TextEntity setTextAlign(TextAlign textAlign) {
        this.align = textAlign;
        return this;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        drawText(g, getText(),
                (int) getX() + 2, (int) getY() + 2,
                getShadowColor(),
                getFont(),
                getTextAlign());
        drawText(g, getText(),
                (int) getX(), (int) getY(),
                getTextColor(),
                getFont(),
                getTextAlign());
        setSize(g.getFontMetrics().stringWidth(getText()), g.getFontMetrics().getHeight());
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
