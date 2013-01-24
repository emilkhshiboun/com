package com.powerbot.nisren.ncooker.graphics.painters;

import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.graphics.wrappers.mouse.Click;
import com.powerbot.nisren.ncooker.graphics.wrappers.mouse.MousePathPoint;
import com.powerbot.nisren.ncooker.graphics.wrappers.util.Painter;
import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 12:11
 */
public class MousePainter extends PaintData implements Painter {
    private final LinkedList<MousePathPoint> mousePath = new LinkedList<MousePathPoint>();
    private final LinkedList<Click> clicks = new LinkedList<Click>();

    @Override
    public void paint(Graphics2D g) {
        if (g == null) return;
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(2));
        Point clientCursor = Mouse.getLocation();
        while (!mousePath.isEmpty() && mousePath.peek().isUp()) {
            mousePath.remove();
        }
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y,
                5000);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp)) {
            mousePath.add(mpp);
        }
        MousePathPoint lastPoint = null;
        for (MousePathPoint p : mousePath) {
            if (lastPoint != null) {
                g.setColor(p.getColor());
                g.drawLine(p.x, p.y, lastPoint.x, lastPoint.y);
            }
            lastPoint = p;
        }

        while (!clicks.isEmpty() && clicks.peek().isUp()) {
            clicks.remove();
        }
        long clickTime = Mouse.getPressTime();
        Point lastClickPos = new Point(Mouse.getPressX(), Mouse.getPressY());
        if (clicks.isEmpty() || !clicks.getLast().equals(clickTime)) {
            clicks.add(new Click(lastClickPos.x, lastClickPos.y, clickTime));
        }
        for (Click c : clicks) {
            c.drawTo(g);
        }
        g.setStroke(stroke);
        g.setColor(getRandomColor()); // COLOr.orange
        double radians = (System.currentTimeMillis() - start_time)
                / (2000 / (2 * Math.PI)) % Math.PI;
        g.drawLine((int) Math.round(clientCursor.x + 7 * Math.cos(radians)),
                (int) Math.round(clientCursor.y + 7 * Math.sin(radians)),
                (int) Math.round(clientCursor.x + 7
                        * Math.cos(radians + Math.PI)), (int) Math.round(clientCursor.y + 7
                * Math.sin(radians + Math.PI)));
        g.drawLine((int) Math.round(clientCursor.x + 7
                * Math.cos(radians + HALF_PI)), (int) Math.round(clientCursor.y
                + 7 * Math.sin(radians + HALF_PI)),
                (int) Math.round(clientCursor.x + 7
                        * Math.cos(radians + THREE_HALF_PI)), (int) Math.round(clientCursor.y + 7
                * Math.sin(radians + THREE_HALF_PI)));

        Color c = getRandomColor();
        g.setColor(getRandomColor());
        g.drawOval(clientCursor.x - 8, clientCursor.y - 8, 16, 16);
        g.setColor(getRandomColor());
        g.fillOval(clientCursor.x - 4, clientCursor.y - 4, 8, 8);
        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
        g.fillOval(clientCursor.x - 8, clientCursor.y - 8, 16, 16);
    }

}
