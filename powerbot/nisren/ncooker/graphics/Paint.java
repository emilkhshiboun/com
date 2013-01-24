package com.powerbot.nisren.ncooker.graphics;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.graphics.painters.DebugPainter;
import com.powerbot.nisren.ncooker.graphics.painters.InfoPainter;
import com.powerbot.nisren.ncooker.graphics.painters.MousePainter;
import com.powerbot.nisren.ncooker.graphics.wrappers.PaintContainer;
import com.powerbot.nisren.ncooker.graphics.wrappers.util.Painter;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:52
 */
public class Paint {
    PaintContainer paintContainer;

    public Paint() {
        paintContainer = new PaintContainer(new InfoPainter(), new MousePainter());
        if (Data.SETTING_DEBUG) paintContainer.add(new DebugPainter());
    }

    public void paint(Graphics2D g) {
        Color color = g.getColor();
        Font font = g.getFont();
        for (Painter painter : paintContainer.get()) {
            if (painter != null) painter.paint(g);
            g.setColor(color);
            g.setFont(font);
        }
    }
}
