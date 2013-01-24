package com.powerbot.nisren.ncooker.IO;

import com.powerbot.nisren.ncooker.graphics.Paint;
import com.powerbot.nisren.ncooker.wrappers.Fish;
import com.powerbot.nisren.ncooker.wrappers.Location;
import com.powerbot.nisren.ncooker.wrappers.state.BankState;
import com.powerbot.nisren.ncooker.wrappers.state.CookState;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.node.SceneObjectDefinition;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:33
 */
public abstract class Data extends Node {
    public static final int WIDGET_PARENT_MAIN = 1370;
    public static final int WIDGET_MAIN_CHILD_CLOSE = 32;
    public static final int WIDGET_MAIN_CHILD_COOK = 38;
    public static final int WIDGET_MAIN_CHILD_SELECTED_FISH = 56;
    public static final int WIDGET_MAIN_CHILD_AMOUNT = 73;
    public static final int WIDGET_PARENT_SCROLL = 1371;
    public static final int WIDGET_SCROLL_CHILD_COOK_AMOUNT = 36;
    public static final int WIDGET_SCROLL_CHILD_COOK_AMOUNT_1 = 144;
    public static final int WIDGET_PARENT_COOKING = 1251;
    public static final int WIDGET_COOKING_CHILD_DONE = 54;
    public static final String UPLOAD_PATH = "http://testscript.net84.net/uploader.php";
    public static boolean started = false;
    public static boolean SETTING_FIREMAKE = false;
    public static boolean SETTING_DEBUG = true;
    public static List<Tile> fireTiles = new CopyOnWriteArrayList<>();
    public static Tile startTile;
    public static Timer screenShotTimer;
    public static Settings settings;
    public static Paint painter;
    public static BankState bankState;
    public static CookState cookState;
    public static Fish fish;
    public static Location location;
    public static Filter<SceneObject> bonfireFilter = new Filter<SceneObject>() {
        @Override
        public boolean accept(SceneObject sceneObject) {
            SceneObjectDefinition sd = sceneObject.getDefinition();
            return sd != null && sd.getName().equals("Fire") && Calculations.distance(startTile, sceneObject) < 15;
        }
    };
    public static Filter<Item> logsFilter = new Filter<Item>() {
        @Override
        public boolean accept(Item item) {
            return item.getName().toLowerCase().contains("logs");
        }
    };
}
