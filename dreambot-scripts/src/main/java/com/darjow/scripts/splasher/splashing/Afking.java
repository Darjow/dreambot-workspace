package com.darjow.scripts.splasher.splashing;

import com.darjow.framework.decisiontree.components.Leaf;

public class Afking extends Leaf {

    public Afking() {
        super("AFK");
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
