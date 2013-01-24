package com.powerbot.nisren.ncooker.wrappers;

import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 20/12/12
 * Time: 06:29
 */
public enum Location {
    Alkharid("Al-Kharid", -1, null, null),
    Bonfire("Bonfire", -1, null, null);
    String name;
    int rangeId;
    Tile bankTile, rangeTile;

    private Location(String name, int rangeId, Tile rangeTile, Tile bankTile) {
        this.name = name;
        this.rangeTile = rangeTile;
        this.bankTile = bankTile;
        this.rangeId = rangeId;
    }

    public String getName() {
        return name;
    }

    public int getRangeId() {
        return rangeId;
    }

    public Tile getBankTile() {
        return bankTile;
    }

    public Tile getRangeTile() {
        return rangeTile;
    }

    public static Location getLocation(String name) {
        for (Location location : values()) {
            if (location.getName().equals(name)) {
                return location;
            }
        }
        return null;
    }
}
