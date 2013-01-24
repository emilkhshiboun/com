package com.powerbot.nisren.ncooker.jobs.tasks;

import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import org.powerbot.core.script.job.Task;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 22/12/12
 * Time: 12:39
 */
public class CookingTask extends Methods {
    @Override
    public boolean activate() {
        return isCooking();
    }

    @Override
    public void execute() {
        PaintData.debug = "CookingTask";
        PaintData.status = "Cooking...";
        Task.sleep(10, 250);
    }
}
