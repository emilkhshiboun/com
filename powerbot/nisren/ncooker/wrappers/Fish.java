package com.powerbot.nisren.ncooker.wrappers;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 20/12/12
 * Time: 06:32
 */
public enum Fish {
    CRAYFISH("Raw crayfish", 13435, 1),
    SHRIMPS("Raw shrimps", 317, 1),
    SARDINE("Raw sardine", 327, 1),
    ACHOVIES("Raw anchovies", 321, 1),
    HERRING("Raw herring", 345, 5),
    MACHEREL("Raw mackerel", 353, 10),
    TROUT("Raw trout", 335, 15),
    COD("Raw cod", 341, 18),
    PIKE("Raw pike", 349, 20),
    SALMON("Raw salmon", 331, 25),
    TUNA("Raw tuna", 359, 30),
    KARAMBWN("Raw karambwan", 3142, 30),
    RAINBOW("Raw rainbow fish", 0, 35),
    CAVEELL("Raw cave eel", 5001, 38),
    LOBSTER("Raw lobster", 377, 40),
    BASS("Raw bass", 363, 43),
    SWORDFISH("Raw swordfish", 371, 45),
    MONKFISH("Raw monkfish", 7944, 62),
    SHARK("Raw shark", 383, 80),
    SEATURTLE("Raw sea turtle", 395, 82),
    CAVEFISH("Raw cavefish", 15264, 88),
    MONTARAY("Raw manta ray", 389, 91),
    ROCKTAIL("Raw rocktail", 15270, 93);
    private String name;
    private int id, level;

    private Fish(String name, int id, int level) {
        this.name = name;
        this.id = id;
        this.level = level;
    }

    public static Fish getFish(String name) {
        for (Fish fish : values()) {
            if (fish.getName().equals(name)) {
                return fish;

            }
        }
        return null;
    }

    public Item getItem() {
        return Bank.isOpen() ? Bank.getItem(id) : Inventory.getItem(id);
    }

    public boolean inInventory(boolean b) {
        return getCount(b) > 0;
    }

    public int getCount(boolean b) {
        if (b) {
            int c = 0;
            for (Item item : Inventory.getItems(b)) {
                if (item.getId() == id) {
                    c++;
                }
            }
            return c;
        } else {
            return Inventory.getCount(id);
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
