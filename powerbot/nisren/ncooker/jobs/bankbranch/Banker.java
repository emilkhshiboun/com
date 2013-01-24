package com.powerbot.nisren.ncooker.jobs.bankbranch;

import com.powerbot.nisren.ncooker.IO.Condition;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.wrappers.state.BankState;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:46
 */
public class Banker extends Methods {
    @Override
    public boolean activate() {
        return bankState != BankState.WALK;
    }

    @Override
    public void execute() {
        PaintData.debug = bankState.toString();
        Condition condition;
        switch (bankState) {
            case OPEN:
                PaintData.status = "Checking interfaces...";
                final WidgetChild doneWidget = Widgets.get(WIDGET_PARENT_COOKING, WIDGET_COOKING_CHILD_DONE);
                if (doneWidget != null && doneWidget.validate()) {
                    condition = new Condition() {
                        @Override
                        public long timeOut() {
                            return Random.nextInt(1200, 3500);
                        }

                        @Override
                        public boolean validate() {
                            PaintData.status = "Validting... [7]";
                            return !doneWidget.validate();
                        }
                    };
                    doneWidget.click(true);
                    condition.sleep();
                }
                PaintData.status = "Opening bank...";
                Bank.open();
                break;
            case DEPOSIT:
                Item logs = getLogs();
                if (fish.inInventory(false)) {
                    if (needFire() && logs != null) {
                        depositAllExcept(fish.getId(), logs.getId());
                    } else {
                        depositAllExcept((fish.getId()));
                    }
                } else if (needFire() && logs != null) {
                    depositAllExcept(logs.getId());
                } else {
                    Bank.depositInventory();
                }
                condition = new Condition() {
                    @Override
                    public long timeOut() {
                        return Random.nextInt(1500, 3500);
                    }

                    @Override
                    public boolean validate() {
                        return Inventory.getCount() == fish.getCount(false);
                    }
                };
                condition.sleep();
                break;
            case DEPOSIT_FOR_LOGS:
                condition = new Condition() {
                    @Override
                    public long timeOut() {
                        return Random.nextInt(1500, 3500);
                    }

                    @Override
                    public boolean validate() {
                        return !Inventory.isFull();
                    }

                };
                if (fish.getCount(true) == Inventory.getCount()) {
                    Bank.deposit(fish.getId(), Bank.Amount.ONE);
                    condition.sleep();
                } else {
                    depositAllExcept(fish.getId());
                    condition.sleep();
                }
                break;
            case WITHDRAW:
                final int count = Bank.getItemCount(true, fish.getId());
                Bank.withdraw(fish.getId(), Bank.Amount.ALL);
                condition = new Condition() {
                    @Override
                    public long timeOut() {
                        return Random.nextInt(2500, 3500);
                    }

                    @Override
                    public boolean validate() {
                        return count != Bank.getItemCount(true, fish.getId());
                    }
                };
                condition.sleep();
                break;
            case WITHDRAW_LOGS:
                Item item = Bank.getItem(logsFilter);
                if (item != null) {
                    Bank.withdraw(item.getId(), Bank.Amount.ONE);
                } else {
                    System.out.println("No logs found in bank, disabling firemake feature.");
                    SETTING_FIREMAKE = false;
                }
                break;
            case CLOSE:
                PaintData.status = "Closing bank...";
                PaintData.fishInBank = Bank.getItemCount(true, fish.getId());
                Bank.close();
                condition = new Condition() {
                    @Override
                    public long timeOut() {
                        return Random.nextInt(1000, 2500);
                    }

                    @Override
                    public boolean validate() {
                        return !Bank.isOpen();
                    }
                };
                condition.sleep();
                break;
        }
    }
}
