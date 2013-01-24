package com.powerbot.nisren.ncooker.graphics.painters;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.graphics.wrappers.PaintArea;
import com.powerbot.nisren.ncooker.graphics.wrappers.util.Text;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.util.Time;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 24/12/12
 * Time: 14:02
 */
public class InfoPainter extends PaintArea {
    private static final Color frameColor = new Color(0, 0, 0, 255);
    private static final Color backgroundColor = new Color(101, 0, 5, 230);
    private static final Color barFillColor = new Color(4, 125, 0, 230);

    public InfoPainter() {
        super(6, 395, 490, 113, frameColor, backgroundColor);
        setFont(new Font("Georgia", Font.BOLD, 12));
        setStroke(new BasicStroke(2));
        int x = 10;
        int y = 30, space = 20;
        add(new Text[]{new Text(x, y, Color.orange, true) {
            @Override
            public String getContent() {
                return "Run Time: " + Time.format(System.currentTimeMillis() - PaintData.start_time);
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Food Cooked: " + PaintData.numberFormat.format(PaintData.foodCooked);
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Food/Hour: " + PaintData.numberFormat.format(PaintData.hourly(PaintData.foodCooked));
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Food burnt: " + PaintData.numberFormat.format(PaintData.foodBurnt);
            }
        }
        });
        x = 170;
        y = 30;
        add(new Text[]{new Text(x, y, Color.orange, true) {
            @Override
            public String getContent() {
                if (Skills.getRealLevel(Skills.COOKING) == 99) {
                    return "Time to Level: 00:00:00";
                }
                return "Time To Level: " + Time.format(PaintData.skillData.timeToLevel(SkillData.Rate.HOUR, Skills.COOKING));
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Exp Gained: " + PaintData.numberFormat.format(PaintData.skillData.experience(Skills.COOKING));
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Exp/Hour: " + PaintData.numberFormat.format(PaintData.skillData.experience(SkillData.Rate.HOUR, Skills.COOKING));
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                if (Skills.getRealLevel(Skills.COOKING) == 99) {
                    return "Exp to Level: 0";
                }
                return "Exp to level: " + PaintData.numberFormat.format(Skills.getExperienceToLevel(Skills.COOKING, Skills.getRealLevel(Skills.COOKING) + 1));
            }

        }
        });
        x = 360;
        y = 30;
        add(new Text[]{new Text(x, y, Color.orange, true) {
            @Override
            public String getContent() {
                return "Level: " + Skills.getRealLevel(Skills.COOKING) + "/" + PaintData.skillData.initialLevels[Skills.COOKING] + " (" + PaintData.skillData.level(Skills.COOKING) + ")";
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Location: " + Data.location.getName();
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {
                return "Fish: " + Data.fish.getName();
            }
        }, new Text(x, y += space, Color.orange, true) {
            @Override
            public String getContent() {

                return "Fish Left: " + PaintData.numberFormat.format((PaintData.fishInBank + Data.fish.getCount(true)));
            }
        }
        });
        x = 10;
        y = 108;
        add(new Text(x, y, Color.orange, true) {
            @Override
            public String getContent() {
                setFont(new Font("Georgia", Font.BOLD, 11));
                return "Status: " + PaintData.status;
            }
        });
        x = 205;
        y = 12;
        add(new
                    Text(x, y, Color.orange, true) {
                        @Override
                        public String getContent() {
                            new Font("Georgia", Font.PLAIN, 8);
                            int level = Skills.getRealLevel(Skills.COOKING) + 1;
                            return (100 - (int) PaintData.getPercentToNextLevel()) + "% To " + (level == 100 ? "200M XP" : "Level " + level);
                        }
                    });
    }

    @Override
    public void paint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
        Rectangle rectangle = new Rectangle(super.x, super.y, super.width, 14);
        g.setColor(Color.black);
        g.draw(rectangle);
        g.setColor(barFillColor);
        rectangle.x += 2;
        rectangle.y += 2;
        rectangle.width -= 2;
        rectangle.height -= 3;
        rectangle.width = (int) (rectangle.width * (PaintData.getPercentToNextLevel() / 100));
        g.fill(rectangle);

    }
}
