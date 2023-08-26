package com.darjow.scripts.splasher.actions.leaves;

import com.darjow.framework.decisiontree.Leaf;
import org.dreambot.api.utilities.Logger;

public class LeaveOne extends Leaf {

    @Override
    public void execute() {
        Logger.info("LeaveOne");

    }

    @Override
    public boolean validate() {
        return true;
    }
}
