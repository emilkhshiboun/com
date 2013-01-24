package com.powerbot.nisren.ncooker.IO;

import com.powerbot.nisren.ncooker.wrappers.Location;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.bot.Context;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 05:55
 */
public abstract class Methods extends Data {

    public static boolean needFire() {
        return getBonfire() == null;
    }

    public static Item getLogs() {
        for (Item i : Inventory.getItems(true, logsFilter)) {
            if (i != null) return i;
        }
        return null;
    }

    public static boolean isCooking() {
        return getCookingWidget() == null ? true : getCookingWidget().validate();
    }

    public static Widget getMainWidget() {
        return Widgets.get(WIDGET_PARENT_MAIN);
    }

    public static Widget getCookingWidget() {
        return Widgets.get(WIDGET_PARENT_COOKING);
    }

    public static boolean bonfire() {
        return Location.Bonfire == location;
    }

    public static SceneObject getBonfire() {
        return SceneEntities.getNearest(bonfireFilter);
    }

    public static Image getImage(String name) {
        try {
            return ImageIO.read(new File(Environment.getStorageDirectory(), name + ".png"));
        } catch (IOException e) {
            return null;
        }
    }
    public static String saveScreenshot() {
        String name = String.valueOf(System.currentTimeMillis());
        Environment.saveScreenCapture(name);
        return name;
    }
    public static void sendImage(BufferedImage image, String url) throws IOException {
        if(image == null) throw new NullPointerException();
        System.gc();
        Context.get().getScriptHandler().log.info("Sending screenshot.");
        url += "?user=" + Environment.getDisplayName();
        HttpURLConnection urlC = (HttpURLConnection) new URL(url).openConnection();
        urlC.setDoOutput(true);
        urlC.setRequestMethod("POST");
        OutputStream out = urlC.getOutputStream();
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", b);
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(b.toByteArray()));
        final int ava = in.available();
        for (int i = 0; i < ava; i++) {
            out.write(in.read());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
        reader.close();
        in.close();
        out.close();
        Context.get().getScriptHandler().log.info("Screenshot sent successfully!");
    }

    public static boolean depositAllExcept(final int... id) {
        if (!Bank.isOpen()) return false;
        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                for (int i : id) {
                    if (item.getId() == i) return false;
                }
                return true;
            }
        });
        if (items == null || items.length == 0) return true;
        Item item = items[0];
        if (item != null) {
            Bank.deposit(item.getId(), Bank.Amount.ALL);
            Task.sleep(10, 25);
            return depositAllExcept(id);
        }
        return false;
    }

    /**
     * Created with IntelliJ IDEA.
     * User: Nisren
     * Date: 29/12/12
     * Time: 19:09
     */
    public static class Antiban {

        public static boolean moveMouseRandomly(int minDistance, int maxDistance) {
            if (maxDistance == 0) {
                return false;
            }
            maxDistance = Random.nextInt(minDistance, maxDistance);
            Point p = new Point(getRandomMouseX(maxDistance), getRandomMouseY(maxDistance));
            if (p.x < 1 || p.y < 1) {
                p.x = p.y = 1;
            }
            Mouse.move(p);
            Task.sleep(10, 100);
            if (Random.nextInt(0, 5) == 0) {
                return false;
            }
            return moveMouseRandomly(maxDistance / 2);
        }

        public static boolean moveMouseRandomly(int maxDistance) {
            return moveMouseRandomly(1, maxDistance);
        }

        public static int getRandomMouseX(int maxDistance) {
            Point p = Mouse.getLocation();
            if (Random.nextInt(0, 2) == 0) {
                return p.x - Random.nextInt(0, p.x < maxDistance ? p.x : maxDistance);
            } else {
                return p.x + Random.nextInt(1, (762 - p.x < maxDistance) ? 762 - p.x : maxDistance);
            }
        }

        public static int getRandomMouseY(int maxDistance) {
            Point p = Mouse.getLocation();
            if (Random.nextInt(0, 2) == 0) {
                return p.y - Random.nextInt(0, p.y < maxDistance ? p.y : maxDistance);
            } else {
                return p.y + Random.nextInt(1, (500 - p.y < maxDistance) ? 500 - p.y : maxDistance);
            }
        }
    }
}
