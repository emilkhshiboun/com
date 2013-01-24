package com.powerbot.nisren.ncooker.IO;

import com.powerbot.nisren.ncooker.wrappers.Fish;
import com.powerbot.nisren.ncooker.wrappers.Location;
import org.powerbot.game.api.methods.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 14:56
 */
public class Settings {
    public static String KEY_FISH = "selectedFish";
    public static String KEY_LOCATION = "selectedLocation";
    HashMap<String, String> map;
    private Properties properties;

    public Settings() {
        properties = new Properties();
        map = new HashMap<>();
        load();
    }

    public String getValue(String key) {
        return map.get(key);
    }

    private File getSettingsFile() {
        File f = new File(Environment.getStorageDirectory(), "settings.ini");
        if (!f.exists()) try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return f;

    }

    public void load() {
        try {
            properties.load(new FileInputStream(getSettingsFile()));
            String value = properties.getProperty(KEY_FISH, Fish.SWORDFISH.getName());
            map.put(KEY_FISH, value);
            value = properties.getProperty(KEY_LOCATION, Location.Bonfire.getName());
            map.put(KEY_LOCATION, value);
        } catch (IOException e) {
        }
    }

    public void save() {
        properties.put(KEY_FISH, Data.fish == null ? "" : Data.fish.getName());
        properties.put(KEY_LOCATION, Data.location == null ? "" : Data.location.getName());
        try {
            File file = getSettingsFile();
            FileWriter fileWriter = new FileWriter(file);
            properties.store(fileWriter, "lol");
        } catch (Exception e) {
            getSettingsFile().deleteOnExit();
        }
    }
}
