package com.darjow.scripts.splasher.actions.leaves;

import com.darjow.framework.decisiontree.Leaf;
import org.dreambot.api.utilities.Logger;

public class LeaveTwoFalse extends Leaf {

    @Override
    public void execute() {
        Logger.error("LeaveTwoFalse");
    }

    @Override
    public boolean validate() {
        return false;
    }
}
