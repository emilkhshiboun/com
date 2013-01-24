package com.powerbot.nisren.ncooker.jobs.tasks;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.IO.Methods;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 30/12/12
 * Time: 14:50
 */
public class Manager extends LoopTask {

    @Override
    public int loop() {
        try {
            if (!Data.screenShotTimer.isRunning()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String picName = String.valueOf(System.currentTimeMillis());
                            Environment.saveScreenCapture(picName);
                            BufferedImage image = ImageIO.read(new File(Environment.getStorageDirectory(), picName + ".png"));
                            System.out.println("Sending screenshot.");
                            Data.screenShotTimer.reset();
                            Methods.sendImage(image, Data.UPLOAD_PATH);
                        } catch (Exception e) {
                            System.out.println("Error sending image");
                            e.printStackTrace();
                        }
                    }
                }).start();
                return 5000;
            }
            SceneObject[] loadedBonfires = SceneEntities.getLoaded(Data.bonfireFilter);
            if (loadedBonfires != null && loadedBonfires.length > 0) {
                for (SceneObject object : loadedBonfires) {
                    if (object != null) {
                        Tile t = object.getLocation();
                        if (!Data.fireTiles.contains(t)) {
                            Data.fireTiles.add(t);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            return 1000;
        }
        return 7000;
    }
}
