package com.powerbot.nisren.ncooker.jobs.cookbranch;

import com.powerbot.nisren.ncooker.IO.Condition;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.wrappers.state.CookState;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:48
 */
public class Cook extends Methods {
    int fireAttempt = 0;

    @Override
    public boolean activate() {
        return cookState == CookState.COOK;
    }

    @Override
    public void execute() {
        PaintData.debug = "Cook";
        Condition condition;
        if (isCooking()) return;
        if (Inventory.isItemSelected()) {
            if (Inventory.getSelectedItem().getId() != fish.getId()) {
                PaintData.status = "Unclicking selected item.";
                Inventory.getSelectedItem().getWidgetChild().click(true);
                condition = new Condition() {
                    @Override
                    public long timeOut() {
                        return Random.nextInt(450, 1000);
                    }

                    @Override
                    public boolean validate() {
                        PaintData.status = "Validating... [1]";
                        return !Inventory.isItemSelected();
                    }
                };
                condition.sleep();
                return;
            } else if (bonfire()) {
                final SceneObject bonfire = getBonfire();
                final Widget mainWidget = getMainWidget();
                if (!bonfire.isOnScreen()) {
                    if (Calculations.distanceTo(bonfire) > 7 || fireAttempt > 5) {
                        PaintData.status = "Walking to bonfire...";
                        Walking.walk(bonfire);
                        if (fireAttempt > 5) Camera.turnTo(bonfire, Random.nextInt(0, 20));
                    } else {
                        PaintData.status = "Trying to locate bonfire";
                        Camera.turnTo(bonfire, Random.nextInt(0, 35));
                        fireAttempt++;
                    }
                } else {
                    fireAttempt = 0;
                    PaintData.status = "Interacting with bonfire";
                    boolean interact = bonfire.interact("Use", fish.getName() + " -> Fire");
                    if (interact) {
                        condition = new Condition() {
                            @Override
                            public long timeOut() {
                                return Random.nextInt(2500, 3500);
                            }

                            @Override
                            public boolean validate() {
                                PaintData.status = "Validating... [2]";
                                if (Players.getLocal().isMoving()) timer.reset();
                                return mainWidget.validate();
                            }

                            @Override
                            public void onFinish() {
                                Player[] players = Players.getLoaded();
                                if (players.length > 0 && Random.nextBoolean()) {
                                    PaintData.status = "Antiban - Turning camera to random player";
                                    Camera.turnTo(players[Random.nextInt(0, players.length)]);
                                }
                            }
                        };
                        condition.sleep();
                    }
                }
            } else {
                //TODO interact with range.
            }
        } else {
            PaintData.status = "Selecting item (" + fish.getName() + ")";
            Item rawFish = fish.getItem();
            if (rawFish != null) {
                rawFish.getWidgetChild().click(true);
                condition = new Condition() {
                    @Override
                    public long timeOut() {
                        return Random.nextInt(500, 1500);
                    }

                    @Override
                    public boolean validate() {
                        PaintData.status = "Validting... [3]";
                        return Inventory.isItemSelected() && Inventory.getSelectedItem().getId() == fish.getId();
                    }
                };
                condition.sleep();
            }
        }
    }
}
