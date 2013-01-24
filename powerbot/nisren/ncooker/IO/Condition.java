package com.powerbot.nisren.ncooker.IO;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:28
 */
public abstract class Condition {
    protected Timer timer;

    public abstract long timeOut();

    public abstract boolean validate();

    public void onFinish() {

    }

    public boolean sleep() {
        timer = new Timer(timeOut());
        while (timer.isRunning() && !validate()) {
            Task.sleep(10, 25);
        }
        if (validate()) onFinish();
        return validate();
    }
}
