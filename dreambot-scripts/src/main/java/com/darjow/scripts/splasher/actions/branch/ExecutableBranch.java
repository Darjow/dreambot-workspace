package com.darjow.scripts.splasher.actions.branch;

import com.darjow.framework.decisiontree.Branch;

public class ExecutableBranch extends Branch {


    @Override
    public void execute() {
        System.out.println("Branch: " + this.getClass().getName());
    }

    @Override
    public boolean validate() {
        return true;
    }
}
