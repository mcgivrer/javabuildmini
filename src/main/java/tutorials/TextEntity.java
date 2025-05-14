package tutorials;

import java.awt.*;

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

}
