package com.powerbot.nisren.ncooker.jobs.bankbranch;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.wrappers.state.BankState;
import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.bot.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:46
 */
public class BankBranch extends Branch {
    public BankBranch() {
        super(new Node[]{new Banker(), new BankWalk()});
    }

    @Override
    public boolean branch() {
        Data.bankState = getBankState();
        return Data.bankState != null && !Methods.isCooking();
    }

    public BankState getBankState() {
        if (Methods.isCooking()) return null;
        if (Data.SETTING_FIREMAKE && Methods.needFire() && Methods.getLogs() == null) {
            if (!Bank.isOpen()) {
                return BankState.OPEN;
            }
            if (Inventory.isFull()) return BankState.DEPOSIT_FOR_LOGS;
            else return BankState.WITHDRAW_LOGS;
        } else if (Data.fish.inInventory(false)) {
            if (Bank.isOpen()) return BankState.CLOSE;
            return null;
        } else if (!Bank.isOpen()) {
            if (Bank.getNearest() == null) return BankState.WALK;
            else return BankState.OPEN;
        } else {
            if (Data.fish.getCount(false) != Inventory.getCount()) {
                if (Data.SETTING_FIREMAKE && Methods.needFire() && Methods.getLogs() == null) {
                    return BankState.DEPOSIT_FOR_LOGS;
                }
                if (Methods.needFire() && Methods.getLogs() != null) {
                    if (Inventory.getCount(Data.fish.getId(), Methods.getLogs().getId()) != Inventory.getCount()) {
                        return BankState.DEPOSIT;
                    } else if (!Inventory.isFull()) {
                        return BankState.WITHDRAW;
                    } else {
                        return BankState.CLOSE;
                    }
                }
                return BankState.DEPOSIT;
            } else if (Bank.getItem(Data.fish.getId()) != null) {
                if (Data.SETTING_FIREMAKE && Methods.needFire() && Methods.getLogs() == null) {
                    return BankState.WITHDRAW_LOGS;
                }
                return BankState.WITHDRAW;
            } else {
                System.out.println("Out of food.");
                Context.get().getScriptHandler().shutdown();
                return null;
            }
        }
    }
}

