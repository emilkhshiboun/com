package com.powerbot.nisren.ncooker.graphics.wrappers.mouse;

import com.powerbot.nisren.ncooker.graphics.io.PaintData;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 11:59
 */
public class Click {

    private long clickTime, finishTime;
    private double radians;
    private int x, y;

    public Click(int x, int y, long clickTime) {
        this.clickTime = clickTime;
        finishTime = clickTime + 5000;
        radians = (clickTime - PaintData.start_time) / (2000 / (2 * Math.PI))
                % Math.PI;
        this.x = x;
        this.y = y;
    }

    public boolean isUp() {
        return System.currentTimeMillis() > finishTime;
    }

    public void drawTo(Graphics2D g) {
        int alpha = (int) ((finishTime - System.currentTimeMillis()) / 5000.0 * 255);
        if (alpha < 0) {
            return;
        }
        Color c = PaintData.getRandomColor();
        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
        g.drawLine((int) (x + 5 * Math.cos(radians)), (int) (y + 5 * Math.sin(radians)),
                (int) (x + 5 * Math.cos(radians + Math.PI)),
                (int) (y + 5 * Math.sin(radians + Math.PI)));
        g.drawLine((int) (x + 5 * Math.cos(radians + PaintData.HALF_PI)),
                (int) (y + 5 * Math.sin(radians + PaintData.HALF_PI)),
                (int) (x + 5 * Math.cos(radians + PaintData.THREE_HALF_PI)),
                (int) (y + 5 * Math.sin(radians + PaintData.THREE_HALF_PI)));
    }

    public boolean equals(long time) {
        return clickTime == time;
    }
}
