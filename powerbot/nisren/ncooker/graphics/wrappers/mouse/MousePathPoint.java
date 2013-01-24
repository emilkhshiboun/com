package com.powerbot.nisren.ncooker.graphics.wrappers.mouse;

import com.powerbot.nisren.ncooker.graphics.io.PaintData;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 12:01
 */
public class MousePathPoint extends Point {

    private static final long serialVersionUID = 1L;
    private long finishTime;
    private double lastingTime;

    public MousePathPoint(int x, int y, int lastingTime) {
        super(x, y);
        this.lastingTime = lastingTime;
        finishTime = System.currentTimeMillis() + lastingTime;
    }

    public boolean isUp() {
        return System.currentTimeMillis() > finishTime;
    }

    public Color getColor() {
        Color c = PaintData.getRandomColor();
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), toColor(256 * ((finishTime - System.currentTimeMillis()) / lastingTime)));
    }

    private int toColor(double d) {
        return Math.min(255, Math.max(0, (int) d));
    }


}