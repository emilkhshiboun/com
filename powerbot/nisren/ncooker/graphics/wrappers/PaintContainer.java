package com.powerbot.nisren.ncooker.graphics.wrappers;

import com.powerbot.nisren.ncooker.graphics.wrappers.util.Painter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 12:16
 */
public class PaintContainer {
    List<Painter> painterList;

    public PaintContainer(Painter... painters) {
        painterList = new LinkedList<>();
        add(painters);
    }

    public void add(Painter... painters) {
        if (painters != null && painters.length > 0) {
            for (Painter painter : painters) {
                painterList.add(painter);
            }
        }
    }

    public void remove(Painter... painters) {
        for (Painter painter : painters) {
            if (painterList.contains(painter)) painterList.remove(painter);
        }
    }

    public Painter[] get() {
        return painterList.toArray(new Painter[painterList.size()]);
    }

    public Painter get(int index) {
        return painterList.get(index);
    }

}
