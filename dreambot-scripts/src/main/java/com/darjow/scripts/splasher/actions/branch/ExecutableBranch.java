package com.darjow.scripts.splasher.actions.branch;

import com.darjow.framework.decisiontree.Branch;
import org.dreambot.api.utilities.Logger;

public class ExecutableBranch extends Branch {


    @Override
    public void execute() {
        Logger.info("Branch: " + this.getClass().getName());
    }

    @Override
    public boolean validate() {
        return true;
    }
}
