package com.powerbot.nisren.ncooker.jobs;

import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.IO.Settings;
import com.powerbot.nisren.ncooker.graphics.Paint;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.gui.MainFrame;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.util.Timer;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:42
 */
public class Setup extends Methods {
    MainFrame gui;

    @Override
    public boolean activate() {
        return !started;
    }

    @Override
    public void execute() {
        PaintData.debug = "Setup";
        bankState = null;
        cookState = null;
        settings = new Settings();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    gui = new MainFrame();
                    gui.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
        } catch (InvocationTargetException e) {
        }
        while (gui.isVisible()) {
            Task.sleep(150);
        }
        painter = new Paint();
        PaintData.start_time = System.currentTimeMillis();
        PaintData.skillData = new SkillData();
        startTile = Players.getLocal().getLocation();
        screenShotTimer = new Timer(1000 * 60 * 60);
    }
}
