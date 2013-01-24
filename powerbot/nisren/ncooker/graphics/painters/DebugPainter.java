package com.powerbot.nisren.ncooker.graphics.painters;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.graphics.wrappers.PaintArea;
import com.powerbot.nisren.ncooker.graphics.wrappers.util.Text;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 15:35
 */
public class DebugPainter extends PaintArea {
    private static final Color frameColor = new Color(0, 0, 0, 255);
    private static final Color backgroundColor = new Color(8, 0, 230, 225);
    private static final Color fishFillColor = new Color(255, 0, 215, 167);

    public DebugPainter() {
        super(5, 80, 150, 0, frameColor, backgroundColor);
        int x = 5;
        int y = 15;
        int space = 20;
        add(new Text[]{new Text(x, y, Color.orange, false) {
            @Override
            public String getContent() {
                return "Mouse: (" + PaintData.mousePoint.x + "," + PaintData.mousePoint.y + ")";
            }
        }, new Text(x, y += space, Color.orange, false) {
            @Override
            public String getContent() {
                return "State: " + PaintData.debug;
            }
        }
        });

        height = (20 * getTextContainer().get().length);
    }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);
        Item item_ = Data.fish.getItem();
        if (item_ != null && (Bank.isOpen() || (Data.fish.inInventory(true) && Tabs.INVENTORY.isOpen()))) {
            g.setColor(fishFillColor);
            WidgetChild item = item_.getWidgetChild();
            if (item != null) {
                g.fill(item.getBoundingRectangle());
                g.setColor(new Color(0, 0, 0, 180));
                g.draw(item.getBoundingRectangle());
            }
        }
        for (Tile t : Data.fireTiles) {
            if (t != null) {
                g.setColor(Color.green);
                if (SceneEntities.getLoaded(t).length > 0) {
                    g.setColor(Color.red);
                }
                t.draw(g);
            }
        }
    }
}
