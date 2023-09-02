package com.darjow.scripts.splasher.branches.setup;

import com.darjow.framework.decisiontree.components.Branch;

public class SetupBranch extends Branch {

    public SetupBranch(){}


    @Override
    public String getStatus() {
        return "[Branch] Setting up before splashing.";
    }
}
