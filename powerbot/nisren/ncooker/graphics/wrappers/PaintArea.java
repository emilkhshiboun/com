package com.powerbot.nisren.ncooker.graphics.wrappers;

import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.graphics.wrappers.util.Painter;
import com.powerbot.nisren.ncooker.graphics.wrappers.util.Text;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 27/12/12
 * Time: 19:16
 */
public class PaintArea extends Rectangle implements Painter {

    private Color frameColor;
    private Color areaColor;
    private TextContainer textContainer;
    private Font font;
    private Stroke stroke;

    public PaintArea(int x, int y, int width, int height, Color frameColor, Color areaColor) {
        super(x, y, width, height);
        this.frameColor = frameColor;
        this.areaColor = areaColor;
        textContainer = new TextContainer();
    }

    public TextContainer getTextContainer() {
        return textContainer;
    }

    public void add(Text... texts) {
        textContainer.add(texts);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void paint(Graphics2D g) {
        Stroke stroke1 = g.getStroke();
        if(stroke != null) g.setStroke(stroke);
        g.setColor(frameColor);
        g.draw(this);
        g.setColor(areaColor);
        g.fillRect(x + 1, y + 1, width - 1, height - 1);
        g.setStroke(stroke1);
        Font defaultFont = g.getFont();
        for (Text text : textContainer.get()) {
            if (text.getFont() != null) g.setFont(text.getFont());
            else g.setFont(font != null ? font : defaultFont);
            g.setColor(text.getColor());
            int x = (int) (getX() + text.getX());
            int y = (int) (getY() + text.getY());
            String str = text.getContent();
            if (text.isShadow()) PaintData.drawStringWithShadow(str, x, y, g);
            else g.drawString(str, x, y);
        }
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
}
