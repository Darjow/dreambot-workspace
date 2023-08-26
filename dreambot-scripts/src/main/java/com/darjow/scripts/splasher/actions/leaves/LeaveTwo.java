package com.darjow.scripts.splasher.actions.leaves;


import com.darjow.framework.decisiontree.Leaf;
import org.dreambot.api.utilities.Logger;

public class LeaveTwo extends Leaf {



    @Override
    public void execute() {
        Logger.log("LeaveTwo");
    }

    @Override
    public boolean validate() {
        return true;
    }
}
