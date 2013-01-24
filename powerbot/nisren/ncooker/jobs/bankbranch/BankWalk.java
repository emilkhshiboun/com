package com.powerbot.nisren.ncooker.jobs.bankbranch;

import com.powerbot.nisren.ncooker.IO.Methods;
import com.powerbot.nisren.ncooker.graphics.io.PaintData;
import com.powerbot.nisren.ncooker.wrappers.state.BankState;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 21/12/12
 * Time: 00:47
 */
public class BankWalk extends Methods {
    @Override
    public boolean activate() {
        return bankState == BankState.WALK;
    }

    @Override
    public void execute() {
        PaintData.debug = "BankWalk";
    }
}
