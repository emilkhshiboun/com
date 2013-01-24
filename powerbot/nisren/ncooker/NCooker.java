package com.powerbot.nisren.ncooker;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.jobs.Setup;
import com.powerbot.nisren.ncooker.jobs.bankbranch.BankBranch;
import com.powerbot.nisren.ncooker.jobs.cookbranch.CookBranch;
import com.powerbot.nisren.ncooker.jobs.tasks.CookingTask;
import com.powerbot.nisren.ncooker.jobs.tasks.Firemake;
import com.powerbot.nisren.ncooker.jobs.tasks.Manager;
import com.powerbot.nisren.ncooker.jobs.tasks.WidgetCloser;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 20/12/12
 * Time: 06:13
 */
@Manifest(authors = {"Nisren"}, description = "AutoCooker", name = "NCooker", version = 0.1)
public class NCooker extends ActiveScript implements PaintListener, MessageListener, MouseMotionListener {
    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;
    private Client client = Context.client();

    public synchronized final void provide(final Node... jobs) {
        for (final Node job : jobs) {
            if (!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    @Override
    public void onStart() {
        log.info("Welcome, " + Environment.getDisplayName() + "!");
        log.info("Please note this script sends progress report (image) every hour to my site");
        log.info("If you are not comfortable with that please stop using my script right now.");
        provide(new Node[]{new Setup(), new WidgetCloser(), new BankBranch(), new Firemake(), new CookBranch(), new CookingTask()});
        getContainer().submit(new Manager());
        Mouse.setSpeed(Mouse.Speed.VERY_FAST);
    }

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 1000;
        }

        if (client != Context.client()) {
            WidgetCache.purge();
            Context.get().getEventManager().addListener(this);
            client = Context.client();
        }
        PaintData.status = "N/A";
        Data.bankState = null;
        Data.cookState = null;
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }
        return 50;
    }

    @Override
    public void onStop() {
        PaintData.debug = "Shutting down";
        Data.settings.save();
        if (System.currentTimeMillis() - PaintData.start_time > (1000 * 60 * 30))
            try {
                BufferedImage image = (BufferedImage) Methods.getImage(Methods.saveScreenshot());
                Methods.sendImage(image, Data.UPLOAD_PATH);
            } catch (Exception e) {
            }
    }

    @Override
    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        if (Data.painter != null) Data.painter.paint(g);
    }

    @Override
    public void messageReceived(MessageEvent m) {
        if (m.getId() == 109) {
            final String message = m.getMessage();
            if (message.contains("manage to") || message.contains("roast a")
                    || message.contains("successfully") || message.contains("cook some")) {
                PaintData.foodCooked++;
            } else if (message.contains("accidentally burn")) {
                PaintData.foodBurnt++;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        PaintData.mousePoint = e.getPoint();
    }
}
