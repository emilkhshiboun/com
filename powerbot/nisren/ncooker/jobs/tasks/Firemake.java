package com.powerbot.nisren.ncooker.jobs.tasks;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import org.powerbot.game.api.methods.node.SceneEntities;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 22/12/12
 * Time: 03:43
 */
public class Firemake extends Methods {
    @Override
    public boolean activate() {
        return !isCooking() && SceneEntities.getNearest(bonfireFilter) == null && getLogs() != null && Data.SETTING_FIREMAKE;
    }

    @Override
    public void execute() {
        PaintData.debug = "Firemake";
    }
}
