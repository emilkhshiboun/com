package com.powerbot.nisren.ncooker.jobs.cookbranch;

import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.wrappers.state.CookState;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:48
 */
public class CookWalk extends Methods {
    @Override
    public boolean activate() {
        return cookState == CookState.WALK;
    }

    @Override
    public void execute() {
        PaintData.debug = "CookWalk";
    }
}
