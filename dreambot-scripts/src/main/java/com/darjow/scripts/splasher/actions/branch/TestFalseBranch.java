package com.darjow.scripts.splasher.actions.branch;

import com.darjow.framework.decisiontree.Branch;
import org.dreambot.api.utilities.Logger;

public class TestFalseBranch extends Branch {
    @Override
    public void execute() {
        Logger.error("Should not see this branch  " + TestFalseBranch.class.getName()  );
    }

    @Override
    public boolean validate() {
        return false;
    }
}
