package com.powerbot.nisren.ncooker.jobs.cookbranch;

import com.powerbot.nisren.ncooker.IO.Condition;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.wrappers.state.CookState;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:49
 */
public class WidgetHandler extends Methods {
    @Override
    public boolean activate() {
        return cookState == CookState.WIDGET_HANDLER;
    }

    @Override
    public void execute() {
        PaintData.debug = "Widget_Handler";
        Condition condition;
        String selectedFish = getMainWidget().getChild(WIDGET_MAIN_CHILD_SELECTED_FISH).getText();
        selectedFish = "Raw " + selectedFish.toLowerCase();
        if (selectedFish.equals(fish.getName())) {
            PaintData.status = "Validting settings";
            int amount = Integer.parseInt(getMainWidget().getChild(WIDGET_MAIN_CHILD_AMOUNT).getText());
            if (amount != fish.getCount(false)) {
                WidgetChild button = Widgets.get(WIDGET_PARENT_SCROLL, WIDGET_SCROLL_CHILD_COOK_AMOUNT);
                int length = button.getChildren().length;
                button = button.getChild(length - 1);
                if (length == 0) {
                    button = Widgets.get(WIDGET_PARENT_SCROLL, WIDGET_SCROLL_CHILD_COOK_AMOUNT_1);
                }
                length = button.getChildren().length;
                button = button.getChild(length - 1);
                if (button != null) {
                    PaintData.status = "Setting fish amount to : " + fish.getCount(false);
                    button.click(true);
                    condition = new Condition() {
                        @Override
                        public long timeOut() {
                            return Random.nextInt(1500, 2500);
                        }

                        @Override
                        public boolean validate() {
                            PaintData.status = "Validting... [4]";
                            int amount = Integer.parseInt(getMainWidget().getChild(WIDGET_MAIN_CHILD_AMOUNT).getText());
                            return amount == fish.getCount(false);
                        }
                    };
                    if (!condition.sleep()) return;
                }
            }
            PaintData.status = "Trying to cook";
            WidgetChild cookButton = getMainWidget().getChild(WIDGET_MAIN_CHILD_COOK);
            cookButton.click(true);
            condition = new Condition() {
                @Override
                public long timeOut() {
                    return Random.nextInt(2000, 4000);
                }

                @Override
                public boolean validate() {
                    PaintData.status = "Validting... [5]";
                    return isCooking();
                }

                @Override
                public void onFinish() {
                    PaintData.status = "Antiban- Moving mouse randomly";
                    Antiban.moveMouseRandomly(200, 750);
                }
            };
            condition.sleep();
        } else {
            PaintData.status = "Wrong fish! Closing interface";
            WidgetChild close = getMainWidget().getChild(WIDGET_MAIN_CHILD_CLOSE);
            close.click(true);
            condition = new Condition() {
                @Override
                public long timeOut() {
                    return Random.nextInt(1000, 2500);
                }

                @Override
                public boolean validate() {
                    PaintData.status = "Validting... [6]";
                    return !getMainWidget().validate();
                }
            };
            condition.sleep();
        }
    }
}
