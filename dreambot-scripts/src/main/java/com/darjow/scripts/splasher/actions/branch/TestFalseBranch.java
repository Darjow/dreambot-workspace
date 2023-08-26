package com.darjow.scripts.splasher.actions.branch;

import com.darjow.framework.decisiontree.Branch;

public class TestFalseBranch extends Branch {
    @Override
    public void execute() {
        System.out.println("Should not see this - branch");
    }

    @Override
    public boolean validate() {
        return false;
    }
}
