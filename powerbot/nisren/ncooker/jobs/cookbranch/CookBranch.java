package com.powerbot.nisren.ncooker.jobs.cookbranch;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.wrappers.state.CookState;
import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:48
 */
public class CookBranch extends Branch {
    public CookBranch() {
        super(new Node[]{new Cook(), new CookWalk(), new WidgetHandler()});
    }

    @Override
    public boolean branch() {
        Data.cookState = getCookState();
        return Data.cookState != null;
    }

    public CookState getCookState() {
        if (Data.fish.inInventory(true) && !Methods.isCooking()) {
            if (Methods.getMainWidget().validate()) {
                return CookState.WIDGET_HANDLER;
            }
            SceneObject object;
            if (!Methods.bonfire()) {
                object = SceneEntities.getNearest(Data.location.getRangeId());
                if (null == object || Calculations.distanceTo(object) > 7) {
                    return CookState.WALK;
                } else {
                    return CookState.COOK;
                }
            } else {
                object = SceneEntities.getNearest(Data.bonfireFilter);
                if (object != null) {
                    return CookState.COOK;
                }
            }
        }
        return null;
    }
}
