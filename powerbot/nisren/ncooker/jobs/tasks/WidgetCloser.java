package com.powerbot.nisren.ncooker.jobs.tasks;

import com.powerbot.nisren.ncooker.IO.Condition;
import com.powerbot.nisren.ncooker.IO.Methods;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 15:41
 */
public class WidgetCloser extends Methods {
    public static HashMap<Integer, Integer> data;

    public WidgetCloser() {
        data = new HashMap<>();
        //TODO ADD MORE WIDGETS.
        data.put(594, 215); //REPORT PLAYER
    }

    @Override
    public void execute() {
        final WidgetChild widgetChild = getWidget();
        if(widgetChild.validate()){
            Condition condition = new Condition() {
                @Override
                public long timeOut() {
                    return Random.nextInt(1500, 3500);
                }

                @Override
                public boolean validate() {
                    return !widgetChild.validate();
                }
            };
            widgetChild.click(true);
            condition.sleep();
        }

    }

    @Override
    public boolean activate() {
        return getWidget() != null;
    }

    public WidgetChild getWidget() {
        for (Map.Entry entry : data.entrySet()) {
            Widget widget = Widgets.get((Integer) entry.getKey());
            if (widget != null && widget.validate()) {
                return widget.getChild((Integer) entry.getValue());
            }
        }
        return null;
    }
}
