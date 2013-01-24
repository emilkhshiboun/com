package com.powerbot.nisren.ncooker.graphics.io;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.SkillData;

import java.awt.*;
import java.text.NumberFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 22/12/12
 * Time: 12:29
 */
public class PaintData {
    public static final double HALF_PI = Math.PI * 0.5;
    public static final double THREE_HALF_PI = Math.PI * 1.5;
    public static int foodCooked = 0;
    public static int foodBurnt = 0;
    public static int fishInBank = 0;
    public static long start_time;
    public static String status = "Starting up";
    public static String debug = "Null";
    public static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    public static SkillData skillData;
    public static Point mousePoint = new Point(-1, -1);

    public static Color getRandomColor() {
        return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public static int hourly(final int data) {
        return (int) (data * SkillData.Rate.HOUR.getTime() / (System.currentTimeMillis() - start_time));
    }

    public static void drawStringWithShadow(final String text, final int x, final int y, final Graphics g) {
        final Color col = g.getColor();
        g.setColor(new Color(0, 0, 0, 255));
        g.drawString(text, x + 1, y + 1);
        g.setColor(col);
        g.drawString(text, x, y);
    }

    public static double getPercentToNextLevel() {
        int level = Skills.getRealLevel(Skills.COOKING);
        if (level == 99) {
            return 100d * (Skills.getExperience(Skills.COOKING) / 200000000d);
        }
        double total = Skills.XP_TABLE[level + 1] - Skills.XP_TABLE[level];
        double exp = Skills.getExperience(Skills.COOKING) - Skills.XP_TABLE[Skills.COOKING];
        return 100 * exp / total;
    }
}
