package com.powerbot.nisren.ncooker.graphics.wrappers.util;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 28/12/12
 * Time: 09:42
 */
public abstract class Text implements TextContent {
    private int x;
    private int y;
    private Color color;
    private boolean shadow;
    private Font font;

    public Text(int x, int y, Color color, boolean shadow) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
    }

    public Text(int x, int y) {
        this(x, y, Color.black, false);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }
}
